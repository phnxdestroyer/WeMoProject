package edu.csusb.wemo.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.csusb.wemo.R;
import edu.csusb.wemo.model.WemoDevice;
import edu.csusb.wemo.presenter.WemoListPresenterImpl;
import edu.csusb.wemo.view.WemoListView;

/**
 * Created by Josiah on 2/24/2017.
 */

public class WemoFragment extends Fragment implements WemoListView, WemoDeviceClickListener, Toolbar.OnMenuItemClickListener {
    WemoListPresenterImpl wemoListPresenter;
    private android.support.v7.widget.RecyclerView rView;
    private RViewAdapter rAdapter;

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
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.frag_tb);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitle(R.string.app_name);

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
        rAdapter.setHasStableIds(true);
        rView.setItemAnimator(null);
        rView.setAdapter(rAdapter);

    }

    @Override
    public void onDestroy(){
        wemoListPresenter.detachView();
        super.onDestroy();
    }

    //Refresh the List
    @Override
    public void refreshList() {
        wemoListPresenter.refreshList();
    }

    @Override
    public void updateWemoDevice(final WemoDevice updateDevice) {
        getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rAdapter.updateDeviceList(updateDevice);
            }
        });
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

    @Override
    public void onWemoSubscribe(WemoDevice device) {
        wemoListPresenter.subscribeToPowerStateAndInsightParams(device);
    }

    @Override
    public String wemoPowerStatus(WemoDevice device) {
        return wemoListPresenter.getPowerStatus(device);
    }

    //ToolBar Selections
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refreshList();
                return true;
            case R.id.menu_about:
                startActivity(new Intent(getActivity(),AboutActivity.class));
                return true;
        }
        return false;
    }
}
