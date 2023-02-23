package com.example.capstonefrontendoriginal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class ResearchExpFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_research_exp, container, false);

        ImageButton backButton = view.findViewById(R.id.back_button_research_exp);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_researchExpFragment_to_modeSelectionFragment);
            }
        });

        Button researchButton = view.findViewById(R.id.research_mode_button);
        researchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_researchExpFragment_to_researchModeFragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}