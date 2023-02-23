package com.example.capstonefrontendoriginal;

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
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button Login = view.findViewById(R.id.login_button);
        Login.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_loginFragment);
    }
}