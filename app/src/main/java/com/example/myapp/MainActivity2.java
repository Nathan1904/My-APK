package com.example.myapp;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {
    private TextView editText;
    private Button previous_page;
    private Button btnGetFromAPI;
    private Button btnSave;
    private Button btnLoad;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://api.github.com/zen";

    SharedPreferences sharedPreferences;

    /// seconde activité

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.previous_page = findViewById(R.id.previous_page);
        editText = (TextView) findViewById(R.id.editText);
        btnGetFromAPI = (Button) findViewById(R.id.btnGetFromAPI);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        previous_page = (Button) findViewById(R.id.previous_page);
        btnLoad.setOnClickListener(btnLoadListener);
        btnSave.setOnClickListener(btnSaveListener);
        btnGetFromAPI.setOnClickListener(btnGetFromAPIListener);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("storedMessage", getString(R.string.text_)); // value to store
        editor.commit();
        editText.setText(getString(R.string.text_));

        editText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                editText.setTextColor(RED);
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });


        previous_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherActivity = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(otherActivity);
            }
        });
    }

    private View.OnClickListener btnLoadListener = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {

            Log.i("DEBUG", "Bouton cliqué Load");

            editText.setText(sharedPreferences.getString("storedMessage", ""));
        }
    };

    private View.OnClickListener btnSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Log.i("DEBUG", "Bouton cliqué Save");

            String message;

            message = editText.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("storedMessage", message); // value to store
            editor.commit();

            Log.i("DEBUG", "messageSaved = " + message);
            editText.setTextColor(BLACK);
        }
    };

    private View.OnClickListener btnGetFromAPIListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Log.i("DEBUG", "Bouton cliqué Get from API");

            sendAndRequestResponse();
        }
    };

    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                editText.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                //Log.i(TAG,"Error :" + volleyError.toString());
                if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "No Connection/Communication Error!", Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Authentication/ Auth Error!", Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                }
                editText.setText("API request error");
            }
        });

        mRequestQueue.add(mStringRequest);
    }

}



