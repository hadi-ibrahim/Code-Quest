package com.example.codequestapp.requests;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.codequestapp.responses.EmailResponseListener;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.QuestResponseListener;
import com.example.codequestapp.responses.ResponseMessage;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class EmailExistGetRequest extends JsonArrayRequest {

    private LiveData<ResponseMessage> data;
    private EmailResponseListener listener;
    private Map<String, String> params;

    public EmailExistGetRequest(int method, String url, @Nullable JSONArray jsonRequest, Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.listener = (EmailResponseListener) listener;
        this.data = this.listener.getData();
        params = new HashMap<String, String>();

    }

    public EmailExistGetRequest() {
        this(Method.GET, RequestUtil.BASE_URL + "api/user/emailExist", null, new EmailResponseListener(), new ErrorResponseListener());
    }

    public void setEmail(String email) {
        params.put("email", email);
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
        return super.getUrl() + "?email=" + params.get("email");
    }
}
