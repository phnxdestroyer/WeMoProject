package edu.csusb.wemo.reference;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.FixedAndroidLogHandler;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.header.ServiceTypeHeader;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.transport.Router;
import org.fourthline.cling.transport.RouterException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.csusb.wemo.R;
import edu.csusb.wemo.model.WemoDevice;
import edu.csusb.wemo.model.WemoDeviceChangeListener;
import edu.csusb.wemo.api.client.upnp.WemoRegistryListener;
import edu.csusb.wemo.service.WemoService;
import edu.csusb.wemo.ui.WemoDeviceClickListener;
import edu.csusb.wemo.ui.WemoDeviceList;


/**
 * @author Christian Bauer
 */
// DOC:CLASS
public class MainActivity extends ListActivity implements WemoDeviceChangeListener, WemoDeviceClickListener {

    // DOC:CLASS
    // DOC:SERVICE_BINDING
    private ArrayAdapter<WemoDeviceDisplay> listAdapter;

    private WemoRegistryListener registryListener = new WemoRegistryListener(this,this);

    private AndroidUpnpService upnpService;

    private ServiceConnection serviceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {

           // Toast.makeText(getApplicationContext(), "Service Connected", Toast.LENGTH_SHORT).show();
            upnpService = (AndroidUpnpService) service;

            // Clear the list
            listAdapter.clear();

            // Get ready for future device advertisements
            upnpService.getRegistry().addListener(registryListener);

            // Now add all devices to the list we already know about
            for (Device device : upnpService.getRegistry().getDevices()) {
                deviceAdded(device);
            }

            // Search asynchronously for all devices, they will respond soon
            upnpService.getControlPoint().search(new ServiceTypeHeader(new ServiceType("Belkin", "basicevent", 1)));
            upnpService.getControlPoint().search();
        }

        public void onServiceDisconnected(ComponentName className) {

            Toast.makeText( getApplicationContext(), "onServiceDisconnected", Toast.LENGTH_SHORT).show();
            upnpService = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Fix the logging integration between java.util.logging and Android internal logging
        org.seamless.util.logging.LoggingUtil.resetRootHandler(
                new FixedAndroidLogHandler()
        );
        // Now you can enable logging as needed for various categories of Cling:
        // Logger.getLogger("org.fourthline.cling").setLevel(Level.FINEST);

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        setListAdapter(listAdapter);

        // This will start the UPnP service if it wasn't already started
        getApplicationContext().bindService(
                new Intent(this, WemoService.class),
                serviceConnection,
                Context.BIND_AUTO_CREATE
        );
        Log.e("WemoMain","service started?");

        Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (upnpService != null) {
            upnpService.getRegistry().removeListener(registryListener);
        }
        // This will stop the UPnP service if nobody else is bound to it
        getApplicationContext().unbindService(serviceConnection);
    }
    // DOC:SERVICE_BINDING

