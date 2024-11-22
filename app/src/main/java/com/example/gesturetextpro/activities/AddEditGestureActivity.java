package com.example.gesturetextpro.activities;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gesturetextpro.R;
import com.example.gesturetextpro.models.Gesture;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddEditGestureActivity extends AppCompatActivity {

    private EditText gestureMessageBox;
    private Spinner gestureTypeSpinner;
    private Button saveButton;
    private FirebaseFirestore db;
    private String gestureId;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_gesture);

        db = FirebaseFirestore.getInstance();
        gestureMessageBox = findViewById(R.id.gestureMessageBox);
        gestureTypeSpinner = findViewById(R.id.gestureTypeSpinner);
        saveButton = findViewById(R.id.saveButton);

        // Setup spinner for gesture types (single_tap, double_tap, etc.)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gesture_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gestureTypeSpinner.setAdapter(adapter);

        userId = getIntent().getStringExtra("userId");

        gestureId = getIntent().getStringExtra("gestureId");
        if (gestureId != null) {
            loadGestureData();
        }

        saveButton.setOnClickListener(v -> saveGesture());
    }

    private void loadGestureData() {
        db.collection("gestures")
                .document(gestureId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Gesture gesture = documentSnapshot.toObject(Gesture.class);
                    if (gesture != null) {
                        gestureTypeSpinner.setSelection(getGestureTypePosition(gesture.getGestureType()));
                        gestureMessageBox.setText(gesture.getMessage());
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error loading gesture data", e));
    }

    private int getGestureTypePosition(String gestureType) {
        // Find the position of the gesture type in the spinner options
        String[] gestureTypes = getResources().getStringArray(R.array.gesture_types);
        for (int i = 0; i < gestureTypes.length; i++) {
            if (gestureTypes[i].equals(gestureType)) {
                return i;
            }
        }
        return 0;  // Default to the first position if the gesture type is not found
    }

    private void saveGesture() {
        String gestureType = gestureTypeSpinner.getSelectedItem().toString();
        String message = gestureMessageBox.getText().toString();

        if (gestureId == null) {
            // Creating a new Gesture
            Gesture gesture = new Gesture(userId, gestureType, message);
            db.collection("gestures").add(gesture)
                    .addOnSuccessListener(documentReference -> {
                        setResult(RESULT_OK);
                        finish();
                    })
                    .addOnFailureListener(e -> Log.e(TAG, "Error saving gesture", e));
        } else {
            // Updating an existing Gesture
            db.collection("gestures")
                    .document(gestureId)
                    .update("gestureType", gestureType, "message", message)
                    .addOnSuccessListener(aVoid -> {
                        setResult(RESULT_OK);
                        finish();
                    })
                    .addOnFailureListener(e -> Log.e(TAG, "Error updating gesture", e));
        }
    }
}
