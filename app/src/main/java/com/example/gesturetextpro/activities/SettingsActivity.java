package com.example.gesturetextpro.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.gesturetextpro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private EditText usernameEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        usernameEditText = findViewById(R.id.usernameEditText);
        saveButton = findViewById(R.id.saveButton);

        db.collection("users")
                .document(auth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        String email = documentSnapshot.getString("email");

                        TextView userView = findViewById(R.id.usernameTextView);
                        userView.setText(username);
                        TextView emailTextView = findViewById(R.id.emailTextView);
                        emailTextView.setText(email);
                    } else {
                        Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to fetch data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });


        saveButton.setOnClickListener(v -> {
            String newUsername = usernameEditText.getText().toString();
            db.collection("users")
                    .document(auth.getCurrentUser().getUid())
                    .update("username", newUsername);
        });
    }
}
