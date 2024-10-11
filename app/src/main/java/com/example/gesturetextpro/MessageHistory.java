package com.example.gesturetextpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MessageHistory extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_history);
    }

    public void home(View view) {
        Intent intent = new Intent(MessageHistory.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}