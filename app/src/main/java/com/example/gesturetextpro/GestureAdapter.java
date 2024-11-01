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

public class GestureAdapter extends ArrayAdapter<String> {
    private final Context mContext;
    private final List<String> mGestureList;

    public GestureAdapter(@NonNull Context context, @NonNull List<String> gestureList) {
        super(context, 0, gestureList);
        this.mContext = context;
        this.mGestureList = gestureList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_gesture, parent, false);
        }

        String currentGesturePhrase = mGestureList.get(position);
        String[] gestureParts = currentGesturePhrase.split(" ➔ ");
        String gesture = gestureParts[0];
        String phrase = gestureParts.length > 1 ? gestureParts[1] : "";

        TextView gestureTextView = listItem.findViewById(R.id.gesture_text);
        TextView phraseTextView = listItem.findViewById(R.id.phrase_text);

        gestureTextView.setText(gesture);
        phraseTextView.setText(phrase);

        return listItem;
    }
}
