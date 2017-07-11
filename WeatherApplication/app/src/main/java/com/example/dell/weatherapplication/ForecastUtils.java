package com.example.dell.weatherapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by dell on 09-07-2017.
 */

public class ForecastUtils {
    private ForecastUtils() {};
    private static ArrayList<WeatherForecast> forecast = null;
    private static String JsonString = "";
    private static String APP_ID = "5d912362975b6279a3d3f4c02316892e";
    private static String API_CALL_URL = "http://api.openweathermap.org/data/2.5/forecast?";

    private static void parseJson(String JsonCode) {

        forecast = new ArrayList<>(5);
        Log.d("xyz",JsonCode);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JsonCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            try {
                JSONArray list = jsonObject.getJSONArray("list");
                String name_of_city = jsonObject.getJSONObject("city").getString("name");
                for(int i = 0; i <= 4; i++ ) {
                    JSONObject object = list.getJSONObject(i*8);
                    String date = object.getString("dt_txt");
                    JSONObject main = object.getJSONObject("main");
                    Double humidity = main.getDouble("humidity");
                    Double temp = main.getDouble("temp");
//                    Double min_temp = main.getDouble("temp_min");
//                    Double max_temp = main.getDouble("temp_max");
                    String description = object.getJSONArray("weather").getJSONObject(0).getString("description");
                    String icon = object.getJSONArray("weather").getJSONObject(0).getString("icon");;
                    String icon_url = "http://openweathermap.org/img/w/"+icon+".png";
                    Double wind_speed = object.getJSONObject("wind").getDouble("speed");
                    Double wind_direction = object.getJSONObject("wind").getDouble("deg");
//                    Double wind_direction = object.getJSONObject("wind").getDouble("deg");
                    WeatherForecast weatherForecast = new WeatherForecast(name_of_city,date,humidity,temp,description,icon_url,wind_speed,wind_direction);
                    forecast.add(weatherForecast);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<WeatherForecast> getForecastDetails() {

        String url_string = API_CALL_URL + "lat="+Utils.latitude+"&lon="+Utils.longitude+"&APPID="+APP_ID;
        URL url = null;
        try {
            url = new URL(url_string);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();
            Log.d("xyz",""+httpURLConnection.getResponseCode());
            if(httpURLConnection.getResponseCode() == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                if(inputStream!=null) {
                    JsonString = getJsonFromInputStream(inputStream);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        parseJson(JsonString);
        return forecast;
    }

    public static String getJsonFromInputStream(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("utf-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(line!=null) {
            stringBuilder.append(line);
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }
}
