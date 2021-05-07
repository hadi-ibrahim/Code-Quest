package com.example.codequestapp.requests;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.codequestapp.responses.EmailResponseListener;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.QuestResponseListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;

public class EmailExistGetRequest extends JsonArrayRequest {
    public EmailExistGetRequest(int method, String url, @Nullable JSONArray jsonRequest, Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public EmailExistGetRequest(String email, TextInputLayout emailContainer) {
        this(Method.GET, RequestUtil.BASE_URL + "api/user/emailExist?email=" + email, null, new EmailResponseListener(emailContainer), new ErrorResponseListener());
    }
}
