package com.example.nearfall;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class PersonalModeFragment extends Fragment implements View.OnClickListener {
    // Can be deleted


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_mode, container, false);

        Button changeModeButton = view.findViewById(R.id.personal_change_mode_button);
        changeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation.findNavController(view).navigate(R.id.action_personalModeFragment_to_modeSelectionFragment);
            }
        });

        LinearLayout Profile = view.findViewById(R.id.personal_user_icon_grey);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation.findNavController(view).navigate(R.id.action_personalModeFragment_to_profileFragment);
            }
        });

        LinearLayout map = view.findViewById(R.id.map_icon_grey);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation.findNavController(view).navigate(R.id.action_personalModeFragment_to_noHeatmapFragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}