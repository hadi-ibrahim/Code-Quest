package com.example.codequestapp.responses;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Response;
import com.example.codequestapp.R;
import com.example.codequestapp.ui.registration.WelcomeFragment;
import com.example.codequestapp.utils.LoginManager;
import com.google.gson.Gson;

public class RegistrationResponseListener implements Response.Listener<String> {

    private final FragmentManager manager;
    private final TextView responseText;

    public RegistrationResponseListener(FragmentManager manager, TextView responseText) {
        this.manager = manager;
        this.responseText = responseText;
    }

    @Override
    public void onResponse(String response) {
        Gson gson = new Gson();
        ResponseMessage message = gson.fromJson(response, ResponseMessage.class);

        if (message.getStatusCode().equals("200")) {
            LoginManager.getInstance().login(message.getMessage());
            System.out.println(message.getMessage());

            responseText.setText("");
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.pageFragment, new WelcomeFragment());
            transaction.commit();
        }
    }
}
