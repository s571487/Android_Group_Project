package com.example.gesturetextpro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button messageHistoryButton, contactsButton, settingsButton, composeMessageButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageHistoryButton = findViewById(R.id.message_history_button);
        contactsButton = findViewById(R.id.contacts_button);
        settingsButton = findViewById(R.id.settings_button);
        composeMessageButton = findViewById(R.id.compose_message_button);

        // Button to view message history
        messageHistoryButton.setOnClickListener(v -> openMessageHistory());

        // Button to view contacts
        contactsButton.setOnClickListener(v -> openContacts());

        // Button to access settings
        settingsButton.setOnClickListener(v -> openSettings());

        // Button to compose a new message via gesture input
        composeMessageButton.setOnClickListener(v -> openGestureInput());
    }

    private void openMessageHistory() {
        Intent intent = new Intent(MainActivity.this, MessageHistory.class);
        startActivity(intent);
    }

    private void openContacts() {
        Intent intent = new Intent(MainActivity.this, Contacts.class);
        startActivity(intent);
    }

    private void openSettings() {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    private void openGestureInput() {
        Intent intent = new Intent(MainActivity.this, GestureInput.class);
        startActivity(intent);
    }
}
