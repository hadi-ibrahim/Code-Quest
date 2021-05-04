package com.example.codequestapp.requests;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.QuestResponseListener;

import org.json.JSONArray;

public class QuestsGetRequest extends JsonArrayRequest {
    public QuestsGetRequest(int method, String url, @Nullable JSONArray jsonRequest, Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public QuestsGetRequest(String url, Context context, RecyclerView cards) {
        this(Method.GET, url, null, new QuestResponseListener<JSONArray>(cards, context), new ErrorResponseListener());
    }
}
