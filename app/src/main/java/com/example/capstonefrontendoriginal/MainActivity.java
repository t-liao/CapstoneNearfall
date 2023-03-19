package com.example.capstonefrontendoriginal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Detection = "detectionKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set view to activity_main.xml
        setContentView(R.layout.activity_main);

        //Set detection to off in case it was on when the app was shut off.
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Detection,"Off");
        editor.commit();

    }

    @Override
    public void onBackPressed() {
        // do nothing
        // disable back button
    }
}