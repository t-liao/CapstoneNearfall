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
        // Invokes SLLiteOpenHelper constructor for current DB_NAME and version
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates SQL query to create table in database, only invoked when it does not exist
        String query = "CREATE TABLE " + USER_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " string,"
                + USER_EMAIL + " string,"
                + DATE_OF_BIRTH + " string,"
                + PASSWORD + " string,"
                + PW_HASH + " string,"
                + DETECTION + " string)";
        // Executes query
        db.execSQL(query);
    }

    public void addUser(User user) {
        // Gets database scope
        SQLiteDatabase db = this.getWritableDatabase();
        // Create and put user values (name, email, etc) into new ContentValues var
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        values.put(USER_EMAIL, user.getEmail());
        values.put(DATE_OF_BIRTH, String.valueOf(user.getDob()));
        values.put(PASSWORD, user.getHashedPassword());
        values.put(PW_HASH, "rand_hash");
        values.put(DETECTION, "Off");

        // Insert new user by inserting values, returns -1 if failed
        long result = db.insert(USER_TABLE_NAME, null, values);
        if (result == -1) {
            // TODO: Add error handling
        }
    }

    // Set currently logged in user to param user
    public void setUser(User user) {
        current_user = user;
    }

    // Returns currently logged in user
    public User getUser() {
        return current_user;
    }

    // TODO: Implement getUserByColId
    // Returns type User by searching db for col_id (primary key)
    private User getUserByColId(int col_id) {
        return null;
    }

    // Returns type User by searching database by email
    public User getUserByEmail(String email) {
        //Gets database scope
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor email_query = db.query(USER_TABLE_NAME, new String[]{ID_COL, USER_EMAIL},
                USER_EMAIL+"="+email, null, null,
                null, null, "1");

        // Get primary key of found row
        int col_id = email_query.getInt(0);
        email_query.close();

        // Returns type User by creating User from found values in row
        return getUserByColId(col_id);
    }

    // Sets current users name
    public void setUserName(String name) {
        setString(USERNAME, name);
    }

    // Sets current users email
    public void setEmail(String email) {
        setString(USER_EMAIL, email);
    }

    // Set detection mode for current user
    public void setDetection(String toggle) {
        setString(DETECTION, toggle);
    }

    // Sets purpose mode for current user
    public void setPurpose(String purpose) {
        setString(PURPOSE, purpose);
    }

    // Sets password for current suer
    public void setPassword(String password) {
        //TODO: Implement hashing algorithm for pw storage
    }

    // Updates key in database with new value
    private void setString(String key, String value) {
        // Gets database scope
        SQLiteDatabase db = this.getWritableDatabase();
        // Create and add key/val pair to ContentValues
        ContentValues values = new ContentValues();
        values.put(key, value);

        // Inserts values into table, returns -1 if fails
        long result = db.insert(USER_TABLE_NAME, null, values);
        if (result == -1) {
            // TODO: Add error handling
        }
        db.close();
    }

    // Upgrade override, drops table if exists and creates new one with updated names
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
}