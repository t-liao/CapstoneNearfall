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

import com.example.nearfall.Database.UserManager;

public class PersonalExpFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_personal_exp.xml
        View view = inflater.inflate(R.layout.fragment_personal_exp, container, false);

        UserManager userManager = MainActivity.getUserManager();
        //Grab stored name value
        String name = userManager.getUser().getUsername();


        //Set name_text to the stored name
        TextView text = (TextView) view.findViewById(R.id.name_text);
        text.setText(name);

        //When back button is clicked
        ImageButton backButton = view.findViewById(R.id.back_button_personal_exp);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to modeSelectionFragment
                Navigation.findNavController(view).navigate(R.id.action_personalExpFragment_to_modeSelectionFragment);
            }
        });

        //When personal button is clicked
        Button personalButton = view.findViewById(R.id.personal_mode_button);
        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Store new purpose mode
                userManager.setPurpose("Personal");
                //Navigate to homeFragment
                Navigation.findNavController(view).navigate(R.id.action_personalExpFragment_to_homeFragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}