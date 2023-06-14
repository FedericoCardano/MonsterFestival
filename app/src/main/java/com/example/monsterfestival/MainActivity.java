package com.example.monsterfestival;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.monsterfestival.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnFragmentVisibleListener {

    private final ArrayList<Integer> FragmentRefList = new ArrayList<>();
    private final ArrayList<String> FragmentStringList = new ArrayList<>();
    private Fragment currentFragment;

    private Toolbar toolbar;
    private TextView textToolbar;
    private final HashSet<String> NoToolbarList = new HashSet<>();
    ActivityMainBinding binding;

    public MainActivity() {
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NoToolbarList.add("Home");
        NoToolbarList.add("Account");
        NoToolbarList.add("Settings");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.INVISIBLE);
        textToolbar = findViewById(R.id.toolbarTitle);
        ImageButton buttonToolbar = findViewById(R.id.toolbarButton);
        buttonToolbar.setOnClickListener(v -> {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Log.d("Test2", String.valueOf(FragmentRefList.get(FragmentRefList.size() - 1)));
            fragmentTransaction.remove(Objects.requireNonNull(fragmentManager.findFragmentById(FragmentRefList.get(FragmentRefList.size() - 1))));
            fragmentTransaction.commit();

            if (FragmentRefList.size() < 2)
            {
                toolbar.setVisibility(View.INVISIBLE);
            }
            else
            {
                toolbar.setVisibility(View.VISIBLE);
                textToolbar.setText(FragmentStringList.get(FragmentStringList.size() - 2));
            }

            FragmentRefList.remove(FragmentRefList.size() - 1);
            FragmentStringList.remove(FragmentStringList.size() - 1);

        });

        currentFragment = null;
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.account:
                    replaceFragment(new AccountFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }
            return true;
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null)
            fragmentTransaction.remove(currentFragment);
        currentFragment = fragment;
        fragmentTransaction.replace(R.id.frame_access_main, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentVisible(Integer fragmentID, String fragmentTag) {
        if (!NoToolbarList.contains(fragmentTag)) {
            FragmentRefList.add(fragmentID);
            FragmentStringList.add(fragmentTag);
            toolbar.setVisibility(View.VISIBLE);
            textToolbar.setText(fragmentTag);
            return;
        }
        toolbar.setVisibility(View.INVISIBLE);
    }
}