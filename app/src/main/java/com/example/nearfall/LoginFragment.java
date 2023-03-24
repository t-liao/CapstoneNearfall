package com.example.nearfall;


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
import com.example.nearfall.Database.User;
import com.example.nearfall.Database.UserManager;

import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener {
    EditText email, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_login.xml
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        UserManager userManager = MainActivity.getUserManager();

        //Grab from EditText
        email =(EditText)view.findViewById(R.id.email_login);
        password = (EditText)view.findViewById(R.id.password_login);

        //When back button is pressed
        ImageButton backButton = view.findViewById(R.id.back_button_login);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to welcomeFragment
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_welcomeFragment);
            }
        });

        //When signup text is clicked
        TextView signUp = view.findViewById(R.id.sign_in_text);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to signUpFragment
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        //When login button is clicked
        Button login = view.findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (email.length()==0) {
                    //if email field is empty
                    email.setError("Enter Email Address");
                    return;
                }
                if (password.length()==0) {
                    //if password field is empty
                    password.setError("Enter Password");
                    return;
                }
                String typedEmail = email.getText().toString();
                String typedPassword = password.getText().toString();
                boolean accountDetailsCorrect = userManager.verifyAccountLogin(typedEmail, typedPassword);
                if (accountDetailsCorrect) {
                    userManager.setUser(userManager.getUserByEmail(typedEmail));
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                } else {
                    Toast.makeText(requireActivity().getApplicationContext(),
                            "The email or password is incorrect!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        //Listening to actions when email field is selected
        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //if enter is pressed, click on the login button
                    login.performClick();

                    //Does not work because the enter key is replaced with tab as there is another EditText after it.
                }
                return false;
            }
        });

        //Listening to actions when password field is selected
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //if enter is pressed, click on the login button
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