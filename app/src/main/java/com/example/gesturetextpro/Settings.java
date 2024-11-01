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

        themeSwitch = findViewById(R.id.switch_theme);
        feedbackSwitch = findViewById(R.id.switch_feedback);
        backupButton = findViewById(R.id.btn_backup);

        loadUserPreferences();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(Settings.this, isChecked ? "Dark Theme Enabled" : "Light Theme Enabled", Toast.LENGTH_SHORT).show();
            saveThemePreference(isChecked);
        });

        feedbackSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(Settings.this, isChecked ? "Gesture Feedback Enabled" : "Gesture Feedback Disabled", Toast.LENGTH_SHORT).show();
            saveFeedbackPreference(isChecked);
        });

        backupButton.setOnClickListener(v -> {
            Toast.makeText(Settings.this, "Backup Started", Toast.LENGTH_SHORT).show();
            performBackup();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadUserPreferences() {
        themeSwitch.setChecked(false);
        feedbackSwitch.setChecked(true);
    }

    private void saveThemePreference(boolean isDarkMode) {
    }

    private void saveFeedbackPreference(boolean feedbackEnabled) {
    }

    private void performBackup() {
    }
}