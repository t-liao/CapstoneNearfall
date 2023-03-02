package com.example.capstonefrontendoriginal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class LoginV2Fragment extends Fragment implements View.OnClickListener {
    EditText email, password;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";
    public static final String Purpose = "purposeKey";
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_v2, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String savedEmail = sharedpreferences.getString(Email,"DEFAULT");
        String savedPassword = sharedpreferences.getString(Password,"DEFAULT");
        String purpose = sharedpreferences.getString(Purpose,"DEFAULT");

        email =(EditText)view.findViewById(R.id.email_signupv2);
        password = (EditText)view.findViewById(R.id.password_loginv2);

        Button signUp = view.findViewById(R.id.sign_up_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginV2Fragment_to_signUp1Fragment);
            }
        });

        Button login = view.findViewById(R.id.login_button2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.length()==0){
                    email.setError("Enter Email Address");
                } else if (password.length()==0){
                    password.setError("Enter Password");
                } else if (password.getText().toString().equals(savedPassword) && email.getText().toString().equals(savedEmail)){

                    if(purpose.equals("Research")){
                        Navigation.findNavController(view).navigate(R.id.action_loginV2Fragment_to_researchModeFragment);
                    } else if (purpose.equals("Personal")){
                        Navigation.findNavController(view).navigate(R.id.action_loginV2Fragment_to_personalModeFragment);
                    }


                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "The email or password is incorrect!",
                            Toast.LENGTH_LONG).show();
                }


            }
        });






        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == android.view.KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    login.performClick();
                }
                return false;
            }
        });

        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == android.view.KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    login.performClick();
                }
                return false;
            }
        });

        return view;
    }



    @Override
    public void onClick(View view) {

    }
}