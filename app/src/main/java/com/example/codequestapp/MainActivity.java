package com.example.codequestapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.codequestapp.ui.achievements.AchievementsFragment;
import com.example.codequestapp.ui.profile.ProfileFragment;
import com.example.codequestapp.ui.quests.QuestsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;
    private QuestsFragment questsFragment = new QuestsFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private AchievementsFragment achievementsFragment = new AchievementsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_quests, R.id.navigation_achievements, R.id.navigation_profile)
                .build();


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                invalidateOptionsMenu();
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_quests:
                        setTitle("Quests");
                        replaceFragment(questsFragment);
                        break;
                    case R.id.navigation_profile:
                        setTitle("Profile");
                        replaceFragment(profileFragment);
                        break;
                    case R.id.navigation_achievements:
                        setTitle("Achievements");
                        replaceFragment(achievementsFragment);
                        break;
                }
                return true;
            }
        });
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        switch(navView.getSelectedItemId()) {
            case R.id.navigation_quests:
                inflater.inflate(R.menu.top_bar_quests, menu);
                break;
            case R.id.navigation_profile:
                inflater.inflate(R.menu.top_bar_profile, menu);
                break;
            case R.id.navigation_achievements:
                inflater.inflate(R.menu.top_bar_achievements, menu);
                break;
        }
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }

    public void toggleColorMode(MenuItem item) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
}