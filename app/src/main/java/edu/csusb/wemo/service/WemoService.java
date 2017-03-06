package edu.csusb.wemo.service;

import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;

import edu.csusb.wemo.api.client.upnp.WemoServiceConfiguration;

/**
 * @author Christian Bauer
 */
// DOC:CLASS
public class WemoService extends AndroidUpnpServiceImpl {

    @Override
    protected UpnpServiceConfiguration createConfiguration() {

        return new WemoServiceConfiguration();

    }
}
// DOC:CLASS