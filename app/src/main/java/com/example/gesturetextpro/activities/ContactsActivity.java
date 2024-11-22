package com.example.gesturetextpro.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gesturetextpro.R;
import com.example.gesturetextpro.adapters.UserAdapter;
import com.example.gesturetextpro.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private RecyclerView userList;
    private UserAdapter userAdapter;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userList = findViewById(R.id.userList);
        users = new ArrayList<>();
        userAdapter = new UserAdapter(users, this);
        userList.setLayoutManager(new LinearLayoutManager(this));
        userList.setAdapter(userAdapter);

        loadUsers();
    }

    private void loadUsers() {
        String currentUserId = auth.getCurrentUser().getUid();
        db.collection("users")
                .whereNotEqualTo("userId", currentUserId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    users.clear();
                    for (User user : queryDocumentSnapshots.toObjects(User.class)) {
                        users.add(user);
                    }
                    userAdapter.notifyDataSetChanged();
                });
    }
    @Override
    public void onUserClick(User user) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("receiverId", user.getUserId());
        intent.putExtra("receiverName", user.getUsername());
        startActivity(intent);
    }

}
