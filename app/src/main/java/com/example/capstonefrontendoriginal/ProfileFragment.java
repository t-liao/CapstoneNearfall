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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Purpose = "purposeKey";
    public static final String Detection = "detectionKey";
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_profile.xml
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Grab the specified sharedpreference
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //Grab purpose and name values from sharedpreference
        String purpose = sharedpreferences.getString(Purpose,"DEFAULT");
        String name = sharedpreferences.getString(Name,"DEFAULT");

        //Set name_text to the stored name
        TextView text = (TextView) view.findViewById(R.id.profile_name_text);
        text.setText(name);

        //When logout is clicked
        FrameLayout Logout = view.findViewById(R.id.Logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Turn off detection

                //Store new detection status
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Detection,"Off");
                editor.commit();
                //Navigate to welcomeFragment
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_welcomeFragment);
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