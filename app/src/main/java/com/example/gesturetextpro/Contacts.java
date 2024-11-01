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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        contactsList = loadContacts();
        ContactsAdapter adapter = new ContactsAdapter(this, contactsList);
        contactsListView.setAdapter(adapter);

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
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<String> loadContacts() {
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