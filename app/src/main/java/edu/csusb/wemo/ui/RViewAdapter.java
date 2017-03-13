package edu.csusb.wemo.ui;

import android.content.Context;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.silencedut.expandablelayout.ExpandableLayout;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import edu.csusb.wemo.R;
import edu.csusb.wemo.model.WemoDevice;
import edu.csusb.wemo.model.WemoInsightSwitch;
import edu.csusb.wemo.presenter.WemoListPresenterImpl;

/**
 * Created by Luong Randy on 2/24/2017.
 */

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.CustomViewHolder> {
    private List<WemoDevice> wemoDeviceLists;
    private Context mContext;
    private WemoDeviceClickListener wemoDeviceClickListener;
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>();


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
        View.OnClickListener buttonHide = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("RViewAdapter","buttonHide");
                holder.editButtonHide.setVisibility(View.GONE);
                holder.expandableLayout.setExpand(false);


            }
        };
        View.OnClickListener buttonShow = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("RViewAdapter","buttonShow");
                holder.editButtonHide.setVisibility(View.VISIBLE);
                holder.expandableLayout.setExpand(true);
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
            }
        };

        if (wemoDeviceClickListener.wemoPowerStatus(device).contains("0")) {
            holder.powerSwitch.setChecked(false);
        } else {
            holder.powerSwitch.setChecked(true);
        }

        holder.powerSwitch.setOnClickListener(listener);
        holder.editButtonHide.setOnClickListener(buttonHide);
        holder.editButtonShow.setOnClickListener(buttonShow);
        holder.updateDescription(insignSwitch.getAveragepowerWatts());
        holder.updateAveragePower(insignSwitch.getAveragepowerWatts());
        holder.updateCurrentPower(insignSwitch.getInstantPowerMilliWatts());
        holder.updateTimeLastOn(insignSwitch.getLastToggleTimestamp());
        holder.updateTimeOnDuration(insignSwitch.getOnNowForSeconds());
        holder.updateGraph(insignSwitch.getOnNowForSeconds(),insignSwitch.getInstantPowerMilliWatts(),series);
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
        public TextView descriptionView;
        public Switch powerSwitch;
        public Button editButtonShow;
        public Button editButtonHide;
        public ExpandableLayout expandableLayout;
        public TextView wemoTimeOnDuration;
        public TextView wemoAveragePower;
        public TextView wemoCurrentPower;
        public GraphView graphView;
        public TextView wemoTimeLastOn;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.wemoAveragePower = (TextView) itemView.findViewById(R.id.wemoaveragepower);
            this.wemoTimeOnDuration = (TextView) itemView.findViewById(R.id.wemotimeonduration);
            this.wemoCurrentPower = (TextView) itemView.findViewById(R.id.wemocurrentpower);
            this.wemoTimeLastOn = (TextView) itemView.findViewById(R.id.wemotimelaston);
            this.nameView = (TextView) itemView.findViewById(R.id.name);
            this.descriptionView = (TextView) itemView.findViewById(R.id.description);
            this.powerSwitch = (Switch) itemView.findViewById(R.id.powerswitch);
            this.editButtonShow = (Button) itemView.findViewById(R.id.editbuttonshow);
            this.editButtonHide = (Button) itemView.findViewById(R.id.editbuttonhide);
            this.expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expanable);
            this.graphView = (GraphView) itemView.findViewById(R.id.graph);
        }

        public void updateDescription(String description){
            if(description!=null) {
                descriptionView.setText(description);
            }
        }
//TODO: get graph logic working and make seperate views for the getters
        public void updateGraph(String onNowForSeconds, String powerState, LineGraphSeries<DataPoint> series) {
            if (onNowForSeconds != null && powerState != null) {
                double seconds = Double.valueOf(onNowForSeconds);
                double power = Double.valueOf(powerState);
                series.appendData(new DataPoint(seconds,power),true,10);
                graphView.addSeries(series);
                graphView.getViewport().setXAxisBoundsManual(true);
                graphView.getViewport().setMinX(0);
                graphView.getViewport().setMaxX(10);
                if(seconds > 10){
                    graphView.getViewport().setMinX(seconds-10);
                    graphView.getViewport().setMaxX(seconds);
                }
                graphView.getViewport().setYAxisBoundsManual(true);
                graphView.getViewport().setMinY(power - 5000);
                graphView.getViewport().setMaxY(power + 5000);
            }
        }
//TODO if it looks good it is good
        public DataPoint[] feedData(double seconds, double power){
            DataPoint[] data = new DataPoint[10];
            for(int i=0;i<10;i++){
                if(data[i] == null){
                    data[i]=new DataPoint(0,0);
                }
            }
            DataPoint v = new DataPoint(seconds,power);
            for(int i=0;i<10;i++){
                if(data[i] == new DataPoint(0,0)){
                    data[i]=v;
                    return data;
                }
            }
            return data;
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
        public void updateTimeLastOn(String timeLastOn){
            if(timeLastOn!=null) {
                wemoTimeLastOn.setText("Last on: " + timeLastOn);
            }
        }
    }
}
