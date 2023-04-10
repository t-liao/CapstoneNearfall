package com.example.nearfall.Location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Looper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.nearfall.MainActivity;
import com.example.nearfall.MainDatabase.Database;
import com.example.nearfall.User.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import java.util.Date;

public class LocationTracker extends MainActivity {
    private static final int PERMISSION_ID = 44;
    private final long UPDATE_INTERVAL = 5 * 1000;
    private final long FASTEST_INTERVAL = 5 * 1000;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private Location lastSavedLocation;
    private Context parent;

    public LocationTracker(Context context) {
        parent = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(parent);
        lastSavedLocation = null;
    }

    @SuppressLint("MissingPermission")
    protected void startLocationUpdates() {
        if (!checkPermissions()) {
            requestPermissions();
        }

        // Create the location request to start receiving updates
        createLocationRequest();

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied

        SettingsClient settingsClient = LocationServices.getSettingsClient(parent);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }


    private LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            User currUser = MainActivity.getUserManager().getUser();
            if (locationResult == null || currUser == null) {
                return;
            }
            lastSavedLocation = locationResult.getLastLocation();
            int userId = currUser.getId();
            Double lat = lastSavedLocation.getLatitude();
            Double lon = lastSavedLocation.getLongitude();
            addLocation(lat, lon, userId);
        }
    };

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

    private boolean checkPermissions() {
        boolean fineLoc = ContextCompat.checkSelfPermission(parent,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean coarseLoc = ActivityCompat.checkSelfPermission(parent,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (fineLoc && coarseLoc) {
            return true;
        }
        requestPermissions();
        return false;
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions((Activity) parent, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    public void addLocation(Double lat, Double lon, int userId) {
        SQLiteDatabase db = MainActivity.getDatabase().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Database.LATITUDE_COL, lat);
        values.put(Database.LONGITUDE_COL, lon);
        values.put(Database.USER_ID, userId);
        values.put(Database.TIMESTAMP, new Date().getTime());
        db.insert(Database.LOCATION_TABLE_NAME, null, values);
    }

    public void resumeLocationDetection() {
        boolean perms = checkPermissions();
        if (!perms) {
            requestPermissions();
            return;
        }
        startLocationUpdates();
    }

    public void pauseLocationDetection() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public Location getLastSavedLocation() {
        return lastSavedLocation;
    }
}