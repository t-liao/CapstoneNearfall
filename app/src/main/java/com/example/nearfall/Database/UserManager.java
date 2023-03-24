package com.example.nearfall.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.nearfall.MainActivity;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class UserManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "nfdb";
    private static final int DB_VERSION = 1;
    private static final String USER_TABLE_NAME = "users";
    private static final String ID_COL = "id";
    private static final String USERNAME = "username";
    private static final String USER_EMAIL = "email";
    private static final String DATE_OF_BIRTH = "date_of_birth";
    private static final String PURPOSE = "purpose";
    private static final String HASHED_PASSWORD = "password";
    private static final String SALT = "salt";
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
                + HASHED_PASSWORD + " string,"
                + SALT + " blob,"
                + PURPOSE + " string)";
        // Executes query
        db.execSQL(query);
    }

    // Adds user to database, throws SQLException if failed
    public void addUser(User user) throws SQLException {
        // Gets database scope
        SQLiteDatabase db = this.getWritableDatabase();
        // Create and put user values (name, email, etc) into new ContentValues var
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        values.put(USER_EMAIL, user.getEmail());
        values.put(DATE_OF_BIRTH, user.getDob());
        values.put(HASHED_PASSWORD, user.getHashedPassword());
        values.put(SALT, user.getSalt());
        values.put(PURPOSE, user.getPurpose());

        // Insert new user by inserting values, returns -1 if failed
        long result = db.insert(USER_TABLE_NAME, null, values);
        if (result == -1) {
            throw new SQLException();
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
        MainActivity.detecting = toggle;
    }

    // Sets purpose mode for current user
    public void setPurpose(String purpose) {
        setString(PURPOSE, purpose);
    }

    // Updates key in database with new value for current user
    private void setString(String key, String value) {
        // Gets database scope
        SQLiteDatabase db = this.getWritableDatabase();
        // Create and add key/val pair to ContentValues
        ContentValues values = new ContentValues();
        values.put(key, value);
        String userEmail = current_user.getEmail();
        int rowId = getCursorFromEmail(userEmail).getColumnIndex(ID_COL);
        // Inserts values into table, returns -1 if fails
        long result = db.update(USER_TABLE_NAME, values, ID_COL+"="+rowId,
                new String[]{String.valueOf(rowId)});
        if (result == -1) {
            // TODO: Add error handling
        }
    }

    public void accountLogout() {
        this.setDetection("Off");
        current_user = null;
    }

    public boolean verifyAccountLogin(String email, String password) {
        User attemptedUserLogin = getUserByEmail(email);
        if (attemptedUserLogin != null && verifyUserPassword(attemptedUserLogin, password))  {
            return true;
        }
        return false;
    }

    // Returns type User by searching database by email
    public User getUserByEmail(String email) {
        //Gets database scope
        Cursor cursor = getCursorFromEmail(email);
        User foundUser;
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            // Get type User by creating User from found values in row if cursor is not empty
            foundUser = getUserFromCursor(cursor);
        } else {
            // User not found in database
            foundUser = null;
        }
        cursor.close();
        return foundUser;
    }

    public boolean emailAlreadyInUse(String email) {
        Cursor cursor = getCursorFromEmail(email);
        return cursor != null && cursor.getCount() > 0;
    }

    // Private functions
    // Returns type User by searching db from cursor
    private User getUserFromCursor(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME));
        String email = cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL));
        String dob = cursor.getString(cursor.getColumnIndexOrThrow(DATE_OF_BIRTH));
        String purpose = cursor.getString(cursor.getColumnIndexOrThrow(PURPOSE));
        String hashedPassword = cursor.getString(cursor.getColumnIndexOrThrow(HASHED_PASSWORD));
        byte[] salt = cursor.getBlob(cursor.getColumnIndexOrThrow(SALT));
        HashedPassword passwordData = null;
        try {
            passwordData = new HashedPassword(hashedPassword, salt, true);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new User(name, email, dob, purpose, passwordData);
    }

    private boolean verifyUserPassword(User user, String attemptedPassword) {
        String correctHashedPassword = user.getHashedPassword();
        HashedPassword hashedPasswordToTest;
        try {
            hashedPasswordToTest = new HashedPassword(attemptedPassword, user.getSalt(), false);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        return Objects.equals(correctHashedPassword, hashedPasswordToTest.getHashedPassword());
    }

    // Returns cursor from email
    private Cursor getCursorFromEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(
                "select * from "+USER_TABLE_NAME +" where "+USER_EMAIL +" = ?", new String[]{email});
    }

    // Upgrade override, drops table if exists and creates new one with updated names
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
}