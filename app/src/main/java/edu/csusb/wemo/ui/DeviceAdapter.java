package edu.csusb.wemo.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;

import edu.csusb.wemo.R;
import edu.csusb.wemo.model.WemoDevice;

/**
 * Created by Luong Randy on 3/9/2017.
 */

public class DeviceAdapter extends ExpandableRecyclerAdapter<WemoDevice,WemoDevice,DeviceParent,DeviceChild> {
    LayoutInflater mInflater;
    Context context;
//TODO:remove
    public DeviceAdapter(@NonNull List parentList, Context context) {
        super(parentList);
        this.context = context;
    }

    @NonNull
    @Override
    public DeviceParent onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.wemo_device_list, parentViewGroup, false);
        return new DeviceParent(view);
    }

    @NonNull
    @Override
    public DeviceChild onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.wemo_device_options, childViewGroup, false);
        return new DeviceChild(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull DeviceParent parentViewHolder, int parentPosition, @NonNull WemoDevice parent) {
    }

    @Override
    public void onBindChildViewHolder(@NonNull DeviceChild childViewHolder, int parentPosition, int childPosition, @NonNull WemoDevice child) {

    }

}
