package edu.scu.levelup;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    SQLiteDatabase db;

    AppDatabase(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    public String Login(String username)
    {
        db = this.getReadableDatabase();
        String query = "Select emailid, password from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String userName;
        String passWord;
        passWord = "Not Found";
        Log.e("AJAY", " VIDEKAR " + passWord);
        if(cursor.moveToFirst())
        {
            do {
                userName = cursor.getString(0);
                Log.e("AJAY", " VIDEKAR " + userName);
                if (userName == username)
                {
                    passWord = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return passWord;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
