package com.example.capstonefrontendoriginal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class ResearchModeFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_research_mode, container, false);

        Button changeModeButton = view.findViewById(R.id.research_change_mode_button);
        changeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_researchModeFragment_to_modeSelectionFragment);
            }
        });

        LinearLayout Profile = view.findViewById(R.id.user_icon_grey);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_researchModeFragment_to_profileFragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}