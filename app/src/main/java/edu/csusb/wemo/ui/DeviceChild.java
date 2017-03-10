package edu.csusb.wemo.ui;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import edu.csusb.wemo.R;

/**
 * Created by Luong Randy on 3/9/2017.
 */
//TODO: Remove
public class DeviceChild extends ChildViewHolder{
    public TextView wemoAvgPower;
    public TextView wemoCurrentPower;
    public TextView wemoLastOn;
    public TextView wemoOnDuration;
    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public DeviceChild(@NonNull View itemView) {
        super(itemView);
        this.wemoAvgPower = (TextView) itemView.findViewById(R.id.wemoaveragepower);
        this.wemoCurrentPower = (TextView) itemView.findViewById(R.id.wemocurrentpower);
        this.wemoLastOn = (TextView) itemView.findViewById(R.id.wemotimelaston);
        this.wemoOnDuration = (TextView) itemView.findViewById(R.id.wemotimeonduration);
    }
}
