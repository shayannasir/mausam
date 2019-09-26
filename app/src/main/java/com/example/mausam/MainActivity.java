package com.example.mausam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView cityInput;
    TextView cityName;
    TextView weatherDisplay;
    TextView tempDisplay;

    public class getWeather extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in  = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data!=-1){
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }
                return result;

            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject json = new JSONObject(s);
                String name = json.getString("name");
                cityName.setText(name);

                String main = json.getString("main");
                JSONObject json_temp = new JSONObject(main);
                String temperature = json_temp.getString("temp");
                Double temp = Double.parseDouble(temperature);
                temp = temp - 273.15;
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                tempDisplay.setText("Temperature: "+df.format(temp).toString()+"°C");

                String message = "";

                String weatherInfo = json.getString("weather");
                JSONArray arr = new JSONArray(weatherInfo);
                JSONObject part = arr.getJSONObject(0);
                String weather = part.getString("main");
                String description  =  part.getString("description");

                if(!weather.equals("")&&!description.equals("")){
                    message = "Weather: "+weather+" ("+description+")";
                }
                if(!message.equals("")){
                    weatherDisplay.setText(message);;
                }
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), "You dumb? Check typo or learn geography.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    public void fetchWeather(View view){
        if(cityInput.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "You blind ? Why no enter city name ?", Toast.LENGTH_LONG).show();
        }
        else{
            getWeather weather = new getWeather();
            weather.execute("http://api.openweathermap.org/data/2.5/weather?q="+cityInput.getText().toString()+"&appid=4d884df145ae2387719f0a348f9d034f");

            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityInput = findViewById(R.id.cityInput);
        cityName = findViewById(R.id.cityName);
        weatherDisplay = findViewById(R.id.weather);
        tempDisplay = findViewById(R.id.temperature);


    }

}
