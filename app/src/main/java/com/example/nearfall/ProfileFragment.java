package com.example.nearfall;

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
import java.io.OutputStream;
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

        //grab user email before @ symbol
        String email = curr_user.getEmail();
        int index = email.indexOf("@");
        String username = email.substring(0, index);

        //When Download Sensor Data is clicked
        FrameLayout downloadSensorData = view.findViewById(R.id.download_sensor_data);
        downloadSensorData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get time when clicked
                long timestamp = new Date().getTime();

                SimpleDateFormat dateFormat = new SimpleDateFormat("_yyyyMMdd_HHmmss");
                String formattedDate = dateFormat.format(new Date(timestamp));

                String SENSORFILENAMEOUT = username + formattedDate + "_sensor_log" + ".csv";
                String SENSORFILENAMEIN = "sensor_log_" + username + ".csv";

                // Use the MediaStore API to write sensor log to the Downloads directory
                ContentValues contentValuesSensor = new ContentValues();
                contentValuesSensor.put(MediaStore.Downloads.DISPLAY_NAME, SENSORFILENAMEOUT);
                contentValuesSensor.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
                contentValuesSensor.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);


                //Ensure that phone has API level 29 or higher that is required
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {

                    try {
                        //For grabbing csv file from app file
                        FileInputStream inputStreamSensor = getActivity().openFileInput(SENSORFILENAMEIN);

                        //For exporting sensor log csv
                        ContentResolver resolverSensor = getActivity().getContentResolver();
                        Uri uriSensor = resolverSensor.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValuesSensor);

                        //If the uri is null, show a Toast message and return
                        if (uriSensor == null) {
                            Toast.makeText(getActivity(),
                                    "Unable to obtain URI to write sensor log",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //For writing to download directory
                        OutputStream outputStreamSensor = resolverSensor.openOutputStream(uriSensor);

                        // Read the contents of the inputStream into a byte array
                        // and write to outputstream
                        byte[] buffer = new byte[inputStreamSensor.available()];
                        int bytesRead;
                        while ((bytesRead = inputStreamSensor.read(buffer)) != -1) {
                            outputStreamSensor.write(buffer, 0, bytesRead);
                        }

                        inputStreamSensor.close();
                        outputStreamSensor.close();

                        Toast.makeText(requireActivity().getApplicationContext(),
                                "Sensor data downloaded successfully!",
                                Toast.LENGTH_LONG).show();

                    } catch (FileNotFoundException e) {
                        Toast.makeText(requireActivity().getApplicationContext(),
                                "No sensor log file exist.",
                                Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(requireActivity().getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }


                } else {
                    //For phones with API level lower than 29

                    //Specify location
                    File externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                    try {

                        //For reading sensor log csv file from app file
                        FileInputStream inputStreamSensor = getActivity().openFileInput(SENSORFILENAMEIN);

                        //Create an output stream to write the file to external storage
                        File outputFileSensor = new File(externalDir, SENSORFILENAMEOUT);
                        FileOutputStream outputStreamSensor = new FileOutputStream(outputFileSensor);

                        // Read the contents of the inputStream into a byte array
                        // and write to outputstream
                        byte[] buffer = new byte[inputStreamSensor.available()];
                        int bytesRead;
                        while ((bytesRead = inputStreamSensor.read(buffer)) != -1) {
                            outputStreamSensor.write(buffer, 0, bytesRead);

                        }

                        //Close streams
                        inputStreamSensor.close();
                        outputStreamSensor.close();

                        Toast.makeText(requireActivity().getApplicationContext(),
                                "Sensor data downloaded successfully!",
                                Toast.LENGTH_LONG).show();

                    } catch (FileNotFoundException e) {
                        Toast.makeText(requireActivity().getApplicationContext(),
                                "No sensor log file exist.",
                                Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(requireActivity().getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }

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

                // Use the MediaStore API to write fall log to the Downloads directory
                ContentValues contentValuesFall = new ContentValues();
                contentValuesFall.put(MediaStore.Downloads.DISPLAY_NAME, FALLFILENAMEOUT);
                contentValuesFall.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
                contentValuesFall.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                //Ensure that phone has API level 29 or higher that is required
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {

                    try {
                        //For grabbing csv file from app file
                        FileInputStream inputStreamFall = getActivity().openFileInput(FALLFILENAMEIN);

                        //For exporting fall log csv
                        ContentResolver resolverFall = getActivity().getContentResolver();
                        Uri uriFall = resolverFall.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValuesFall);

                        //If the uri is null, show a Toast message and return
                        if (uriFall == null) {
                            Toast.makeText(getActivity(), "Unable to obtain URI to write fall log", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //For writing to download directory
                        OutputStream outputStreamFall = resolverFall.openOutputStream(uriFall);

                        // Read the contents of the inputStream into a byte array
                        // and write to outputstream
                        byte[] bufferFall = new byte[inputStreamFall.available()];
                        int bytesReadFall;
                        while ((bytesReadFall = inputStreamFall.read(bufferFall)) != -1) {
                            outputStreamFall.write(bufferFall, 0, bytesReadFall);
                        }

                        inputStreamFall.close();
                        outputStreamFall.close();

                        Toast.makeText(requireActivity().getApplicationContext(),
                                "Fall data downloaded successfully!",
                                Toast.LENGTH_LONG).show();

                    } catch (FileNotFoundException e) {
                        Toast.makeText(requireActivity().getApplicationContext(),
                                "No fall log file exist.",
                                Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(requireActivity().getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    //For phones with API level lower than 29

                    //Specify location
                    File externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                    try {
                        //For reading fall log csv file from app file
                        FileInputStream inputStreamFall = getActivity().openFileInput(FALLFILENAMEIN);

                        //Create an output stream to write the file to external storage
                        File outputFileFall = new File(externalDir, FALLFILENAMEOUT);
                        outputFileFall.createNewFile();
                        FileOutputStream outputStreamFall = new FileOutputStream(outputFileFall);

                        // Read the contents of the inputStream into a byte array
                        // and write to outputstream
                        byte[] bufferFall = new byte[inputStreamFall.available()];
                        int bytesReadFall;
                        while ((bytesReadFall = inputStreamFall.read(bufferFall)) != -1) {
                            outputStreamFall.write(bufferFall, 0, bytesReadFall);
                        }

                        //Close streams
                        inputStreamFall.close();
                        outputStreamFall.close();

                        Toast.makeText(requireActivity().getApplicationContext(),
                                "Fall data downloaded successfully!",
                                Toast.LENGTH_LONG).show();

                    } catch (FileNotFoundException e) {
                        Toast.makeText(requireActivity().getApplicationContext(),
                                "No fall log file exist.",
                                Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(requireActivity().getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
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