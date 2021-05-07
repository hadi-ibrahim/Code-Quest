package com.example.codequestapp.responses;

import android.app.DownloadManager;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.models.User;
import com.example.codequestapp.ui.quests.QuestCardAdapter;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;

public class EmailResponseListener implements Response.Listener<JSONArray> {

    private final TextInputLayout email;

    public EmailResponseListener(TextInputLayout email) {
        this.email = email;
    }

    @Override
    public void onResponse(JSONArray response) {
        User[] users = new Gson().fromJson(response.toString(), User[].class);
        if (users.length>0) {
            email.setErrorEnabled(true);
            email.setError("Email already exists.");
        }
        else email.setErrorEnabled(false);

    }
}
