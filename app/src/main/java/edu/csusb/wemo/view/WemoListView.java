package edu.csusb.wemo.view;

import android.app.Activity;

import org.fourthline.cling.model.meta.Device;

import edu.csusb.wemo.model.WemoDevice;

/**
 * Created by Josiah on 2/21/2017.
 */

public interface WemoListView {

    void refreshList();
    void updateWemoDevice(WemoDevice updateDevice);
    void addDeviceToList(WemoDevice wemoDevice);
    Activity getContext();

    void onPowerStateChange(String powerState);

    void deleteDevice(WemoDevice device);
}
