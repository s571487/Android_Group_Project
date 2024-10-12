package com.example.gesturetextpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class GestureCustom extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_custom);
    }

    public void home(View view) {
        Intent intent = new Intent(GestureCustom.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}