package com.example.monsterfestival.fragment_dir;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.monsterfestival.R;


public class SettingsFragment extends Fragment {

    private Button ButtonNotifiche, ButtonFeedback, ButtonCredit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ButtonNotifiche = view.findViewById(R.id.ButtonNotifiche);
        ButtonFeedback = view.findViewById(R.id.ButtonFeedback);
        ButtonCredit = view.findViewById(R.id.ButtonCredit);

        ButtonNotifiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}