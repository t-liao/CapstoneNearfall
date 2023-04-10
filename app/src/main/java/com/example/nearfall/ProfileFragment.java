package com.example.nearfall;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nearfall.User.User;
import com.example.nearfall.User.UserManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_profile.xml
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        UserManager userManager = MainActivity.getUserManager();
        User curr_user = userManager.getUser();
        String purpose = curr_user.getPurpose();
        String name = curr_user.getUsername();

        //Set name_text to the stored name
        TextView text = (TextView) view.findViewById(R.id.profile_name_text);
        text.setText(name);

        //When logout is clicked
        FrameLayout Logout = view.findViewById(R.id.Logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Turn off detection by storing new detection status
                userManager.accountLogout();
                //Navigate to welcomeFragment
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_welcomeFragment);
            }
        });

        //When Export Sensor Data is clicked
        FrameLayout exportSensorData = view.findViewById(R.id.export_sensor_data);
        exportSensorData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //grab user email before @ symbol
                String email = curr_user.getEmail();
                int index = email.indexOf("@");
                String username = email.substring(0, index);


                String SENSORFILENAME = "sensor_log_" + username + ".csv";
                String FALLFILENAME = "fall_log_" + username + ".csv";
                // Use the MediaStore API to write the file to the Downloads directory
                ContentResolver resolver = getActivity().getContentResolver();
                ContentValues contentValuesSensor = new ContentValues();
                contentValuesSensor.put(MediaStore.Downloads.DISPLAY_NAME, SENSORFILENAME);
                contentValuesSensor.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
                contentValuesSensor.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                ContentValues contentValuesFall = new ContentValues();
                contentValuesFall.put(MediaStore.Downloads.DISPLAY_NAME, FALLFILENAME);
                contentValuesFall.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
                contentValuesFall.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                //Ensure that phone has API level 29 or higher that is required
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {

                    try {
                        //For exporting sensor log csv
                        Uri uriSensor = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValuesSensor);

                        //For writing to download directory
                        OutputStream outputStreamSensor = resolver.openOutputStream(uriSensor);
                        //For grabbing csv file from app file
                        FileInputStream inputStreamSensor = getActivity().openFileInput(SENSORFILENAME);

                        // Read the contents of the inputStream into a byte array
                        // and write to outputstream
                        byte[] buffer = new byte[inputStreamSensor.available()];
                        int bytesRead;
                        while ((bytesRead = inputStreamSensor.read(buffer)) != -1) {
                            outputStreamSensor.write(buffer, 0, bytesRead);
                        }

                        inputStreamSensor.close();
                        outputStreamSensor.close();

                        //For exporting fall log csv
                        Uri uriFall = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValuesFall);

                        //For writing to download directory
                        OutputStream outputStreamFall = resolver.openOutputStream(uriFall);
                        //For grabbing csv file from app file
                        FileInputStream inputStreamFall = getActivity().openFileInput(FALLFILENAME);

                        // Read the contents of the inputStream into a byte array
                        // and write to outputstream
                        byte[] bufferFall = new byte[inputStreamFall.available()];
                        int bytesReadFall;
                        while ((bytesReadFall = inputStreamFall.read(bufferFall)) != -1) {
                            outputStreamFall.write(bufferFall, 0, bytesReadFall);
                        }

                        inputStreamFall.close();
                        outputStreamFall.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(requireActivity().getApplicationContext(),
                            "File is exported!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(requireActivity().getApplicationContext(),
                            "Phone API level needs to be 29 or higher",
                            Toast.LENGTH_LONG).show();
                }


            }
        });

        //When home icon is clicked
        LinearLayout home = view.findViewById(R.id.home_icon_grey);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to homeFragment
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_homeFragment);

            }
        });

        //When map icon is clicked
        LinearLayout map = view.findViewById(R.id.map_icon_grey);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (purpose.equals("Research")){
                    //if mode is research

                    //Navigate to noHeatmapFragment
                    Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_noHeatmapFragment);
                } else if (purpose.equals("Personal")) {
                    //if mode is personal

                    //Navigate to heatmapFragment
                    Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_heatmapFragment);
                }
            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {

    }
}