package com.example.nearfall.Database;

import com.example.nearfall.MainActivity;

import java.util.Date;

public class User {
    private String username;
    private String email;
    private String detection;
    private Date dob;
    private String password;
    private String purpose;
    private UserManager userManager;

    public User(String username, String email, Date dob, String password, String purpose, String detection){
        this.username = username;
        this.email = email;
        this.detection = detection;
        this.dob = dob;
        this.password = password;
        this.purpose = purpose;

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
    public Date getDob() {
        return this.dob;
    }

    // Return user's purpose
    public String getPurpose() {
        return this.purpose;
    }

    // Return hashed password
    public String getHashedPassword() {
        return this.password;
     }

}
