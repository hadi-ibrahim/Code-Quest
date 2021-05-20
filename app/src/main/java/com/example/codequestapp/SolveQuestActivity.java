package com.example.codequestapp;


import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.codequestapp.adapters.PuzzleCardAdapter;
import com.example.codequestapp.models.Puzzle;
import com.example.codequestapp.models.PuzzleOption;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.models.Question;
import com.example.codequestapp.ui.achievements.AchievementsFragment;
import com.example.codequestapp.ui.profile.ProfileFragment;
import com.example.codequestapp.ui.quests.QuestCardAdapter;
import com.example.codequestapp.adapters.QuestionCardAdapter;
import com.example.codequestapp.ui.quests.QuestsFragment;
import com.example.codequestapp.ui.registration.RegistrationActivity;
import com.example.codequestapp.utils.AppContext;
import com.example.codequestapp.utils.LoginManager;
import com.example.codequestapp.viewmodels.QuestViewModel;
import com.example.codequestapp.viewmodels.SpecificQuestViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SolveQuestActivity extends AppCompatActivity {

    private Quest quest;
    private SpecificQuestViewModel viewModel;
    private RecyclerView questionsView;
    private RecyclerView puzzlesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);
        requireLogin();

        questionsView = findViewById(R.id.questionsView);
        puzzlesView = findViewById(R.id.puzzlesView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.quest = (Quest) getIntent().getParcelableExtra("quest");
        getSupportActionBar().setTitle(quest.getTitle());

        viewModel = new ViewModelProvider(this).get(SpecificQuestViewModel.class);
        viewModel.init();
        viewModel.getData().observe(this, response -> {
            QuestionCardAdapter adapter = new QuestionCardAdapter( response.getQuestions().toArray(new Question[0]), this);
            questionsView.setAdapter(adapter);
            questionsView.setLayoutManager(new LinearLayoutManager(this));

            PuzzleCardAdapter puzzleAdapter = new PuzzleCardAdapter( response.getPuzzles().toArray(new Puzzle[0]), this);
            puzzlesView.setAdapter(puzzleAdapter);
            puzzlesView.setLayoutManager(new LinearLayoutManager(this));

        });
        viewModel.getPuzzlesAndQuestions(quest.getId());

    }

    @Override
    protected void onResume() {
        super.onResume();
        requireLogin();
    }

    private void requireLogin() {
        if (!LoginManager.getInstance().isLoggedIn()) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_bar_quests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;

    }

    public void toggleColorMode(MenuItem item) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

}