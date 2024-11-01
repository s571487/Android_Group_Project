package com.example.gesturetextpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.ArrayAdapter;

import java.util.List;

public class GroupAdapter extends ArrayAdapter<String> {
    private final Context mContext;
    private final List<String> mGroupList;

    public GroupAdapter(@NonNull Context context, @NonNull List<String> groupList) {
        super(context, 0, groupList);
        this.mContext = context;
        this.mGroupList = groupList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_group, parent, false);
        }

        String currentGroup = mGroupList.get(position);

        TextView groupTextView = listItem.findViewById(R.id.group_name);
        groupTextView.setText(currentGroup);

        return listItem;
    }
}
