package edu.csusb.wemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import org.fourthline.cling.android.FixedAndroidLogHandler;

import java.util.ArrayList;
import java.util.List;

import edu.csusb.wemo.R;


/**
 * @author Christian Bauer
 */
// DOC:CLASS
public class WemoActivity extends AppCompatActivity implements RInterface {
    private android.support.v7.widget.RecyclerView rView;
    private RViewAdapter rAdapter;
    private List<WemoDeviceList> wemoDeviceList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        rAdapter = new RViewAdapter(list,this,this);
        rView.setAdapter(rAdapter);
        // Fix the logging integration between java.util.logging and Android internal logging
        org.seamless.util.logging.LoggingUtil.resetRootHandler(
                new FixedAndroidLogHandler()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    // DOC:SERVICE_BINDING



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }

    @Override
    public void onWemoSwitchClick(WemoDeviceList deviceList) {
        Log.d("bird","up");
    }
    // DOC:MENU


    // DOC:CLASS_END
    // ...
}
