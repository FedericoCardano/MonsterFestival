package com.example.monsterfestival;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class CompareMonstersFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    FloatingActionButton btn1, btn2, clear;

    TextView detailID1, detailName1, detailCAR1, detailCOST1, detailDES1, detailFOR1, detailINT1, detailSAG1;
    TextView detailID2, detailName2, detailCAR2, detailCOST2, detailDES2, detailFOR2, detailINT2, detailSAG2;
    LinearLayout monster1, monster2, btnClear;

    View rootView;
    View monsterView1, monsterView2;

    private int green, red, gray;

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
        clear = rootView.findViewById(R.id.clearBtn);
        monster1 = rootView.findViewById(R.id.monster1);
        monster2 = rootView.findViewById(R.id.monster2);
        btnClear = rootView.findViewById(R.id.btnClear);

        green = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.verde);
        red = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.rosso);
        gray = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.grigio);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btn1.getParent() != null) {
                    ((ViewGroup)btn1.getParent()).removeView(btn1); // <- fix
                }
                if(btn2.getParent() != null) {
                    ((ViewGroup)btn2.getParent()).removeView(btn2); // <- fix
                }
                monster1.removeView(monsterView1);
                monster1.addView(btn1);
                monster2.removeView(monsterView2);
                monster2.addView(btn2);
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
                clear.setVisibility(View.GONE);
                final Compare compare = Compare.getCompare();
                compare.setMonster1();
                compare.setMonster2();

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                monsterView1 = getLayoutInflater().inflate(R.layout.compare_monsters_cart,null,false);

                setAllVisibility(false);

                final Compare compare = Compare.getCompare();
                compare.setFlag(true);
                compare.setNumero(1);

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


                detailID1 = monsterView1.findViewById(R.id.detailID);
                detailName1 = monsterView1.findViewById(R.id.detailName);
                detailCAR1 = monsterView1.findViewById(R.id.detailCAR);
                detailCOST1 = monsterView1.findViewById(R.id.detailCOST);
                detailDES1 = monsterView1.findViewById(R.id.detailDES);
                detailFOR1 = monsterView1.findViewById(R.id.detailFOR);
                detailINT1 = monsterView1.findViewById(R.id.detailINT);
                detailSAG1 = monsterView1.findViewById(R.id.detailSAG);



            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                monsterView2 = getLayoutInflater().inflate(R.layout.compare_monsters_cart,null,false);

                setAllVisibility(false);

                final Compare compare = Compare.getCompare();
                compare.setFlag(true);
                compare.setNumero(2);

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

                detailID2 = monsterView2.findViewById(R.id.detailID);
                detailName2 = monsterView2.findViewById(R.id.detailName);
                detailCAR2 = monsterView2.findViewById(R.id.detailCAR);
                detailCOST2 = monsterView2.findViewById(R.id.detailCOST);
                detailDES2 = monsterView2.findViewById(R.id.detailDES);
                detailFOR2 = monsterView2.findViewById(R.id.detailFOR);
                detailINT2 = monsterView2.findViewById(R.id.detailINT);
                detailSAG2 = monsterView2.findViewById(R.id.detailSAG);




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
        rootView.findViewById(R.id.btnClear).setVisibility(value ? View.VISIBLE : View.GONE);




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

        final Compare compare = Compare.getCompare();
        DataClass dataclass2 = compare.getMonster2();
        DataClass dataclass1 = compare.getMonster1();


        if (dataclass2 != null) {
            if (dataclass1 != null) {
                if (Integer.valueOf(dataclass2.getCar()) > Integer.valueOf(dataclass1.getCar())) {
                    detailCAR2.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass2.getCar()) < Integer.valueOf(dataclass1.getCar())) {
                    detailCAR2.setBackgroundColor(red);
                }
                else {
                    detailCAR2.setBackgroundColor(gray);
                }

                if (Integer.valueOf(dataclass2.getCost()) > Integer.valueOf(dataclass1.getCost())) {
                    detailCOST2.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass2.getCost()) < Integer.valueOf(dataclass1.getCost())) {
                    detailCOST2.setBackgroundColor(red);
                }
                else {
                    detailCOST2.setBackgroundColor(gray);
                }

                if (Integer.valueOf(dataclass2.getDes()) > Integer.valueOf(dataclass1.getDes())) {
                    detailDES2.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass2.getDes()) < Integer.valueOf(dataclass1.getDes())) {
                    detailDES2.setBackgroundColor(red);
                }
                else {
                    detailDES2.setBackgroundColor(gray);
                }

                if (Integer.valueOf(dataclass2.getFor()) > Integer.valueOf(dataclass1.getFor())) {
                    detailFOR2.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass2.getFor()) < Integer.valueOf(dataclass1.getFor())) {
                    detailFOR2.setBackgroundColor(red);
                }
                else {
                    detailFOR2.setBackgroundColor(gray);
                }

                if (Integer.valueOf(dataclass2.getInt()) > Integer.valueOf(dataclass1.getInt())) {
                    detailINT2.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass2.getInt()) < Integer.valueOf(dataclass1.getInt())) {
                    detailINT2.setBackgroundColor(red);
                }
                else {
                    detailINT2.setBackgroundColor(gray);
                }

                if (Integer.valueOf(dataclass2.getSag()) > Integer.valueOf(dataclass1.getSag())) {
                    detailSAG2.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass2.getSag()) < Integer.valueOf(dataclass1.getSag())) {
                    detailSAG2.setBackgroundColor(red);
                }
                else {
                    detailSAG2.setBackgroundColor(gray);
                }
                rootView.findViewById(R.id.clearBtn).setVisibility(View.VISIBLE);
            }
            detailID2.setText(dataclass2.getID());
            detailName2.setText(dataclass2.getNome());
            detailCAR2.setText(dataclass2.getCar());
            detailCOST2.setText(dataclass2.getCost());
            detailDES2.setText(dataclass2.getDes());
            detailFOR2.setText(dataclass2.getFor());
            detailINT2.setText(dataclass2.getInt());
            detailSAG2.setText(dataclass2.getSag());

            btn2.setVisibility(View.GONE);
            Log.d("ADebugTag", "Value: " + monsterView2.getParent());
            if(monsterView2.getParent() != null) {
                ((ViewGroup)monsterView2.getParent()).removeView(monsterView2); // <- fix
            }
            monster2.addView(monsterView2);
        }

        if (dataclass1 != null) {
            if (dataclass2 != null) {
                if (Integer.valueOf(dataclass1.getCar()) > Integer.valueOf(dataclass2.getCar())) {
                    detailCAR1.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass1.getCar()) < Integer.valueOf(dataclass2.getCar())) {
                    detailCAR1.setBackgroundColor(red);
                }
                else {
                    detailCAR1.setBackgroundColor(gray);
                }

                if (Integer.valueOf(dataclass1.getCost()) > Integer.valueOf(dataclass2.getCost())) {
                    detailCOST1.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass1.getCost()) < Integer.valueOf(dataclass2.getCost())) {
                    detailCOST1.setBackgroundColor(red);
                }
                else {
                    detailCOST1.setBackgroundColor(gray);
                }

                if (Integer.valueOf(dataclass1.getDes()) > Integer.valueOf(dataclass2.getDes())) {
                    detailDES1.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass1.getDes()) < Integer.valueOf(dataclass2.getDes())) {
                    detailDES1.setBackgroundColor(red);
                }
                else {
                    detailDES1.setBackgroundColor(gray);
                }

                if (Integer.valueOf(dataclass1.getFor()) > Integer.valueOf(dataclass2.getFor())) {
                    detailFOR1.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass1.getFor()) < Integer.valueOf(dataclass2.getFor())) {
                    detailFOR1.setBackgroundColor(red);
                }
                else {
                    detailFOR1.setBackgroundColor(gray);
                }

                if (Integer.valueOf(dataclass1.getInt()) > Integer.valueOf(dataclass2.getInt())) {
                    detailINT1.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass1.getInt()) < Integer.valueOf(dataclass2.getInt())) {
                    detailINT1.setBackgroundColor(red);
                }
                else {
                    detailINT1.setBackgroundColor(gray);
                }

                if (Integer.valueOf(dataclass1.getSag()) > Integer.valueOf(dataclass2.getSag())) {
                    detailSAG1.setBackgroundColor(green);
                }
                else if (Integer.valueOf(dataclass1.getSag()) < Integer.valueOf(dataclass2.getSag())) {
                    detailSAG1.setBackgroundColor(red);
                }
                else {
                    detailSAG1.setBackgroundColor(gray);
                }
                rootView.findViewById(R.id.clearBtn).setVisibility(View.VISIBLE);
            }
            detailID1.setText(dataclass1.getID());
            detailName1.setText(dataclass1.getNome());
            detailCAR1.setText(dataclass1.getCar());
            detailCOST1.setText(dataclass1.getCost());
            detailDES1.setText(dataclass1.getDes());
            detailFOR1.setText(dataclass1.getFor());
            detailINT1.setText(dataclass1.getInt());
            detailSAG1.setText(dataclass1.getSag());

            btn1.setVisibility(View.GONE);
            if(monsterView1.getParent() != null) {
                ((ViewGroup)monsterView1.getParent()).removeView(monsterView1); // <- fix
            }

            monster1.addView(monsterView1);
        }

    }
}