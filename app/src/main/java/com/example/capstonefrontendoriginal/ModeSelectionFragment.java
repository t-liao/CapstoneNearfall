package com.example.capstonefrontendoriginal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class ModeSelectionFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mode_selection, container, false);

        ImageButton researchExp = view.findViewById(R.id.imageButton_research);
        researchExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_modeSelectionFragment_to_researchExpFragment);
            }
        });

        ImageButton personalExp = view.findViewById(R.id.imageButton_personal);
        personalExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_modeSelectionFragment_to_personalExpFragment);
            }
        });

        Button researchButton = view.findViewById(R.id.research_button);
        researchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_modeSelectionFragment_to_researchModeFragment);
            }
        });

        Button personalButton = view.findViewById(R.id.personal_button);
        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_modeSelectionFragment_to_personalModeFragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}