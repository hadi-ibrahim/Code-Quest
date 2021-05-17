package com.example.codequestapp.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.codequestapp.R;
import com.example.codequestapp.models.User;
import com.example.codequestapp.viewmodels.ProfileViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.TimeZone;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private MaterialDatePicker picker;

    private TextInputLayout birthdayContainer;
    private TextInputEditText birthdayTxt;

    private TextInputLayout usernameContainer;
    private TextInputEditText usernameTxt;

    private TextInputLayout emailContainer;
    private TextInputEditText emailTxt;

    private TextInputLayout fullNameContainer;
    private TextInputEditText fullNameTxt;

    protected View mView;

    private ImageView profileImg;
    private ImageView calendarIcon;

    private Button btn;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.init();
        profileViewModel.getData().observe(this, user -> {
            if (user != null) {
                usernameTxt.setText(user.getUsername());
                birthdayTxt.setText(user.getBirthday());
                fullNameTxt.setText(user.getFullName());
                emailTxt.setText(user.getEmail());
                Picasso.with(getContext()).load(user.getImgPath()).into(profileImg);
            }
        });
        profileViewModel.getUpdateMessage().observe(this, responseMessage -> {
            if (responseMessage != null) {
                if (responseMessage.isSuccess())
                    Snackbar.make(mView, responseMessage.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        calendarIcon = view.findViewById(R.id.calendarIconProfile);
        birthdayContainer = view.findViewById(R.id.birthdayFieldProfile);
        birthdayTxt = view.findViewById(R.id.birthdayFieldProfileText);

        emailContainer = view.findViewById(R.id.emailFieldProfile);
        emailTxt = view.findViewById(R.id.emailFieldProfileText);

        fullNameContainer = view.findViewById(R.id.nameFieldProfile);
        fullNameTxt = view.findViewById(R.id.nameFieldProfileText);

        usernameContainer = view.findViewById(R.id.usernameProfileField);
        usernameTxt = view.findViewById(R.id.usernameFieldProfileText);

        profileImg = view.findViewById(R.id.profilePic);
        mView = view;

        usernameTxt.setEnabled(false);

        btn = (Button) view.findViewById(R.id.updateProfileBtn);
        btn.setOnClickListener(v -> updateProfile());
        profileViewModel.getProfileInfo();
        calendarIcon.setOnClickListener(v -> picker.show(getActivity().getSupportFragmentManager(), "tag"));
        birthdayTxt.setOnClickListener(v -> picker.show(getActivity().getSupportFragmentManager(), "tag"));

        return view;
    }

    private void updateProfile() {
        User user = new User();
        user.setBirthday(birthdayTxt.getText().toString());
        user.setEmail(emailTxt.getText().toString());
        user.setFullName(fullNameTxt.getText().toString());

        profileViewModel.updateProfileInfo(user);
    }
}