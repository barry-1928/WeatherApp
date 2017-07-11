package Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static Databases.ContractClass.StorageTable.CREATE_TABLE;
import static Databases.ContractClass.StorageTable.DROP_TABLE;

/**
 * Created by dell on 10-07-2017.
 */

public class MyHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "MyDatabase";
    public static int DATABASE_VERSION = 2;


    public MyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
