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
import com.example.codequestapp.requests.QuestsGetRequest;
import com.example.codequestapp.requests.RequestQueueSingleton;
import com.example.codequestapp.requests.RequestUtil;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.QuestResponseListener;
import com.example.codequestapp.ui.registration.SignupFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

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
        RequestQueue queue = RequestQueueSingleton.getInstance(getContext()).getRequestQueue();

        QuestsGetRequest jsonRequest = new QuestsGetRequest(getContext(), questCards);
        queue.add(jsonRequest);
        return root;
    }
}