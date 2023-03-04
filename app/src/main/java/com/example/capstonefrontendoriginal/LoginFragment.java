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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment implements View.OnClickListener {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String savedEmail = sharedpreferences.getString(Email,"DEFAULT");
        String savedPassword = sharedpreferences.getString(Password,"DEFAULT");
        String purpose = sharedpreferences.getString(Purpose,"DEFAULT");

        email =(EditText)view.findViewById(R.id.email_login);
        password = (EditText)view.findViewById(R.id.password_login);

        ImageButton backButton = view.findViewById(R.id.back_button_login);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_welcomeFragment);
            }
        });

        TextView signUp = view.findViewById(R.id.sign_in_text);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        Button login = view.findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.length()==0){
                    email.setError("Enter Email Address");
                } else if (password.length()==0){
                    password.setError("Enter Password");
                } else if (password.getText().toString().equals(savedPassword) && email.getText().toString().equals(savedEmail)){

                    if(purpose.equals("Research") || purpose.equals("Personal")){
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                    }  else {
                        Toast.makeText(getActivity().getApplicationContext(), "Error: Purpose was stored incorrectly",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "The email or password is incorrect!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    login.performClick();
                }
                return false;
            }
        });
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
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