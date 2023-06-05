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
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        if (isFiltersApplied)
            applyFilters(text);
        else {
            ArrayList<DataClass> searchList = new ArrayList<>();
            for (DataClass dataClass : dataList) {
                if (dataClass.getNome().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(dataClass);
                }
            }
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

        if (isFiltersApplied) {
            applyFilters("");
        } else {
            selectedAmbieteFilters.clear();
            selectedCategoriaFilters.clear();
            selectedTagliaFilters.clear();
            isAmbienteSelected = false;
            isCategoriaSelected = false;
            isTagliaSelected = false;
        }
    }

    private void applyFilters(String text) {
        ArrayList<DataClass> tempList = new ArrayList<>();
        NativeLib objectNativeLib = new NativeLib();
        List<HashSet<Integer>> filterTableList = new ArrayList<>();

        dialog.show();

        List<CompletableFuture<Void>> queryFutures = new ArrayList<>();

        // Creazione dei futuri delle query
        if (isAmbienteSelected) {
            DatabaseReference refFiltro = databaseReference.child("Filtri").child("Ambiente");
            for (String filtro : selectedAmbieteFilters) {
                CompletableFuture<Void> future = new CompletableFuture<>();
                queryFutures.add(future);
                refFiltro.equalTo(filtro).addListenerForSingleValueEvent(createValueEventListener(future, filterTableList));
            }
        }
        if (isCategoriaSelected) {
            DatabaseReference refFiltro = databaseReference.child("Filtri").child("Categoria");
            for (String filtro : selectedCategoriaFilters) {
                CompletableFuture<Void> future = new CompletableFuture<>();
                queryFutures.add(future);
                refFiltro.equalTo(filtro).addListenerForSingleValueEvent(createValueEventListener(future, filterTableList));
            }
        }
        if (isTagliaSelected) {
            DatabaseReference refFiltro = databaseReference.child("Filtri").child("Taglia");
            for (String filtro : selectedTagliaFilters) {
                CompletableFuture<Void> future = new CompletableFuture<>();
                queryFutures.add(future);
                refFiltro.equalTo(filtro).addListenerForSingleValueEvent(createValueEventListener(future, filterTableList));
            }
        }

        // Attendi il completamento di tutte le query
        CompletableFuture<Void> allQueriesFuture = CompletableFuture.allOf(queryFutures.toArray(new CompletableFuture[0]));

        // Gestisci il completamento di tutte le query
        allQueriesFuture.thenAccept(ignored -> {
            if (filterTableList.size() != 0) {
                // Esegui una intersezione custom tra i vari HashSet
                HashSet<Integer> result = objectNativeLib.processTables(filterTableList);
                if (result.size() == 0) {
                    handleSearchResults(new ArrayList<>());
                    return;
                }

                List<CompletableFuture<Void>> monsterQueryFutures = new ArrayList<>();

                // Creazione dei futuri delle query per i mostri
                for (Integer ID : result) {
                    CompletableFuture<Void> future = new CompletableFuture<>();
                    monsterQueryFutures.add(future);
                    databaseReference.child("ID").equalTo(ID).addListenerForSingleValueEvent(createValueEventListener(future, tempList, text));
                }

                // Attendi il completamento di tutte le query per i mostri
                CompletableFuture<Void> allMonsterQueriesFuture = CompletableFuture.allOf(monsterQueryFutures.toArray(new CompletableFuture[0]));

                // Gestisci il completamento di tutte le query per i mostri
                allMonsterQueriesFuture.thenRun(() -> handleSearchResults(tempList));
            } else {
                CompletableFuture<Void> future = new CompletableFuture<>();
                databaseReference.child("ID").addListenerForSingleValueEvent(createValueEventListener(future, tempList, text));

                // Attendi il completamento della query
                future.thenRun(() -> handleSearchResults(tempList));
            }
        });

        allQueriesFuture.exceptionally(ex -> {
            Toast.makeText(getActivity(), getResources().getString(R.string.ricerca_fallita), Toast.LENGTH_SHORT).show();
            handleSearchResults(new ArrayList<>());
            return null;
        });

        // Attendi il completamento di tutte le query
        try {
            allQueriesFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private ValueEventListener createValueEventListener(CompletableFuture<Void> future, List<HashSet<Integer>> filterTableList) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashSet<Integer> set = new HashSet<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    set.add(snapshot.getValue(Integer.class));
                }
                if (set.size() > 0)
                    filterTableList.add(set);
                future.complete(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        };
    }

    private ValueEventListener createValueEventListener(CompletableFuture<Void> future, ArrayList<DataClass> tempList, String text) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (text.isEmpty() || Objects.requireNonNull(snapshot.child("Nome").getValue(String.class)).contains(text)) {
                        tempList.add(new DataClass(snapshot));
                    }
                }
                future.complete(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        };
    }

    private void handleSearchResults(ArrayList<DataClass> tempList) {
        dialog.dismiss();
        if (!tempList.isEmpty())
            Collections.sort(tempList);
        adapter.searchDataList(tempList);
    }

}