    // DOC:MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, R.string.searchLAN).setIcon(android.R.drawable.ic_menu_search);
        // DOC:OPTIONAL
        menu.add(0, 1, 0, R.string.switchRouter).setIcon(android.R.drawable.ic_menu_revert);
        menu.add(0, 2, 0, R.string.toggleDebugLogging).setIcon(android.R.drawable.ic_menu_info_details);
        // DOC:OPTIONAL
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                if (upnpService == null)
                    break;
                Toast.makeText(this, R.string.searchingLAN, Toast.LENGTH_SHORT).show();
                upnpService.getRegistry().removeAllRemoteDevices();
                upnpService.getControlPoint().search();
                break;
            // DOC:OPTIONAL
            case 1:
                if (upnpService != null) {
                    Router router = upnpService.get().getRouter();
                    try {
                        if (router.isEnabled()) {
                            Toast.makeText(this, R.string.disablingRouter, Toast.LENGTH_SHORT).show();
                            router.disable();
                        } else {
                            Toast.makeText(this, R.string.enablingRouter, Toast.LENGTH_SHORT).show();
                            router.enable();
                        }
                    } catch (RouterException ex) {
                        Toast.makeText(this, getText(R.string.errorSwitchingRouter) + ex.toString(), Toast.LENGTH_LONG).show();
                        ex.printStackTrace(System.err);
                    }
                }
                break;
            case 2:
                Logger logger = Logger.getLogger("org.fourthline.cling");
                if (logger.getLevel() != null && !logger.getLevel().equals(Level.INFO)) {
                    Toast.makeText(this, R.string.disablingDebugLogging, Toast.LENGTH_SHORT).show();
                    logger.setLevel(Level.INFO);
                } else {
                    Toast.makeText(this, R.string.enablingDebugLogging, Toast.LENGTH_SHORT).show();
                    logger.setLevel(Level.FINEST);
                }
                break;
            // DOC:OPTIONAL
        }
        return false;
    }
    // DOC:MENU

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(R.string.deviceDetails);




        WemoDeviceDisplay deviceDisplay = (WemoDeviceDisplay)l.getItemAtPosition(position);
        Action a = deviceDisplay.device.findService(new ServiceType("Belkin", "basicevent")).getAction("SetBinaryState");


        Action get = deviceDisplay.device.findService(new ServiceType("Belkin", "basicevent")).getAction("GetBinaryState");

        @SuppressWarnings("unchecked")
        ActionInvocation invocation = new ActionInvocation(get);

        new ActionCallback.Default(invocation,
                upnpService.getControlPoint()).run();

        ActionException anException = invocation.getFailure();
        if (anException != null && anException.getMessage() != null) {
            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
        }

        Map<String, ActionArgumentValue> result = invocation.getOutputMap();
        Map<String, StateVariableValue> mapToProcess = new HashMap<String, StateVariableValue>();
        String s = "0";
        for (String variable : result.keySet()) {
            ActionArgumentValue newArgument = result.get(variable);
             s = (String) newArgument.getValue();
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }



        /*
        ActionCallback callback = new ActionCallback (invocation){
            @Override
            public void success(ActionInvocation invocation) {
                ActionArgumentValue status  = invocation.getOutput("BinaryState");

                if( status != null) {
                    String s = (String) status.getValue();
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void failure(ActionInvocation invocation,
                                UpnpResponse operation,
                                String defaultMsg) {
                System.err.println(defaultMsg);
            }

        };

        upnpService.getControlPoint().execute(callback);
        /*
        ServiceType serviceType = new ServiceType("Belkin", "basicevent");
        Service service = deviceDisplay.device.findService(serviceType);
        Action action = service.getAction("GetBinaryState");

        @SuppressWarnings("unchecked")
        ActionInvocation invocation = new ActionInvocation(action);
       // executeActionInvocation(invocation);

        new ActionCallback.Default(invocation,
                upnpService.getControlPoint()).run();

        ActionException anException = invocation.getFailure();
        if (anException != null && anException.getMessage() != null) {
           // logger.warn(anException.getMessage());
        }

        Map<String, ActionArgumentValue> result = invocation.getOutputMap();
        String s = (String) (invocation.getOutput("BinaryState").getValue());

        */
        String toggle = "1";
        if(!s.equals("0"))

            toggle = "0";


        try {
            @SuppressWarnings("unchecked")
            ActionInvocation invo = new ActionInvocation(a);
            invo.setInput("BinaryState",toggle);
           // executeActionInvocation(invocation);

            new ActionCallback.Default(invo,
                    upnpService.getControlPoint()).run();
        } catch (InvalidValueException e ){

        }

        /*
        upnpService.getControlPoint().execute(new ActionCallback(new ActionInvocation(a)) {
            @Override
            public void success(ActionInvocation actionInvocation) {

            }

            @Override
            public void failure(ActionInvocation actionInvocation, UpnpResponse upnpResponse, String s) {

            }
        })
        */


        dialog.setMessage(deviceDisplay.getDetailsMessage());
        dialog.setButton(
                getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }
        );
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(12);
        super.onListItemClick(l, v, position, id);
    }




    @Override
    public void deviceAdded(final Device device) {
        runOnUiThread(new Runnable() {
            public void run() {
                WemoDeviceDisplay d = new WemoDeviceDisplay(getApplicationContext(),device);
                int position = listAdapter.getPosition(d);
                if (position >= 0) {
                    // Device already in the list, re-set new value at same position
                    listAdapter.remove(d);
                    listAdapter.insert(d, position);
                } else {
                    listAdapter.add(d);
                }
            }
        });
    }

    public void deviceRemoved(final Device device) {
        runOnUiThread(new Runnable() {
            public void run() {
                listAdapter.remove(new WemoDeviceDisplay(getApplicationContext(),device));
            }
        });
    }

    @Override
    public void deviceUpdate(WemoDevice device) {
        //TODO: something here.
    }

    @Override
    public void onWemoSwitchClick(WemoDevice device) {

    }
    // DOC:CLASS_END
    // ...
}
