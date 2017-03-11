package edu.csusb.wemo.ui;

import android.content.Context;
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

import com.silencedut.expandablelayout.ExpandableLayout;

import java.util.List;

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
        final WemoInsightSwitch insignSwitch =  new WemoInsightSwitch(wemoDeviceLists.get(position));
       // holder.descriptionView.setText(device.getWemoDescription());
        holder.nameView.setText(insignSwitch.wemoDevice.device.getDisplayString());
        View.OnClickListener buttonHide = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("RViewAdapter","buttonHide");
                holder.editButtonHide.setVisibility(View.GONE);

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
        holder.powerSwitch.setOnClickListener(listener);
        holder.editButtonHide.setOnClickListener(buttonHide);
        holder.editButtonShow.setOnClickListener(buttonShow);
        holder.updateDescription(insignSwitch.getAveragepowerWatts());
        holder.updateAveragePower(insignSwitch.getAveragepowerWatts());
        holder.updateCurrentPower(insignSwitch.getPowerState());
        holder.updateTimeLastOn(insignSwitch.getLastToggleTimestamp());
        holder.updateTimeOnDuration(insignSwitch.getOnNowForSeconds());
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
        }

        public void updateDescription(String description){
            if(description!=null) {
                descriptionView.setText(description);
            }
        }

        public void updateAveragePower(String averagePower){
            if(averagePower!=null) {
                wemoAveragePower.setText("Average Power: " + averagePower);
            }
        }

        public void updateTimeOnDuration(String timeOnDuration){
            if(timeOnDuration!=null) {
                wemoTimeOnDuration.setText("Time On: " + timeOnDuration);
            }
        }
        public void updateCurrentPower(String currentPower){
            if(currentPower!=null) {
                wemoCurrentPower.setText("Current Power: " + currentPower);
            }
        }
        public void updateTimeLastOn(String timeLastOn){
            if(timeLastOn!=null) {
                wemoTimeLastOn.setText("Last on: " + timeLastOn);
            }
        }
    }
}
