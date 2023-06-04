package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchMonstersFragment extends Fragment {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<DataClass> dataList;
    @SuppressLint("StaticFieldLeak")
    static MyAdapter adapter;
    @SuppressLint("StaticFieldLeak")
    public static SearchView searchView;
    public static CardView filtersCard;
    public static HashSet<String> selectedAmbieteFilters = new HashSet<>();
    public static HashSet<String> selectedCategoriaFilters = new HashSet<>();
    public static HashSet<String> selectedTagliaFilters = new HashSet<>();
    public static boolean isAmbienteSelected = false;
    public static boolean isCategoriaSelected = false;
    public static boolean isTagliaSelected = false;
    public static boolean isFiltersApplied = false;

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

        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new MyAdapter(getActivity(), dataList, this);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Monster");
        dialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.child("ID").getChildren()) {
                    String ambiete = i.child("Ambiente").getValue(String.class);
                    String ca = String.valueOf(i.child("CA").getValue(long.class));
                    String categoria = i.child("Categoria").getValue(String.class);
                    String nome = i.child("Nome").getValue(String.class);
                    String pf = String.valueOf(i.child("PF").getValue(long.class));
                    String sfida = String.valueOf(i.child("Sfida").getValue(long.class));
                    String taglia = i.child("Taglia").getValue(String.class);
                    String descrizione = i.child("Descrizione").getValue(String.class);
                    String car = i.child("CAR").getValue(String.class);
                    String cost = i.child("COST").getValue(String.class);
                    String des = i.child("DES").getValue(String.class);
                    String _for = i.child("FOR").getValue(String.class);
                    String _int = i.child("INT").getValue(String.class);
                    String sag = i.child("SAG").getValue(String.class);
                    DataClass dataClass = new DataClass(ambiete, ca, categoria, nome, pf, sfida, taglia, descrizione, car, cost, des, _for, _int, sag);
                    dataClass.setKey(i.getKey());
                    dataList.add(dataClass);
                }
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        return view;
    }

    public void searchList(String text) {
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass : dataList) {
            if (dataClass.getNome().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
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

        if (isFiltersApplied) {
            applyFilters();
        } else {
            selectedAmbieteFilters.clear();
            selectedCategoriaFilters.clear();
            selectedTagliaFilters.clear();
            isAmbienteSelected = false;
            isCategoriaSelected = false;
            isTagliaSelected = false;
        }
    }

    private void applyFilters() {
        ArrayList<DataClass> tempList = new ArrayList<>();

        for (DataClass dataClass : dataList) {

            if (isAmbienteSelected && !selectedAmbieteFilters.contains(dataClass.getAmbiente()))
                continue;
            if (isCategoriaSelected && !selectedCategoriaFilters.contains(dataClass.getCategoria()))
                continue;
            if (isTagliaSelected && !selectedTagliaFilters.contains(dataClass.getTaglia()))
                continue;

            tempList.add(dataClass);

        }

        adapter.searchDataList(tempList);
    }
}
