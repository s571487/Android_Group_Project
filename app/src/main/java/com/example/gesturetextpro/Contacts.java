package com.example.gesturetextpro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class Contacts extends AppCompatActivity {
    private ListView contactsListView;
    private List<String> contactsList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactsListView = findViewById(R.id.contacts_list);

        // Initialize the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Load contacts (stubbed function with hard-coded values)
        contactsList = loadContacts();

        // Set up adapter for displaying contacts
        ContactsAdapter adapter = new ContactsAdapter(this, contactsList);
        contactsListView.setAdapter(adapter);

        // Set click listener for each contact item
        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedContact = contactsList.get(position);
                openGestureInputActivity(selectedContact);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back when Up button is pressed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<String> loadContacts() {
        // Stubbed data - replace with actual contacts data retrieval
        List<String> contacts = new ArrayList<>();
        contacts.add("Alice Johnson");
        contacts.add("Bob Smith");
        contacts.add("Charlie Brown");
        return contacts;
    }

    private void openGestureInputActivity(String contact) {
        Intent intent = new Intent(this, GestureInput.class);
        intent.putExtra("contact_name", contact);
        startActivity(intent);
    }
}