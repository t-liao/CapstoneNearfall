package com.example.nearfall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.nearfall.Location.LocationTracker;
import com.example.nearfall.MainDatabase.Database;
import com.example.nearfall.User.UserManager;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static UserManager userManager;
    private static LocationTracker locationTracker;
    private static Database database;
    public static String detecting = "Off";
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private float[] mGyroscopeData = { 0.0f, 0.0f, 0.0f };
    private float[] mAccelerometerData = { 0.0f, 0.0f, 0.0f };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set view to activity_main.xml
        setContentView(R.layout.activity_main);

        database = new Database(this);
        userManager = new UserManager();
        locationTracker = new LocationTracker(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    @Override
    public void onStart() {
        super.onStart();
        // register sensors
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onDestroy() {
        super.onDestroy();
        // Only unregister sensors when the app is terminated, so that it runs in the background
        // Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        // Log.d("Service Destroyed", "Service Destroyed");
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float [] values = sensorEvent.values;

        // If detection is off, don't do anything
        if (detecting.equals("Off")){
            return;
        }

        //Comment out if you want to log the data in logcat
        // String logMessage = String.format("%d: 0'%g'", sensorEvent.sensor.getType(), values[0]);
        // Log.d("Sensor Data IN:", logMessage);
        switch(sensorEvent.sensor.getType()) {
            case Sensor.TYPE_GYROSCOPE:
                mGyroscopeData[0] = values[0];
                mGyroscopeData[1] = values[1];
                mGyroscopeData[2] = values[2];
                break;
            case Sensor.TYPE_ACCELEROMETER:
                mAccelerometerData[0] = values[0];
                mAccelerometerData[1] = values[1];
                mAccelerometerData[2] = values[2];
                break;
        }

        // Store data
        String FILENAME = "sensor_log.csv";
        String toDisplay = String.format("%f, %f, %f, %f, %f, %f %n", mAccelerometerData[0], mAccelerometerData[1], mAccelerometerData[2], mGyroscopeData[0], mGyroscopeData[1], mGyroscopeData[2]);
        try{
            FileOutputStream out = openFileOutput( FILENAME, Context.MODE_APPEND );
            out.write( toDisplay.getBytes() );
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

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