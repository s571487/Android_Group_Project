package com.example.gesturetextpro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

        messageHistoryButton.setOnClickListener(v -> openMessageHistory());

        contactsButton.setOnClickListener(v -> openContacts());

        settingsButton.setOnClickListener(v -> openSettings());

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
