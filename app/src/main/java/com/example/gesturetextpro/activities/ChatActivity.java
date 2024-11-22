package com.example.gesturetextpro.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gesturetextpro.R;
import com.example.gesturetextpro.adapters.ChatAdapter;
import com.example.gesturetextpro.models.Gesture;
import com.example.gesturetextpro.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";

    private String receiverId;
    private String receiverName;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private RecyclerView messageList;
    private TextView receiverNameText;
    private EditText messageBox;
    private ImageButton sendButton;

    private GestureDetector gestureDetector;

    private Map<String, String> predefinedMessages = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Log.d(TAG, "onCreate: Initializing components");

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Get receiver details from Intent
        receiverId = getIntent().getStringExtra("receiverId");
        receiverName = getIntent().getStringExtra("receiverName");

        // Initialize UI elements
        messageList = findViewById(R.id.messageList);
        receiverNameText = findViewById(R.id.receiverName);
        messageBox = findViewById(R.id.messageBox);
        sendButton = findViewById(R.id.sendButton);

        receiverNameText.setText(receiverName);
        messageList.setLayoutManager(new LinearLayoutManager(this));

        // Initialize GestureDetector
        gestureDetector = new GestureDetector(this, new GestureListener());


        // Attach gesture listener to the specific View
        View gestureArea = findViewById(R.id.gestureArea);
        gestureArea.setOnTouchListener((v, event) -> {
            Log.d(TAG, "TouchEvent on gestureArea: " + event.toString());
            gestureDetector.onTouchEvent(event);
            return true;
        });


        // Send Button Listener
        sendButton.setOnClickListener(v -> {
            String messageContent = messageBox.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                sendMessage(messageContent);
                messageBox.setText(""); // Clear the message box
            } else {
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        // Load gestures from Firestore
        loadGesturesFromFirestore();

        // Load chat messages
        loadMessages();
    }

    private void loadGesturesFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("gestures")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        Map<String, String> newGestures = new HashMap<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String gestureType = document.getString("gestureType");
                            String message = document.getString("message");
                            if (gestureType != null && message != null) {
                                newGestures.put(gestureType, message);
                            }
                        }
                        updateGestureMessages(newGestures);
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error loading gestures", e));
    }

    private void updateGestureMessages(Map<String, String> newGestures) {
        predefinedMessages.clear();
        predefinedMessages.putAll(newGestures);
    }



    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            String message = predefinedMessages.get("single_tap");
            if (message != null) {
                populateMessage(message);
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            String message = predefinedMessages.get("double_tap");
            if (message != null) {
                populateMessage(message);
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float deltaX = e2.getX() - e1.getX();
            float deltaY = e2.getY() - e1.getY();

            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                String message = deltaX > 0 ? predefinedMessages.get("swipe_right") : predefinedMessages.get("swipe_left");
                if (message != null) {
                    populateMessage(message);
                }
            } else {
                String message = deltaY > 0 ? predefinedMessages.get("swipe_down") : predefinedMessages.get("swipe_up");
                if (message != null) {
                    populateMessage(message);
                }
            }
            return true;
        }
    }



    private void populateMessage(String content) {
        if (content == null || content.isEmpty()) {
            return; // Avoid processing null or empty messages
        }

        // Get the existing text from the message box
        String existingText = messageBox.getText().toString().trim();

        // Concatenate the new content with a space
        if (!existingText.isEmpty()) {
            content = existingText + " " + content;
        }

        // Set the updated content back to the message box
        messageBox.setText(content);

        // Move the cursor to the end for better UX
        messageBox.setSelection(content.length());

        Log.d(TAG, "populateMessage: Updated content -> " + content);
    }


    private void sendMessage(String content) {
        String senderId = auth.getCurrentUser().getUid();
        Message message = new Message(senderId, receiverId, content, com.google.firebase.Timestamp.now());

        db.collection("messages").add(message)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Message sent: " + content))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to send message", e));

        loadMessages();
    }

    private void loadMessages() {
        String currentUserId = auth.getCurrentUser().getUid();

        // First query: Messages sent by current user to receiver
        db.collection("messages")
                .whereEqualTo("senderId", currentUserId)
                .whereEqualTo("receiverId", receiverId)
                .get()
                .addOnSuccessListener(senderQuerySnapshot -> {
                    // Create a list to hold all messages
                    List<Message> allMessages = new ArrayList<>();

                    // Add messages sent by current user to receiver
                    allMessages.addAll(senderQuerySnapshot.toObjects(Message.class));

                    // Second query: Messages sent by receiver to current user
                    db.collection("messages")
                            .whereEqualTo("senderId", receiverId)
                            .whereEqualTo("receiverId", currentUserId)
                            .get()
                            .addOnSuccessListener(receiverQuerySnapshot -> {
                                // Add messages sent by receiver to current user
                                allMessages.addAll(receiverQuerySnapshot.toObjects(Message.class));

                                // Sort messages by timestamp
                                Collections.sort(allMessages, (m1, m2) ->
                                        m1.getTimestamp().compareTo(m2.getTimestamp()));

                                // Update UI on main thread
                                runOnUiThread(() -> {
                                    ChatAdapter chatAdapter = new ChatAdapter(allMessages, currentUserId);
                                    messageList.setAdapter(chatAdapter);

                                    // Scroll to the last message
                                    if (!allMessages.isEmpty()) {
                                        messageList.scrollToPosition(allMessages.size() - 1);
                                    }

                                    // Log the number of messages
                                    Log.d(TAG, "Total messages loaded: " + allMessages.size());
                                });
                            })
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "Error loading receiver messages", e);
                                Toast.makeText(this, "Failed to load messages", Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error loading sender messages", e);
                    Toast.makeText(this, "Failed to load messages", Toast.LENGTH_SHORT).show();
                });
    }

}

