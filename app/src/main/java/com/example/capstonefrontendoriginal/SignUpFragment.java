package com.example.capstonefrontendoriginal;

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

import java.util.Calendar;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    EditText name, email, password, p2, dateOfBirth;

    private DatePickerDialog dp;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";
    public static final String Dob = "dateOfBirthKey";
    public static final String Purpose = "purposeKey";
    public static final String Detection = "detectionKey";
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_sign_up.xml
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        //Grab the specified sharedpreference
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Move the screen so that the EditText visible and above the soft keyboard
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        name =(EditText)view.findViewById(R.id.name_signup);
        email =(EditText)view.findViewById(R.id.email_signup);
        password = (EditText)view.findViewById(R.id.password_signup);
        p2 = (EditText)view.findViewById(R.id.repeat_password_signup);
        dateOfBirth = (EditText)view.findViewById(R.id.date_signup);
        RadioButton researchRadioButton = (RadioButton) view.findViewById(R.id.researchRadioButton);
        RadioButton personalRadioButton = (RadioButton) view.findViewById(R.id.personalRadioButton);

        //When date of birth field is clicked
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                //Show a datePickerDialog to select date
                dp = new DatePickerDialog(getActivity(),

                        //Select Theme
                        AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        //Set text to selected date
                        dateOfBirth.setText(day + "/" + (month + 1) + "/" + year);
                    }
                },year, month, day);
                dp.show();

            }
        });

        //When back button is clicked
        ImageButton Back_Button = view.findViewById(R.id.back_button_signup);
        Back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to loginFragment
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
            }
        });

        //When createAccount button is clicked
        Button createAccount = view.findViewById(R.id.create_account_button);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.length()==0){
                    //if name field is empty
                    name.setError("Enter Name");
                } else if (!isEmailValid(email.getText().toString())) {
                    //if entered address is not a valid email
                    email.setError("Email Address Is Invalid");
                } else if(email.length()==0){
                    //if email field is empty
                    email.setError("Enter Email Address");
                } else if (password.length() == 0) {
                    //if password field is empty
                    password.setError("Enter Password");
                } else if (p2.length() == 0) {
                    //if repeat password field is empty
                    p2.setError("Enter Password");
                } else if (!password.getText().toString().equals( p2.getText().toString())){
                    //if password and repeat password values do not match

                    //Print error
                    Toast.makeText(getActivity().getApplicationContext(), "Password does not match!",
                            Toast.LENGTH_LONG).show();
                } else if (dateOfBirth.length()==0) {
                    //if date of birth field is empty
                    dateOfBirth.setError("Enter Date Of Birth");
                } else {
                    String e  = email.getText().toString();
                    String n  = name.getText().toString();
                    String p  = password.getText().toString();
                    String d  = dateOfBirth.getText().toString();
                    String research  = researchRadioButton.getText().toString();
                    String personal  = personalRadioButton.getText().toString();

                    //Store all text field as string to sharedpreference
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear().commit();
                    editor.putString(Email, e);
                    editor.putString(Name, n);
                    editor.putString(Password, p);
                    editor.putString(Dob, d);
                    editor.putString(Detection,"Off");
                    editor.commit();

                    if (researchRadioButton.isChecked()){
                        //Store purpose
                        editor.putString(Purpose, research);
                        editor.commit();
                    } else if (personalRadioButton.isChecked()){
                        //Store purpose
                        editor.putString(Purpose, personal);
                        editor.commit();
                    }
                    //Navigate to tutorialOptionFragment
                    Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_tutorialOptionFragment);
                }
            }

            //Check whether email entered is a valid email
            boolean isEmailValid(CharSequence email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
        });

        //Listening for action when repeat password field is selected
        p2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //if enter key is pressed

                    //Hide soft keyboard
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    //click on date of birth field
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