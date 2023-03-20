package com.example.nearfall;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.nearfall.Database.UserManager;

public class SignUp1Fragment extends Fragment implements View.OnClickListener {
    EditText email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up1, container, false);

        email=(EditText)view.findViewById(R.id.email_signup1);
        UserManager userManager = MainActivity.getUserManager();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Button Next = view.findViewById(R.id.next_button_signup1);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.length()==0){
                    email.setError("Enter Email Address");
                } else if (!isEmailValid(email.getText().toString())) {
                    email.setError("Email Address Is Invalid");
                }else {
                    String e  = email.getText().toString();
                    userManager.setEmail(e);
                    Navigation.findNavController(view).navigate(R.id.action_signUp1Fragment_to_signUp2Fragment);
                }
            }
            boolean isEmailValid(CharSequence email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
        });

        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Next.performClick();
                }
                return false;
            }
        });

        ImageButton Back = view.findViewById(R.id.back_button_signup1);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUp1Fragment_to_loginV2Fragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}