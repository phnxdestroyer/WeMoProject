package edu.csusb.wemo.utils;

import android.util.Log;

import edu.csusb.wemo.model.WemoDevice;

/**
 * Created by Josiah on 2/25/2017.
 */

public  class ParseUtils {

    public static void updatePar(String parameters){
        //String[] paramsArray = TextUtils.split(parameters,"|");
        String[] paramsArray = parameters.split("\\|");
        if(paramsArray.length>0){
            for(String val: paramsArray){
                Log.i("WemoParse",val);
            }
        }
    }

    public static void updateWemoDevice(WemoDevice wemoDevice,String parameters){
        String[] paramsArray = parameters.split("\\|");
        if(paramsArray.length>0){
            for(String val: paramsArray){
                Log.i("WemoParse",val);
            }


        }

    }
}
