package edu.csusb.wemo.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import org.fourthline.cling.model.meta.Device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Josiah on 2/25/2017.
 */

public class WemoDevice implements Parent<WemoDevice> {
    public Map<String,String> properties;

    public Device device;

    public WemoDevice(Device device) {
        this.device = device;
        this.properties = new HashMap<>();
    }


    @Override
    public List<WemoDevice> getChildList() {
        return null;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
