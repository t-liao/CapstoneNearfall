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

public class SignUp3Fragment extends Fragment implements View.OnClickListener {
    EditText password, p2;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Password = "passwordKey";
    SharedPreferences sharedpreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up3, container, false);

        password=(EditText)view.findViewById(R.id.password_signup3);
        p2 = (EditText)view.findViewById(R.id.password_repeat_signup3);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Button Next = view.findViewById(R.id.next_button_signup3);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (password.length() == 0) {
                    password.setError("Enter Password");
                } else if (p2.length() == 0) {
                    p2.setError("Enter Password");
                }else if(password.getText().toString().equals( p2.getText().toString())){
                    String p  = password.getText().toString();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Password, p);
                    editor.commit();

                    Navigation.findNavController(view).navigate(R.id.action_signUp3Fragment_to_signUp4Fragment);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Password does not match!",
                            Toast.LENGTH_LONG).show();
                }


            }
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Next.performClick();
                }
                return false;
            }
        });

        p2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Next.performClick();
                }
                return false;
            }
        });

        ImageButton Back = view.findViewById(R.id.back_button_signup3);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUp3Fragment_to_signUp2Fragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}