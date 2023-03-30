package com.example.nearfall.MainDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database  extends SQLiteOpenHelper {
    public static final String DB_NAME = "nfdb";
    public static final int DB_VERSION = 1;
    public static final String USER_TABLE_NAME = "users";
    public static final String ID_COL = "id";
    public static final String USERNAME = "username";
    public static final String USER_EMAIL = "email";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    public static final String PURPOSE = "purpose";
    public static final String HASHED_PASSWORD = "password";
    public static final String SALT = "salt";
    public static final String LOCATION_TABLE_NAME = "locations";
    public static final String LATITUDE_COL = "latitude";
    public static final String LONGITUDE_COL = "longitude";
    public static final String USER_ID = "user_id";
    public static final String TIMESTAMP = "timestamp";

    public Database(Context context) {
        // Invokes SLLiteOpenHelper constructor for current DB_NAME and version
        super(context, DB_NAME, null, DB_VERSION);
    }

    // On database create, executes following SQL
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates SQL query to create user table in database
        String createUserTable = "CREATE TABLE " + USER_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " string,"
                + USER_EMAIL + " string,"
                + DATE_OF_BIRTH + " string,"
                + HASHED_PASSWORD + " string,"
                + SALT + " blob,"
                + PURPOSE + " string)";
        // Executes query
        db.execSQL(createUserTable);

        // Creates SQL query to create location table in database
        String createLocationTable = "CREATE TABLE " + LOCATION_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LATITUDE_COL + " DOUBLE,"
                + LONGITUDE_COL + " DOUBLE,"
                + TIMESTAMP + "DATETIME,"
                + USER_ID + " INTEGER)";
        // Executes query
        db.execSQL(createLocationTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE_NAME);
        onCreate(db);
    }
}
