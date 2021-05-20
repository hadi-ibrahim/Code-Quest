package com.example.codequestapp.requests;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.ResponseMessage;
import com.example.codequestapp.responses.UsernameResponseListener;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class UsernameExistGetRequest extends JsonArrayRequest {

    private LiveData<ResponseMessage> data;
    private UsernameResponseListener listener;
    private Map<String, String> params;


    public UsernameExistGetRequest(int method, String url, @Nullable JSONArray jsonRequest, Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.listener = (UsernameResponseListener) listener;
        data = this.listener.getData();
        params = new HashMap<String, String>();
    }

    public UsernameExistGetRequest() {
        this(Method.GET, RequestUtil.BASE_URL + "api/user/usernameExist", null, new UsernameResponseListener(), new ErrorResponseListener());
    }

    public void setUsername(String username) {
        params.put("username", username);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    public LiveData<ResponseMessage> getData() {
        return data;
    }

    @Override
    public String getUrl() {
        return super.getUrl() + "?username=" + params.get("username");
    }
}
