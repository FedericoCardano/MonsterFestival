package com.example.monsterfestival.fragment_dir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.classes_dir.ChildModelClass;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.adapter_dir.ParentAdapter;
import com.example.monsterfestival.classes_dir.ParentModelClass;
import com.example.monsterfestival.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SearchFiltersFragment extends Fragment implements View.OnClickListener, OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    View rootView;
    private TextView clearFilters;
    RecyclerView recyclerView;
    ArrayList<ParentModelClass> parentModelClassArrayList;
    ParentAdapter parentAdapter;

    private int filtriCounter = 0;

    protected final HashMap<View, String> filtri_ambiente_HM = new HashMap<>();
    protected final HashMap<View, String> filtri_categoria_HM = new HashMap<>();
    protected final HashMap<View, String> filtri_taglia_HM = new HashMap<>();
    private final ArrayList<ChildModelClass> filtri_ambiente = new ArrayList<>();
    private final ArrayList<ChildModelClass> filtri_categoria = new ArrayList<>();
    private final ArrayList<ChildModelClass> filtri_taglia = new ArrayList<>();


    private final Object ThreadLock = new Object();
    private int black;
    private int white;
    private int rossoPorpora;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search_filters, container, false);

        initColors();
        initAll();

        rootView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right));
        return rootView;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initAll() {
        clearFilters = rootView.findViewById(R.id.id_clear_btn);
        clearFilters.setOnClickListener(this);

        recyclerView = rootView.findViewById(R.id.rv_parent);
        parentModelClassArrayList = new ArrayList<>();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String temp = sharedPreferences.getString("objectNativeLib", "");
        NativeLib objectNativeLib;
        if (temp.equals(""))
            objectNativeLib = new NativeLib();
        else
            objectNativeLib = new NativeLib(new Gson().fromJson(temp, NativeLib.class));

        for (int i = 1; i < objectNativeLib.nomiFiltri.get(0).size(); i++) {
            filtri_ambiente.add(new ChildModelClass(objectNativeLib.nomiFiltri.get(0).get(i)));
        }
        for (int i = 1; i < objectNativeLib.nomiFiltri.get(2).size(); i++) {
            filtri_categoria.add(new ChildModelClass(objectNativeLib.nomiFiltri.get(2).get(i)));
        }
        for (int i = 1; i < objectNativeLib.nomiFiltri.get(5).size(); i++) {
            filtri_taglia.add(new ChildModelClass(objectNativeLib.nomiFiltri.get(5).get(i)));
        }

        parentModelClassArrayList.add(new ParentModelClass("Ambienti", filtri_ambiente));
        parentModelClassArrayList.add(new ParentModelClass("Categorie", filtri_categoria));
        parentModelClassArrayList.add(new ParentModelClass("Taglie", filtri_taglia));

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        parentAdapter = new ParentAdapter(parentModelClassArrayList, requireContext(), this);
        recyclerView.setAdapter(parentAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.requestLayout();
        parentAdapter.notifyDataSetChanged();

    }

    public void addFilters(ArrayList<View> childView) {

        synchronized (ThreadLock) {
            filtriCounter++;
            for (int i = 0; i < childView.size(); i++) {
                View child = childView.get(i);
                child.setOnClickListener(this);
                switch (filtriCounter) {
                    case 1:
                        filtri_ambiente_HM.put(child, filtri_ambiente.get(i).getNomeFiltro());
                        if (SearchMonstersFragment.isAmbienteSelected && SearchMonstersFragment.selectedAmbieteFilters.contains(filtri_ambiente_HM.get(child)))
                            lookSelected(child);
                        break;
                    case 2:
                        filtri_categoria_HM.put(child, filtri_categoria.get(i).getNomeFiltro());
                        if (SearchMonstersFragment.isCategoriaSelected && SearchMonstersFragment.selectedCategoriaFilters.contains(filtri_categoria_HM.get(child)))
                            lookSelected(child);
                        break;
                    case 3:
                        filtri_taglia_HM.put(child, filtri_taglia.get(i).getNomeFiltro());
                        if (SearchMonstersFragment.isTagliaSelected && SearchMonstersFragment.selectedTagliaFilters.contains(filtri_taglia_HM.get(child)))
                            lookSelected(child);
                        break;
                }
            }
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        if (view == clearFilters) {
            // Applica i Filtri

            SearchMonstersFragment.isAmbienteSelected = SearchMonstersFragment.selectedAmbieteFilters.size() != 0;
            SearchMonstersFragment.isCategoriaSelected = SearchMonstersFragment.selectedCategoriaFilters.size() != 0;
            SearchMonstersFragment.isTagliaSelected = SearchMonstersFragment.selectedTagliaFilters.size() != 0;
            SearchMonstersFragment.areFiltersApplied = SearchMonstersFragment.isAmbienteSelected ||
                                                      SearchMonstersFragment.isCategoriaSelected ||
                                                      SearchMonstersFragment.isTagliaSelected;

            SearchMonstersFragment fragment = (SearchMonstersFragment) getParentFragment();
            if (fragment != null)
                fragment.setFilters();

        } else if (filtri_ambiente_HM.containsKey(view)) {
            // Filtri Ambiente

            String testo = Objects.requireNonNull(filtri_ambiente_HM.get(view));
            if (SearchMonstersFragment.selectedAmbieteFilters.contains(testo)) {
                SearchMonstersFragment.selectedAmbieteFilters.remove(testo);
                lookDeselected(view);
            } else {
                SearchMonstersFragment.selectedAmbieteFilters.add(testo);
                lookSelected(view);
            }

        } else if (filtri_categoria_HM.containsKey(view)) {
            // Filtri Categoria

            String testo = Objects.requireNonNull(filtri_categoria_HM.get(view));
            if (SearchMonstersFragment.selectedCategoriaFilters.contains(testo)) {
                SearchMonstersFragment.selectedCategoriaFilters.remove(testo);
                lookDeselected(view);
            } else {
                SearchMonstersFragment.selectedCategoriaFilters.add(testo);
                lookSelected(view);
            }

        } else if (filtri_taglia_HM.containsKey(view)) {
            // Filtri Taglia

            String testo = Objects.requireNonNull(filtri_taglia_HM.get(view));
            if (SearchMonstersFragment.selectedTagliaFilters.contains(testo)) {
                SearchMonstersFragment.selectedTagliaFilters.remove(testo);
                lookDeselected(view);
            } else {
                SearchMonstersFragment.selectedTagliaFilters.add(testo);
                lookSelected(view);
            }

        }

    }

    private void initColors()
    {
        black = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.black);
        white = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.white);
        rossoPorpora = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.rossoPorpora);
    }

    private void lookSelected(View selectedFilter)
    {
        View view = selectedFilter.findViewById(R.id.tv_child_item);
        view.setBackgroundColor(rossoPorpora);
        ((TextView) view).setTextColor(white);
    }

    private void lookDeselected(View selectedFilter)
    {
        View view = selectedFilter.findViewById(R.id.tv_child_item);
        view.setBackgroundColor(white);
        ((TextView) view).setTextColor(black);
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
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.nome_search_filters));
        }
    }

    public void ripristinaVisibilitaElementi() {

    }
}