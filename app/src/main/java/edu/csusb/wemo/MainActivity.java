package edu.csusb.wemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import edu.csusb.wemo.RecyclerView.RViewAdapter;
import edu.csusb.wemo.RecyclerView.WemoDeviceList;
import edu.csusb.wemo.RecyclerView.feed;

import static edu.csusb.wemo.R.styleable.RecyclerView;

public class MainActivity extends AppCompatActivity {
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
        item.setName("bird");
        item.setDescription("dinosaurs are birds");
        WemoDeviceList item2 = new WemoDeviceList();
        item.setName("birds");
        item.setDescription("dinosaurs are birdss");
        List<WemoDeviceList> list = new ArrayList<>();
        list.add(item);
        list.add(item2);

        adapter = new RViewAdapter(list,this);
        rView.setAdapter(adapter);
    }



}
