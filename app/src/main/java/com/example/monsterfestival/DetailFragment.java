package com.example.monsterfestival;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DetailFragment extends Fragment {

    TextView detailDesc, detailName, detailAmbiente, detailCA, detailCAR, detailCOST, detailCategoria, detailDES, detailFOR, detailINT, detailPF, detailSAG, detailSfida, detailTaglia;
    FloatingActionButton addButton, closeButton;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

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

        closeButton = view.findViewById(R.id.close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.remove(DetailFragment.this);
                fragmentTransaction.commit();

                SearchMonstersFragment.searchView.setVisibility(View.VISIBLE);
                SearchMonstersFragment.filtersCard.setVisibility(View.VISIBLE);
            }
        });

        Fragment parentFragment = getParentFragment();
        if (parentFragment != null) {
            // Il Fragment ha un genitore

            Fragment grandparentFragment = parentFragment.getParentFragment();
            if (grandparentFragment != null) {
                // Il genitore del Fragment ha un genitore

                Fragment greatgrandparentFragment = grandparentFragment.getParentFragment();
                if (greatgrandparentFragment != null) {
                    // Il genitore del genitore del Fragment ha un genitore

                    if (greatgrandparentFragment instanceof HomeFragment) {
                        addButton.setVisibility(View.INVISIBLE);
                    }
                    else {
                        addButton.setVisibility(View.VISIBLE);
                    }

                }

            }

        }

        return view;
    }
}