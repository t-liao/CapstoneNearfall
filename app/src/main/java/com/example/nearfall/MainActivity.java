package com.example.nearfall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nearfall.Location.LocationTracker;
import com.example.nearfall.MainDatabase.Database;
import com.example.nearfall.User.UserManager;

public class MainActivity extends AppCompatActivity {
    private static UserManager userManager;
    private static LocationTracker locationTracker;
    private static Database database;
    public static String detecting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set view to activity_main.xml
        setContentView(R.layout.activity_main);
        database = new Database(MainActivity.this);
        userManager = new UserManager(database);
        locationTracker = new LocationTracker(database);
    }

    @Override
    public void onBackPressed() {
        // do nothing
        // disable back button
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    public static LocationTracker getLocationTracker() {
        return locationTracker;
    }

    public static Database getDatabase() {
        return database;
    }

}