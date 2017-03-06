package edu.csusb.wemo.model;

import java.util.Map;

/**
 * Created by Josiah on 2/25/2017.
 * UNUSED
 */

public class WemoInsightProperties {
    /**
     *
     */
    static String POWER_STATE_ON = "1";
    static String POWER_STATE_OFF = "0";
    static String POWER_STATE_ON_UNDER_THRESHOLD = "8";

    String POWER_STATE;

    /**
     * Unix Stamp
     */
    public String LAST_TOGGLE_TIMESTAMP;
    /**
     *  Seconds it has been on since getting turned on (0 if off)
     */
    public String ON_NOW_FOR_SECONDS;
    /**
     *  Seconds it has been on today
     */
    public String ON_TODAY;
    /**
     *  Seconds on last two weeks
     */
    public String ON_TOTAL;
    /**
     *  The period over which averages are calculated
     */
    public String TIME_WINDOW;
    /**
     *  Average power (W)
     */
    public String AVERAGE_POWER_W;
    /**
     * Instantaneous power (mW)
     */
    public String INSTANT_POWER_MW;
    /**
     * Energy used today in mW-minutes
     */
    public String ENERGY_USED_TODAY_MW_MIN;
    /**
     * Energy used over past two weeks in mW-minutes
     */
    public String ENERGY_USED_TOTAL_MW_MIN;

    public WemoInsightProperties(String updateString){
        paramsUpdate(updateString);
    }

    public WemoInsightProperties(Map<String, String> properties) {
        POWER_STATE = properties.get(WemoInsightSwitch.POWER_STATE);
        LAST_TOGGLE_TIMESTAMP = properties.get(WemoInsightSwitch.LAST_TOGGLE_TIMESTAMP);
        ON_NOW_FOR_SECONDS = properties.get(WemoInsightSwitch.ON_NOW_FOR_SECONDS);
        ON_TODAY = properties.get(WemoInsightSwitch.ON_TODAY);
        ON_TOTAL = properties.get(WemoInsightSwitch.ON_TOTAL);
        TIME_WINDOW = properties.get(WemoInsightSwitch.TIME_WINDOW);
        AVERAGE_POWER_W = properties.get(WemoInsightSwitch.AVERAGE_POWER_W);
        INSTANT_POWER_MW = properties.get(WemoInsightSwitch.INSTANT_POWER_MW);
        ENERGY_USED_TODAY_MW_MIN = properties.get(WemoInsightSwitch.ENERGY_USED_TODAY_MW_MIN);
        ENERGY_USED_TOTAL_MW_MIN = properties.get(WemoInsightSwitch.ENERGY_USED_TOTAL_MW_MIN);
    }


    public void paramsUpdate(String updateString){
        if(updateString== null){
            return;
        }
        String[] paramsArray = updateString.split("\\|");
        if(paramsArray.length>0){{
            POWER_STATE = paramsArray[0];
            LAST_TOGGLE_TIMESTAMP = paramsArray[1];
            ON_NOW_FOR_SECONDS = paramsArray[2];
            ON_TODAY = paramsArray[3];
            ON_TOTAL = paramsArray[4];
            TIME_WINDOW = paramsArray[5];
            AVERAGE_POWER_W = paramsArray[6];
            INSTANT_POWER_MW = paramsArray[7];
            ENERGY_USED_TODAY_MW_MIN = paramsArray[8];
            ENERGY_USED_TOTAL_MW_MIN = paramsArray[9];
        }}
    }

    public String getLastToggleTimestamp() {
        return LAST_TOGGLE_TIMESTAMP;
    }

    public String getOnNowForSeconds() {
        return ON_NOW_FOR_SECONDS;
    }

    public String getOnToday() {
        return ON_TODAY;
    }

    public String getOnTotal() {
        return ON_TOTAL;
    }

    public String getTimeWindow() {
        return TIME_WINDOW;
    }

    public String getAveragepowerWatts() {
        return AVERAGE_POWER_W;
    }

    public String getInstantPowerMilliWatts() {
        return INSTANT_POWER_MW;
    }

    public String getEnergyUsedTodayMilliWattsPerMin() {
        return ENERGY_USED_TODAY_MW_MIN;
    }

    public String getEnergyUsedTotalMilliWattsPerMin() {
        return ENERGY_USED_TOTAL_MW_MIN;
    }




    public void setLastToggleTimestamp(String LAST_TOGGLE_TIMESTAMP) {
         this.LAST_TOGGLE_TIMESTAMP = LAST_TOGGLE_TIMESTAMP;
    }

    public void setOnNowForSeconds(String ON_NOW_FOR_SECONDS) {
        this.ON_NOW_FOR_SECONDS = ON_NOW_FOR_SECONDS;
    }

    public void setOnToday(String ON_TODAY) {
        this.ON_TODAY =ON_TODAY ;
    }

    public void setOnTotal(String ON_TOTAL) {
        this.ON_TOTAL = ON_TOTAL;
    }

    public void setTimeWindow(String TIME_WINDOW) {
        this.TIME_WINDOW = TIME_WINDOW;
    }

    public void setAveragepowerWatts(String AVERAGE_POWER_W) {
        this.AVERAGE_POWER_W = AVERAGE_POWER_W;
    }

    public void setInstantPowerMilliWatts(String INSTANT_POWER_MW) {
        this.INSTANT_POWER_MW = INSTANT_POWER_MW;
    }

    public void setEnergyUsedTodayMilliWattsPerMin(String ENERGY_USED_TODAY_MW_MIN) {
        this.ENERGY_USED_TODAY_MW_MIN = ENERGY_USED_TODAY_MW_MIN;
    }

    public void setEnergyUsedTotalMilliWattsPerMin(String ENERGY_USED_TOTAL_MW_MIN) {
        this.ENERGY_USED_TOTAL_MW_MIN = ENERGY_USED_TOTAL_MW_MIN;
    }
    
    
    
}
