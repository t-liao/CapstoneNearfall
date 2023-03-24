package com.example.nearfall.Database;

import com.example.nearfall.MainActivity;

public class User {
    private String username;
    private String email;
    private String detection;
    private String dob;
    private String purpose;
    private HashedPassword passwordData;
    private UserManager userManager;

    public User(String username, String email, String dob, String purpose, String detection, HashedPassword passwordData){
        this.username = username;
        this.email = email;
        this.detection = detection;
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

    // Return detection
    public String getDetection() {
        return this.detection;
    }
    // Return user's date of birth
    public String getDob() {
        return this.dob;
    }

    // Return user's purpose
    public String getPurpose() {
        return this.purpose;
    }

    public String getHashedPassword() {
        return this.passwordData.getHashedPassword();
    }

    public byte[] getSalt() {
        return this.passwordData.getSalt();
    }
}
