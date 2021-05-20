package com.example.codequestapp.requests;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.responses.EmailResponseListener;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.QuestResponseListener;
import com.example.codequestapp.responses.ResponseMessage;
import com.example.codequestapp.utils.TokenEncryptedSharedPreferences;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class QuestGetRequest extends StringRequest {
    private LiveData<Quest> data;
    private QuestResponseListener listener;
    private Map<String, String> header;
    private final Map<String, String> params;


    public QuestGetRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.header = new HashMap<String, String>();
        this.listener = (QuestResponseListener) listener;
        this.data = this.listener.getData();
        params = new HashMap<String, String>();
        header.put("auth-token", TokenEncryptedSharedPreferences.getInstance().getAuthToken());

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.header;
    }

    public QuestGetRequest() {
        this(Method.GET, RequestUtil.BASE_URL + "api/quests/questionsAndPuzzles/", new QuestResponseListener(), new ErrorResponseListener());
    }

    public void setId(int id) {
        params.put("id", "" + id);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    public LiveData<Quest> getData() {
        return data;
    }

    @Override
    public String getUrl() {
        return super.getUrl() + params.get("id");
    }
}
