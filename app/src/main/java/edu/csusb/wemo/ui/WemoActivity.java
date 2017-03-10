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
public class WemoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    // DOC:MENU


    // DOC:CLASS_END
    // ...
}
