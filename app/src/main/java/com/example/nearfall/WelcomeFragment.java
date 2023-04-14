package com.example.nearfall;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nearfall.User.UserManager;

public class WelcomeFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_welcome.xml
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        UserManager userManager = MainActivity.getUserManager();

        //get Nav controller since it is not initialized yet
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        NavController navCo = navHostFragment.getNavController();

        //Check if user had past data to stay signed in
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                "CurrUser", Context.MODE_PRIVATE);
        String userEmail = sharedPref.getString("User","");
        if (!(userEmail.length() == 0)){
            userManager.setUser(userManager.getUserByEmail(userEmail));
            navCo.navigate(R.id.action_welcomeFragment_to_homeFragment);
        }


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