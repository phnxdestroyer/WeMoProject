package edu.csusb.wemo.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.silencedut.expandablelayout.ExpandableLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.csusb.wemo.R;
import edu.csusb.wemo.model.WemoDevice;
import edu.csusb.wemo.model.WemoInsightSwitch;

/**
 * Created by Luong Randy on 2/24/2017.
 */

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.CustomViewHolder> {
    private List<WemoDevice> wemoDeviceLists;
    private Context mContext;
    private WemoDeviceClickListener wemoDeviceClickListener;
    private Map<WemoDevice,LineGraphSeries<DataPoint>> graphs = new HashMap<>();

    public RViewAdapter(List<WemoDevice> wemoDeviceLists, Context mContext, WemoDeviceClickListener wemoDeviceClickListener) {
        this.wemoDeviceLists = wemoDeviceLists;
        this.mContext = mContext;
        this.wemoDeviceClickListener = wemoDeviceClickListener;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("RViewAdapter","onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wemo_device_options,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        Log.e("RViewAdapter","onBindViewHolder");
        final WemoDevice device = wemoDeviceLists.get(position);
        final WemoInsightSwitch insignSwitch =  new WemoInsightSwitch(device);
        // holder.descriptionView.setText(device.getWemoDescription());
        holder.nameView.setText(insignSwitch.wemoDevice.device.getDisplayString());

        if(graphs.containsKey(device)){
            Log.e("RViewAdapter","graphs.containsKey(device)=true");
            DataPoint v = new DataPoint(Double.valueOf(insignSwitch.getOnNowForSeconds()),Double.valueOf(insignSwitch.getInstantPowerMilliWatts()));
            //DataPoint t = new DataPoint(Double.valueOf(insignSwitch.getOnTotal()),Double.valueOf(insignSwitch.getInstantPowerMilliWatts()));
            graphs.get(device).appendData(v,true,50);

            holder.updateGraph(insignSwitch.getOnNowForSeconds(),insignSwitch.getInstantPowerMilliWatts(),v);

        } else {
            Log.e("RViewAdapter","graphs.containsKey(device)=false");
            graphs.put(device,new LineGraphSeries<DataPoint>());
            holder.addGraph(insignSwitch.getOnNowForSeconds(),insignSwitch.getInstantPowerMilliWatts(),graphs.get(device));
        }

        View.OnClickListener buttonHide = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("RViewAdapter","buttonHide");
                holder.editButtonHide.setVisibility(View.GONE);
                holder.expandableLayout.setExpand(false);


            }
        };
        final View.OnClickListener buttonShow = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("RViewAdapter","buttonShow");
                //holder.editButtonHide.setVisibility(View.VISIBLE);
                //holder.expandableLayout.setExpand(true);
                /*
                if(holder.expandableLayout.isExpanded()){
                    holder.expandableLayout.setExpand(false);
                }
                */


                wemoDeviceClickListener.onWemoSubscribe(device);


                //wemoDeviceClickListener.onWemoEditClick(deviceList);
            }
        };
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //switch on off
                Log.e("RViewAdapter","switch");

                //holder.editDescription.setText(insignSwitch.getPowerState());
                wemoDeviceClickListener.onWemoSwitchClick(device);
                holder.updateOnDeviceOff(device,insignSwitch);
            }
        };

        holder.powerSwitch.setOnClickListener(listener);
        holder.editButtonHide.setOnClickListener(buttonHide);
        holder.editButtonShow.setOnClickListener(buttonShow);
        holder.expandableLayout.setOnClickListener(buttonShow);
        holder.updateDescription(insignSwitch.getAveragepowerWatts());
        holder.updateAveragePower(insignSwitch.getAveragepowerWatts());
        holder.updateCurrentPower(insignSwitch.getInstantPowerMilliWatts());
        holder.updateTimeLastOn(insignSwitch.getLastToggleTimestamp(),1);
        holder.updateTimeOnDuration(insignSwitch.getOnNowForSeconds());
        holder.updateOnDeviceOff(device, insignSwitch);
        //holder.addGraph(insignSwitch.getOnNowForSeconds(),insignSwitch.getInstantPowerMilliWatts(),graphs.get(device));
        //holder.updateGraph(insignSwitch.getOnNowForSeconds(),insignSwitch.getInstantPowerMilliWatts(),series);
    }
    public void updateDeviceList(WemoDevice newDevice){
        Log.e("RViewAdapter","updateDeviceList");
        for(int i = 0; i < wemoDeviceLists.size();i++){
            WemoInsightSwitch newSwitch = new WemoInsightSwitch(newDevice);
            final WemoInsightSwitch device =  new WemoInsightSwitch(wemoDeviceLists.get(i));
            if(device.getSerialNumber() ==  newSwitch.getSerialNumber()){
                Log.e("RViewAdapter","      "+i);
                wemoDeviceLists.set(i,newDevice);
                notifyItemChanged(i);
            }
        }
    }

    public void removeDeviceList(WemoDevice newDevice){
        for(int i = 0; i < wemoDeviceLists.size();i++){
            WemoInsightSwitch newSwitch = new WemoInsightSwitch( newDevice);
            final WemoInsightSwitch device =  new WemoInsightSwitch(wemoDeviceLists.get(i));
            if(device.getSerialNumber() ==  newSwitch.getSerialNumber()){
                wemoDeviceLists.remove(i);
                notifyItemRemoved(i);
            }
        }
    }
    @Override
    public int getItemCount() {
        return wemoDeviceLists.size();
    }

    public void addDevice(WemoDevice wemoDevice) {
        wemoDeviceLists.add(wemoDevice);
        notifyItemInserted(wemoDeviceLists.size()-1);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        //public TextView descriptionView;
        public Switch powerSwitch;
        public Button editButtonShow;
        public Button editButtonHide;
        public ExpandableLayout expandableLayout;
        public TextView wemoTimeOnDuration;
        public TextView wemoAveragePower;
        public TextView wemoCurrentPower;
        public GraphView graphView;
        public LineGraphSeries<DataPoint> series;
        public TextView wemoTimeLastOn;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.wemoAveragePower = (TextView) itemView.findViewById(R.id.wemoaveragepower);
            this.wemoTimeOnDuration = (TextView) itemView.findViewById(R.id.wemotimeonduration);
            this.wemoCurrentPower = (TextView) itemView.findViewById(R.id.wemocurrentpower);
            this.wemoTimeLastOn = (TextView) itemView.findViewById(R.id.wemotimelaston);
            this.nameView = (TextView) itemView.findViewById(R.id.name);
            //this.descriptionView = (TextView) itemView.findViewById(R.id.description);
            this.powerSwitch = (Switch) itemView.findViewById(R.id.powerswitch);
            this.editButtonShow = (Button) itemView.findViewById(R.id.editbuttonshow);
            this.editButtonHide = (Button) itemView.findViewById(R.id.editbuttonhide);
            this.expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expanable);
            this.graphView = (GraphView) itemView.findViewById(R.id.graph);
        }

        public void updateDescription(String description){
            if(description!=null) {
                //descriptionView.setText(description);
            }
        }
        public void addGraph(String onNowForSeconds, String powerState, LineGraphSeries<DataPoint> newSeries) {
            Log.e("RViewAdapter", "addGraph");
            if (onNowForSeconds != null && powerState != null) {
                Log.e("RViewAdapter", "      addSeries");
                double seconds = Double.valueOf(onNowForSeconds);
                double power = Double.valueOf(powerState);
                //newSeries.appendData(new DataPoint(seconds,power),true,10);
                graphView.getViewport().setXAxisBoundsManual(true);
                graphView.removeAllSeries();// joe added
                series = newSeries;
                graphView.addSeries(series);
                graphView.getViewport().setMinX(0);
                graphView.getViewport().setMaxX(10);
                if (seconds > 10) {
                    graphView.getViewport().setMinX(seconds - 10);
                    graphView.getViewport().setMaxX(seconds+30);
                }
                graphView.getViewport().setYAxisBoundsManual(true);
                graphView.getViewport().setMinY(power - 10000);
                graphView.getViewport().setMaxY(power + 10000);
            }
        }


        public void updateOnDeviceOff(WemoDevice device, WemoInsightSwitch insignSwitch){
            if (wemoDeviceClickListener.wemoPowerStatus(device).contains("0")) {
                powerSwitch.setChecked(false);
                wemoAveragePower.setText("Average Power: - ");
                wemoCurrentPower.setText("Current Power: - ");
                wemoTimeOnDuration.setText("Time On: - ");
                long unixTime = System.currentTimeMillis() / 1000L;
                updateTimeLastOn(String.valueOf(unixTime),0);
            } else {
                powerSwitch.setChecked(true);
            }
        }

        public void updateGraph(String onNowForSeconds, String powerState,DataPoint dataPoint) {
            Log.e("RViewAdapter", "   updateGraph");
            if (onNowForSeconds != null && powerState != null) {
                if(series!=null) {
                    // double seconds = Double.valueOf(onNowForSeconds);
                    //double power = Double.valueOf(powerState);
                    if (dataPoint != null) {
                        double power = Double.valueOf(powerState);
                        graphView.getViewport().setMinY(power - 10000);
                        graphView.getViewport().setMaxY(power + 10000);
                        series.appendData(dataPoint, true, 50);
                    } else {
                        Log.e("RViewAdapter", "datapoint==null");
                    }
                }
                else {
                    Log.e("RViewAdapter", "      series==null");
                    addGraph(onNowForSeconds, powerState, new LineGraphSeries<DataPoint>(new DataPoint[]{dataPoint}));
                    //series.appendData(new DataPoint(seconds+2,power),true,10);
                }
            }
        }
        public void updateAveragePower(String averagePower){
            if(averagePower!=null) {
                wemoAveragePower.setText("Average Power: " + averagePower + " W");
            }
        }

        public void updateTimeOnDuration(String timeOnDuration){
            if(timeOnDuration!=null) {
                int timeInt = Integer.valueOf(timeOnDuration);
                int timeMin = timeInt/60;
                int timeHour = timeInt/3600;
                int timeDay = timeInt/86400;
                wemoTimeOnDuration.setText("Time On: " + timeInt + " sec");
                if(timeInt > 60){
                    wemoTimeOnDuration.setText("Time On: " + timeMin + " min");
                } else if(timeInt > 3600){
                    wemoTimeOnDuration.setText("Time On: " + timeHour + " hrs " + timeMin + " min");
                } else if(timeInt > 86400){
                    wemoTimeOnDuration.setText("Time On: " + timeDay + " days " + timeHour + " hrs");
                }

            }
        }
        public void updateCurrentPower(String currentPower){
            if(currentPower != null) {
                double power = Double.valueOf(currentPower) / 1000;
                if (currentPower != null) {
                    wemoCurrentPower.setText("Current Power: " + power + " W");
                }
            }
        }
        public void updateTimeLastOn(String timeLastOn,int onoff){
            if(timeLastOn!=null) {
                long unixSeconds = Long.valueOf(timeLastOn);
                Date stampDate = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
                Date currentDate = new Date();
                currentDate.getTime();
                SimpleDateFormat ymd = new SimpleDateFormat("MM-dd-yyyy"); // the format of your date
                SimpleDateFormat hours = new SimpleDateFormat("HH");
                SimpleDateFormat minutes = new SimpleDateFormat("mm");
                SimpleDateFormat seconds = new SimpleDateFormat("ss");

                //ymd.setTimeZone(TimeZone.getTimeZone("GMT-7"));
                //hms.setTimeZone(TimeZone.getTimeZone("GMT-7"));// give a timezone reference for formating (see comment at the bottom
                //String formattedDate = sdf.format(date); woke up late at night if I don't like where I end up I will kill myself.
                String currentFormatedDate = ymd.format(currentDate);
                String stampFormatedDate = ymd.format(stampDate);
                String status;
                if(onoff == 0){
                    status = "Last On: ";
                } else {
                    status = "On Since: ";
                }
                Log.d("TimeStamp","YMD");
                Log.d("StampFormated",stampFormatedDate);
                Log.d("CurrentFormated",currentFormatedDate);
                if(stampFormatedDate.contains(currentFormatedDate)){
                    String sFDminutes = minutes.format(stampDate);
                    String sFD = hours.format(stampDate);
                    if(Integer.valueOf(sFD)!= null && Integer.valueOf(sFD) > 12){
                        int pm = Integer.valueOf(sFD) % 12;
                        wemoTimeLastOn.setText(status + pm + ":" +sFDminutes+" PM");
                    } else if(sFD == "00"){
                        sFD = "12";
                        wemoTimeLastOn.setText(status + sFD + ":" +sFDminutes+" AM");
                    } else {
                        wemoTimeLastOn.setText(status + sFD + ":" +sFDminutes+" AM");

                    }
                } else {
                    wemoTimeLastOn.setText("Last On: " + stampFormatedDate);
                }
            }
        }



    }
}
