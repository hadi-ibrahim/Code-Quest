package com.example.codequestapp.responses;

import android.app.DownloadManager;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.codequestapp.models.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;

public class UsernameResponseListener implements Response.Listener<JSONArray> {

    private final TextInputLayout username;

    public UsernameResponseListener(TextInputLayout username) {
        this.username = username;
    }

    @Override
    public void onResponse(JSONArray response) {
        User[] users = new Gson().fromJson(response.toString(), User[].class);
        System.out.println("-----------------------------");
        if (users.length>0) {
            username.setErrorEnabled(true);
            username.setError("Username already exists.");
        }
        else username.setErrorEnabled(false);

    }
}
