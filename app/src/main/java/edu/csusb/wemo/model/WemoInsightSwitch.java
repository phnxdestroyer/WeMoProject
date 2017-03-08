package edu.csusb.wemo.model;

import org.fourthline.cling.model.meta.Device;

import java.util.Map;

/**
 * Created by Josiah on 2/25/2017.
 */

public class WemoInsightSwitch  {

    public WemoDevice wemoDevice;
    Map<String,String> properties;
    public static String POWER_STATE= "POWER_STATE";

    /**
     * Unix Stamp
     */
    public static String LAST_TOGGLE_TIMESTAMP= "LAST_TOGGLE_TIMESTAMP";
    /**
     *  Seconds it has been on since getting turned on (0 if off)
     */
    public static String ON_NOW_FOR_SECONDS= "ON_NOW_FOR_SECONDS";
    /**
     *  Seconds it has been on today
     */
    public static String ON_TODAY= "ON_TODAY";
    /**
     *  Seconds on last two weeks
     */
    public static String ON_TOTAL= "ON_TOTAL";
    /**
     *  The period over which averages are calculated
     */
    public static String TIME_WINDOW= "TIME_WINDOW";
    /**
     *  Average power (W)
     */
    public static String AVERAGE_POWER_W= "AVERAGE_POWER_W";
    /**
     * Instantaneous power (mW)
     */
    public static String INSTANT_POWER_MW= "INSTANT_POWER_MW";
    /**
     * Energy used today in mW-minutes
     */
    public static String ENERGY_USED_TODAY_MW_MIN= "ENERGY_USED_TODAY_MW_MIN";
    /**
     * Energy used over past two weeks in mW-minutes
     */
    public static String ENERGY_USED_TOTAL_MW_MIN= "ENERGY_USED_TOTAL_MW_MIN";





    public WemoInsightSwitch(WemoDevice wemoDevice){
        this.wemoDevice = wemoDevice;
        properties = wemoDevice.properties;
    }

    public static void paramsUpdate(Map<String,String> properties, String updateString){
        if(updateString== null){
            return;
        }
        String[] paramsArray = updateString.split("\\|");
        if(paramsArray.length>0){{
            properties.put(POWER_STATE,paramsArray[0]);
            properties.put(LAST_TOGGLE_TIMESTAMP,paramsArray[1]);
            properties.put(ON_NOW_FOR_SECONDS , paramsArray[2]);
            properties.put(ON_TODAY , paramsArray[3]);
            properties.put(ON_TOTAL , paramsArray[4]);
            properties.put(TIME_WINDOW , paramsArray[5]);
            properties.put(AVERAGE_POWER_W , paramsArray[6]);
            properties.put(INSTANT_POWER_MW , paramsArray[7]);
            properties.put(ENERGY_USED_TODAY_MW_MIN , paramsArray[8]);
            properties.put( ENERGY_USED_TOTAL_MW_MIN , paramsArray[9]);
        }}
    }

    public String getPowerState() {
        return  properties.get(WemoInsightSwitch.POWER_STATE);
    }

    public String getLastToggleTimestamp() {
        return  properties.get(WemoInsightSwitch.LAST_TOGGLE_TIMESTAMP);
    }

    public String getOnNowForSeconds() {
        return properties.get(WemoInsightSwitch.ON_NOW_FOR_SECONDS);
    }

    public String getOnToday() {
        return  properties.get(WemoInsightSwitch.ON_TODAY);
    }

    public String getOnTotal() {
        return properties.get(WemoInsightSwitch.ON_TOTAL);
    }

    public String getTimeWindow() {
        return properties.get(WemoInsightSwitch.TIME_WINDOW);
    }

    public String getAveragepowerWatts() {
        return  properties.get(WemoInsightSwitch.AVERAGE_POWER_W);
    }

    public String getInstantPowerMilliWatts() {
        return properties.get(WemoInsightSwitch.INSTANT_POWER_MW);
    }

    public String getEnergyUsedTodayMilliWattsPerMin() {
        return properties.get(WemoInsightSwitch.ENERGY_USED_TODAY_MW_MIN);
    }

    public String getEnergyUsedTotalMilliWattsPerMin() {

        return properties.get(WemoInsightSwitch.ENERGY_USED_TOTAL_MW_MIN);
    }

    public String getSerialNumber(){
        return wemoDevice.device.getDetails().getSerialNumber();
    }

    public WemoInsightProperties getInsightProperties(){
       return new WemoInsightProperties(properties);
    }
    public  Map<String, String> getPropertiesMap(){
            return properties;
        }

}
