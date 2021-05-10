package com.example.codequestapp.requests;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.codequestapp.models.User;
import com.example.codequestapp.responses.LoginErrorResponseListener;
import com.example.codequestapp.responses.RegistrationResponseListener;
import com.example.codequestapp.responses.ResponseMessage;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SignupPostRequest extends StringRequest {

    private User user;

    public SignupPostRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public SignupPostRequest(User user, Context context, TextView responseText, FragmentManager manager) {
        this(Method.POST, RequestUtil.BASE_URL + "api/user/register", new RegistrationResponseListener(), new LoginErrorResponseListener());
        this.user = user;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        params.put("fullName", user.getFullName());
        params.put("birthday", user.getBirthday());
        params.put("email", user.getEmail());
        return params;
    }


    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    public Map<String, String> getHeaders() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/x-www-form-urlencoded");
        return params;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        Gson gson = new Gson();
        return Response.success(gson.toJson(response.data), HttpHeaderParser.parseCacheHeaders(response));
    }

}

