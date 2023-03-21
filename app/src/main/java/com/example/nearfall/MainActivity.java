package com.example.nearfall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nearfall.Database.UserManager;

public class MainActivity extends AppCompatActivity {
    private static UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set view to activity_main.xml
        setContentView(R.layout.activity_main);
        userManager = new UserManager(MainActivity.this);
    }

    public static UserManager getUserManager() {
        return userManager;
    }

}