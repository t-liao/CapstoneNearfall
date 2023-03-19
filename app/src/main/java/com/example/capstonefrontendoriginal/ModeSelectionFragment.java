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


public class ModeSelectionFragment extends Fragment implements View.OnClickListener {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Purpose = "purposeKey";
    public static final String Name = "nameKey";
    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_mode_selection.xml
        View view = inflater.inflate(R.layout.fragment_mode_selection, container, false);

        //Grab the specified sharedpreference
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Grab stored values in name
        String name = sharedpreferences.getString(Name,"DEFAULT");

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
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Purpose, "Research");
                editor.commit();
                //Navigate to homeFragment
                Navigation.findNavController(view).navigate(R.id.action_modeSelectionFragment_to_homeFragment);
            }
        });

        Button personalButton = view.findViewById(R.id.personal_button);
        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Store new purpose mode
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Purpose, "Personal");
                editor.commit();
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