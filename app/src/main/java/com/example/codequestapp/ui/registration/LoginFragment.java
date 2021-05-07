package com.example.codequestapp.ui.registration;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.codequestapp.R;
import com.example.codequestapp.models.User;
import com.example.codequestapp.requests.LoginPostRequest;
import com.example.codequestapp.requests.RequestQueueSingleton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {


    private TextInputEditText password;
    private TextInputEditText username;

    private TextView responseText;

    private Button signIn;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = view.findViewById(R.id.usernameFieldText);
        password = view.findViewById(R.id.passwordFieldText);

        responseText = view.findViewById(R.id.responseText);

        signIn = view.findViewById(R.id.signInBtn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                System.out.println(username.getText().toString());
                RequestQueue queue = RequestQueueSingleton.getInstance(getContext()).getRequestQueue();
                LoginPostRequest request = new LoginPostRequest(user, getContext(), responseText, getActivity().getSupportFragmentManager());
                queue.add(request);
//                switchFragment(welcomeFragment);
            }
        });

        return view;
    }
}