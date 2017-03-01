package edu.csusb.wemo;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.csusb.wemo.RecyclerView.RInterface;
import edu.csusb.wemo.RecyclerView.RViewAdapter;
import edu.csusb.wemo.RecyclerView.WemoDeviceList;

public class MainActivity extends AppCompatActivity implements RInterface{
    private android.support.v7.widget.RecyclerView rView;
    private RViewAdapter adapter;
    private List<WemoDeviceList> wemoDeviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rView = (RecyclerView) findViewById(R.id.rview);
        rView.setLayoutManager(new LinearLayoutManager(this));

        WemoDeviceList item = new WemoDeviceList();
        item.setName("Wemo Device 1");
        item.setDescription("LampDinningHall");
        WemoDeviceList item2 = new WemoDeviceList();
        item2.setName("Wemo Device 2");
        item2.setDescription("NightLightBedRoom");
        List<WemoDeviceList> list = new ArrayList<>();
        list.add(item);
        list.add(item2);

        adapter = new RViewAdapter(list,this,this);
        rView.setAdapter(adapter);
    }


    @Override
    public void onWemoSwitchClick(WemoDeviceList deviceList) {
        Log.d("bird","up");
    }

    public void onUpdateDevice(WemoDeviceList deviceList){
        adapter.updateDeviceList(deviceList);
    }

}
