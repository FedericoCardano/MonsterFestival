package com.example.monsterfestival.fragment_dir;

import android.annotation.SuppressLint;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsterfestival.R;


public class SettingsFragment extends Fragment {

    private SeekBar SeekBarAggiornamenti;
    private Button ButtonAggiornamenti, ButtonFeedback, ButtonCredit;

    private TextView TextAggiornamenti;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SeekBarAggiornamenti = view.findViewById(R.id.seekBarAggiornamenti);
        TextAggiornamenti = view.findViewById(R.id.perdiodoAggiornamenti);

        ButtonAggiornamenti = view.findViewById(R.id.ButtonAggiornamenti);
        ButtonFeedback = view.findViewById(R.id.ButtonFeedback);
        ButtonCredit = view.findViewById(R.id.ButtonCredit);

        SeekBarAggiornamenti.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                if (progress == 0)
                    TextAggiornamenti.setText("Mai");
                else if (progress == 1)
                    TextAggiornamenti.setText("1 Giorno");
                else
                    TextAggiornamenti.setText(progress + " Giorni");



            }
        });

        ButtonAggiornamenti.setOnClickListener(view14 -> {

        });

        ButtonFeedback.setOnClickListener(view13 -> Toast.makeText(getActivity(), getResources().getString(R.string.funzione_in_arrivo), Toast.LENGTH_SHORT).show());

        ButtonCredit.setOnClickListener(view12 -> {
            Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.popup_conferma_cancellazione);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.show();

            dialog.findViewById(R.id.btnNo).setOnClickListener(view1 -> dialog.dismiss());
        });

        return view;
    }
}