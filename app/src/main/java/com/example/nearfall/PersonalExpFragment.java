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
        View view = inflater.inflate(R.layout.fragment_personal_exp, container, false);

        UserManager userManager = MainActivity.getUserManager();
        String name = userManager.getUser().getUsername();


        TextView text = (TextView) view.findViewById(R.id.name_text);
        text.setText(name);

        ImageButton backButton = view.findViewById(R.id.back_button_personal_exp);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_personalExpFragment_to_modeSelectionFragment);
            }
        });

        Button personalButton = view.findViewById(R.id.personal_mode_button);
        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManager.setPurpose("Personal");
                Navigation.findNavController(view).navigate(R.id.action_personalExpFragment_to_homeFragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}