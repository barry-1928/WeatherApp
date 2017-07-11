package com.example.dell.weatherapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import Databases.MyHelper;

import static Databases.ContractClass.StorageTable.DATE_AND_TIME_COL;
import static Databases.ContractClass.StorageTable.DESCRIPTION_COL;
import static Databases.ContractClass.StorageTable.MAX_TEMP_COL;
import static Databases.ContractClass.StorageTable.MIN_TEMP_COL;
import static Databases.ContractClass.StorageTable.NAME_OF_CITY_COL;
import static Databases.ContractClass.StorageTable.SUNRISE_COL;
import static Databases.ContractClass.StorageTable.SUNSET_COL;
import static Databases.ContractClass.StorageTable.TABLE_NAME;
import static Databases.ContractClass.StorageTable.WIND_DIRECTION_COL;
import static Databases.ContractClass.StorageTable.WIND_SPEED_COL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class WeatherFragment extends Fragment {

    WeatherDetails weatherDetails = null;
    public WeatherFragment() {
    }

    public void setData(WeatherDetails weatherDetails) {
        this.weatherDetails = weatherDetails;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyHelper myHelper = new MyHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_OF_CITY_COL,weatherDetails.name_of_city);
        contentValues.put(DESCRIPTION_COL,weatherDetails.description);
        contentValues.put(MIN_TEMP_COL,weatherDetails.min_temp);
        contentValues.put(MAX_TEMP_COL,weatherDetails.max_temp);
        contentValues.put(WIND_SPEED_COL,weatherDetails.wind_speed);
        contentValues.put(WIND_DIRECTION_COL,weatherDetails.wind_direction);
        contentValues.put(SUNRISE_COL,weatherDetails.sunrise);
        contentValues.put(SUNSET_COL,weatherDetails.sunset);
        contentValues.put(DATE_AND_TIME_COL,weatherDetails.date);
        long id = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if(id != -1) {
            Log.d("xyz","Item inserted at index "+id);
        }
        sqLiteDatabase.close();
        Button forecast = (Button) getView().findViewById(R.id.next_five_days);
        forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),further_forecast_activity.class);
                intent.putExtra("city",weatherDetails.name_of_city);
                startActivity(intent);
            }
        });
        TextView city = (TextView) getView().findViewById(R.id.city_name);
        city.setText(weatherDetails.name_of_city);
        TextView desc = (TextView) getView().findViewById(R.id.description);
        desc.setText(weatherDetails.description);
        TextView max_temp = (TextView) getView().findViewById(R.id.max_temp);
        max_temp.setText("" + weatherDetails.max_temp + "\u2103");
        TextView min_temp = (TextView) getView().findViewById(R.id.min_temp);
        min_temp.setText("" + weatherDetails.min_temp + "\u2103");
        TextView humidity = (TextView) getView().findViewById(R.id.humidity);
        humidity.setText("" + weatherDetails.humidity+ " %");
        TextView wind_speed = (TextView) getView().findViewById(R.id.wind_speed);
        wind_speed.setText("" + weatherDetails.wind_speed + " MPH");
        TextView wind_direction = (TextView) getView().findViewById(R.id.wind_degree);
        wind_direction.setText("" + weatherDetails.wind_direction + "78\u00B0");
        TextView sunrise = (TextView) getView().findViewById(R.id.sunrise);
        sunrise.setText(weatherDetails.sunrise);
        TextView sunset = (TextView) getView().findViewById(R.id.sunset);
        sunset.setText(weatherDetails.sunset);



    }
}
