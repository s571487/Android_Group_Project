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

public class ContactsAdapter extends ArrayAdapter<String> {
    private final Context mContext;
    private final List<String> mContactsList;

    public ContactsAdapter(@NonNull Context context, @NonNull List<String> contactsList) {
        super(context, 0, contactsList);
        this.mContext = context;
        this.mContactsList = contactsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
        }

        String currentContact = mContactsList.get(position);
        TextView contactTextView = listItem.findViewById(R.id.contact_name);
        contactTextView.setText(currentContact);

        return listItem;
    }
}

