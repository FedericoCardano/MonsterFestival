package com.example.monsterfestival.fragment_dir;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

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

        ButtonFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), getResources().getString(R.string.funzione_in_arrivo), Toast.LENGTH_SHORT).show();
            }
        });

        ButtonCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_conferma_cancellazione);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                dialog.show();

                dialog.findViewById(R.id.btnNo).setOnClickListener(view1 -> dialog.dismiss());
            }
        });

        return view;
    }
}