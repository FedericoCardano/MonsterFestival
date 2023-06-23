package com.example.monsterfestival.fragment_dir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.DatabaseUpdateWorker;

import java.util.concurrent.TimeUnit;


public class SettingsFragment extends Fragment {

    private SeekBar SeekBarAggiornamenti;
    private Button ButtonAggiornamenti, ButtonFeedback, ButtonCredit;

    private TextView TextAggiornamenti;

    private EditText NumAvventurieri, LvAvventurieri;

    private Constraints constraints;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

        SeekBarAggiornamenti = view.findViewById(R.id.seekBarAggiornamenti);
        TextAggiornamenti = view.findViewById(R.id.perdiodoAggiornamenti);

        ButtonAggiornamenti = view.findViewById(R.id.ButtonAggiornamenti);
        ButtonFeedback = view.findViewById(R.id.ButtonFeedback);
        ButtonCredit = view.findViewById(R.id.ButtonCredit);

        NumAvventurieri = view.findViewById(R.id.editTextNumAvventurieri);
        LvAvventurieri = view.findViewById(R.id.editTextLvAvventurieri);

        SeekBarAggiornamenti.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                int interval;
                if (seekBar.getProgress() == 0)
                    interval = -1;
                else
                    interval = seekBar.getProgress() * 24 * 60 * 60 * 1000;

                editor.putInt("intervalPeriodicWork", interval);
                editor.apply();

                if (interval == -1) {
                    WorkManager.getInstance(requireActivity()).cancelUniqueWork("Aggiornamenti_Database");
                }
                else {
                    PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                            DatabaseUpdateWorker.class,
                            /* intervallo di controllo in millisecondi */ interval,
                            TimeUnit.MILLISECONDS
                    ).setConstraints(constraints).build();
                    WorkManager.getInstance(requireActivity()).enqueueUniquePeriodicWork("Aggiornamenti_Database", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
                }
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
            Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DatabaseUpdateWorker.class).setConstraints(constraints).build();
            WorkManager.getInstance(requireActivity()).enqueue(workRequest);
            Toast.makeText(getActivity(), getResources().getString(R.string.controllo_aggiornamenti_effettuato), Toast.LENGTH_SHORT).show();
        });

        ButtonFeedback.setOnClickListener(view13 -> Toast.makeText(getActivity(), getResources().getString(R.string.funzione_in_arrivo), Toast.LENGTH_SHORT).show());

        ButtonCredit.setOnClickListener(view12 -> {
            Dialog dialog = new Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.popup_credit);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.show();

            dialog.findViewById(R.id.btnOk).setOnClickListener(view15 -> dialog.dismiss());
        });

        NumAvventurieri.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("NumAvventurieri", Integer.parseInt(String.valueOf(s)));
                editor.apply();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        LvAvventurieri.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("LvAvventurieri", Integer.parseInt(String.valueOf(s)));
                editor.apply();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        return view;
    }
}