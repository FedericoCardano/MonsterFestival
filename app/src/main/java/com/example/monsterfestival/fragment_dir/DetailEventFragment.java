package com.example.monsterfestival.fragment_dir;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;

import java.util.ArrayList;


public class DetailEventFragment extends Fragment implements OnFragmentRemoveListener {

    TextView tvNomeEvento,tvCausaEvento,tvReazioneEvento;
    OnFragmentVisibleListener fragmentVisibleListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_detail_event, container, false);
        tvNomeEvento = view.findViewById(R.id.tvNomeEvento);
        tvCausaEvento = view.findViewById(R.id.tvCausaEvento);
        tvReazioneEvento = view.findViewById(R.id.tvReazioneEvento);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ArrayList<String> evento = bundle.getStringArrayList("Evento");
            tvNomeEvento.setText(evento.get(0));
            tvCausaEvento.setText(evento.get(1));
            tvReazioneEvento.setText(evento.get(2));
        }

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentVisibleListener) {
            fragmentVisibleListener = (OnFragmentVisibleListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fragmentVisibleListener != null) {
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.event_detail));
        }
    }


    @Override
    public void ripristinaVisibilitaElementi() {

    }
}