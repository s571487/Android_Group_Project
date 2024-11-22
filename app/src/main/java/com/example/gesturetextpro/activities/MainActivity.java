package com.example.gesturetextpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.gesturetextpro.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button chatButton = findViewById(R.id.chatButton);
        Button messageHistoryButton = findViewById(R.id.messageHistoryButton);
        Button contactsButton = findViewById(R.id.contactsButton);
        Button gestureCustomizationButton = findViewById(R.id.gestureCustomizationButton);
        Button settingsButton = findViewById(R.id.settingsButton);

        chatButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        });

        messageHistoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MessageHistoryActivity.class);
            startActivity(intent);
        });

        contactsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        });

        gestureCustomizationButton.setOnClickListener(v -> {
            startActivity(new Intent(this, GestureManagementActivity.class));
        });

        settingsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
