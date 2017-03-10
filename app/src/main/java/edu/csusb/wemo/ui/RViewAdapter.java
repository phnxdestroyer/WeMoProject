package edu.csusb.wemo.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wemo_device_list,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        final WemoDevice device = wemoDeviceLists.get(position);
        final WemoInsightSwitch insignSwitch =  new WemoInsightSwitch(wemoDeviceLists.get(position));
       // holder.descriptionView.setText(device.getWemoDescription());
        holder.nameView.setText(insignSwitch.wemoDevice.device.getDisplayString());
        View.OnClickListener buttonHide = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.editButtonHide.setVisibility(View.GONE);
                holder.editDescription.setVisibility(View.GONE);
                if(holder.editDescription.getText().length()>0){
                    holder.descriptionView.setText(holder.editDescription.getText());
                }
            }
        };
        View.OnClickListener buttonShow = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.editDescription.setVisibility(View.VISIBLE);
                holder.editButtonHide.setVisibility(View.VISIBLE);

                //wemoDeviceClickListener.onWemoEditClick(deviceList);
            }
        };
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //switch on off
                wemoDeviceClickListener.onWemoSwitchClick(device);
            }
        };
        holder.powerSwitch.setOnClickListener(listener);
        holder.editButtonHide.setOnClickListener(buttonHide);
        holder.editButtonShow.setOnClickListener(buttonShow);
    }
    public void updateDeviceList(WemoDevice newDevice){
        for(int i = 0; i < wemoDeviceLists.size();i++){
            WemoInsightSwitch newSwitch = new WemoInsightSwitch(newDevice);
            final WemoInsightSwitch device =  new WemoInsightSwitch(wemoDeviceLists.get(i));
            if(device.getSerialNumber() ==  newSwitch.getSerialNumber()){
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
        protected TextView nameView;
        protected TextView descriptionView;
        protected Switch powerSwitch;
        protected Button editButtonShow;
        protected Button editButtonHide;
        protected EditText editDescription;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.nameView = (TextView) itemView.findViewById(R.id.name);
            this.descriptionView = (TextView) itemView.findViewById(R.id.description);
            this.powerSwitch = (Switch) itemView.findViewById(R.id.powerswitch);
            this.editButtonShow = (Button) itemView.findViewById(R.id.editbuttonshow);
            this.editButtonHide = (Button) itemView.findViewById(R.id.editbuttonhide);
            this.editDescription = (EditText) itemView.findViewById(R.id.editdescription);
        }
    }
}
