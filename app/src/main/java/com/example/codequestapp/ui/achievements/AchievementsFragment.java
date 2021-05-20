package com.example.codequestapp.ui.achievements;

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
import com.example.codequestapp.adapters.AchievementsCardAdapter;
import com.example.codequestapp.models.CategoryProgress;
import com.example.codequestapp.viewmodels.AchievementsViewModel;

public class AchievementsFragment extends Fragment {

    private AchievementsViewModel achievementsViewModel;
    private RecyclerView progressBars;

    public AchievementsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        achievementsViewModel = new ViewModelProvider(this).get(AchievementsViewModel.class);
        achievementsViewModel.init();
        achievementsViewModel.getData().observe(this, response -> {
            if (response != null) {
                CategoryProgress[] bars = response.toArray(new CategoryProgress[0]);
                AchievementsCardAdapter adapter = new AchievementsCardAdapter(response.toArray(bars));
                progressBars.setAdapter(adapter);
                progressBars.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievements , container, false);
        progressBars = view.findViewById(R.id.progressBars);
        achievementsViewModel.getProgress();
        return view;
    }

}