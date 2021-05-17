package com.example.codequestapp.requests;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.codequestapp.models.User;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.ProfileResponseListener;
import com.example.codequestapp.responses.ResponseMessage;
import com.example.codequestapp.responses.UpdateProfileResponseListener;
import com.example.codequestapp.utils.TokenEncryptedSharedPreferences;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ProfilePutRequest extends StringRequest {
    private LiveData<ResponseMessage> data;
    private Map<String, String> header;
    private UpdateProfileResponseListener listener;
    private User user;


    public ProfilePutRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.header = new HashMap<String, String>();
        this.listener = (UpdateProfileResponseListener) listener;
        this.data =  this.listener.getData();
        header.put("auth-token", TokenEncryptedSharedPreferences.getInstance().getAuthToken());

    }

    public ProfilePutRequest() {
        this(Request.Method.PUT, RequestUtil.BASE_URL + "api/user", new UpdateProfileResponseListener(), new ErrorResponseListener());
    }

    public LiveData<ResponseMessage> getData() {
        return data;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.header;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("fullName", user.getFullName());
        params.put("email", user.getEmail());
        params.put("birthday", user.getBirthday());
        return params;
    }
}
