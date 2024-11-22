package com.example.gesturetextpro.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gesturetextpro.R;
import com.example.gesturetextpro.adapters.ChatAdapter;
import com.example.gesturetextpro.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Log.d(TAG, "onCreate: Initializing components");

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        receiverId = getIntent().getStringExtra("receiverId");
        receiverName = getIntent().getStringExtra("receiverName");

        messageList = findViewById(R.id.messageList);
        receiverNameText = findViewById(R.id.receiverName);
        messageBox = findViewById(R.id.messageBox);
        sendButton = findViewById(R.id.sendButton);

        receiverNameText.setText(receiverName);
        messageList.setLayoutManager(new LinearLayoutManager(this));

        gestureDetector = new GestureDetector(this, new GestureListener());

        View gestureArea = findViewById(R.id.gestureArea);
        gestureArea.setOnTouchListener((v, event) -> {
            Log.d(TAG, "TouchEvent on gestureArea: " + event.toString());
            gestureDetector.onTouchEvent(event);
            return true;
        });


        sendButton.setOnClickListener(v -> {
            String messageContent = messageBox.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                sendMessage(messageContent);
                messageBox.setText("");
            } else {
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        loadGesturesFromFirestore();

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
            return;
        }

        String existingText = messageBox.getText().toString().trim();

        if (!existingText.isEmpty()) {
            content = existingText + " " + content;
        }

        messageBox.setText(content);

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

        db.collection("messages")
                .whereEqualTo("senderId", currentUserId)
                .whereEqualTo("receiverId", receiverId)
                .get()
                .addOnSuccessListener(senderQuerySnapshot -> {
                    List<Message> allMessages = new ArrayList<>();
                    allMessages.addAll(senderQuerySnapshot.toObjects(Message.class));

                    db.collection("messages")
                            .whereEqualTo("senderId", receiverId)
                            .whereEqualTo("receiverId", currentUserId)
                            .get()
                            .addOnSuccessListener(receiverQuerySnapshot -> {
                                allMessages.addAll(receiverQuerySnapshot.toObjects(Message.class));

                                Collections.sort(allMessages, (m1, m2) ->
                                        m1.getTimestamp().compareTo(m2.getTimestamp()));

                                runOnUiThread(() -> {
                                    ChatAdapter chatAdapter = new ChatAdapter(allMessages, currentUserId);
                                    messageList.setAdapter(chatAdapter);

                                    if (!allMessages.isEmpty()) {
                                        messageList.scrollToPosition(allMessages.size() - 1);
                                    }

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

