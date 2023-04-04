package com.example.nearfall.Location;

import com.example.nearfall.MainActivity;
import com.example.nearfall.MainDatabase.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.nearfall.User.User;
import com.example.nearfall.User.UserManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationTracker extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private static final long UPDATE_INTERVAL = 2000;
    private static final long FASTEST_INTERVAL = 2000;
    private GoogleApiClient googleApiClient;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        checkLocation(); //check whether location service is enable or not in your  phone
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Double lat;
        Double lon;
        int userId;
        UserManager userManager = MainActivity.getUserManager();
        User curr_user = userManager.getUser();
        checkLocation();
        startLocationUpdates();

//        if (locationServices == null) {
//            startLocationUpdates();
//        }
//        if (locationServices != null) {
//            lat = locationServices.getLatitude();
//            lon = locationServices.getLongitude();
//            userId = curr_user.getId();
//            addLocation(lat, lon, userId);
//        } else {
//            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Main", "Connection Suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("Main", "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        isLocationEnabled();
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationRequest locationRequest = new LocationRequest();
        // I suppressed the missing-permission warning because this wouldn't be executed in my
        // case without location services being enabled
//        @SuppressLint("MissingPermission") android.location.Location lastKnownLocation = locationManager.getCurrentLocation();
//        double userLat = lastKnownLocation.getLatitude();
//        double userLong = lastKnownLocation.getLongitude();

    }

    @Override
    public void onLocationChanged(Location location) {
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean providerPerms = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return providerPerms;
    }

    public void addLocation(Double lat, Double lon, int userId) {
        SQLiteDatabase db = MainActivity.getDatabase().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Database.LATITUDE_COL, lat);
        values.put(Database.LONGITUDE_COL, lon);
        values.put(Database.USER_ID, userId);
        db.insert(Database.LOCATION_TABLE_NAME, null, values);
    }
}