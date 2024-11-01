package com.example.gesturetextpro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MessageHistory extends AppCompatActivity {
    private ListView messageHistoryListView;
    private List<String> messageHistoryList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        messageHistoryListView = findViewById(R.id.message_history_list);

        messageHistoryList = loadMessageHistory();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageHistoryList);
        messageHistoryListView.setAdapter(adapter);

        messageHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openMessageThread(position);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<String> loadMessageHistory() {
        List<String> history = new ArrayList<>();
        history.add("John Doe - Hey! How's it going?");
        history.add("Jane Smith - Meeting rescheduled to 3 PM.");
        history.add("Family Group - Don't forget dinner at 7.");
        return history;
    }

    private void openMessageThread(int position) {
        String conversation = messageHistoryList.get(position);
        Toast.makeText(this, "Opening conversation with: " + conversation, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MessageHistory.this, GestureInput.class);
        intent.putExtra("conversationId", position);
        startActivity(intent);
    }
}