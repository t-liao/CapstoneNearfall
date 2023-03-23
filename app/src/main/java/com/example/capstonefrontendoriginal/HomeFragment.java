package com.example.capstonefrontendoriginal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements View.OnClickListener {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Purpose = "purposeKey";
    public static final String Detection = "detectionKey";
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_home.xml
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Grab the specified sharedpreference
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Grab stored values in purpose and detection
        String purpose = sharedpreferences.getString(Purpose,"DEFAULT");
        String detection = sharedpreferences.getString(Detection,"DEFAULT");

        //Set home_mode_text to the correct purpose mode
        TextView text = (TextView) view.findViewById(R.id.home_mode_text);
        text.setText(purpose + " Mode");

        //Grab the start_stop_button and detection_on_off_text
        TextView startStopButton = view.findViewById(R.id.start_stop_button);
        TextView detectionText = view.findViewById(R.id.detection_on_off_text);

        if (detection.equals("Off")){
            // Detection is currently off

            //Set start stop button to green with start text
            //Set detection text to say detection off
            startStopButton.setText("START");
            startStopButton.setBackgroundResource(R.drawable.content_rect_green);
            detectionText.setText("Detection Off");
        } else if (detection.equals("On")){
            // Detection is currently on

            //Set start stop button to red with stop text
            //Set detection text to say detection on
            startStopButton.setText("STOP");
            startStopButton.setBackgroundResource(R.drawable.content_rect_red);
            detectionText.setText("Detection On");
        } else {
            //if detection value stored in the sharedpreference is not On or Off
            //Print error
            Toast.makeText(getActivity().getApplicationContext(), "Detection on/off was not set properly",
                    Toast.LENGTH_LONG).show();
        }

        //When start stop button is clicked
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startStopButton.getText().toString().equals("START")){
                    //Turn detection on

                    //Set start stop button to red with stop text
                    //Set detection text to say detection on
                    startStopButton.setText("STOP");
                    startStopButton.setBackgroundResource(R.drawable.content_rect_red);
                    detectionText.setText("Detection On");

                    //Store current detection status
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Detection,"On");
                    editor.commit();

                } else {
                    //Turn detection off

                    //Set start stop button to green with start text
                    //Set detection text to say detection off
                    startStopButton.setText("START");
                    startStopButton.setBackgroundResource(R.drawable.content_rect_green);
                    detectionText.setText("Detection Off");

                    //Store current detection status
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Detection,"Off");
                    editor.commit();
                }
            }
        });

        //When fall button is clicked
        Button fallButton = view.findViewById(R.id.fall_button);
        fallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startStopButton.getText().toString().equals("STOP")) {
                    //If detection is running
                    //Record fall


                } else {
                    //If detection is not on
                    //Print
                    Toast.makeText(getActivity().getApplicationContext(), "Fall detection is not on.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        //When near fall button is clicked
        Button nearFallButton = view.findViewById(R.id.near_fall_button);
        nearFallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startStopButton.getText().toString().equals("STOP")) {
                    //If detection is still running

                    //Record near fall

                } else {
                    //If detection is not on
                    //Print
                    Toast.makeText(getActivity().getApplicationContext(), "Fall detection is not on.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        //When change mode button is clicked
        Button changeModeButton = view.findViewById(R.id.change_mode_button);
        changeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startStopButton.getText().toString().equals("STOP")) {
                    //If detection is still running
                    //Print message
                    Toast.makeText(getActivity().getApplicationContext(), "Please stop fall detection \nbefore changing mode.",
                            Toast.LENGTH_LONG).show();
                } else {
                    //Navigate to modeSelectionFragment
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_modeSelectionFragment);
                }

            }
        });

        //When user icon is clicked
        LinearLayout Profile = view.findViewById(R.id.user_icon_grey);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to profileFragment
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_profileFragment);
            }
        });

        //when map icon is clicked
        LinearLayout map = view.findViewById(R.id.map_icon_grey);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (purpose.equals("Research")){
                    //If purpose is research navigate to noHeatmapFragment
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_noHeatmapFragment);
                } else if (purpose.equals("Personal")) {
                    //If purpose is personal navigate to heatmapFragment
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_heatmapFragment);
                }


            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}