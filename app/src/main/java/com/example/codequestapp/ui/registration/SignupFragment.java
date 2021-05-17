package com.example.codequestapp.ui.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.codequestapp.R;
import com.example.codequestapp.models.User;
import com.example.codequestapp.utils.LoginManager;
import com.example.codequestapp.viewmodels.EmailExistViewModel;
import com.example.codequestapp.viewmodels.SignupViewModel;
import com.example.codequestapp.viewmodels.UsernameExistViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Pattern;


public class SignupFragment extends Fragment {

    private SignupViewModel signupViewModel;
    private EmailExistViewModel emailViewModel;
    private UsernameExistViewModel usernameViewModel;


    private TextInputLayout emailContainer;
    private TextInputEditText email;
    private TextInputEditText username;
    private TextInputLayout usernameContainer;
    private TextInputEditText name;
    private TextInputLayout nameContainer;
    private TextInputLayout passwordContainer;
    private TextInputEditText password;
    private TextInputLayout verifyPasswordContainer;
    private TextInputEditText verifyPassword;
    private TextInputEditText birthdayTxt;
    private ImageView calendarIcon;
    private MaterialDatePicker picker;

    private Button signupBtn;

    private TextView required;
    private TextView responseText;

    private RequestQueue queue;

    public SignupFragment() {

    }

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.YEAR, 1900);
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        long startYear = calendar.getTimeInMillis();
        CalendarConstraints constraints = new CalendarConstraints.Builder()
                        .setStart(startYear)
                        .setEnd(today)
                        .build();
        picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(constraints)
                .build();

        picker.addOnPositiveButtonClickListener(selection -> {
//                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
            birthdayTxt.setText(picker.getHeaderText());
        });
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        signupViewModel.init();
        signupViewModel.getData().observe(this, message -> {
            if (message != null) {
                if (message.isSuccess()) {
                    if (message.getStatusCode().equals("200")) {
                        LoginManager.getInstance().login(message.getMessage());
                        System.out.println(message.getMessage());

                        responseText.setText("");
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.pageFragment, new WelcomeFragment());
                        transaction.commit();
                    }
                } else responseText.setText(message.getMessage());
            }
        });
        emailViewModel = new ViewModelProvider(this).get(EmailExistViewModel.class);
        emailViewModel.init();
        emailViewModel.getData().observe(this, message -> {
            if (message != null) {
                if (!message.isSuccess()) {
                    if (message.getStatusCode().equals("200")) {
                        emailContainer.setErrorEnabled(true);
                        emailContainer.setError("Username already exists.");
                    }
                } else emailContainer.setErrorEnabled(false);

            } else responseText.setText(message.getMessage());
        });

        usernameViewModel = new ViewModelProvider(this).get(UsernameExistViewModel.class);
        usernameViewModel.init();
        usernameViewModel.getData().observe(this, message -> {
            if (message != null) {
                if (!message.isSuccess()) {
                    if (message.getStatusCode().equals("200")) {
                        usernameContainer.setErrorEnabled(true);
                        usernameContainer.setError("Username already exists.");
                    }
                } else usernameContainer.setErrorEnabled(false);

            } else responseText.setText(message.getMessage());
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        required = view.findViewById(R.id.requiredTxtSignup);
        responseText = view.findViewById(R.id.responseTextSignup);
        signupBtn = view.findViewById(R.id.signUpBtn);
        birthdayTxt = view.findViewById(R.id.birthdayFieldProfileText);
        calendarIcon = view.findViewById(R.id.calendarIcon);

        passwordContainer = view.findViewById(R.id.passwordFieldSignup);
        password = view.findViewById(R.id.passwordFieldSignupText);

        name = view.findViewById(R.id.nameFieldProfileText);
        nameContainer = view.findViewById(R.id.nameFieldProfile);

        verifyPasswordContainer = view.findViewById(R.id.verifyPasswordFieldSignup);
        verifyPassword = view.findViewById(R.id.verifyPasswordFieldSignupText);

        username = view.findViewById(R.id.usernameFieldProfileText);
        usernameContainer = view.findViewById(R.id.usernameProfileField);

        email = view.findViewById(R.id.emailFieldProfileText);
        emailContainer = view.findViewById(R.id.emailFieldProfile);

        signupBtn = view.findViewById(R.id.signUpBtn);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkIfEmailAlreadyExists();
                    if (!isValidEmail(email.getText()))
                        emailContainer.setError("Invalid email");
                    else
                        emailContainer.setErrorEnabled(false);
                }
                validateSubmission();
            }
        });

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkIfUsernameExists();
                    if (username.getText().toString().isEmpty())
                        usernameContainer.setError("Username cannot be empty");
                    else usernameContainer.setErrorEnabled(false);
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

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (name.getText().toString().isEmpty())
                        nameContainer.setError("Name cannot be empty");
                    else nameContainer.setErrorEnabled(false);
                }
                validateSubmission();
            }
        });

        verifyPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!password.getText().toString().equals(verifyPassword.getText().toString()))
                        verifyPasswordContainer.setError("Passwords do not match.");
                    else verifyPasswordContainer.setErrorEnabled(false);
                }
                validateSubmission();
            }
        });


        calendarIcon.setOnClickListener(v -> picker.show(getActivity().getSupportFragmentManager(), "tag"));
        birthdayTxt.setOnClickListener(v -> picker.show(getActivity().getSupportFragmentManager(), "tag"));
        signupBtn.setOnClickListener(v -> signUp());
        return view;
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches() && !emailContainer.isErrorEnabled());
    }

    public static boolean isStrongPassword(CharSequence target) {
        Pattern pattern = Pattern.compile("^.{8,}$");
        return (!TextUtils.isEmpty(target) && pattern.matcher(target).matches());
    }

    public void validateSubmission() {
        if (verifyPasswordContainer.isErrorEnabled() || passwordContainer.isErrorEnabled() || emailContainer.isErrorEnabled() || fieldsAreEmpty() || usernameContainer.isErrorEnabled()) {
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
                || username.getText().toString().isEmpty()
                || name.getText().toString().isEmpty();
    }

    private void signUp() {
        signupViewModel.signUp(getUserFromFields());
    }

    private void checkIfEmailAlreadyExists() {
        emailViewModel.validateEmail(email.getText().toString());
    }

    private void checkIfUsernameExists() {
        usernameViewModel.validateUsername(username.getText().toString());
    }

    private User getUserFromFields() {
        User user = new User();
        user.setUsername(username.getText().toString());
        user.setBirthday(birthdayTxt.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEmail(email.getText().toString());
        user.setFullName(name.getText().toString());
        return user;
    }

}