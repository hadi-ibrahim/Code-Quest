package com.example.codequestapp.ui.registration;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.codequestapp.R;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.models.User;
import com.example.codequestapp.requests.LoginPostRequest;
import com.example.codequestapp.requests.RequestQueueSingleton;
import com.example.codequestapp.ui.quests.QuestCardAdapter;
import com.example.codequestapp.utils.AppContext;
import com.example.codequestapp.utils.LoginManager;
import com.example.codequestapp.viewmodels.LoginViewModel;
import com.example.codequestapp.viewmodels.QuestViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;
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
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.init();
        viewModel.getData().observe(this, message -> {
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = view.findViewById(R.id.usernameFieldText);
        password = view.findViewById(R.id.passwordFieldText);

        responseText = view.findViewById(R.id.responseText);

        signIn = view.findViewById(R.id.signInBtn);
        signIn.setOnClickListener(v -> signIn());

        return view;
    }

    private void signIn() {
        User user = new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        viewModel.login(user);
    }
}