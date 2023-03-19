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
import android.widget.ImageButton;
import android.widget.TextView;

public class TutorialOptionFragment extends Fragment implements View.OnClickListener {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_tutorial_option.xml
        View view = inflater.inflate(R.layout.fragment_tutorial_option, container, false);

        //Grab the specified sharedpreference and stored name value
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(Name,"DEFAULT");

        //Set name_text to stored name
        TextView text = (TextView) view.findViewById(R.id.name_text);
        text.setText(name);

        //When yes button is clicked
        Button yesTutorialButton = view.findViewById(R.id.yes_tutorial_button);
        yesTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to tutorial1Fragment
                Navigation.findNavController(view).navigate(R.id.action_tutorialOptionFragment_to_tutorial1Fragment);
            }
        });

        //When skip button is clicked
        Button skipTutorialButton = view.findViewById(R.id.skip_tutorial_button);
        skipTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to homeFragment
                Navigation.findNavController(view).navigate(R.id.action_tutorialOptionFragment_to_homeFragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}