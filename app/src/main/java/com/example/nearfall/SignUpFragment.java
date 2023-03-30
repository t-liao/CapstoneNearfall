package com.example.nearfall;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.SQLException;
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
import com.example.nearfall.User.HashedPassword;
import com.example.nearfall.User.User;
import com.example.nearfall.User.UserManager;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    EditText name, email, password, p2, dateOfBirth;

    private DatePickerDialog dp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_sign_up.xml
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        UserManager userManager = MainActivity.getUserManager();
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
                    String mode;


                    if (researchRadioButton.isChecked()){
                        mode = "Research";
                    } else {
                        mode = "Personal";
                    }
                    try {
                        // Checks if email already used
                        if (userManager.emailAlreadyInUse(e)) {
                            Toast.makeText(requireActivity().getApplicationContext(),
                                    "Email is already in use",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        //Create new user
                        User new_user = new User(n, e, mode, mode, new HashedPassword(p));
                        int userId = userManager.addUser(new_user);
                        new_user.setId(userId);
                        //Set current user to new created user
                        userManager.setUser(new_user);
                        //Navigate to tutorialOptionFragment
                        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_tutorialOptionFragment);
                    } catch (NoSuchAlgorithmException | SQLException ex) {
                        Toast.makeText(requireActivity().getApplicationContext(),
                                "Error: Something went wrong, please try again",
                                Toast.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }
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