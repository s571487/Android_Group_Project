package com.example.gesturetextpro;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class GestureCustom extends AppCompatActivity {
    private EditText gestureInput, phraseInput;
    private Button saveGestureButton;
    private ListView gestureListView;
    private List<String> gestureList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_custom);

        // Initialize the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        gestureInput = findViewById(R.id.gesture_input);
        phraseInput = findViewById(R.id.phrase_input);
        saveGestureButton = findViewById(R.id.save_gesture_button);
        gestureListView = findViewById(R.id.gesture_list);

        // Load saved gestures (stubbed function with hard-coded values)
        gestureList = loadSavedGestures();

        // Set up adapter to display saved gestures
        GestureAdapter adapter = new GestureAdapter(this, gestureList);
        gestureListView.setAdapter(adapter);

        // Save button to add new gesture
        saveGestureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGesture();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back when Up button is pressed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<String> loadSavedGestures() {
        // Stubbed data - in a real app, load gestures from a database
        List<String> gestures = new ArrayList<>();
        gestures.add("Wave ➔ Hello");
        gestures.add("Thumbs up ➔ Good job");
        return gestures;
    }

    private void saveGesture() {
        String gesture = gestureInput.getText().toString().trim();
        String phrase = phraseInput.getText().toString().trim();

        if (gesture.isEmpty() || phrase.isEmpty()) {
            Toast.makeText(this, "Please enter both gesture and phrase", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save gesture to list (stubbed, replace with DB save in real app)
        gestureList.add(gesture + " ➔ " + phrase);
        Toast.makeText(this, "Gesture saved", Toast.LENGTH_SHORT).show();
        gestureInput.setText("");
        phraseInput.setText("");
    }
}
