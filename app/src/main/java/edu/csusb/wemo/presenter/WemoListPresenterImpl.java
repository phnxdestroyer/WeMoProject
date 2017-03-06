package edu.csusb.wemo.presenter;

import android.util.Log;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.meta.StateVariable;

import java.util.List;

import edu.csusb.wemo.model.WemoDevice;
import edu.csusb.wemo.model.WemoDeviceChangeListener;
import edu.csusb.wemo.model.WemoServiceInteractor;
import edu.csusb.wemo.view.WemoListView;

/**
 * Created by Josiah on 2/21/2017.
 */

public class WemoListPresenterImpl implements WemoListPresenter, WemoDeviceChangeListener {
    WemoListView wemoListView;
    WemoServiceInteractor wemoServiceInteractor;
    List<WemoDevice> wemoList;
    public WemoListPresenterImpl(){

    }

    @Override
    public void setView(WemoListView view) {
        wemoListView = view;
    }

    @Override
    public void detachView() {
     wemoListView = null;
        wemoServiceInteractor.stopListeningToDevices();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onPause() {
        wemoServiceInteractor.stopListeningToDevices();
    }


    @Override
    public void initializeDevices() {
        wemoServiceInteractor = new WemoServiceInteractor(wemoListView.getContext(),this);
    }



    /**
     * If device is a wemo device, turn it into a WemoDevice, maybe should have a seperate interface
     * @param device
     */
    @Override
    public void deviceAdded(Device device) {
        Log.e("WemoList","deviceAdded");
        if(device.isFullyHydrated()){
            for(Service service:device.getServices()) {
                //String s = service.getServiceType().toFriendlyString();
                Log.i("WemoList", "      "+service.getServiceType().toFriendlyString()+"|"+service.getServiceId());
                for(StateVariable stateVariable:service.getStateVariables()) {
                    Log.i("WemoList","            "+stateVariable.getName());
                }

            }
            device.getServices();
           // getPowerStatus(new WemoDeviceDisplay(null,device));
           // toggleButtonClick(new WemoDeviceDisplay(null,device));

            WemoDevice wemoDevice = new WemoDevice(device);
            wemoServiceInteractor.subscribeInsightParams(wemoDevice);
           // toggleButtonClick(wemoDevice);
            wemoListView.addDeviceToList(wemoDevice);
        }

    }

    @Override
    public void subscribeToPowerState(WemoDevice device){
         wemoServiceInteractor.subcribeToBinaryState(device);
    }

    @Override
    public void subscribeToPowerStateAndInsightParams(WemoDevice device){
         wemoServiceInteractor.subscribeInsightParams(device);
    }

    @Override
    public void toggleButtonClick(WemoDevice device){
        wemoListView.onPowerStateChange(getPowerStatus(device));
        wemoServiceInteractor.togglePowerOnOff(device);
    }
    @Override
    public String getPowerStatus(WemoDevice device){
        return wemoServiceInteractor.getPowerState(device);
    }

    @Override
    public void deviceRemoved(Device device) {
        Log.e("WemoList","deviceRemoved");
    }

    @Override
    public void deviceUpdate(WemoDevice updateDevice) {
        wemoListView.updateWemoDevice(updateDevice);
        //wemoViewList.

        /*
        for(int index = 0 ; index < wemoList.size(); index++){
            WemoDevice wemoDevice = wemoList.get(index);
            if(wemoDevice.device.getDetails().getSerialNumber() == updateDevice.device.getDetails().getSerialNumber()){
                wemoList.set(index,wemoDevice);
                wemoListView.updateWemoDevice();
            }
        }
        */
    }
}
