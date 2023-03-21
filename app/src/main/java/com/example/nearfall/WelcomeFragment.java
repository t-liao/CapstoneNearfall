package com.example.nearfall;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WelcomeFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_welcome.xml
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        //When login button is clicked
        Button Login = view.findViewById(R.id.login_button);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to loginFragment
                Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_loginFragment);
            }
        });

        //When create account button is clicked
        Button createAccount = view.findViewById(R.id.create_account_wel_button);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to signUpFragment
                Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_signUpFragment);
            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {

    }
}