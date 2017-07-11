package com.example.dell.weatherapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class further_forecast_activity extends AppCompatActivity {
    ListView forecast_list;
    TextView city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.further_forecast_activity);
        forecast_list = (ListView) findViewById(R.id.list);
        AsyncTaskForForecast task = new AsyncTaskForForecast();
        city = (TextView) findViewById(R.id.city);
        task.execute();
    }

    class AsyncTaskForForecast extends AsyncTask<Void, Void, ArrayList<WeatherForecast>> {


        @Override
        protected ArrayList<WeatherForecast> doInBackground(Void... params) {
            ArrayList<WeatherForecast> forecast = new ArrayList<>();;
            forecast = ForecastUtils.getForecastDetails();
            return forecast;
        }

        @Override
        protected void onPostExecute(ArrayList<WeatherForecast> weatherForecasts) {
            MyAdapter adapter = new MyAdapter(weatherForecasts, further_forecast_activity.this);
            city.setText(weatherForecasts.get(0).city_name);
            forecast_list.setAdapter(adapter);

        }
    }
}

class MyAdapter extends BaseAdapter {

    ArrayList<WeatherForecast> weatherForecasts;
    Context context;

    MyAdapter(ArrayList<WeatherForecast> weatherForecasts, Context context) {
        this.weatherForecasts = weatherForecasts;
        this.context = context;
    }


    @Override
    public int getCount() {
        return weatherForecasts.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherForecasts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyHolder holder = null;
        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.single_row_for_forecast,parent,false);
            holder = new MyHolder(row);
            row.setTag(holder);
        }
        else {
            holder = (MyHolder) row.getTag();
        }
        WeatherForecast temp = weatherForecasts.get(position);
        holder.description.setText(temp.description);
        holder.date.setText(temp.date);
        holder.humidity.setText(""+temp.humidity+" %");
        holder.speed.setText(""+temp.wind_speed+" MPH");
        holder.direction.setText(""+temp.wind_direction);
        holder.temp.setText(""+temp.temp+"\u2103");
        Picasso.with(context).load(temp.icon_url).into(holder.icon);
        return row;
    }
}

class MyHolder {
    TextView description;
    TextView date;
    TextView temp;
    TextView speed;
    TextView direction;
    TextView humidity;
    ImageView icon;
    MyHolder(View view) {
        description = (TextView) view.findViewById(R.id.description123);
        date = (TextView) view.findViewById(R.id.date123);
        temp = (TextView) view.findViewById(R.id.temp123);
        speed = (TextView) view.findViewById(R.id.wind_speed123);
        direction = (TextView) view.findViewById(R.id.wind_degree123);
        humidity = (TextView) view.findViewById(R.id.humidity123);
        icon = (ImageView) view.findViewById(R.id.icon123);
    }

}
