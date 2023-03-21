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
        User curr_user = userManager.getUser();

        //Grab email, password, and purpose values
        String savedEmail = curr_user.getEmail();
        String savedPassword = curr_user.getHashedPassword();
        String purpose = curr_user.getPurpose();

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
                if (email.length()==0){
                    //if email field is empty
                    email.setError("Enter Email Address");
                } else if (password.length()==0){
                    //if password field is empty
                    password.setError("Enter Password");
                } else if (password.getText().toString().equals(savedPassword) && email.getText().toString().equals(savedEmail)){
                    //if password and email matches the saved password
//else if() Needs to be changed when switched to database storage
                    //if purpose was stored correctly
                    if(purpose.equals("Research") || purpose.equals("Personal")){
                        userManager.setPurpose(purpose);
                        //Navigate to homeFragment
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                    }  else {
                        //if purpose was stored incorrectly

                        //Print Error
                        Toast.makeText(getActivity().getApplicationContext(), "Error: Purpose was stored incorrectly",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    // if email or password is not the same as stored

                    //Print error
                    Toast.makeText(getActivity().getApplicationContext(), "The email or password is incorrect!",
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