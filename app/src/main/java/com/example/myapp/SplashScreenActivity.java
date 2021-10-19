package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private final int SPLASH_SCREEN_TIMEOUT= 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.senku);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },  SPLASH_SCREEN_TIMEOUT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();

    }
}