package com.example.nearfall;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        //When tutorial is clicked
        FrameLayout tutorial = view.findViewById(R.id.tutorial);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String detection = curr_user.getDetection();
                if (detection.equals("On")) {
                    //If detection is still running
                    //Print message
                    Toast.makeText(getActivity().getApplicationContext(), "Please stop fall detection \nbefore going through the tutorial.",
                            Toast.LENGTH_LONG).show();
                } else {
                    //Navigate to tutorial1Fragment
                    Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_tutorial1Fragment);
                }
            }
        });

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

        // Grab username with no spaces
        String username = curr_user.getUsernameWithoutSpaces();

        //When Download Sensor Data is clicked
        FrameLayout downloadSensorData = view.findViewById(R.id.download_sensor_data);
        downloadSensorData.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              //get time when clicked
              SimpleDateFormat dateFormat = new SimpleDateFormat("_yyyyMMdd_HHmmss");
              String formattedDate = dateFormat.format(new Date().getTime());

              String SENSORFILENAMEOUT = username + formattedDate + "_sensor_log" + ".csv";
              String SENSORFILENAMEIN = "sensor_log_" + username + ".csv";
              File outputDest = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), SENSORFILENAMEOUT);
              File sensorFile = new File (getContext().getFilesDir(), SENSORFILENAMEIN);
              try {
                  copyFile(sensorFile, outputDest);
              } catch (IOException ex) {
                  ex.printStackTrace();
                  Toast.makeText(requireActivity().getApplicationContext(),
                          ex.getMessage(),
                          Toast.LENGTH_LONG).show();
                  return;
              }

              Toast.makeText(requireActivity().getApplicationContext(),
                      "Sensor data downloaded successfully!",
                      Toast.LENGTH_LONG).show();
          }
      });

        //When Download Fall Data is clicked
        FrameLayout downloadFallData = view.findViewById(R.id.download_fall_data);
        downloadFallData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get time when clicked
                long timestamp = new Date().getTime();

                SimpleDateFormat dateFormat = new SimpleDateFormat("_yyyyMMdd_HHmmss");
                String formattedDate = dateFormat.format(new Date(timestamp));

                String FALLFILENAMEOUT = username + formattedDate + "_fall_log"  + ".csv";
                String FALLFILENAMEIN = "fall_log_" + username + ".csv";
                File fallOutputDest = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FALLFILENAMEOUT);
                File fallData = new File (getContext().getFilesDir(), FALLFILENAMEIN);

                try {
                    copyFile(fallData, fallOutputDest);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Toast.makeText(requireActivity().getApplicationContext(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(requireActivity().getApplicationContext(),
                        "Fall data downloaded successfully!",
                        Toast.LENGTH_LONG).show();
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

    private static void copyFile(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }
}