package edu.csusb.wemo.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import edu.csusb.wemo.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Luong Randy on 2/24/2017.
 */

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.CustomViewHolder> {
    private List<WemoDeviceList> wemoDeviceLists;
    private Context mContext;
    private RInterface rInterface;

    public RViewAdapter(List<WemoDeviceList> wemoDeviceLists, Context mContext, RInterface rInterface) {
        this.wemoDeviceLists = wemoDeviceLists;
        this.mContext = mContext;
        this.rInterface = rInterface;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wemo_device_list,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        final WemoDeviceList deviceList = wemoDeviceLists.get(position);
        holder.descriptionView.setText(deviceList.getWemoDescription());
        holder.nameView.setText(deviceList.getWemoName());
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

                //rInterface.onWemoEditClick(deviceList);
            }
        };
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //switch on off
                rInterface.onWemoSwitchClick(deviceList);
            }
        };
        holder.powerSwitch.setOnClickListener(listener);
        holder.editButtonHide.setOnClickListener(buttonHide);
        holder.editButtonShow.setOnClickListener(buttonShow);
    }
    //TODO: change to serial number instead of names
    public void updateDeviceList(WemoDeviceList deviceList){
        for(int i = 0; i < wemoDeviceLists.size();i++){
            if(wemoDeviceLists.get(i).getWemoName() == deviceList.getWemoName()){
                wemoDeviceLists.set(i,deviceList);
                notifyItemChanged(i);
            }
        }
    }

    @Override
    public int getItemCount() {
        return wemoDeviceLists.size();
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
