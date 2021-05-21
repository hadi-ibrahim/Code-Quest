package com.example.codequestapp.requests;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.models.User;
import com.example.codequestapp.responses.LoginErrorResponseListener;
import com.example.codequestapp.responses.RegistrationResponseListener;
import com.example.codequestapp.responses.ResponseMessage;
import com.example.codequestapp.responses.SolveQuestResponse;
import com.example.codequestapp.utils.TokenEncryptedSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SolveQuestPostRequest extends JsonObjectRequest {

    private Quest quest;
    private LiveData<ResponseMessage> data;
    private SolveQuestResponse listener;
    private Map<String, String> header;


    public SolveQuestPostRequest(int method, String url, JsonObject obj, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url,null, listener, errorListener);
        this.header = new HashMap<String, String>();
        this.listener = (SolveQuestResponse) listener;
        this.data = this.listener.getData();
        header.put("auth-token", TokenEncryptedSharedPreferences.getInstance().getAuthToken());

    }

    public SolveQuestPostRequest() {
        this(Method.POST, RequestUtil.BASE_URL + "api/quests/solve", null,  new SolveQuestResponse(), new LoginErrorResponseListener());
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public LiveData<ResponseMessage> getData () {
        return this.data;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.header;
    }

    @Override
    public byte[] getBody() {
        Gson gson = new Gson();
        String json =  "{\"quest\":" + gson.toJson(quest) + " }";
        try {
            return json.getBytes("utf-8");
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", "utf-8");
            return null;
        }
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

}

