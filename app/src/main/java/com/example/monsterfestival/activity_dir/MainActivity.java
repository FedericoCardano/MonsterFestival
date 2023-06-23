package com.example.monsterfestival.activity_dir;

//import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

//import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.fragment_dir.AccountFragment;
import com.example.monsterfestival.fragment_dir.HomeFragment;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.example.monsterfestival.fragment_dir.SettingsFragment;
import com.example.monsterfestival.databinding.ActivityMainBinding;
import com.google.android.material.appbar.AppBarLayout;
//import com.google.gson.Gson;

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
        buttonToolbar.setOnClickListener(v -> tornaIndietro());

        currentFragment = null;
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            while(CustomonBackPressed) {
                tornaIndietro();
            }
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
        {
            if (currentFragment instanceof HomeFragment)
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.fade_out);
            else if (currentFragment instanceof SettingsFragment)
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.fade_out);
            else if (fragment instanceof HomeFragment)
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.fade_out);
            else
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.fade_out);
            fragmentTransaction.remove(currentFragment);
        }
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

    public void tornaIndietro(int nVolte) {
        while(nVolte-- > 0)
            tornaIndietro();
    }
    public void tornaIndietro() {
        FragmentTransaction fragmentTransaction = FragmentManagerList.get(FragmentManagerList.size() - 1).beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
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
            tornaIndietro();
        else
            super.onBackPressed();
    }
}