package com.example.codequestapp.responses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.example.codequestapp.models.CategoryProgress;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class ProgressResponseListener implements Response.Listener<JSONArray> {

    private MutableLiveData<ArrayList<CategoryProgress>> data;

    public ProgressResponseListener() {
        this.data = new MutableLiveData<ArrayList<CategoryProgress>>();
    }

    @Override
    public void onResponse(JSONArray response) {
        CategoryProgress[] progresses = new Gson().fromJson(response.toString(), CategoryProgress[].class);
        data.postValue(new ArrayList<CategoryProgress>(Arrays.asList(progresses)));
    }

    public LiveData<ArrayList<CategoryProgress>> getLiveData() {
        return data;
    }
}
