package com.example.dell.weatherapplication;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dell on 09-07-2017.
 */

public class WeatherForecast {
    String date;String humidity;String temp;String description;String icon_url;Double wind_speed;
    String city_name;String wind_direction;

    WeatherForecast(String city_name,String date,Double humidity,Double temp,String description,String icon_url,Double wind_speed, Double wind_direction) {
        this.city_name = Utils.city;
        String[] date_time = date.split(" ");
        this.date = date_time[0];
        //String[] year_month_date = date.split("-");
        //DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        //Calendar calendar = Calendar.getInstance();
        //calendar.set(Integer.parseInt(year_month_date[0]),Integer.parseInt(year_month_date[1]),Integer.parseInt(year_month_date[2]));
        //Date date1 = calendar.getTime();
        //this.date = dateFormat.format(date1);
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        this.humidity = decimalFormat.format(humidity);
        this.temp = decimalFormat.format(temp - 273.15);
        this.description = description.toUpperCase();
        this.icon_url = icon_url;
        this.wind_speed = wind_speed;
        this.wind_direction = decimalFormat.format(wind_direction);
    }
}
