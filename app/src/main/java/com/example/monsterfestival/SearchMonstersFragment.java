package com.example.monsterfestival;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchMonstersFragment extends Fragment {

    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<DataClass> dataList;
    static MyAdapter adapter;
    SearchView searchView;
    FloatingActionButton fab;
    private ImageView filtersBtn;
    private CardView filtersCard;
    public static ArrayList<String> selectedAmbieteFilters = new ArrayList<>();
    public static ArrayList<String> selectedCategoriaFilters = new ArrayList<>();
    public static ArrayList<String> selectedTagliaFilters = new ArrayList<>();
    public static boolean isAmbienteSelected = false;
    public static boolean isCategoriaSelected = false;
    public static boolean isTagliaSelected = false;
    public static boolean isFiltersApplied = false;
    private ArrayList<DataClass> tempList;

    private SearchFiltersFragment searchFiltersFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_monsters, container, false);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UploadActivity.class);
                startActivity(intent);
            }
        });

        filtersBtn = view.findViewById(R.id.filters_btn);
        filtersCard = view.findViewById(R.id.filters_card);
        filtersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrameLayout container = view.findViewById(R.id.frame_access_search);

                container.bringToFront();
                filtersCard.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.INVISIBLE);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
        adapter = new MyAdapter(getActivity(), dataList);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Monster");
        dialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot it : itemSnapshot.getChildren()) {
                        for (DataSnapshot i : it.getChildren()) {
                            String ambiete = i.child("Ambiete").getValue(String.class);
                            String ca = String.valueOf(i.child("CA").getValue(long.class));
                            String categoria = i.child("Categoria").getValue(String.class);
                            String nome = i.child("Nome").getValue(String.class);
                            String pf = String.valueOf(i.child("PF").getValue(long.class));
                            String sfida = String.valueOf(i.child("Sfida").getValue(long.class));
                            String taglia = i.child("Taglia").getValue(String.class);
                            DataClass dataClass = new DataClass(ambiete, ca, categoria, nome, pf, sfida, taglia);
                            dataClass.setKey(i.getKey());
                            dataList.add(dataClass);
                        }
                    }
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
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void setFilters() {
        FrameLayout container = getActivity().findViewById(R.id.frame_access_search);

        container.bringToFront();
        filtersCard.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);

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

        if (isAmbienteSelected && isCategoriaSelected && isTagliaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterAmbiente : selectedAmbieteFilters) {
                    if (dataClass.getAmbiente().contains(filterAmbiente)) {
                        for (String filterCategoria : selectedCategoriaFilters) {
                            if (dataClass.getCategoria().contains(filterCategoria)) {
                                for (String filterTaglia : selectedTagliaFilters) {
                                    if (dataClass.getTaglia().contains(filterTaglia)) {
                                        tempList.add(dataClass);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (isAmbienteSelected && isCategoriaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterAmbiente : selectedAmbieteFilters) {
                    if (dataClass.getAmbiente().contains(filterAmbiente)) {
                        for (String filterCategoria : selectedCategoriaFilters) {
                            if (dataClass.getCategoria().contains(filterCategoria)) {
                                tempList.add(dataClass);
                            }
                        }
                    }
                }
            }
        } else if (isAmbienteSelected && isTagliaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterAmbiente : selectedAmbieteFilters) {
                    if (dataClass.getAmbiente().contains(filterAmbiente)) {
                        for (String filterTaglia : selectedTagliaFilters) {
                            if (dataClass.getTaglia().contains(filterTaglia)) {
                                tempList.add(dataClass);
                            }
                        }
                    }
                }
            }
        } else if (isCategoriaSelected && isTagliaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterCategoria : selectedCategoriaFilters) {
                    if (dataClass.getCategoria().contains(filterCategoria)) {
                        for (String filterTaglia : selectedTagliaFilters) {
                            if (dataClass.getTaglia().contains(filterTaglia)) {
                                tempList.add(dataClass);
                            }
                        }
                    }
                }
            }
        } else if (isAmbienteSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterAmbiente : selectedAmbieteFilters) {
                    if (dataClass.getAmbiente().contains(filterAmbiente)) {
                        tempList.add(dataClass);
                    }
                }
            }
        } else if (isCategoriaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterCategoria : selectedCategoriaFilters) {
                    if (dataClass.getCategoria().contains(filterCategoria)) {
                        tempList.add(dataClass);
                    }
                }
            }
        } else if (isTagliaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterTaglia : selectedTagliaFilters) {
                    if (dataClass.getTaglia().contains(filterTaglia)) {
                        tempList.add(dataClass);
                    }
                }
            }
        } else {
            tempList.addAll(dataList);
        }

        adapter.searchDataList(tempList);
        selectedAmbieteFilters.clear();
        selectedCategoriaFilters.clear();
        selectedTagliaFilters.clear();
        isAmbienteSelected = false;
        isCategoriaSelected = false;
        isTagliaSelected = false;
        isFiltersApplied = false;
    }
}
