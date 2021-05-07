package com.example.codequestapp.responses;

import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class LoginErrorResponseListener implements Response.ErrorListener {

    private TextView responseText;

    @Override
    public void onErrorResponse(VolleyError error) {
        responseText.setText("Invalid username or password.");
    }

    public LoginErrorResponseListener(TextView response) {
        this.responseText = response;
    }

}
