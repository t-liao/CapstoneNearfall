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

public class PersonalExpFragment extends Fragment implements View.OnClickListener{
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Purpose = "purposeKey";
    public static final String Name = "nameKey";
    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_personal_exp.xml
        View view = inflater.inflate(R.layout.fragment_personal_exp, container, false);

        //Grab the specified sharedpreference
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //Grab stored name value
        String name = sharedpreferences.getString(Name,"DEFAULT");

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
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Purpose, "Personal");
                editor.commit();
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