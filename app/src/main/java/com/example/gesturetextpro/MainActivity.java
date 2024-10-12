package com.example.gesturetextpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gestureinput(View view) {
        Intent intent = new Intent(MainActivity.this,GestureInput.class);
        startActivity(intent);
    }

    public void messagehistory(View view) {
        Intent intent = new Intent(MainActivity.this,MessageHistory.class);
        startActivity(intent);
    }

    public void contacts(View view) {
        Intent intent = new Intent(MainActivity.this,Contacts.class);
        startActivity(intent);
    }

    public void gesturecustom(View view) {
        Intent intent = new Intent(MainActivity.this,GestureCustom.class);
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(MainActivity.this,Settings.class);
        startActivity(intent);
    }
}
