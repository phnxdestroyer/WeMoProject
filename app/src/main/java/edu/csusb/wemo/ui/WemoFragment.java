package edu.csusb.wemo.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.csusb.wemo.R;
import edu.csusb.wemo.model.WemoDevice;
import edu.csusb.wemo.model.WemoInsightSwitch;
import edu.csusb.wemo.presenter.WemoListPresenterImpl;
import edu.csusb.wemo.view.WemoListView;

/**
 * Created by Josiah on 2/24/2017.
 */

public class WemoFragment extends Fragment implements WemoListView, WemoDeviceClickListener{
    WemoListPresenterImpl wemoListPresenter;
    private android.support.v7.widget.RecyclerView rView;
    private RViewAdapter rAdapter;
    private List<WemoDeviceList> wemoDeviceList;


    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wemoListPresenter = new WemoListPresenterImpl();
        wemoListPresenter.setView(this);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                 Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wemoListPresenter.initializeDevices();

        rView = (RecyclerView) view.findViewById(R.id.rview);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //WemoDeviceList item = new WemoDeviceList();
        //item.setName("Wemo Device 1");
        //item.setDescription("LampDinningHall");
        //WemoDeviceList item2 = new WemoDeviceList();
        //item2.setName("Wemo Device 2");
        //item2.setDescription("NightLightBedRoom");
        List<WemoDevice> list = new ArrayList<>();
       // list.add(item);
       // list.add(item2);
        rAdapter = new RViewAdapter(list,getContext(),this);
        rView.setAdapter(rAdapter);

    }

    @Override
    public void onDestroy(){
        wemoListPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void refreshList() {

    }

    @Override
    public void updateWemoDevice(WemoDevice updateDevice) {
        rAdapter.updateDeviceList(updateDevice);
    }

    @Override
    public void addDeviceToList(final WemoDevice wemoDevice) {
        getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rAdapter.addDevice(wemoDevice);
            }
        });

    }


    @Override
    public Activity getContext(){
        return getActivity();
    }

    @Override
    public void onPowerStateChange(String powerState) {

    }

    @Override
    public void deleteDevice(final WemoDevice device) {
        getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rAdapter.removeDeviceList(device);
            }
        });

    }

    @Override
    public void onWemoSwitchClick(WemoDevice device) {
        Log.d("bird","up");
        wemoListPresenter.toggleButtonClick(device);
    }
}
