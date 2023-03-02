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
        View view = inflater.inflate(R.layout.fragment_personal_exp, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(Name,"DEFAULT");

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
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Purpose, "Personal");
                editor.commit();
                Navigation.findNavController(view).navigate(R.id.action_personalExpFragment_to_personalModeFragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}