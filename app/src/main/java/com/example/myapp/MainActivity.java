package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import static android.R.attr.button;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button next_page;
    private  long backPressed;
    private Toast backToast;
    private static ImageView imgview;
    private static Button buttonchg;
    private int current_image;
    private Button Replaysound;
    private int styleNum = 0;
    int[] images = {R.drawable.senku1,R.drawable.senku2,R.drawable.senku3,R.drawable.senku4,R.drawable.senku5};
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.next_page = findViewById(R.id.next_page);
        buttonclick();

        //jouer le son

        this.mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.senku);}

        //changer img

        public void buttonclick() {
            Replaysound = findViewById(R.id.replay_sound);

            imgview = (ImageView) findViewById(R.id.imageView);
            buttonchg = (Button) findViewById(R.id.change_style);
            buttonchg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    current_image++;
                    current_image = current_image % images.length;
                    imgview.setImageResource(images[current_image]);

                    Log.i("DEBUG", "Bouton cliqué Change Style");
                    if(styleNum == 0) {
                        buttonchg.setBackgroundColor(RED);
                        Replaysound.setBackgroundColor(BLUE);
                        next_page.setBackgroundColor(GREEN);
                        styleNum = 1;
                    } else {
                        imgview.setImageResource(images[current_image]);
                        buttonchg.setBackgroundColor(BLACK);
                        Replaysound.setBackgroundColor(BLACK);
                        next_page.setBackgroundColor(BLACK);
                        styleNum = 0;
                    }

                }
            });

        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherActivity = new Intent(getApplicationContext(),MainActivity2.class);
                        startActivity(otherActivity);

            }
        })
        ;}


    public void playSound(View view) {
        mediaPlayer.start();

    }

    @Override
    public void onBackPressed() {

        if(backPressed+2000> System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();

            return;
        }
        else{
            backToast=  Toast.makeText(getBaseContext(),"Appuyer encore pour quitter", Toast.LENGTH_SHORT);
                    backToast.show();
        }
            backPressed = System.currentTimeMillis();
    }


        }