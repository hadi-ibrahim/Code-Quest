package com.example.codequestapp.requests;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.codequestapp.responses.EmailResponseListener;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.QuestResponseListener;
import com.example.codequestapp.responses.UsernameResponseListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;

public class UsernameExistGetRequest extends JsonArrayRequest {
    public UsernameExistGetRequest(int method, String url, @Nullable JSONArray jsonRequest, Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public UsernameExistGetRequest(String username, TextInputLayout usernameContainer) {
        this(Method.GET, RequestUtil.BASE_URL + "api/user/usernameExist?username=" + username, null, new UsernameResponseListener(usernameContainer), new ErrorResponseListener());
    }
}
