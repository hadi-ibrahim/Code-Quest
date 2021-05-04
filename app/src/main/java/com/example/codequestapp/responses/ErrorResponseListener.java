package com.example.codequestapp.responses;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class ErrorResponseListener implements Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println(error.getMessage());
    }
}
