package com.example.nearfall;

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
import android.widget.RadioButton;

import com.example.nearfall.Database.UserManager;

public class SignUp5Fragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up5, container, false);

        RadioButton researchRadioButton = (RadioButton) view.findViewById(R.id.researchRadioButton);
        RadioButton personalRadioButton = (RadioButton) view.findViewById(R.id.personalRadioButton);
        UserManager userManager = MainActivity.getUserManager();


        Button Next = view.findViewById(R.id.next_button_signup5);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (researchRadioButton.isChecked()){
                    userManager.setPurpose("Research");

                    //Navigation.findNavController(view).navigate(R.id.action_signUp5Fragment_to_researchModeFragment);
                } else if (personalRadioButton.isChecked()){
                    userManager.setPurpose("Personal");

                    //Navigation.findNavController(view).navigate(R.id.action_signUp5Fragment_to_personalModeFragment);
                }

            }
        }); //ctrl + /

        ImageButton Back = view.findViewById(R.id.back_button_signup5);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUp5Fragment_to_signUp4Fragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}