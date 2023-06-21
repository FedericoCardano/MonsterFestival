package com.example.monsterfestival.fragment_dir;

import android.annotation.SuppressLint;
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
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monsterfestival.classes_dir.Compare;
import com.example.monsterfestival.classes_dir.DataClass;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CompareMonstersFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    FloatingActionButton btn1, btn2, clear;

    TextView detailSfida1, detailPF1, detailCA1, detailID1, detailName1, detailCAR1, detailCOST1, detailDES1, detailFOR1, detailINT1, detailSAG1;
    TextView detailSfida2, detailPF2, detailCA2, detailID2, detailName2, detailCAR2, detailCOST2, detailDES2, detailFOR2, detailINT2, detailSAG2;
    ImageButton deleteBtn1, deleteBtn2;
    LinearLayout monster1, monster2, btnClear;

    View rootView;
    View monsterView1, monsterView2;

    private int green, red, gray, brawn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"CutPasteId", "InflateParams"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        brawn = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.brawnColor);



        clear.setOnClickListener(view -> {

            if(btn1.getParent() != null) {
                ((ViewGroup)btn1.getParent()).removeView(btn1);
            }
            if(btn2.getParent() != null) {
                ((ViewGroup)btn2.getParent()).removeView(btn2);
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

        });
        btn1.setOnClickListener(view -> {

            monsterView1 = getLayoutInflater().inflate(R.layout.recyclerview_compare_monster_cart,null,false);
           if(monsterView2 == null) {
               monsterView2 = getLayoutInflater().inflate(R.layout.recyclerview_compare_monster_cart, null, false);
               deleteBtn2 = monsterView2.findViewById(R.id.delete_btn);
           }
            setAllVisibility(false);

            final Compare compare = Compare.getCompare();
            compare.setFlag(true);
            compare.setNumero(1);

            FrameLayout container12 = rootView.findViewById(R.id.frame_access_compare_monsters);

            // Inizializza il Fragment
            SearchMonstersFragment myFragment = new SearchMonstersFragment();

            // Ottieni il FragmentManager e inizia la transazione
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Aggiunti il Fragment al Container View
            fragmentTransaction.add(container12.getId(), myFragment);

            // Esegui la transazione
            fragmentTransaction.commit();

            detailSfida1 = monsterView1.findViewById(R.id.detailSfida);
            detailPF1 = monsterView1.findViewById(R.id.detailPF);
            detailCA1 = monsterView1.findViewById(R.id.detailCA);
            detailID1 = monsterView1.findViewById(R.id.detailID);
            detailName1 = monsterView1.findViewById(R.id.detailName);
            detailCAR1 = monsterView1.findViewById(R.id.detailCAR);
            detailCOST1 = monsterView1.findViewById(R.id.detailCOST);
            detailDES1 = monsterView1.findViewById(R.id.detailDES);
            detailFOR1 = monsterView1.findViewById(R.id.detailFOR);
            detailINT1 = monsterView1.findViewById(R.id.detailINT);
            detailSAG1 = monsterView1.findViewById(R.id.detailSAG);
            deleteBtn1 = monsterView1.findViewById(R.id.delete_btn);

        });

        btn2.setOnClickListener(view -> {

            monsterView2 = getLayoutInflater().inflate(R.layout.recyclerview_compare_monster_cart,null,false);
            if(monsterView1 == null) {
                monsterView1 = getLayoutInflater().inflate(R.layout.recyclerview_compare_monster_cart, null, false);
                deleteBtn1 = monsterView1.findViewById(R.id.delete_btn);
            }
            setAllVisibility(false);

            final Compare compare = Compare.getCompare();
            compare.setFlag(true);
            compare.setNumero(2);

            FrameLayout container1 = rootView.findViewById(R.id.frame_access_compare_monsters);

            // Inizializza il Fragment
            SearchMonstersFragment myFragment = new SearchMonstersFragment();

            // Ottieni il FragmentManager e inizia la transazione
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Aggiunti il Fragment al Container View
            fragmentTransaction.add(container1.getId(), myFragment);

            // Esegui la transazione
            fragmentTransaction.commit();

            detailSfida2 = monsterView2.findViewById(R.id.detailSfida);
            detailPF2 = monsterView2.findViewById(R.id.detailPF);
            detailCA2 = monsterView2.findViewById(R.id.detailCA);
            detailID2 = monsterView2.findViewById(R.id.detailID);
            detailName2 = monsterView2.findViewById(R.id.detailName);
            detailCAR2 = monsterView2.findViewById(R.id.detailCAR);
            detailCOST2 = monsterView2.findViewById(R.id.detailCOST);
            detailDES2 = monsterView2.findViewById(R.id.detailDES);
            detailFOR2 = monsterView2.findViewById(R.id.detailFOR);
            detailINT2 = monsterView2.findViewById(R.id.detailINT);
            detailSAG2 = monsterView2.findViewById(R.id.detailSAG);
            deleteBtn2 = monsterView2.findViewById(R.id.delete_btn);

        });

        rootView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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


        deleteBtn1.setOnClickListener(view -> {
            if (btn1.getParent() != null) {
                ((ViewGroup) btn1.getParent()).removeView(btn1);
            }

            monster1.removeView(monsterView1);
            monster1.addView(btn1);
            btn1.setVisibility(View.VISIBLE);
            clear.setVisibility(View.GONE);
            compare.setMonster1();

            detailSfida2.setTextColor(brawn);
            detailPF2.setTextColor(brawn);
            detailCA2.setTextColor(brawn);
            detailID2.setTextColor(brawn);
            detailName2.setTextColor(brawn);
            detailCAR2.setTextColor(brawn);
            detailCOST2.setTextColor(brawn);
            detailDES2.setTextColor(brawn);
            detailFOR2.setTextColor(brawn);
            detailINT2.setTextColor(brawn);
            detailSAG2.setTextColor(brawn);

        });
        deleteBtn2.setOnClickListener(view -> {
            if (btn2.getParent() != null) {
                ((ViewGroup) btn2.getParent()).removeView(btn2);
            }

            monster2.removeView(monsterView2);
            monster2.addView(btn2);
            btn2.setVisibility(View.VISIBLE);
            clear.setVisibility(View.GONE);
            compare.setMonster2();

            detailSfida1.setTextColor(brawn);
            detailPF1.setTextColor(brawn);
            detailCA1.setTextColor(brawn);
            detailID1.setTextColor(brawn);
            detailName1.setTextColor(brawn);
            detailCAR1.setTextColor(brawn);
            detailCOST1.setTextColor(brawn);
            detailDES1.setTextColor(brawn);
            detailFOR1.setTextColor(brawn);
            detailINT1.setTextColor(brawn);
            detailSAG1.setTextColor(brawn);
        });

        DataClass dataclass2 = compare.getMonster2();
        DataClass dataclass1 = compare.getMonster1();

        if (dataclass1 != null && dataclass2 != null) {
            setColorConfront(detailSfida1, detailSfida2, Float.parseFloat(dataclass1.getSfida()) - Float.parseFloat(dataclass2.getSfida()));
            setColorConfront(detailPF1, detailPF2, Integer.parseInt(dataclass1.getPf()) - Integer.parseInt(dataclass2.getPf()));
            setColorConfront(detailCA1, detailCA2, Integer.parseInt(dataclass1.getCa()) - Integer.parseInt(dataclass2.getCa()));
            setColorConfront(detailCAR1, detailCAR2, Integer.parseInt(dataclass1.getCar()) - Integer.parseInt(dataclass2.getCar()));
            setColorConfront(detailCOST1, detailCOST2, Integer.parseInt(dataclass1.getCost()) - Integer.parseInt(dataclass2.getCost()));
            setColorConfront(detailDES1, detailDES2, Integer.parseInt(dataclass1.getDes()) - Integer.parseInt(dataclass2.getDes()));
            setColorConfront(detailFOR1, detailFOR2, Integer.parseInt(dataclass1.getFor()) - Integer.parseInt(dataclass2.getFor()));
            setColorConfront(detailINT1, detailINT2, Integer.parseInt(dataclass1.getInt()) - Integer.parseInt(dataclass2.getInt()));
            setColorConfront(detailSAG1, detailSAG2, Integer.parseInt(dataclass1.getSag()) - Integer.parseInt(dataclass2.getSag()));

            rootView.findViewById(R.id.clearBtn).setVisibility(View.VISIBLE);
        }


        if (dataclass1 != null) {
            detailSfida1.setText(dataclass1.getSfida());
            detailPF1.setText(dataclass1.getPf());
            detailCA1.setText(dataclass1.getCa());
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

        if (dataclass2 != null) {
            detailSfida2.setText(dataclass2.getSfida());
            detailPF2.setText(dataclass2.getPf());
            detailCA2.setText(dataclass2.getCa());
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

    }

    private void setColorConfront(TextView monster1, TextView monster2, float diff) {
        if (diff > 0) {
            monster1.setTextColor(green);
            monster2.setTextColor(red);
        }
        else if (diff < 0) {
            monster1.setTextColor(red);
            monster2.setTextColor(green);
        }
        else {
            monster1.setTextColor(gray);
            monster2.setTextColor(gray);
        }
    }
}