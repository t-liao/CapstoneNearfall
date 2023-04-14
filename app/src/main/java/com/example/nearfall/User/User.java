package com.example.nearfall.User;

import com.example.nearfall.MainActivity;

public class User {
    private String username;
    private String email;
    private String dob;
    private String purpose;
    private HashedPassword passwordData;
    private UserManager userManager;
    private int userId;

    public User(String username, String email, String dob, String purpose, HashedPassword passwordData, int id) {
        setData(username, email, dob, purpose, passwordData);
        this.userId = id;
    }

    public User(String username, String email, String dob, String purpose, HashedPassword passwordData) {
        setData(username, email, dob, purpose, passwordData);
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
        return this.userId;
    }

    public String getUsernameWithoutSpecialCharacters() {
        return username.replaceAll("[^a-zA-Z0-9]", "");
    }


    public void setId(int id) {
        this.userId = id;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private void setData(String username, String email, String dob, String purpose, HashedPassword passwordData) {
        // Set object data
        this.username = username;
        this.email = email;
        this.dob = dob;
        this.purpose = purpose;
        this.passwordData = passwordData;
        // Session's user manager
        this.userManager = MainActivity.getUserManager();
    }
 }
