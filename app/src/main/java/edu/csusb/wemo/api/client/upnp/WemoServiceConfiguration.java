package edu.csusb.wemo.api.client.upnp;

import org.fourthline.cling.android.AndroidUpnpServiceConfiguration;
import org.fourthline.cling.binding.xml.DeviceDescriptorBinder;
import org.fourthline.cling.binding.xml.ServiceDescriptorBinder;
import org.fourthline.cling.model.Namespace;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.transport.spi.DatagramProcessor;

/**
 * Created by Josiah on 2/19/2017.
 */

public class WemoServiceConfiguration extends AndroidUpnpServiceConfiguration {


    protected DatagramProcessor createDatagramProcessor() {
        return new WeMoDatagramProcessorImpl();
    }

    /**
     * WeMo fixed device descriptor parser
     */
    @Override
    protected DeviceDescriptorBinder createDeviceDescriptorBinderUDA10() {
        return new WeMoRecoveringUDA10DeviceDescriptorBinderImpl();
    }

    /**
     * WeMo fixed service descriptor parser
     */
    @Override
    protected ServiceDescriptorBinder createServiceDescriptorBinderUDA10() {
        return new WeMoUDA10ServiceDescriptorBinderSAXImpl();
    }

    /**
     * GENA namespace
     */
    @Override
    protected Namespace createNamespace() {
        return new Namespace("/WeMo");
    }

    /**
     * Filter WeMo messages only
     */
    @Override
    public ServiceType[] getExclusiveServiceTypes() {
        return new ServiceType[]{new ServiceType("Belkin", "basicevent"),new ServiceType("Belkin", "insight")};
    }
}
