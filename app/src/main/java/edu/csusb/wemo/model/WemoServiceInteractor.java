package edu.csusb.wemo.model;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.message.header.ServiceTypeHeader;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.model.types.ServiceType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.csusb.wemo.api.client.upnp.WemoRegistryListener;
import edu.csusb.wemo.service.WemoService;

import static org.fourthline.cling.binding.xml.Descriptor.Device.ELEMENT.device;

/**
 * Created by Josiah on 2/21/2017.
 */

public class WemoServiceInteractor {

    Activity context;
    AndroidUpnpService upnpService;
    WemoDeviceChangeListener wemoDeviceChangeListener;
    public boolean isServiceBound = false;

    public WemoServiceInteractor(Activity context, WemoDeviceChangeListener wemoDeviceChangeListener){
        this.context = context;
        this.wemoDeviceChangeListener = wemoDeviceChangeListener;
        startService();

    }


    private WemoRegistryListener registryListener;

    private ServiceConnection serviceConnection =new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("WemoService","onServiceConnected");

            isServiceBound = true;
            upnpService = (AndroidUpnpService) service;
            registryListener = new WemoRegistryListener(context, wemoDeviceChangeListener);
            upnpService.getRegistry().addListener(registryListener);
            searchForWemo();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("WemoService","onServiceDisconnected");
            isServiceBound = false;

        }
    };

    /**
     * upnpService will search for devices and notify the RegistryListener
     */

    public void searchForUpnpDevices(){
        upnpService.getControlPoint().search();
    }

    public void searchForWemo(){
        Log.e("WemoService","searchForWemo");

        upnpService.getControlPoint().search(new ServiceTypeHeader(new ServiceType("Belkin", "basicevent", 1)));
    }

    /**
     * ask for the Registry for what devices it currently is holding
     */
    public void returnPreviousDevices(){
        for (Device device : upnpService.getRegistry().getDevices()) {
            wemoDeviceChangeListener.deviceAdded(device);
        }
    }

    public void startService(){
        context.getApplicationContext().bindService(
                new Intent(context, WemoService.class),
                serviceConnection,
                Context.BIND_AUTO_CREATE
        );
    }

    public void stopListeningToDevices(){
        if (upnpService != null) {
            upnpService.getRegistry().removeListener(registryListener);

        }
        context.getApplicationContext().unbindService(serviceConnection);

    }




    public WemoSubscriptionCallback subscribeInsightParams(final WemoDevice wemoDevice) {
            // WeMo basic event service
            Service service = wemoDevice.device.findService(new ServiceType("Belkin", "insight"));

            WemoSubscriptionCallback insightSub =  new WemoSubscriptionCallback(wemoDevice.device.getDetails().getSerialNumber(), UUID.randomUUID().toString(),  service, 600) {

                @Override
                @SuppressWarnings("unchecked")
                protected void eventReceived(GENASubscription subscription) {
                    String sequence = String.valueOf(subscription.getCurrentSequence().getValue());
                    Log.e("WemoSubscription","Received event from device (switch) {"+ this.deviceId+"} with sequence id {"+sequence+"}");

                    // update device

                    Map<String, StateVariableValue> values = subscription.getCurrentValues();
                    String insight = "null";
                    for (String variable : values.keySet()) {
                        //ActionArgumentValue newArgument = result.get(variable);
                        Log.i("WemoSubscription",variable);
                    }
                    try {
                        insight = String.valueOf(values.get("InsightParams").getValue());
                        WemoInsightSwitch.paramsUpdate(wemoDevice.properties,insight);
                        wemoDeviceChangeListener.deviceUpdate(wemoDevice);
                        //wemoDeviceListener.deviceUpdate(wemoDevice);



                    } catch (NullPointerException e){

                    }
                    Log.i("WemoSubscription","     "+insight);

                }
            };
            this.upnpService.getControlPoint().execute(insightSub);
            return insightSub;

    }

    public void subcribeToBinaryState( WemoDevice wemoDevice) {
        // WeMo basic event service
        Service service = wemoDevice.device.findService(new ServiceType("Belkin", "basicevent"));

        WemoSubscriptionCallback insightSub =  new WemoSubscriptionCallback(wemoDevice.device.getDetails().getSerialNumber(), UUID.randomUUID().toString(),  service, 600) {

            @Override
            @SuppressWarnings("unchecked")
            protected void eventReceived(GENASubscription subscription) {
                String sequence = String.valueOf(subscription.getCurrentSequence().getValue());
                Log.e("WemoSubscription","Received event from device (switch) {"+ this.deviceId+"} with sequence id {"+sequence+"}");

                // update device

                Map<String, StateVariableValue> values = subscription.getCurrentValues();
                for (String variable : values.keySet()) {
                    //ActionArgumentValue newArgument = result.get(variable);
                    Log.i("WemoSubscription",variable);
                }
            }
        };

        this.upnpService.getControlPoint().execute(insightSub);
    }

    public String getPowerState(WemoDevice deviceDisplay){
        Action get = deviceDisplay.device.findService(new ServiceType("Belkin", "basicevent")).getAction("GetBinaryState");

        @SuppressWarnings("unchecked")
        ActionInvocation invocation = new ActionInvocation(get);


        new ActionCallback.Default(invocation,
                upnpService.getControlPoint()).run();

        ActionException anException = invocation.getFailure();
        if (anException != null && anException.getMessage() != null) {
            // TODO Event when ActionFails
            Log.i("WemoService", "      getPowerState:"+" Failure");
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, ActionArgumentValue> result = invocation.getOutputMap();
        Map<String, StateVariableValue> mapToProcess = new HashMap<String, StateVariableValue>();
        StringBuilder stringBuilder = new StringBuilder();
        for (String variable : result.keySet()) {

            ActionArgumentValue newArgument = result.get(variable);
            Log.i("WemoService", "      variable:"+variable+"="+(String)newArgument.getValue());
            stringBuilder.append( (String) newArgument.getValue());

        }

        return stringBuilder.toString();


    }

    public void togglePowerOnOff(WemoDevice deviceDisplay){
        String state = getPowerState(deviceDisplay);
        String toggle = "1";
        if(!state.equals("0")) {
            toggle = "0";
        }
        try {
            Action a = deviceDisplay.device.findService(new ServiceType("Belkin", "basicevent")).getAction("SetBinaryState");
            @SuppressWarnings("unchecked")
            ActionInvocation invo = new ActionInvocation(a);
            invo.setInput("BinaryState",toggle);
            // executeActionInvocation(invocation);

            new ActionCallback.Default(invo,
                    upnpService.getControlPoint()).run();
        } catch (InvalidValueException e ){

        }
    }

}
