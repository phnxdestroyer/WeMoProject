package edu.csusb.wemo.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import edu.csusb.wemo.R;

/**
 * Created by Luong Randy on 3/9/2017.
 */
//TODO: Remove
public class DeviceParent extends ParentViewHolder{
    public TextView nameView;
    public TextView descriptionView;
    public Switch powerSwitch;
    public Button editButtonShow;
    public Button editButtonHide;
    public EditText editDescription;

    public DeviceParent(View itemView) {
        super(itemView);
        this.nameView = (TextView) itemView.findViewById(R.id.name);
        this.descriptionView = (TextView) itemView.findViewById(R.id.description);
        this.powerSwitch = (Switch) itemView.findViewById(R.id.powerswitch);
        this.editButtonShow = (Button) itemView.findViewById(R.id.editbuttonshow);
        this.editButtonHide = (Button) itemView.findViewById(R.id.editbuttonhide);
    }
}