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
                String FILENAME = "sensor_log.csv";
                // Use the MediaStore API to write the file to the Downloads directory
                ContentResolver resolver = getActivity().getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Downloads.DISPLAY_NAME, FILENAME);
                contentValues.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
                contentValues.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {

                    try {
                        Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);

                        //For writing to download directory
                        OutputStream outputStream = resolver.openOutputStream(uri);
                        //For grabbing csv file from app file
                        FileInputStream inputStream = getActivity().openFileInput(FILENAME);

                        // Read the contents of the file into a byte array
                        byte[] buffer = new byte[inputStream.available()];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }

                        inputStream.close();
                        outputStream.close();
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