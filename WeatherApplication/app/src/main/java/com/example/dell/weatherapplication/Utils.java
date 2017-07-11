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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.cert.Certificate;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

/**
 * Created by dell on 06-07-2017.
 */

public class Utils {
    private static String JsonString = "";
    private static String APP_ID = "5d912362975b6279a3d3f4c02316892e";
    private static String API_CALL_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static WeatherDetails weatherDetails = null;
    public static Double latitude;
    public static Double longitude;
    public static String city;

    private Utils() {}

    public static void parseJson(String JsonCode) {


        Log.d("xyz",JsonCode);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JsonCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            try {
                JSONObject weather_array = jsonObject.getJSONArray("weather").getJSONObject(0);
                String description =  weather_array.getString("description");
                String icon_url = "http://openweathermap.org/img/w/"+weather_array.getString("icon")+".png";
                JSONObject main = jsonObject.getJSONObject("main");
                Double humidity = main.getDouble("humidity");
                Double temp = main.getDouble("temp");
                Double min_temp = main.getDouble("temp_min");
                Double max_temp = main.getDouble("temp_max");
                JSONObject wind = jsonObject.getJSONObject("wind");
                Double wind_speed = wind.getDouble("speed");
                Double wind_direction = wind.getDouble("deg");
                JSONObject sys = jsonObject.getJSONObject("sys");
                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String name_of_city = city;
                Long date = jsonObject.getLong("dt");
                weatherDetails = new WeatherDetails(name_of_city,description,temp,humidity,min_temp,max_temp,wind_speed,wind_direction,sunrise,sunset,icon_url,date);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static WeatherDetails getInputStreamFromURL(Double latitude, Double longitude,String city) {
        Utils.city = city;
        Utils.latitude = latitude;
        Utils.longitude = longitude;
        String url_string = API_CALL_URL + "lat="+latitude+"&lon="+longitude+"&APPID="+APP_ID;
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
        return weatherDetails;
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
