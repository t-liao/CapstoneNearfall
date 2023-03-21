package com.example.nearfall.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;


public class UserManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "nfdb";
    private static final int DB_VERSION = 1;
    private static final String USER_TABLE_NAME = "users";
    private static final String ID_COL = "id";
    private static final String USERNAME = "username";
    private static final String USER_EMAIL = "email";
    private static final String DATE_OF_BIRTH = "date_of_birth";
    private static final String PURPOSE = "purpose";
    private static final String PASSWORD = "password";
    private static final String PW_HASH = "pw_hash";
    private static final String DETECTION = "detection";
    private static User current_user;



    public UserManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + USER_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " string,"
                + USER_EMAIL + " string,"
                + DATE_OF_BIRTH + " string,"
                + PASSWORD + " string,"
                + PW_HASH + " string,"
                + DETECTION + " string)";

        db.execSQL(query);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        values.put(USER_EMAIL, user.getEmail());
        values.put(DATE_OF_BIRTH, String.valueOf(user.getDob()));
        values.put(PASSWORD, user.getHashedPassword());
        values.put(PW_HASH, "rand_hash");
        values.put(DETECTION, "Off");

        long result = db.insert(USER_TABLE_NAME, null, values);
        if (result == -1) {
            // TODO: Add error handling
        }
    }

    public void setUser(User user) {
        current_user = user;
    }

    public User getUser() {
        return current_user;
    }

    private User getUserByColId(int col_id) {
        return null;
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor email_query = db.query(USER_TABLE_NAME, new String[]{ID_COL, USER_EMAIL},
                USER_EMAIL+"="+email, null, null,
                null, null, "1");

        int col_id = email_query.getInt(0);
        email_query.close();
        User fetched_user = getUserByColId(col_id);


        return null;
    }

    public void setUserName(String name) {
        setString(USERNAME, name);
    }

    public void setEmail(String email) {
        setString(USER_EMAIL, email);
    }

    public void setDetection(String toggle) {
        setString(DETECTION, toggle);
    }

    public void setPurpose(String purpose) {
        setString(PURPOSE, purpose);
    }

    public void setPassword(String password) {

    }

    private void setString(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(key, value);
        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
}