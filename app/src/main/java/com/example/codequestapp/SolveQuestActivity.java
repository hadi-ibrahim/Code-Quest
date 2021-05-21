package com.example.codequestapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.codequestapp.adapters.PuzzleCardAdapter;
import com.example.codequestapp.models.Puzzle;
import com.example.codequestapp.models.PuzzleOption;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.models.Question;
import com.example.codequestapp.adapters.QuestionCardAdapter;
import com.example.codequestapp.responses.ResponseMessage;
import com.example.codequestapp.ui.registration.RegistrationActivity;
import com.example.codequestapp.utils.AppContext;
import com.example.codequestapp.utils.LoginManager;
import com.example.codequestapp.viewmodels.SpecificQuestViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SolveQuestActivity extends AppCompatActivity {

    private Quest quest;
    private SpecificQuestViewModel viewModel;
    private RecyclerView questionsView;
    private RecyclerView puzzlesView;
    private Button submitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);
        requireLogin();

        questionsView = findViewById(R.id.questionsView);
        puzzlesView = findViewById(R.id.puzzlesView);

        submitBtn = findViewById(R.id.solveBtn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.quest = (Quest) getIntent().getParcelableExtra("quest");
        getSupportActionBar().setTitle(quest.getTitle());

        viewModel = new ViewModelProvider(this).get(SpecificQuestViewModel.class);
        viewModel.init();

        viewModel.getData().observe(this, response -> {
            QuestionCardAdapter adapter = new QuestionCardAdapter( response.getQuestions().toArray(new Question[0]), this, questionsView);
            questionsView.setAdapter(adapter);
            questionsView.setLayoutManager(new LinearLayoutManager(this));

            PuzzleCardAdapter puzzleAdapter = new PuzzleCardAdapter( response.getPuzzles().toArray(new Puzzle[0]), this, puzzlesView);
            puzzlesView.setAdapter(puzzleAdapter);
            puzzlesView.setLayoutManager(new LinearLayoutManager(this));

        });
        viewModel.getSolveResponse().observe(this, responseMessage -> {
            if(responseMessage.isSuccess()) {
                System.out.println("true");
                Snackbar.make(submitBtn, "Achievement Unlocked!", Snackbar.LENGTH_LONG) .show();
            }
            else Snackbar.make(submitBtn, "Wrong Answers, Try Again!", Snackbar.LENGTH_LONG) .show();

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

    public void solveQuest(View view) {
        PuzzleCardAdapter puzzlesAdapter = (PuzzleCardAdapter) puzzlesView.getAdapter();
        QuestionCardAdapter questionAdapter = (QuestionCardAdapter) questionsView.getAdapter();

        ArrayList<Puzzle> puzzles = puzzlesAdapter.generatePuzzleAnswers();
        ArrayList<Question> questions = questionAdapter.generateQuestionAnswers();

        quest.setPuzzles(puzzles);
        quest.setQuestions(questions);
        viewModel.solve(quest);

    }
}