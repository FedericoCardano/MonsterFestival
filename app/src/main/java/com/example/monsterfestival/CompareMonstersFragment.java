package com.example.monsterfestival;

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

import java.util.Objects;

public class CompareMonstersFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    FloatingActionButton btn1, btn2;

    TextView detailID, detailName, detailAmbiente, detailCA, detailCAR, detailCOST, detailCategoria, detailDES, detailFOR, detailINT, detailPF, detailSAG, detailSfida, detailTaglia;

    LinearLayout monster1, monster2;

    View rootView;

    /*public static CompareMonstersFragment newInstance(String param1, String param2) {
        CompareMonstersFragment fragment = new CompareMonstersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {

        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_compare_monsters, container, false);

        btn1 = rootView.findViewById(R.id.add_btn1);
        btn2 = rootView.findViewById(R.id.add_btn2);
        monster1 = rootView.findViewById(R.id.monster1);
        monster2 = rootView.findViewById(R.id.monster2);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final View monsterView = getLayoutInflater().inflate(R.layout.compare_monsters_cart,null,false);

                setAllVisibility(false);

                FrameLayout container = rootView.findViewById(R.id.frame_access_compare_monsters);

                // Inizializza il Fragment
                SearchMonstersFragment myFragment = new SearchMonstersFragment();

                // Ottieni il FragmentManager e inizia la transazione
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Aggiunti il Fragment al Container View
                fragmentTransaction.add(container.getId(), myFragment);

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

                    btn1.setVisibility(View.GONE);
                    //monster1.addView(monsterView);
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final View monsterView = getLayoutInflater().inflate(R.layout.compare_monsters_cart,null,false);

                setAllVisibility(false);

                FrameLayout container = rootView.findViewById(R.id.frame_access_compare_monsters);

                // Inizializza il Fragment
                SearchMonstersFragment myFragment = new SearchMonstersFragment();

                // Ottieni il FragmentManager e inizia la transazione
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Aggiunti il Fragment al Container View
                fragmentTransaction.add(container.getId(), myFragment);

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

    }
}