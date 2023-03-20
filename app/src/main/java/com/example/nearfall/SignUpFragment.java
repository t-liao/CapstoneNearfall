package com.example.nearfall;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nearfall.Database.User;
import com.example.nearfall.Database.UserManager;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    EditText name, email, password, p2, dateOfBirth;

    private DatePickerDialog dp;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";
    public static final String Dob = "dateOfBirthKey";
    public static final String Purpose = "purposeKey";
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        UserManager userManager = MainActivity.getUserManager();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        name =(EditText)view.findViewById(R.id.name_signup);
        email =(EditText)view.findViewById(R.id.email_signup);
        password = (EditText)view.findViewById(R.id.password_signup);
        p2 = (EditText)view.findViewById(R.id.repeat_password_signup);
        dateOfBirth = (EditText)view.findViewById(R.id.date_signup);
        RadioButton researchRadioButton = (RadioButton) view.findViewById(R.id.researchRadioButton);
        RadioButton personalRadioButton = (RadioButton) view.findViewById(R.id.personalRadioButton);

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dp = new DatePickerDialog(getActivity(),
                        AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dateOfBirth.setText(day + "/" + (month + 1) + "/" + year);
                    }
                },year, month, day);
                dp.show();

            }
        });

        ImageButton Back_Button = view.findViewById(R.id.back_button_signup);
        Back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
            }
        });

        Button createAccount = view.findViewById(R.id.create_account_button);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.length()==0){
                    name.setError("Enter Name");
                } else if (!isEmailValid(email.getText().toString())) {
                    email.setError("Email Address Is Invalid");
                } else if(email.length()==0){
                    email.setError("Enter Email Address");
                } else if (password.length() == 0) {
                    password.setError("Enter Password");
                } else if (p2.length() == 0) {
                    p2.setError("Enter Password");
                } else if (!password.getText().toString().equals( p2.getText().toString())){
                    Toast.makeText(getActivity().getApplicationContext(), "Password does not match!",
                            Toast.LENGTH_LONG).show();
                } else if (dateOfBirth.length()==0) {
                    dateOfBirth.setError("Enter Date Of Birth");
                } else {
                    String e  = email.getText().toString();
                    String n  = name.getText().toString();
                    String p  = password.getText().toString();
                    Date d  = new Date(dateOfBirth.getText().toString());
                    String mode;


                    if (researchRadioButton.isChecked()){
                        mode = "Research";
                    } else {
                        mode = "Personal";
                    }

                    User new_user = new User(n, e, d, p, mode);
                    userManager.addUser(new_user);
                    userManager.setUser(new_user);
                    Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_tutorialOptionFragment);
                }
            }

            boolean isEmailValid(CharSequence email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
        });
        p2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    dateOfBirth.performClick();
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