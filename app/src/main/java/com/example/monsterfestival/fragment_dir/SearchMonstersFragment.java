package com.example.monsterfestival.fragment_dir;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.classes_dir.DataClass;
import com.example.monsterfestival.adapter_dir.MyAdapter;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.example.monsterfestival.activity_dir.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SearchMonstersFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<DataClass> dataList;
    @SuppressLint("StaticFieldLeak")
    static MyAdapter adapter;
    @SuppressLint("StaticFieldLeak")
    public static SearchView searchView;
    public static CardView filtersCard;

    public static AlertDialog dialog;
    public static HashSet<String> selectedAmbieteFilters;
    public static HashSet<String> selectedCategoriaFilters;
    public static HashSet<String> selectedTagliaFilters;
    public static boolean isAmbienteSelected;
    public static boolean isCategoriaSelected;
    public static boolean isTagliaSelected;
    public static boolean areFiltersApplied;

    private String _text;
    private SearchFiltersFragment searchFiltersFragment;

    private NativeLib objectNativeLib;

    public final Lock ThreadLock = new ReentrantLock();

    public SearchMonstersFragment() {
    }

    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_monsters, container, false);

        ImageView filtersBtn = view.findViewById(R.id.filters_btn);
        filtersCard = view.findViewById(R.id.filters_card);
        filtersBtn.setOnClickListener(view1 -> {
            if (ThreadLock.tryLock()) {
                try {
                    FrameLayout container1 = requireActivity().findViewById(R.id.frame_access_search);

                    container1.bringToFront();
                    filtersCard.setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);

                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null)
                        imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);

                    // Inizializza il Fragment
                    searchFiltersFragment = new SearchFiltersFragment();

                    // Ottieni il FragmentManager e inizia la transazione
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Aggiunti il Fragment al Container View
                    fragmentTransaction.add(container1.getId(), searchFiltersFragment);

                    // Esegui la transazione
                    fragmentTransaction.commitNow();
                } finally {
                    ThreadLock.unlock();
                }
            }
        });

        selectedAmbieteFilters = new HashSet<>();
        selectedCategoriaFilters = new HashSet<>();
        selectedTagliaFilters = new HashSet<>();
        isAmbienteSelected = false;
        isCategoriaSelected = false;
        isTagliaSelected = false;
        areFiltersApplied = false;

        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.recyclerview_progress_layout);
        dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new MyAdapter(getActivity(), dataList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setMotionEventSplittingEnabled(false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Monster");

        searchView = view.findViewById(R.id.search);
        showSoftKeyboard(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty() && !_text.isEmpty())
                    searchList(newText);
                return true;
            }
        });

        dialog.show();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", null), NativeLib.class));

        for (ArrayList<String> element : objectNativeLib.getID())
            dataList.add(new DataClass(element));
        adapter.notifyDataSetChanged();

        dialog.dismiss();

        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        return view;
    }

    public void searchList(String text) {
        _text = text;

        if (areFiltersApplied)
            applyFilters(text);
        else {
            dialog.show();
            ArrayList<DataClass> searchList = new ArrayList<>();
            for (DataClass dataClass : dataList) {
                if (stringSimilarity(text, dataClass.getNome())) {
                    searchList.add(dataClass);
                }
            }
            dialog.dismiss();
            adapter.searchDataList(searchList);
        }
    }

    public void showSoftKeyboard(SearchView searchView) {
        if (searchView.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void setFilters() {
        requireActivity().findViewById(R.id.frame_access_search).bringToFront();
        searchView.setVisibility(View.VISIBLE);
        filtersCard.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        ((MainActivity) requireActivity()).tornaIndietro();

        applyFilters(_text);
    }

    private void applyFilters(String text) {
        ArrayList<ArrayList<String>> listaFiltri = new ArrayList<>();

        dialog.show();

        listaFiltri.add(new ArrayList<>());
        if (isAmbienteSelected)
            for (String filtro : selectedAmbieteFilters)
                listaFiltri.get(0).add(filtro);
        listaFiltri.add(new ArrayList<>());
        if (isCategoriaSelected)
            for (String filtro : selectedCategoriaFilters)
                listaFiltri.get(1).add(filtro);
        listaFiltri.add(new ArrayList<>());
        if (isTagliaSelected)
            for (String filtro : selectedTagliaFilters)
                listaFiltri.get(2).add(filtro);

        ArrayList<ArrayList<String>> tempList = objectNativeLib.execSearch(text, listaFiltri);
        ArrayList<DataClass> dataList = new ArrayList<>();
        for (ArrayList<String> element : tempList)
            dataList.add(new DataClass(element));

        dialog.dismiss();

        if (!dataList.isEmpty())
            Collections.sort(dataList);
        adapter.searchDataList(dataList);

    }

    private boolean stringSimilarity(String text1, String text2) {
        text2 = text2.toLowerCase();
        for (String t : convertString2HashSet(text1))
            if (!text2.contains(t))
                return false;
        return true;
    }

    private HashSet<String> convertString2HashSet(String text) {
        return new HashSet<>(Arrays.asList(text.toLowerCase().split(" ")));
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
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.nome_search));
        }
    }

    public void ripristinaVisibilitaElementi() {
        filtersCard.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

}
