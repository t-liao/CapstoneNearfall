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

public class SignUp4Fragment extends Fragment implements View.OnClickListener {
    EditText day, month, year;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Day = "dayKey";
    public static final String Month = "monthKey";
    public static final String Year = "yearKey";

    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up4, container, false);

        day=(EditText)view.findViewById(R.id.day_signup4);
        month=(EditText)view.findViewById(R.id.month_signup4);
        year=(EditText)view.findViewById(R.id.year_signup4);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Button Next = view.findViewById(R.id.next_button_signup4);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(day.length()==0){
                    day.setError("Enter Day");
                } else if (month.length()==0){
                    month.setError("Enter Month");
                } else if (year.length()==0){
                    year.setError("Enter Year");
                } else {
                    String d  = day.getText().toString();
                    String m  = month.getText().toString();
                    String y  = year.getText().toString();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Day, d);
                    editor.putString(Month, m);
                    editor.putString(Year, y);
                    editor.commit();

                    Navigation.findNavController(view).navigate(R.id.action_signUp4Fragment_to_signUp5Fragment);
                }

            }
        });

        day.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Next.performClick();
                }
                return false;
            }
        });
        month.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Next.performClick();
                }
                return false;
            }
        });
        year.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Next.performClick();
                }
                return false;
            }
        });

        ImageButton Back = view.findViewById(R.id.back_button_signup4);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUp4Fragment_to_signUp3Fragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}