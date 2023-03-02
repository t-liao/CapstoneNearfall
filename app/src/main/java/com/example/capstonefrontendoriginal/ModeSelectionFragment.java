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
        View view = inflater.inflate(R.layout.fragment_mode_selection, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(Name,"DEFAULT");

        TextView text = (TextView) view.findViewById(R.id.name_text);
        text.setText(name);

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

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Purpose, "Research");
                editor.commit();
                Navigation.findNavController(view).navigate(R.id.action_modeSelectionFragment_to_researchModeFragment);
            }
        });

        Button personalButton = view.findViewById(R.id.personal_button);
        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Purpose, "Personal");
                editor.commit();
                Navigation.findNavController(view).navigate(R.id.action_modeSelectionFragment_to_personalModeFragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}