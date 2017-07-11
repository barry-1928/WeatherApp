package com.example.dell.weatherapplication;

import android.database.sqlite.SQLiteDatabase;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dell on 08-07-2017.
 */

public class WeatherDetails {
    String name_of_city;
    String description;
    String temp;
    String humidity;
    String min_temp;
    String max_temp;
    String wind_speed;
    String wind_direction;
    String sunrise;
    String sunset;
    String icon_url;
    String date;

    WeatherDetails(String name, String description, Double temp, Double humidity, Double min_temp, Double max_temp, Double wind_speed, Double wind_direction, Long sunrise, Long sunset, String icon_url, Long date_long) {
        this.name_of_city = name;
        this.description = String.format(description).toUpperCase();
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        this.temp = decimalFormat.format(temp);
        this.humidity = decimalFormat.format(humidity);
        this.min_temp = decimalFormat.format(min_temp - 273.15);
        this.max_temp = decimalFormat.format(max_temp - 273.15);
        this.wind_speed = decimalFormat.format(wind_speed);
        this.wind_direction = decimalFormat.format(wind_direction);
        this.sunrise = extract_date(sunrise*1000);
        String[] temp1 = this.sunrise.split(" ");
        temp1[1] = temp1[1] + temp1[2];
        this.sunrise = temp1[0] + "\n" + temp1[1];
        this.sunset  = extract_date(sunset*1000);
        temp1 = this.sunset.split(" ");
        temp1[1] = temp1[1] + temp1[2];
        this.sunset = temp1[0] + "\n" + temp1[1];
        this.icon_url = icon_url;
        this.date = DateFormat.getDateTimeInstance().format(new Date());
//        Date date_obj = new Date(date_long*1000);
//        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//        date = dateFormat.format(date_obj);
    }

    String extract_date(Long date) {
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date);
        Date date_object = new Date(timestamp.getTime());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aaa");
        return dateFormat.format(date_object);
    }
}
