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


public class ModeSelectionFragment extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_mode_selection.xml
        View view = inflater.inflate(R.layout.fragment_mode_selection, container, false);

        UserManager userManager = MainActivity.getUserManager();
        //Grab stored name
        String name = userManager.getUser().getUsername();

        //Set name_text to the stored name
        TextView text = (TextView) view.findViewById(R.id.name_text);
        text.setText(name);

        //When research side icon is clicked
        ImageButton researchExp = view.findViewById(R.id.imageButton_research);
        researchExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to researchExpFragment
                Navigation.findNavController(view).navigate(R.id.action_modeSelectionFragment_to_researchExpFragment);
            }
        });

        //When personal side icon is clicked
        ImageButton personalExp = view.findViewById(R.id.imageButton_personal);
        personalExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to personalExpFragment
                Navigation.findNavController(view).navigate(R.id.action_modeSelectionFragment_to_personalExpFragment);
            }
        });

        //When research button is clicked
        Button researchButton = view.findViewById(R.id.research_button);
        researchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Store new purpose mode
                userManager.setPurpose("Research");
                //Navigate to homeFragment
                Navigation.findNavController(view).navigate(R.id.action_modeSelectionFragment_to_homeFragment);
            }
        });

        Button personalButton = view.findViewById(R.id.personal_button);
        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Store new purpose mode
                userManager.setPurpose("Personal");
                //Navigate to homeFragment
                Navigation.findNavController(view).navigate(R.id.action_modeSelectionFragment_to_homeFragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}