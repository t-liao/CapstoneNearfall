package com.example.nearfall;

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

import com.example.nearfall.Database.UserManager;

public class SignUp2Fragment extends Fragment implements View.OnClickListener {
    EditText name;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up2, container, false);

        name=(EditText)view.findViewById(R.id.name_signup2);
        UserManager userManager = MainActivity.getUserManager();

        Button Next = view.findViewById(R.id.next_button_signup2);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.length()==0){
                    name.setError("Enter Name");
                } else {
                    String n  = name.getText().toString();
                    userManager.setUserName(n);
                    Navigation.findNavController(view).navigate(R.id.action_signUp2Fragment_to_signUp3Fragment);
                }

            }
        });

        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Next.performClick();
                }
                return false;
            }
        });

        ImageButton Back = view.findViewById(R.id.back_button_signup2);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUp2Fragment_to_signUp1Fragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}