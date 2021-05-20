package com.example.codequestapp.requests;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.codequestapp.models.User;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.ProfileResponseListener;
import com.example.codequestapp.utils.TokenEncryptedSharedPreferences;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ProfileGetRequest extends JsonArrayRequest {

    private LiveData<User> data;
    private Map<String, String> header;
    private ProfileResponseListener listener;


    public ProfileGetRequest(int method, String url, @Nullable JSONArray jsonRequest, Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.header = new HashMap<String, String>();
        this.listener = (ProfileResponseListener) listener;
        this.data =  this.listener.getData();
        header.put("auth-token", TokenEncryptedSharedPreferences.getInstance().getAuthToken());


    }

    public ProfileGetRequest() {
        this(Method.GET, RequestUtil.BASE_URL + "api/user", null, new ProfileResponseListener(), new ErrorResponseListener());
    }

    public LiveData<User> getData() {
        return data;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.header;
    }

}
