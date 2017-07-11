package com.example.dell.weatherapplication;

import android.app.FragmentManagerNonConfig;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import Databases.MyHelper;

import static Databases.ContractClass.StorageTable.TABLE_NAME;

public class MainActivity extends AppCompatActivity {
    LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main_activity);
        toolbar.setTitle("Weather App");
        toolbar.setTitleTextColor(Color.parseColor("#ffff00"));
        toolbar.inflateMenu(R.menu.menu_main_activity);
        FloatingActionButton floatingActionButton;
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchHistoryActivity.class);
                startActivity(intent);
            }
        });
//        MyHelper myHelper = new MyHelper(this);
//        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
//        sqLiteDatabase.delete(TABLE_NAME,null,null);
//        sqLiteDatabase.close();
        PlaceAutocompleteFragment placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        placeAutocompleteFragment.setHint("Please enter the name of a city here");
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                latLng = place.getLatLng();
                Log.d("xyz","Lat=" + latLng.latitude + " " + "Long="+latLng.longitude);
                MyAsyncTask asyncTask = new MyAsyncTask(place.getName().toString());
                asyncTask.execute(latLng.latitude,latLng.longitude);
            }

            @Override
            public void onError(Status status) {
                Log.d("xyz","Error: "+ status);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity,menu);
        return true;
    }

    public void openAppDetailsDialog(MenuItem menuItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Weather App Info");
        builder.setItems(new String[] {getResources().getString(R.string.app_details)},null);
        builder.show();
    }

    class MyAsyncTask extends AsyncTask<Double,Void,WeatherDetails> {
        String city;

        MyAsyncTask(String city) {
            this.city = city;
        }

        @Override
        protected WeatherDetails doInBackground(Double... params) {
            WeatherDetails weatherDetails = Utils.getInputStreamFromURL(params[0],params[1],city);
            return weatherDetails;
        }

        @Override
        protected void onPostExecute(WeatherDetails weatherDetails) {
            WeatherFragment weatherFragment = new WeatherFragment();
            weatherFragment.setData(weatherDetails);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,weatherFragment,"weather_fragment");
            fragmentTransaction.commit();
        }
    }
}
