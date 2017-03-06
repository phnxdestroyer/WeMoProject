package edu.csusb.wemo.api.client.upnp;

import android.app.Activity;
import android.widget.Toast;

import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

import edu.csusb.wemo.model.WemoDeviceChangeListener;

/**
 * Created by Josiah on 2/20/2017.
 */

public class WemoRegistryListener extends DefaultRegistryListener  {

    Activity context;
    WemoDeviceChangeListener wemoDeviceChangeListener;
    public WemoRegistryListener(Activity context, WemoDeviceChangeListener wemoDeviceChangeListener){
        this.wemoDeviceChangeListener = wemoDeviceChangeListener;
        this.context = context;
    }
    /* Discovery performance optimization for very slow Android devices! */
    @Override
    public void remoteDeviceDiscoveryStarted(Registry registry, final RemoteDevice device) {
        context.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(
                        context,
                        "Discovery failed of '" + device.getDisplayString() + "': "
                        ,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
        wemoDeviceChangeListener.deviceAdded(device);
    }

    @Override
    public void remoteDeviceDiscoveryFailed(Registry registry, final RemoteDevice device, final Exception ex) {
        context.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(
                        context,
                        "Discovery failed of '" + device.getDisplayString() + "': "
                                + (ex != null ? ex.toString() : "Couldn't retrieve device/service descriptors"),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
        wemoDeviceChangeListener.deviceRemoved(device);
    }
        /* End of optimization, you can remove the whole block if your Android handset is fast (>= 600 Mhz) */

    @Override
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
        wemoDeviceChangeListener.deviceAdded(device);
    }

    @Override
    public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
        wemoDeviceChangeListener.deviceRemoved(device);
    }

    @Override
    public void localDeviceAdded(Registry registry, LocalDevice device) {
        wemoDeviceChangeListener.deviceAdded(device);
    }

    @Override
    public void localDeviceRemoved(Registry registry, LocalDevice device) {
        wemoDeviceChangeListener.deviceRemoved(device);
    }


}