package edu.csusb.wemo.model;

import org.fourthline.cling.model.meta.Device;

/**
 * Created by Josiah on 2/20/2017.
 */

public interface WemoDeviceChangeListener {
    void deviceAdded(final Device device);
    void deviceRemoved(final Device device);
    void deviceUpdate(WemoDevice device);

}
