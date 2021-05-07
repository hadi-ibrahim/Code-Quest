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

public class LoginPostRequest extends StringRequest {

    private User user;

    public LoginPostRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public LoginPostRequest(User user, Context context, TextView responseText, FragmentManager manager) {
        this(Method.POST, RequestUtil.BASE_URL + "api/user/login", new RegistrationResponseListener(manager, responseText), new LoginErrorResponseListener(responseText));
        this.user = user;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
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
        String responseString = "";
        ResponseMessage res = new ResponseMessage();
        if (response != null) {
            responseString = String.valueOf(response.statusCode);
            res = new ResponseMessage(responseString, new String(response.data));
        }
        Gson gson = new Gson();
        return Response.success(gson.toJson(res), HttpHeaderParser.parseCacheHeaders(response));
    }

}

