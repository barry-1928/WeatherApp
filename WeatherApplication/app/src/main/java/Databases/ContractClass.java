package Databases;

import android.provider.BaseColumns;

/**
 * Created by dell on 10-07-2017.
 */

public class ContractClass {

    private ContractClass() {}

    public static class StorageTable implements BaseColumns {
        public static String TABLE_NAME = "StorageTable";
        public static String NAME_OF_CITY_COL = "CityName";
        public static String DESCRIPTION_COL = "Description";
        public static String MIN_TEMP_COL = "MinTemp";
        public static String MAX_TEMP_COL = "MaxTemp";
        public static String WIND_SPEED_COL = "WindSpeed";
        public static String WIND_DIRECTION_COL = "WindDirection";
        public static String SUNRISE_COL = "Sunrise";
        public static String SUNSET_COL = "Sunset";
        public static String DATE_AND_TIME_COL ="DateTime";

        public static String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+_ID+" PRIMARY KEY, "+NAME_OF_CITY_COL+" VARCHAR(255), "+DESCRIPTION_COL+" VARCHAR(255), "+MIN_TEMP_COL+" REAL, "+MAX_TEMP_COL+" REAL, "+WIND_SPEED_COL+" REAL, "+WIND_DIRECTION_COL+" REAL, "+SUNRISE_COL+" VARCHAR(255), "+SUNSET_COL+" VARCHAR(255), "+DATE_AND_TIME_COL+" VARCHAR(255))";
        public static String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME+"";
    }
}