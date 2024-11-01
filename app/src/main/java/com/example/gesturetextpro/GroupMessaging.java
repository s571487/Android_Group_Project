package com.example.gesturetextpro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class GroupMessaging extends AppCompatActivity {
    private ListView groupListView;
    private List<String> groupList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_messaging);

        groupListView = findViewById(R.id.group_list);

        // Initialize the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Load group data (stubbed)
        groupList = loadGroupData();

        // Set up adapter to display groups
        GroupAdapter adapter = new GroupAdapter(this, groupList);
        groupListView.setAdapter(adapter);

        // Set click listener to open group conversations
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedGroup = groupList.get(position);
                openGroupConversation(selectedGroup);
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

    private List<String> loadGroupData() {
        // Stubbed data - replace with actual group data retrieval
        List<String> groups = new ArrayList<>();
        groups.add("Family Group");
        groups.add("Friends Group");
        groups.add("Project Team");
        return groups;
    }

    private void openGroupConversation(String groupName) {
        Intent intent = new Intent(this, GestureInput.class);
        intent.putExtra("group_name", groupName);
        startActivity(intent);
        Toast.makeText(this, "Opening conversation for " + groupName, Toast.LENGTH_SHORT).show();
    }
}
