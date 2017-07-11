package com.example.dell.weatherapplication;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

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

public class SearchHistoryActivity extends AppCompatActivity {
    ListView search_list;
    ArrayList<Data> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_history_layout);
        search_list = (ListView) findViewById(R.id.search_list);
        datas = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search_history_activity);
        toolbar.setTitle("Search History");
        toolbar.setTitleTextColor(Color.parseColor("#ffff00"));
        toolbar.inflateMenu(R.menu.menu_search_history_activity);
        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,new String[] {NAME_OF_CITY_COL,DATE_AND_TIME_COL,DESCRIPTION_COL,MIN_TEMP_COL,MAX_TEMP_COL,WIND_SPEED_COL,WIND_DIRECTION_COL,SUNRISE_COL,SUNSET_COL},null,null,null,null,null);
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                String city = cursor.getString(cursor.getColumnIndex(NAME_OF_CITY_COL));
                String date = cursor.getString(cursor.getColumnIndex(DATE_AND_TIME_COL));
                String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_COL));
                String min_temp = cursor.getString(cursor.getColumnIndex(MIN_TEMP_COL));
                String max_temp = cursor.getString(cursor.getColumnIndex(MAX_TEMP_COL));
                String wind_speed = cursor.getString(cursor.getColumnIndex(WIND_SPEED_COL));
                String wind_direction = cursor.getString(cursor.getColumnIndex(WIND_DIRECTION_COL));
                String sunrise = cursor.getString(cursor.getColumnIndex(SUNRISE_COL));
                String sunset = cursor.getString(cursor.getColumnIndex(SUNSET_COL));
                datas.add(new Data(city, date,description,min_temp,max_temp,wind_speed,wind_direction,sunrise,sunset));
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        Collections.reverse(datas);
        SearchListAdapter searchListAdapter = new SearchListAdapter();
        search_list.setAdapter(searchListAdapter);
        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchHistoryActivity.this);
                Data temp = datas.get(position);
                String[] items = {"Description: "+temp.description+"","Max Temp: "+temp.max_temp+"℃","Min Temp: "+temp.min_temp+"℃","Wind Speed: "+temp.wind_speed+" MPH","Wind Direction: "+temp.wind_direction+"\u00B0","Sunrise: "+temp.sunrise+"","Sunset: "+temp.sunset+""};
                builder.setTitle(temp.city + "   " + temp.date);
                builder.setItems(items,null);
                builder.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_history_activity,menu);
        return true;
    }

    public void openSearchHistoryDetailsDialog(MenuItem menuItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search Details Info");
        builder.setItems(new String[] {getResources().getString(R.string.search_history_details)},null);
        builder.show();
    }


    class Data {
        String city;
        String date;
        String description,min_temp,max_temp,wind_speed,wind_direction,sunrise,sunset;

        Data(String city, String date,String description,String min_temp,String max_temp,String wind_speed,String wind_direction,String sunrise,String sunset) {
            this.city = city;
            this.date = date;
            this.description = description;
            this.max_temp = max_temp;
            this.min_temp = min_temp;
            this.sunrise = sunrise;
            this.sunset = sunset;
            this.wind_speed = wind_speed;
            this.wind_direction = wind_direction;
        }

    }

    class SearchListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            MyHolder myHolder = null;
            if(row == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.single_row_for_city_and_date,parent,false);
                myHolder = new MyHolder(row);
                row.setTag(myHolder);
            }
            else {
                myHolder = (MyHolder) row.getTag();
            }

            Data temp = datas.get(position);
            myHolder.date_textview.setText(temp.date);
            myHolder.city_name_textview.setText(temp.city);

            return row;
        }
    }

    class MyHolder {
        TextView city_name_textview;
        TextView date_textview;

        MyHolder(View view) {
            city_name_textview = (TextView) view.findViewById(R.id.city_name_history);
            date_textview = (TextView) view.findViewById(R.id.date_history);
        }
    }

}


