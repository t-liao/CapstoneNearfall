package com.example.capstonefrontendoriginal;

import android.content.Context;
import android.content.Intent;
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
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String purpose = sharedpreferences.getString(Purpose,"DEFAULT");

        TextView text = (TextView) view.findViewById(R.id.home_mode_text);
        text.setText(purpose + " Mode");

        TextView startStopButton = view.findViewById(R.id.start_stop_button);
        TextView detectionText = view.findViewById(R.id.detection_on_off_text);
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startStopButton.getText().toString().equals("START")){
                    //Turn detection on
                    startStopButton.setText("STOP");
                    startStopButton.setBackgroundResource(R.drawable.content_circle_red);
                    detectionText.setText("Detection On");

                    Intent myIntent = new Intent();
                    myIntent.setClassName("com.example", "com.example.TestNFD2");
                    // for ex: your package name can be "com.example"
                    // your activity name will be "com.example.Contact_Developer"
                    startActivity(myIntent);

                } else {
                    //Turn detection off
                    startStopButton.setText("START");
                    startStopButton.setBackgroundResource(R.drawable.content_circle_green);
                    detectionText.setText("Detection Off");
                }
            }
        });


        Button changeModeButton = view.findViewById(R.id.change_mode_button);
        changeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startStopButton.getText().toString().equals("STOP")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please stop fall detection \nbefore changing mode.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_modeSelectionFragment);
                }

            }
        });

        LinearLayout Profile = view.findViewById(R.id.user_icon_grey);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_profileFragment);
            }
        });

        LinearLayout map = view.findViewById(R.id.map_icon_grey);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (purpose.equals("Research")){
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_noHeatmapFragment);
                } else if (purpose.equals("Personal")) {
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