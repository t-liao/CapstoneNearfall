package com.example.nearfall.Database;

import com.example.nearfall.MainActivity;

import java.util.Date;

public class User {
    private String username;
    private String email;
    private Date dob;
    private String password;
    private String purpose;
    private UserManager userManager;

    public User(String username, String email, Date dob, String password, String purpose){
        this.username = username;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.purpose = purpose;
        this.userManager = MainActivity.getUserManager();
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public Date getDob() {
        return this.dob;
    }

    public String getPurpose() {
        return this.purpose;
    }


     public String getHashedPassword() {
        return this.password;
     }

}
