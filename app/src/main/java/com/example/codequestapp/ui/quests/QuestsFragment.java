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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.codequestapp.R;
import com.example.codequestapp.ui.registration.SignupFragment;

import org.json.JSONArray;

public class QuestsFragment extends Fragment {


    public QuestsFragment() {

    }
    public static QuestsFragment newInstance() {
        return new QuestsFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quests, container, false);
        final TextView textView = root.findViewById(R.id.text_quest);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://192.168.0.106:3000/api/quests";

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                textView.setText("Response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Error : " + error.getMessage());
            }
        });
        queue.add(stringRequest);
        return root;
    }
}