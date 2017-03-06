package edu.csusb.wemo.model;

import android.util.Log;

import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Service;

/**
 * Created by Josiah on 2/23/2017.
 */

@SuppressWarnings("unused")
public abstract class WemoSubscriptionCallback extends SubscriptionCallback {

    // Logger


    protected final String deviceId;              // Device Id
    protected final String subscriptionId;        // Subscription Id// Storage

    protected WemoSubscriptionCallback(String deviceId, String subscriptionId, Service service) {
        super(service);
        this.deviceId = deviceId;
        this.subscriptionId = subscriptionId;

    }

    protected WemoSubscriptionCallback(String deviceId, String subscriptionId, Service service, int requestedDurationSeconds) {
        super(service, requestedDurationSeconds);
        this.deviceId = deviceId;
        this.subscriptionId = subscriptionId;

    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    @Override
    protected void failed(GENASubscription subscription, UpnpResponse responseStatus, Exception exception, String defaultMsg) {
        Log.e("WemoSubscription","Device {"+ this.deviceId+"} subscription {"+ this.subscriptionId+"} failed: {"+ defaultMsg+"}");
        checkAndEmptyGENA();
    }

    @Override
    protected void established(GENASubscription subscription) {
        Log.e("WemoSubscription","Device {"+ this.deviceId+"} subscription {"+ this.subscriptionId+"}");

    }

    @Override
    protected void ended(GENASubscription subscription, CancelReason reason, UpnpResponse responseStatus) {
        Log.e("WemoSubscription","Device {"+ this.deviceId+"} subscription {"+ this.subscriptionId+"} reason: {"+ reason+"}");

        checkAndEmptyGENA();
    }

    @Override
    protected void eventsMissed(GENASubscription subscription, int numberOfMissedEvents) {
        Log.e("WemoSubscription","Device {"+ this.deviceId+"} subscription {"+ this.subscriptionId+"} numberOfMissedEvents: {"+ numberOfMissedEvents+"}");

    }

    /**
     * Check if subscription id is the same then set to empty
     */
    protected void checkAndEmptyGENA() {
       // if (this.subscriptionId.equals(this.storage.getDeviceGENA(this.deviceId, WeMoScheme.GENA_ID))) {
          //  this.storage.updateDeviceGENA(this.deviceId, "");
         //   logger.debug("Clear device {} subscription {}", this.deviceId, this.subscriptionId);
        }

}