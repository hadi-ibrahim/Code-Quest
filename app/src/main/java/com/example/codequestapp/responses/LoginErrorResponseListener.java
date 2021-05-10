package com.example.codequestapp.responses;

import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class LoginErrorResponseListener implements Response.ErrorListener {

    private MutableLiveData<ResponseMessage> data;

    public MutableLiveData<ResponseMessage> getData() {
        return data;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        data.postValue(new ResponseMessage("401", "Invalid username or password",false));
    }

}
