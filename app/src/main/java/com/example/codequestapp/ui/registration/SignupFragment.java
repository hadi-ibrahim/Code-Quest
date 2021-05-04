package com.example.codequestapp.ui.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.codequestapp.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;


public class SignupFragment extends Fragment {
    private TextInputLayout emailContainer;
    private TextInputEditText email;
    private TextInputEditText username;

    private TextInputLayout birthdayContainer;
    private TextInputLayout passwordContainer;
    private TextInputEditText password;
    private TextInputLayout verifyPasswordContainer;
    private TextInputEditText verifyPassword;
    private TextInputEditText birthdayTxt;
    private ImageView calendarIcon;
    private MaterialDatePicker picker;
    private Button signupBtn;

    private TextView required;

    public SignupFragment() {

    }

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build();

        picker.addOnPositiveButtonClickListener(selection -> {
//                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
            birthdayTxt.setText(picker.getHeaderText());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        required = view.findViewById(R.id.requiredTxt);

        signupBtn = view.findViewById(R.id.signUpBtn);
        birthdayTxt = view.findViewById(R.id.birthdayFieldText);
        calendarIcon = view.findViewById(R.id.calendarIcon);
        emailContainer = view.findViewById(R.id.emailFieldSignup);
        passwordContainer = view.findViewById(R.id.passwordFieldSignup);
        password = view.findViewById(R.id.passwordFieldSignupText);

        verifyPasswordContainer = view.findViewById(R.id.verifyPasswordFieldSignup);
        verifyPassword = view.findViewById(R.id.verifyPasswordFieldSignupText);
        username = view.findViewById(R.id.usernameFieldSignupText);

        email = view.findViewById(R.id.emailFieldSignupText);
        birthdayContainer = view.findViewById(R.id.birthdayFieldSignup);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!isValidEmail(email.getText()))
                        emailContainer.setError("Invalid email");
                    else
                        emailContainer.setErrorEnabled(false);
                }
                validateSubmission();
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!isStrongPassword(password.getText()))
                        passwordContainer.setError("Password must contain at least 8 characters");
                    else passwordContainer.setErrorEnabled(false);
                }
                validateSubmission();
            }
        });

        verifyPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String pass = password.getText().toString();
                    String ver = verifyPassword.getText().toString();
                    if (!password.getText().toString().equals(verifyPassword.getText().toString()))
                        verifyPasswordContainer.setError("Passwords do not match.");
                    else verifyPasswordContainer.setErrorEnabled(false);
                }
                validateSubmission();
            }
        });


        calendarIcon.setOnClickListener(v -> picker.show(getActivity().getSupportFragmentManager(), "tag"));
        birthdayTxt.setOnClickListener(v -> picker.show(getActivity().getSupportFragmentManager(), "tag"));
        return view;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isStrongPassword(CharSequence target) {
        Pattern pattern = Pattern.compile("^.{8,}$");
        return (!TextUtils.isEmpty(target) && pattern.matcher(target).matches());
    }

    public void validateSubmission() {
        if (verifyPasswordContainer.isErrorEnabled() || passwordContainer.isErrorEnabled() || emailContainer.isErrorEnabled() || fieldsAreEmpty()) {
            signupBtn.setEnabled(false);
            required.setVisibility(View.VISIBLE);
        } else {
            required.setVisibility(View.INVISIBLE);
            signupBtn.setEnabled(true);
        }
    }

    private boolean fieldsAreEmpty() {
        return verifyPassword.getText().toString().isEmpty()
                || password.getText().toString().isEmpty()
                || email.getText().toString().isEmpty()
                || birthdayTxt.getText().toString().isEmpty()
                || username.getText().toString().isEmpty();
    }

}