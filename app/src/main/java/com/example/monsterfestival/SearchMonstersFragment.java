package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customsearchlibrary.NativeLib;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class SearchMonstersFragment extends Fragment {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<DataClass> dataList;
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
    public static boolean areFiltersNotChanged;

    private String _text;
    private SearchFiltersFragment searchFiltersFragment;

    public SearchMonstersFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_monsters, container, false);

        ImageView filtersBtn = view.findViewById(R.id.filters_btn);
        filtersCard = view.findViewById(R.id.filters_card);
        filtersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrameLayout container = requireActivity().findViewById(R.id.frame_access_search);

                container.bringToFront();
                filtersCard.setVisibility(View.INVISIBLE);
                searchView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);

                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                // Inizializza il Fragment
                searchFiltersFragment = new SearchFiltersFragment();

                // Ottieni il FragmentManager e inizia la transazione
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Aggiunti il Fragment al Container View
                fragmentTransaction.add(container.getId(), searchFiltersFragment);

                // Esegui la transazione
                fragmentTransaction.commit();
            }
        });

        searchView = view.findViewById(R.id.search);
        showSoftKeyboard(searchView);

        selectedAmbieteFilters = new HashSet<>();
        selectedCategoriaFilters = new HashSet<>();
        selectedTagliaFilters = new HashSet<>();
        isAmbienteSelected = false;
        isCategoriaSelected = false;
        isTagliaSelected = false;
        areFiltersApplied = false;
        areFiltersNotChanged = true;

        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new MyAdapter(getActivity(), dataList, this);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Monster");
        dialog.show();
        databaseReference.child("ID").addValueEventListener(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.getChildren())
                    dataList.add(new DataClass(i));
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("") && !_text.equals(""))
                    searchList(newText);
                return false;
            }
        });

        return view;
    }

    public void searchList(String text) {
        _text = text;

        if (areFiltersNotChanged)
            return;
        areFiltersNotChanged = true;

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
        FrameLayout container = requireActivity().findViewById(R.id.frame_access_search);

        container.bringToFront();
        searchView.setVisibility(View.VISIBLE);
        filtersCard.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Aggiunti il Fragment al Container View
        fragmentTransaction.remove(searchFiltersFragment);

        // Esegui la transazione
        fragmentTransaction.commit();

        if (!areFiltersNotChanged)
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
                listaFiltri.get(0).add(filtro);
        listaFiltri.add(new ArrayList<>());
        if (isTagliaSelected)
            for (String filtro : selectedTagliaFilters)
                listaFiltri.get(0).add(filtro);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        NativeLib objectNativeLib = new Gson().fromJson(sharedPreferences.getString("objectNativeLib", null), NativeLib.class);

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

}
