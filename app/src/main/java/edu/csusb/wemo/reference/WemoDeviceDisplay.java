package edu.csusb.wemo.reference;

import android.content.Context;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;

/**
 * Created by Josiah on 2/20/2017.
 */

public class WemoDeviceDisplay {

    public Device device;

    public WemoDeviceDisplay(Context context,Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    // DOC:DETAILS
    public String getDetailsMessage() {
        StringBuilder sb = new StringBuilder();
        if (getDevice().isFullyHydrated()) {
            sb.append(getDevice().getDisplayString());
            sb.append("\n\n");
            for (Service service : getDevice().getServices()) {
                sb.append(service.getServiceType()).append("\n");
            }
        } else {

            /**
             * Add message this message as the info dialog
             *Device details are being discovered, please wait.
             */
            //sb.append(context.getString(R.string.deviceDetailsNotYetAvailable2));
        }
        return sb.toString();
    }
    // DOC:DETAILS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WemoDeviceDisplay that = (WemoDeviceDisplay) o;
        return device.equals(that.device);
    }

    @Override
    public int hashCode() {
        return device.hashCode();
    }

    @Override
    public String toString() {
        String name =
                getDevice().getDetails() != null && getDevice().getDetails().getFriendlyName() != null
                        ? getDevice().getDetails().getFriendlyName()
                        : getDevice().getDisplayString();
        // Display a little star while the device is being loaded (see performance optimization earlier)
        return device.isFullyHydrated() ? name : name + " *";
    }
}