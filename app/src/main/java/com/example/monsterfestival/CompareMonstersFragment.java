package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CompareMonstersFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    FloatingActionButton btn1, btn2;

    TextView detailID, detailName, detailAmbiente, detailCA, detailCAR, detailCOST, detailCategoria, detailDES, detailFOR, detailINT, detailPF, detailSAG, detailSfida, detailTaglia;

    LinearLayout monster1, monster2;

    View rootView;
    View monsterView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_compare_monsters, container, false);

        btn1 = rootView.findViewById(R.id.add_btn1);
        btn2 = rootView.findViewById(R.id.add_btn2);
        monster1 = rootView.findViewById(R.id.monster1);
        monster2 = rootView.findViewById(R.id.monster2);

        FrameLayout containerFrame = rootView.findViewById(R.id.frame_access_compare_monsters);

        btn1.setOnClickListener(view -> {

            final View monsterView = getLayoutInflater().inflate(R.layout.compare_monsters_cart,null,false);

            setAllVisibility(false);

            // Inizializza il Fragment
            SearchMonstersFragment myFragment = new SearchMonstersFragment();

            // Ottieni il FragmentManager e inizia la transazione
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Aggiunti il Fragment al Container View
            fragmentTransaction.add(containerFrame.getId(), myFragment);

            // Esegui la transazione
            fragmentTransaction.commit();

            detailID = monsterView.findViewById(R.id.detailID);
            detailName = monsterView.findViewById(R.id.detailName);
            detailAmbiente = monsterView.findViewById(R.id.detailAmbiente);
            detailCA = monsterView.findViewById(R.id.detailCA);
            detailCAR = monsterView.findViewById(R.id.detailCAR);
            detailCOST = monsterView.findViewById(R.id.detailCOST);
            detailCategoria = monsterView.findViewById(R.id.detailCategoria);
            detailDES = monsterView.findViewById(R.id.detailDES);
            detailFOR = monsterView.findViewById(R.id.detailFOR);
            detailINT = monsterView.findViewById(R.id.detailINT);
            detailPF = monsterView.findViewById(R.id.detailPF);
            detailSAG = monsterView.findViewById(R.id.detailSAG);
            detailSfida = monsterView.findViewById(R.id.detailSfida);
            detailTaglia = monsterView.findViewById(R.id.detailTaglia);

            Compare compare = new Compare();
            DataClass dataclass = compare.getMonster1();

            if (dataclass != null) {
                detailID.setText(dataclass.getID());
                detailName.setText(dataclass.getNome());
                detailAmbiente.setText(dataclass.getAmbiente());
                detailCA.setText(dataclass.getCa());
                detailCAR.setText(dataclass.getCar());
                detailCOST.setText(dataclass.getCost());
                detailCategoria.setText(dataclass.getCategoria());
                detailDES.setText(dataclass.getDes());
                detailFOR.setText(dataclass.getFor());
                detailINT.setText(dataclass.getInt());
                detailPF.setText(dataclass.getPf());
                detailSAG.setText(dataclass.getSag());
                detailSfida.setText(dataclass.getSfida());
                detailTaglia.setText(dataclass.getTaglia());

                btn1.setVisibility(View.GONE);
                //monster1.addView(monsterView);
            }

        });

        btn2.setOnClickListener(view -> {

            monsterView = getLayoutInflater().inflate(R.layout.compare_monsters_cart, null,false);

            setAllVisibility(false);

            // Inizializza il Fragment
            SearchMonstersFragment myFragment = new SearchMonstersFragment();

            // Ottieni il FragmentManager e inizia la transazione
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Aggiunti il Fragment al Container View
            fragmentTransaction.add(containerFrame.getId(), myFragment);

            // Esegui la transazione
            fragmentTransaction.commit();

            detailID = monsterView.findViewById(R.id.detailID);
            detailName = monsterView.findViewById(R.id.detailName);
            detailAmbiente = monsterView.findViewById(R.id.detailAmbiente);
            detailCA = monsterView.findViewById(R.id.detailCA);
            detailCAR = monsterView.findViewById(R.id.detailCAR);
            detailCOST = monsterView.findViewById(R.id.detailCOST);
            detailCategoria = monsterView.findViewById(R.id.detailCategoria);
            detailDES = monsterView.findViewById(R.id.detailDES);
            detailFOR = monsterView.findViewById(R.id.detailFOR);
            detailINT = monsterView.findViewById(R.id.detailINT);
            detailPF = monsterView.findViewById(R.id.detailPF);
            detailSAG = monsterView.findViewById(R.id.detailSAG);
            detailSfida = monsterView.findViewById(R.id.detailSfida);
            detailTaglia = monsterView.findViewById(R.id.detailTaglia);

            Compare compare = new Compare();
            DataClass dataclass = compare.getMonster2();

            if (dataclass != null) {
                detailID.setText(dataclass.getID());
                detailName.setText(dataclass.getNome());
                detailAmbiente.setText(dataclass.getAmbiente());
                detailCA.setText(dataclass.getCa());
                detailCAR.setText(dataclass.getCar());
                detailCOST.setText(dataclass.getCost());
                detailCategoria.setText(dataclass.getCategoria());
                detailDES.setText(dataclass.getDes());
                detailFOR.setText(dataclass.getFor());
                detailINT.setText(dataclass.getInt());
                detailPF.setText(dataclass.getPf());
                detailSAG.setText(dataclass.getSag());
                detailSfida.setText(dataclass.getSfida());
                detailTaglia.setText(dataclass.getTaglia());

                btn2.setVisibility(View.GONE);
                monster2.addView(monsterView);
            }

        });

        return rootView;
    }

    void setAllVisibility(boolean value) {
        rootView.findViewById(R.id.monster1).setVisibility(value ? View.VISIBLE : View.GONE);
        rootView.findViewById(R.id.monster2).setVisibility(value ? View.VISIBLE : View.GONE);
        rootView.findViewById(R.id.view).setVisibility(value ? View.VISIBLE : View.GONE);
        rootView.findViewById(R.id.add_btn1).setVisibility(value ? View.VISIBLE : View.GONE);
        rootView.findViewById(R.id.add_btn2).setVisibility(value ? View.VISIBLE : View.GONE);



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
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.nome_compare_monsters));
        }
    }

    @Override
    public void ripristinaVisibilitaElementi() {

        setAllVisibility(true);

        Compare compare = new Compare();
        DataClass dataclass2 = compare.getMonster2();
        DataClass dataclass1 = compare.getMonster1();

        if (dataclass1 != null) {
            detailID.setText(dataclass1.getID());
            detailName.setText(dataclass1.getNome());
            detailAmbiente.setText(dataclass1.getAmbiente());
            detailCA.setText(dataclass1.getCa());
            detailCAR.setText(dataclass1.getCar());
            detailCOST.setText(dataclass1.getCost());
            detailCategoria.setText(dataclass1.getCategoria());
            detailDES.setText(dataclass1.getDes());
            detailFOR.setText(dataclass1.getFor());
            detailINT.setText(dataclass1.getInt());
            detailPF.setText(dataclass1.getPf());
            detailSAG.setText(dataclass1.getSag());
            detailSfida.setText(dataclass1.getSfida());
            detailTaglia.setText(dataclass1.getTaglia());

            btn1.setVisibility(View.GONE);
            monster1.addView(monsterView);
        }

        if (dataclass2 != null) {
            detailID.setText(dataclass2.getID());
            detailName.setText(dataclass2.getNome());
            detailAmbiente.setText(dataclass2.getAmbiente());
            detailCA.setText(dataclass2.getCa());
            detailCAR.setText(dataclass2.getCar());
            detailCOST.setText(dataclass2.getCost());
            detailCategoria.setText(dataclass2.getCategoria());
            detailDES.setText(dataclass2.getDes());
            detailFOR.setText(dataclass2.getFor());
            detailINT.setText(dataclass2.getInt());
            detailPF.setText(dataclass2.getPf());
            detailSAG.setText(dataclass2.getSag());
            detailSfida.setText(dataclass2.getSfida());
            detailTaglia.setText(dataclass2.getTaglia());

            btn2.setVisibility(View.GONE);
            monster2.addView(monsterView);
        }

    }
}