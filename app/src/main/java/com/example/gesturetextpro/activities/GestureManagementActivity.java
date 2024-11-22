package com.example.gesturetextpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gesturetextpro.R;
import com.example.gesturetextpro.adapters.GestureAdapter;
import com.example.gesturetextpro.models.Gesture;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GestureManagementActivity extends AppCompatActivity implements GestureAdapter.OnGestureClickListener {

    private FirebaseFirestore db;
    private RecyclerView gestureList;
    private GestureAdapter gestureAdapter;
    private List<Gesture> gestures = new ArrayList<>();
    private Button addGestureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_management);

        db = FirebaseFirestore.getInstance();
        gestureList = findViewById(R.id.gestureList);
        addGestureButton = findViewById(R.id.addGestureButton);

        gestureList.setLayoutManager(new LinearLayoutManager(this));
        gestureAdapter = new GestureAdapter(gestures, this);
        gestureList.setAdapter(gestureAdapter);

        loadGestures();

        addGestureButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditGestureActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    private void loadGestures() {
        db.collection("gestures")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        gestures.clear();
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Gesture gesture = document.toObject(Gesture.class);
                            if (gesture != null) {
                                gesture.setId(document.getId());
                                gestures.add(gesture);
                            }
                        }
                        gestureAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> Log.e("GestureManagement", "Failed to load gestures", e));
    }

    @Override
    public void onEditGesture(Gesture gesture) {
        Intent intent = new Intent(this, AddEditGestureActivity.class);
        intent.putExtra("gestureId", gesture.getId());
        startActivityForResult(intent, 2);
    }

    @Override
    public void onDeleteGesture(Gesture gesture) {
        db.collection("gestures")
                .document(gesture.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("GestureManagement", "Gesture deleted successfully");
                    loadGestures();
                })
                .addOnFailureListener(e -> Log.e("GestureManagement", "Error deleting gesture", e));
    }

    @Override
    public void onEditGesture(android.gesture.Gesture gesture) {

    }

    @Override
    public void onDeleteGesture(android.gesture.Gesture gesture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadGestures();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
