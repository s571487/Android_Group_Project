package com.example.gesturetextpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class GroupMessaging extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_messaging);
    }

    public void groupmessaging(View view) {
        Intent intent = new Intent(GroupMessaging.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}