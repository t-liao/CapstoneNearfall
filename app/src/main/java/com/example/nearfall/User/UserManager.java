package com.example.nearfall.User;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.nearfall.MainActivity;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import com.example.nearfall.MainDatabase.Database;

public class UserManager {
    private static User current_user;

    // Adds user to database, throws SQLException if failed
    public int addUser(User user) throws SQLException {
        // Gets database scope
        SQLiteDatabase db = MainActivity.getDatabase().getWritableDatabase();
        // Create and put user values (name, email, etc) into new ContentValues var
        ContentValues values = new ContentValues();
        values.put(Database.USERNAME, user.getUsername());
        values.put(Database.USER_EMAIL, user.getEmail());
        values.put(Database.DATE_OF_BIRTH, user.getDob());
        values.put(Database.HASHED_PASSWORD, user.getHashedPassword());
        values.put(Database.SALT, user.getSalt());
        values.put(Database.PURPOSE, user.getPurpose());

        // Insert new user by inserting values, returns -1 if failed
        long result = db.insert(Database.USER_TABLE_NAME, null, values);
        if (result == -1) {
            throw new SQLException();
        }
        // Cast to int, not accurate over 2<<31 but not likely to have more than this
        return (int) result;
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
        setString(Database.USERNAME, name);
        current_user.setUsername(name);
    }

    // Sets current users email
    public void setEmail(String email) {
        setString(Database.USER_EMAIL, email);
        current_user.setEmail(email);
    }

    // Set detection mode for current user
    public void setDetection(String toggle) {
        MainActivity.detecting = toggle;
    }

    // Sets purpose mode for current user
    public void setPurpose(String purpose) {
        setString(Database.PURPOSE, purpose);
        current_user.setPurpose(purpose);

    }

    // Updates key in database with new value for current user
    private void setString(String key, String value) {
        // Gets database scope
        SQLiteDatabase db = MainActivity.getDatabase().getWritableDatabase();
        // Create and add key/val pair to ContentValues
        ContentValues values = new ContentValues();
        values.put(key, value);
        int rowId = current_user.getId();
        // Inserts values into table, returns -1 if fails
        db.update(Database.USER_TABLE_NAME, values,
                Database.ID_COL+"="+ rowId, null);
    }

    public void accountLogout() {
        this.setDetection("Off");
        current_user = null;
    }

    public boolean verifyAccountLogin(String email, String password) {
        User attemptedUserLogin = getUserByEmail(email);
        if (attemptedUserLogin != null && verifyUserPassword(attemptedUserLogin, password)) {
            return true;
        }
        return false;
    }

    // Returns type User by searching database by email
    public User getUserByEmail(String email) {
        //Gets cursor by email
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

    // Returns cursor from email
    public Cursor getCursorFromEmail(String email) {
        SQLiteDatabase db = MainActivity.getDatabase().getWritableDatabase();
        return db.rawQuery(
                "select * from " + Database.USER_TABLE_NAME + " where " + Database.USER_EMAIL +
                        " = ?", new String[]{email});
    }

    // Private functions
    // Returns type User by searching db from cursor
    private User getUserFromCursor(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndexOrThrow(Database.USERNAME));
        String email = cursor.getString(cursor.getColumnIndexOrThrow(Database.USER_EMAIL));
        String dob = cursor.getString(cursor.getColumnIndexOrThrow(Database.DATE_OF_BIRTH));
        String purpose = cursor.getString(cursor.getColumnIndexOrThrow(Database.PURPOSE));
        String hashedPassword = cursor.getString(cursor.getColumnIndexOrThrow(Database.HASHED_PASSWORD));
        int userId = cursor.getInt(cursor.getColumnIndexOrThrow(Database.ID_COL));
        byte[] salt = cursor.getBlob(cursor.getColumnIndexOrThrow(Database.SALT));
        HashedPassword passwordData = null;
        try {
            passwordData = new HashedPassword(hashedPassword, salt, true);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new User(name, email, dob, purpose, passwordData, userId);
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
}