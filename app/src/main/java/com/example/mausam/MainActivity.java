package com.example.mausam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class getweather extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            
        }
    }
}
