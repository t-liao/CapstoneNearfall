package com.example.nearfall.User;

import android.database.Cursor;

import com.example.nearfall.MainActivity;
import com.example.nearfall.MainDatabase.Database;

public class User {
    private String username;
    private String email;
    private String dob;
    private String purpose;
    private HashedPassword passwordData;
    private UserManager userManager;
    private int userId;

    public User(String username, String email, String dob, String purpose, HashedPassword passwordData){
        this.username = username;
        this.email = email;
        this.dob = dob;
        this.purpose = purpose;
        this.passwordData = passwordData;

        // Session's user manager
        this.userManager = MainActivity.getUserManager();
    }

    public String getUsername() {
        return this.username;
    }

    // Return user's email
    public String getEmail() {
        return this.email;
    }

    // Return user's date of birth
    public String getDob() {
        return this.dob;
    }

    // Return user's purpose
    public String getPurpose() {
        return this.purpose;
    }

    public String getDetection() {
        return MainActivity.detecting;
    }

    public String getHashedPassword() {
        return this.passwordData.getHashedPassword();
    }

    public byte[] getSalt() {
        return this.passwordData.getSalt();
    }

    public int getId() {
        Cursor cursor = userManager.getCursorFromEmail(this.email);
        return cursor.getInt(cursor.getColumnIndexOrThrow(Database.ID_COL));
    }
}