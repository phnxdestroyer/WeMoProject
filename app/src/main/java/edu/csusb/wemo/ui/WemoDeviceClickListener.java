package edu.csusb.wemo.ui;

import edu.csusb.wemo.model.WemoDevice;
import edu.csusb.wemo.model.WemoInsightSwitch;

/**
 * Created by Luong Randy on 2/28/2017.
 */

public interface WemoDeviceClickListener {
    void onWemoSwitchClick(WemoDevice device);
}
