package com.example.monsterfestival;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class DetailFragment extends Fragment implements OnFragmentRemoveListener {

    TextView detailDesc, detailName, detailAmbiente, detailCA, detailCAR, detailCOST, detailCategoria, detailDES, detailFOR, detailINT, detailPF, detailSAG, detailSfida, detailTaglia;
    FloatingActionButton addButton;

    Fragment parent;
    OnFragmentVisibleListener fragmentVisibleListener;

    void setParent(Fragment _parent) {
        parent = _parent;
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_monster, container, false);

        detailDesc = view.findViewById(R.id.detailDesc);
        detailName = view.findViewById(R.id.detailName);
        detailAmbiente = view.findViewById(R.id.detailAmbiente);
        detailCA = view.findViewById(R.id.detailCA);
        detailCAR = view.findViewById(R.id.detailCAR);
        detailCOST = view.findViewById(R.id.detailCOST);
        detailCategoria = view.findViewById(R.id.detailCategoria);
        detailDES = view.findViewById(R.id.detailDES);
        detailFOR = view.findViewById(R.id.detailFOR);
        detailINT = view.findViewById(R.id.detailINT);
        detailPF = view.findViewById(R.id.detailPF);
        detailSAG = view.findViewById(R.id.detailSAG);
        detailSfida = view.findViewById(R.id.detailSfida);
        detailTaglia = view.findViewById(R.id.detailTaglia);
        addButton = view.findViewById(R.id.add_btn);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            detailDesc.setText(bundle.getString("Descrizione"));
            detailName.setText(bundle.getString("Nome"));
            detailAmbiente.setText(bundle.getString("Ambiente"));
            detailCA.setText(bundle.getString("CA"));
            detailCAR.setText(bundle.getString("CAR"));
            detailCOST.setText(bundle.getString("COST"));
            detailCategoria.setText(bundle.getString("Categoria"));
            detailDES.setText(bundle.getString("DES"));
            detailFOR.setText(bundle.getString("FOR"));
            detailINT.setText(bundle.getString("INT"));
            detailPF.setText(bundle.getString("PF"));
            detailSAG.setText(bundle.getString("SAG"));
            detailSfida.setText(bundle.getString("Sfida"));
            detailTaglia.setText(bundle.getString("Taglia"));
        }

        addButton.setOnClickListener(view1 -> {
            Cart cart = CartHelper.getCart();
            ArrayList<String> dati = new ArrayList<>();
            dati.add(bundle.getString("Nome"));
            dati.add(bundle.getString("Descrizione"));
            dati.add(bundle.getString("Ambiente"));
            dati.add(bundle.getString("Categoria"));
            dati.add(bundle.getString("Taglia"));
            dati.add(bundle.getString("Sfida"));
            dati.add(bundle.getString("PF"));
            dati.add(bundle.getString("CA"));
            dati.add(bundle.getString("FOR"));
            dati.add(bundle.getString("DES"));
            dati.add(bundle.getString("COST"));
            dati.add(bundle.getString("INT"));
            dati.add(bundle.getString("SAG"));
            dati.add(bundle.getString("CAR"));
            DataClass dataClass = new DataClass(dati);
            cart.add(dataClass, 1, getContext());
        });

        if (parent != null) {
            Fragment grandparentFragment = parent.getParentFragment();
            if (grandparentFragment != null) {
                    if (grandparentFragment instanceof HomeFragment) {
                        addButton.setVisibility(View.INVISIBLE);
                    }
                    else {
                        addButton.setVisibility(View.VISIBLE);
                    }
            }
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
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.nome_detailt_monster));
        }
    }

    public void ripristinaVisibilitaElementi() {

    }
}