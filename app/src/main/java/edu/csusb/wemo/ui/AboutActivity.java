package edu.csusb.wemo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import edu.csusb.wemo.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    //Create icons for action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //  getMenuInflater().inflate(R.menu.menu_about,menu);
        return true;
    }

    //Code for choosing action bar item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //startActivity(new Intent(this,WemoActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
