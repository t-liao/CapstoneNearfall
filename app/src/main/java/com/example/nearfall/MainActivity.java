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
        // Turns off detection to start
        //TODO: May be easier to remove from database and just init with a variable here instead
        userManager.setDetection("Off");
    }

    @Override
    public void onBackPressed() {
        // do nothing
        // disable back button
    }

    public static UserManager getUserManager() {
        return userManager;
    }

}