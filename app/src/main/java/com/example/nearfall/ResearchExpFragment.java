package com.example.nearfall;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nearfall.User.UserManager;

public class ResearchExpFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_research_exp.xml
        View view = inflater.inflate(R.layout.fragment_research_exp, container, false);

        UserManager userManager = MainActivity.getUserManager();
        //Grab stored name value
        String name = userManager.getUser().getUsername();

        //Set name_text to the stored name
        TextView text = (TextView) view.findViewById(R.id.name_text);
        text.setText(name);

        //When back button is clicked
        ImageButton backButton = view.findViewById(R.id.back_button_research_exp);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to modeSelectionFragment
                Navigation.findNavController(view).navigate(R.id.action_researchExpFragment_to_modeSelectionFragment);
            }
        });

        //When research button is clicked
        Button researchButton = view.findViewById(R.id.research_mode_button);
        researchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Store new purpose mode
                userManager.setPurpose("Research");
                //Navigate to homeFragment
                Navigation.findNavController(view).navigate(R.id.action_researchExpFragment_to_homeFragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}