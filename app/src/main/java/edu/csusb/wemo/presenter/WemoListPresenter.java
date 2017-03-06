package edu.csusb.wemo.presenter;

import edu.csusb.wemo.model.WemoDevice;
import edu.csusb.wemo.view.WemoListView;

/**
 * Created by Josiah on 2/21/2017.
 */

public interface WemoListPresenter extends Presenter<WemoListView> {


    void initializeDevices();

    void subscribeToPowerState(WemoDevice device);

    void subscribeToPowerStateAndInsightParams(WemoDevice device);

    void toggleButtonClick(WemoDevice device);

    String getPowerStatus(WemoDevice device);
}
