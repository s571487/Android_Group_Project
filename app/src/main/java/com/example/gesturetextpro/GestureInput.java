package com.example.gesturetextpro;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GestureInput extends AppCompatActivity {
    private EditText messageInput;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_input);

        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(v -> sendMessage());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        detectGesture();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void detectGesture() {
        String detectedGesture = "wave";
        handleGesture(detectedGesture);
    }

    private void handleGesture(String gesture) {
        if ("wave".equals(gesture)) {
            messageInput.append("👋");
        } else {
            messageInput.append("[Gesture detected: " + gesture + "] ");
        }
    }

    private void sendMessage() {
        String message = messageInput.getText().toString().trim();
        if (message.isEmpty()) {
            Toast.makeText(this, "Cannot send an empty message", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sendGestureMessage(message)) {
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
            messageInput.setText("");
        } else {
            Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean sendGestureMessage(String message) {
        return true;
    }
}
