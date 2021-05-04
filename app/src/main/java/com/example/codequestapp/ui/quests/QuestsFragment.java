package com.example.codequestapp.ui.quests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.codequestapp.R;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.requests.RequestUtil;
import com.example.codequestapp.ui.registration.SignupFragment;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

public class QuestsFragment extends Fragment {


    public QuestsFragment() {

    }
    public static QuestsFragment newInstance() {
        return new QuestsFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quests, container, false);
        RecyclerView questCards = root.findViewById(R.id.questCards);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = RequestUtil.BASE_URL + "api/quests";

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Quest [] quests = new Gson().fromJson(response.toString(), Quest[].class);
                QuestCardAdapter adapter = new QuestCardAdapter(quests, getContext());
                questCards.setAdapter(adapter);
                questCards.setLayoutManager(new LinearLayoutManager(getContext()));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
        return root;
    }
}