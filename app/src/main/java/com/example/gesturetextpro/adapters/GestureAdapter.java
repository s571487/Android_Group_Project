package com.example.gesturetextpro.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gesturetextpro.R;
import com.example.gesturetextpro.models.Gesture;

import java.util.List;

public class GestureAdapter extends RecyclerView.Adapter<GestureAdapter.GestureViewHolder> {

    private List<Gesture> gestures;
    private OnGestureClickListener listener;

    public interface OnGestureClickListener {
        void onEditGesture(Gesture gesture);
        void onDeleteGesture(Gesture gesture);

        void onEditGesture(android.gesture.Gesture gesture);

        void onDeleteGesture(android.gesture.Gesture gesture);
    }

    public GestureAdapter(
            List<Gesture> gestures, OnGestureClickListener listener) {
        this.gestures = gestures;
        this.listener = listener;
    }

    @Override
    public GestureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gesture, parent, false);
        return new GestureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GestureViewHolder holder, int position) {
        Gesture gesture = gestures.get(position);
        holder.gestureName.setText(gesture.getGestureType());
        holder.gestureMessage.setText(gesture.getMessage());

        holder.editButton.setOnClickListener(v -> listener.onEditGesture(gesture));
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteGesture(gesture));
    }

    @Override
    public int getItemCount() {
        return gestures.size();
    }

    public static class GestureViewHolder extends RecyclerView.ViewHolder {
        TextView gestureName;
        TextView gestureMessage;
        Button editButton, deleteButton;

        public GestureViewHolder(View itemView) {
            super(itemView);
            gestureName = itemView.findViewById(R.id.gestureName);
            gestureMessage = itemView.findViewById(R.id.gestureMessage);
            editButton = itemView.findViewById(R.id.editGestureButton);
            deleteButton = itemView.findViewById(R.id.deleteGestureButton);
        }
    }
}
