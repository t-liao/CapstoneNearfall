package com.example.capstonefrontendoriginal;

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

public class SignUp1Fragment extends Fragment implements View.OnClickListener {
    EditText email;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "emailKey";
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up1, container, false);

        email=(EditText)view.findViewById(R.id.email_signup1);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Button Next = view.findViewById(R.id.next_button_signup1);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.length()==0){
                    email.setError("Enter Email Address");
                } else {
                    String e  = email.getText().toString();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Email, e);
                    editor.commit();

                    Navigation.findNavController(view).navigate(R.id.action_signUp1Fragment_to_signUp2Fragment);
                }
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