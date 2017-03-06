package edu.csusb.wemo.model;

import org.fourthline.cling.model.meta.Device;

import java.util.Map;

/**
 * Created by Josiah on 2/25/2017.
 */

public class WemoDevice  {
    public Map<String,String> properties;

    public Device device;

    public WemoDevice(Device device) {
        this.device = device;
    }
}
