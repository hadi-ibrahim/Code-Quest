package com.example.codequestapp.requests;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.codequestapp.models.CategoryProgress;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.ProgressResponseListener;
import com.example.codequestapp.responses.QuestResponseListener;
import com.example.codequestapp.utils.TokenEncryptedSharedPreferences;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProgressGetRequest extends JsonArrayRequest {
    private LiveData<ArrayList<CategoryProgress>> data;
    private ProgressResponseListener listener;
    private Map<String, String> header = new HashMap<String, String>();

    public ProgressGetRequest(int method, String url, @Nullable JSONArray jsonRequest, Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.listener = (ProgressResponseListener) listener;
        data = this.listener.getLiveData();
        header.put("auth-token", TokenEncryptedSharedPreferences.getInstance().getAuthToken());

    }

    public ProgressGetRequest() {
        this(Request.Method.GET, RequestUtil.BASE_URL + "api/user/stats", null, new ProgressResponseListener(), new ErrorResponseListener());
    }

    public LiveData<ArrayList<CategoryProgress>> getQuestsData() {
        return data;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.header;
    }

    public LiveData<ArrayList<CategoryProgress>> getData() { return this.data; }
}
