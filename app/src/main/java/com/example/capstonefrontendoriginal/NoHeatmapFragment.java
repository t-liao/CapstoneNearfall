package com.example.capstonefrontendoriginal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoHeatmapFragment extends Fragment implements View.OnClickListener{
//    public static final String MyPREFERENCES = "MyPrefs" ;
//    public static final String Purpose = "purposeKey";
//    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_heatmap, container, false);

//        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        String purpose = sharedpreferences.getString(Purpose,"DEFAULT");

        //When user icon is clicked
        LinearLayout profile = view.findViewById(R.id.user_icon_grey);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to profileFragment
                Navigation.findNavController(view).navigate(R.id.action_noHeatmapFragment_to_profileFragment);
            }
        });

        //When home icon is clicked
        LinearLayout home = view.findViewById(R.id.home_icon_grey);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to homeFragment
                Navigation.findNavController(view).navigate(R.id.action_noHeatmapFragment_to_homeFragment);
            }
        });



        return view;
    }


    @Override
    public void onClick(View view) {

    }
}