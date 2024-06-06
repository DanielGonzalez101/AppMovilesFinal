package com.example.marvelstudios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FrameLayout frm_home;
    BottomNavigationView btn_navigation;
    HomeFragment homeFragment = new HomeFragment();
    RequestFragment requestFragment = new RequestFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frm_home = findViewById(R.id.frm_home);
        btn_navigation = findViewById(R.id.btn_navigation);
        loadFragment(homeFragment);
        btn_navigation.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.nav_home) {
                    loadFragment(homeFragment);
                    return true;
                } else if (item.getItemId()==R.id.nav_request) {
                    loadFragment(requestFragment);
                    return true;
                } else if (item.getItemId()==R.id.nav_settings) {
                    loadFragment(settingsFragment);
                    return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fr) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frm_home,fr);
        transaction.commit();
    }
}