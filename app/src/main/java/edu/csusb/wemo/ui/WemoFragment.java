package edu.csusb.wemo.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

public class WemoFragment extends Fragment implements WemoListView{
    WemoListPresenterImpl wemoListPresenter;


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

    }

    @Override
    public void addDeviceToList(WemoDevice wemoDevice) {


    }


    @Override
    public Activity getContext(){
        return getActivity();
    }

    @Override
    public void onPowerStateChange(String powerState) {

    }
}
