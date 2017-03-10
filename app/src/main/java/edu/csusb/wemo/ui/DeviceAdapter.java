package edu.csusb.wemo.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;
import java.util.zip.Inflater;

import edu.csusb.wemo.R;

/**
 * Created by Luong Randy on 3/9/2017.
 */

public class DeviceAdapter extends ExpandableRecyclerAdapter<DeviceParent, DeviceChild> {
    LayoutInflater mInflater;
    Context context;

    public DeviceAdapter(@NonNull List parentList, Context context) {
        super(parentList);
        this.context = context;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.wemo_device_list, parentViewGroup, false);
        return new DeviceParent(view);
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.wemo_device_options, childViewGroup, false);
        return new DeviceChild(view);
    }
//TODO: This stuff
    @Override
    public void onBindChildViewHolder(@NonNull ChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull Object child) {

    }

    @Override
    public void onBindParentViewHolder(@NonNull ParentViewHolder parentViewHolder, int parentPosition, @NonNull Parent parent) {

    }

}
