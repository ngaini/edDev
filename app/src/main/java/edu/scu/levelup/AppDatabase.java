package edu.scu.levelup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ajay on 2016-02-20.
 */
public class AppDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AppDatabase.db";
    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_FULLNAME = "fullname";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_PHONE_NUMBER = "phonenumber";
    public static final String COLUMN_EMAILID = "emailid";
    public static final String COLUMN_PASSWORD = "password";
    public static final String query = "CREATE TABLE "+TABLE_NAME+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_ROLE+" VARCHAR(255), "+COLUMN_FULLNAME+" VARCHAR(255), "+COLUMN_AGE+" VARCHAR(5), "+COLUMN_PHONE_NUMBER+" VARCHAR(255), "+COLUMN_EMAILID+" VARCHAR(320), "+COLUMN_PASSWORD+" VARCHAR(255));";


    AppDatabase(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
