package com.example.codequestapp.responses;

import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class SignupErrorResponseListener implements Response.ErrorListener {

    private TextView responseText;

    @Override
    public void onErrorResponse(VolleyError error) {
        responseText.setText("Sign up failed. Try again later!");
    }

    public SignupErrorResponseListener(TextView response) {
        this.responseText = response;
    }

}
