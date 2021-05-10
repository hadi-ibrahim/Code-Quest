package com.example.codequestapp.requests;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.codequestapp.models.User;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.QuestResponseListener;

import org.json.JSONArray;

public class ProfileGetRequest extends JsonArrayRequest {

    private MutableLiveData<User> profile;

    public ProfileGetRequest(int method, String url, @Nullable JSONArray jsonRequest, Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);

    }

    public ProfileGetRequest() {
        this(Method.GET, RequestUtil.BASE_URL + "api/quests", null, new QuestResponseListener(), new ErrorResponseListener());
    }


}
