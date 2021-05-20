package com.example.codequestapp.ui.quests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codequestapp.R;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.viewmodels.QuestViewModel;

public class QuestsFragment extends Fragment {

    private QuestViewModel viewModel;
    private RecyclerView questCards;

    public QuestsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QuestViewModel.class);
        viewModel.init();
        viewModel.getData().observe(this, response -> {
            if (response != null) {
                Quest[] quests = response.toArray(new Quest[0]);
                QuestCardAdapter adapter = new QuestCardAdapter(response.toArray(quests), getContext(), questCards);
                questCards.setAdapter(adapter);
                questCards.setLayoutManager(new LinearLayoutManager(getContext()));            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quests, container, false);
        questCards = view.findViewById(R.id.questCards);
        viewModel.getQuests();
        return view;
    }
}