package com.example.gesturetextpro;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Settings extends AppCompatActivity {
    private Switch themeSwitch;
    private Switch feedbackSwitch;
    private Button backupButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize UI elements
        themeSwitch = findViewById(R.id.switch_theme);
        feedbackSwitch = findViewById(R.id.switch_feedback);
        backupButton = findViewById(R.id.btn_backup);

        // Load user preferences (stubbed with default values)
        loadUserPreferences();

        // Initialize the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set listeners
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(Settings.this, isChecked ? "Dark Theme Enabled" : "Light Theme Enabled", Toast.LENGTH_SHORT).show();
            // Save theme setting (stubbed)
            saveThemePreference(isChecked);
        });

        feedbackSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(Settings.this, isChecked ? "Gesture Feedback Enabled" : "Gesture Feedback Disabled", Toast.LENGTH_SHORT).show();
            // Save feedback setting (stubbed)
            saveFeedbackPreference(isChecked);
        });

        backupButton.setOnClickListener(v -> {
            Toast.makeText(Settings.this, "Backup Started", Toast.LENGTH_SHORT).show();
            // Backup data (stubbed)
            performBackup();
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

    private void loadUserPreferences() {
        // Stubbed loading of preferences
        themeSwitch.setChecked(false); // Example: default to light theme
        feedbackSwitch.setChecked(true); // Example: default feedback enabled
    }

    private void saveThemePreference(boolean isDarkMode) {
        // Stubbed save function for theme preference
    }

    private void saveFeedbackPreference(boolean feedbackEnabled) {
        // Stubbed save function for feedback preference
    }

    private void performBackup() {
        // Stubbed backup function
    }
}