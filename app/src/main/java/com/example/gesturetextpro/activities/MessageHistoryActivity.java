package com.example.gesturetextpro.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gesturetextpro.R;
import com.example.gesturetextpro.adapters.ChatAdapter;
import com.example.gesturetextpro.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageHistoryActivity extends AppCompatActivity {
    private static final String TAG = "MessageHistoryActivity";
    private FirebaseAuth auth;
    private RecyclerView messageList;
    private ChatAdapter chatAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_history);

        messageList = findViewById(R.id.messageList);
        messageList.setLayoutManager(new LinearLayoutManager(this));
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadMessages();
    }


    private void loadMessages() {
        db.collection("messages")
                .whereEqualTo("senderId", auth.getCurrentUser().getUid()) // Retrieve all messages where currentUserId is the sender
                .get()
                .addOnSuccessListener(senderQuerySnapshot -> {
                    List<Message> allMessages = new ArrayList<>();
                    allMessages.addAll(senderQuerySnapshot.toObjects(Message.class));

                    // Sort messages by timestamp
                    Collections.sort(allMessages, (m1, m2) ->
                            m1.getTimestamp().compareTo(m2.getTimestamp()));

                    // Update UI on main thread
                    runOnUiThread(() -> {
                        chatAdapter = new ChatAdapter(allMessages, auth.getCurrentUser().getUid());
                        messageList.setAdapter(chatAdapter);

                        // Scroll to the last message
                        if (!allMessages.isEmpty()) {
                            messageList.scrollToPosition(allMessages.size() - 1);
                        }

                        Log.d(TAG, "Total messages loaded: " + allMessages.size());
                    });
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error loading messages", e);
                    Toast.makeText(this, "Failed to load messages", Toast.LENGTH_SHORT).show();
                });
    }
}
