package com.example.monsterfestival;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.monsterfestival.databinding.ActivityMainBinding;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements OnFragmentVisibleListener {

    private final ArrayList<FragmentManager> FragmentManagerList = new ArrayList<>();
    private final ArrayList<Fragment> FragmentRefList = new ArrayList<>();
    private final ArrayList<String> FragmentStringList = new ArrayList<>();
    private Fragment currentFragment;

    private AppBarLayout toolbar;
    private TextView textToolbar;
    private final HashSet<String> NoToolbarList = new HashSet<>();
    ActivityMainBinding binding;

    private boolean CustomonBackPressed = false;

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

        toolbar = findViewById(R.id.appBarLayout);
        toolbar.setVisibility(View.GONE);
        textToolbar = findViewById(R.id.toolbarTitle);
        ImageButton buttonToolbar = findViewById(R.id.toolbarButton);
        buttonToolbar.setOnClickListener(v -> {
            tornaIndetro();
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
    public void onFragmentVisible(FragmentManager fragmentManager, Fragment fragmentInstance, String fragmentTag) {
        if (!NoToolbarList.contains(fragmentTag)) {
            CustomonBackPressed = true;
            FragmentManagerList.add(fragmentManager);
            FragmentRefList.add(fragmentInstance);
            FragmentStringList.add(fragmentTag);
            toolbar.setVisibility(View.VISIBLE);
            textToolbar.setText(fragmentTag);
            return;
        }
        CustomonBackPressed = false;
        toolbar.setVisibility(View.GONE);
    }

    public void tornaIndetro() {
        FragmentTransaction fragmentTransaction = FragmentManagerList.get(FragmentManagerList.size() - 1).beginTransaction();
        fragmentTransaction.remove(FragmentRefList.get(FragmentRefList.size() - 1));
        fragmentTransaction.commit();

        if (FragmentRefList.size() < 2)
        {
            toolbar.setVisibility(View.GONE);
            replaceFragment(new HomeFragment());
            CustomonBackPressed = false;
        }
        else
        {
            toolbar.setVisibility(View.VISIBLE);
            textToolbar.setText(FragmentStringList.get(FragmentStringList.size() - 2));
            ((OnFragmentRemoveListener) FragmentRefList.get(FragmentRefList.size() - 2)).ripristinaVisibilitaElementi();
            CustomonBackPressed = true;
        }

        FragmentManagerList.remove(FragmentManagerList.size() - 1);
        FragmentRefList.remove(FragmentRefList.size() - 1);
        FragmentStringList.remove(FragmentStringList.size() - 1);
    }

    @Override
    public void onBackPressed() {
        if (CustomonBackPressed)
            tornaIndetro();
        else
            super.onBackPressed();
    }
}