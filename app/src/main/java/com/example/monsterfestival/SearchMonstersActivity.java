package com.example.monsterfestival;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaSessionManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SearchMonstersActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<DataClass> dataList;
   static MyAdapter adapter;
    SearchView searchView;
    FloatingActionButton fab;
    private ImageView filtersBtn;
    public static ArrayList<String> selectedAmbieteFilters = new ArrayList<>();
    public static ArrayList<String> selectedCategoriaFilters = new ArrayList<>();
    public static ArrayList<String> selectedTagliaFilters = new ArrayList<>();
    //public static ArrayList<String> selectedAlignmentFilters = new ArrayList<>();
    public static boolean isAmbieteSelected = false;
    public static boolean isCategoriaSelected = false;
    public static boolean isTagliaSelected = false;
    //public static boolean isAlignmentSelected = false;
    public static boolean isFiltersApplied = false;
    private ArrayList<DataClass> tempList;


    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (isFiltersApplied) {
                ApplyFilters();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_monsters);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchMonstersActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        filtersBtn = findViewById(R.id.filters_btn);
        filtersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchMonstersActivity.this, SearchFiltersActivity.class);
                //startActivity(intent);
                startForResult.launch(intent);
            }
        });

        searchView = findViewById(R.id.search);
        showSoftKeyboard(searchView);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchMonstersActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchMonstersActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new MyAdapter(SearchMonstersActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Monster");
        dialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    dataList.clear();
                    for (DataSnapshot it: itemSnapshot.getChildren()) {
                        //dataList.clear();
                        for (DataSnapshot i: it.getChildren()) {
                            String ambiete = i.child("Ambiete").getValue(String.class);
                            String ca = String.valueOf(i.child("CA").getValue(long.class));
                            String categoria = i.child("Categoria").getValue(String.class);
                            String nome = i.child("Nome").getValue(String.class);
                            String pf = String.valueOf(i.child("PF").getValue(long.class));
                            String sfida = String.valueOf(i.child("Sfida").getValue(long.class));
                            String taglia = i.child("Taglia").getValue(String.class);
                            DataClass dataClass = new DataClass(ambiete, ca, categoria, nome, pf, sfida, taglia);
                            //DataClass dataClass = i.getValue(DataClass.class);
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

    }
    public void searchList(String text){
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass: dataList){
            if (dataClass.getNome().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }

    public void showSoftKeyboard(SearchView searchView) {
        if (searchView.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void ApplyFilters() {
        ArrayList<DataClass> tempList = new ArrayList<>();
        Log.d("TEStArraySize", dataList.size() + "");
        if (tempList.size() > 0) {
            tempList.clear();
            Log.d("listClear", tempList.size() + "");
        }
        /*if (isRaceSelected && isM_ClassSelected && isBackgroundSelected && isAlignmentSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterRace: selectedRaceFilters) {
                    if(dataClass.getRace().contains(filterRace)) {
                        for (String filterM_Class: selectedM_ClassFilters) {
                            if(dataClass.getM_class().contains(filterM_Class)) {
                                for (String filterBackground: selectedBackgroundFilters) {
                                    if(dataClass.getBackground().contains(filterBackground)) {
                                        for (String filterAlignment: selectedAlignmentFilters) {
                                            if(dataClass.getAlignment().contains(filterAlignment)) {
                                                tempList.add(dataClass);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }*/ if (isAmbieteSelected && isCategoriaSelected && isTagliaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterRace: selectedAmbieteFilters) {
                    if(dataClass.getAmbiete().contains(filterRace)) {
                        for (String filterM_Class: selectedCategoriaFilters) {
                            if(dataClass.getCategoria().contains(filterM_Class)) {
                                for (String filterBackground: selectedTagliaFilters) {
                                    if(dataClass.getTaglia().contains(filterBackground)) {
                                        tempList.add(dataClass);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } /*else if (isRaceSelected && isM_ClassSelected && isAlignmentSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterRace: selectedRaceFilters) {
                    if(dataClass.getRace().contains(filterRace)) {
                        for (String filterM_Class: selectedM_ClassFilters) {
                            if(dataClass.getM_class().contains(filterM_Class)) {
                                for (String filterAlignment: selectedAlignmentFilters) {
                                    if(dataClass.getAlignment().contains(filterAlignment)) {
                                        tempList.add(dataClass);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (isRaceSelected && isBackgroundSelected && isAlignmentSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterRace: selectedRaceFilters) {
                    if(dataClass.getRace().contains(filterRace)) {
                        for (String filterBackground: selectedBackgroundFilters) {
                            if(dataClass.getBackground().contains(filterBackground)) {
                                for (String filterAlignment: selectedAlignmentFilters) {
                                    if(dataClass.getAlignment().contains(filterAlignment)) {
                                        tempList.add(dataClass);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (isM_ClassSelected && isBackgroundSelected && isAlignmentSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterM_Class: selectedM_ClassFilters) {
                    if(dataClass.getM_class().contains(filterM_Class)) {
                        for (String filterBackground: selectedBackgroundFilters) {
                            if(dataClass.getBackground().contains(filterBackground)) {
                                for (String filterAlignment: selectedAlignmentFilters) {
                                    if(dataClass.getAlignment().contains(filterAlignment)) {
                                        tempList.add(dataClass);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/ else if (isAmbieteSelected && isCategoriaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterRace: selectedAmbieteFilters) {
                    if(dataClass.getAmbiete().contains(filterRace)) {
                        for (String filterM_Class: selectedCategoriaFilters) {
                            if(dataClass.getCategoria().contains(filterM_Class)) {
                                        tempList.add(dataClass);
                            }
                        }
                    }
                }
            }
        } else if (isAmbieteSelected && isTagliaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterRace: selectedAmbieteFilters) {
                    if(dataClass.getAmbiete().contains(filterRace)) {
                        for (String filterBackground: selectedTagliaFilters) {
                            if(dataClass.getTaglia().contains(filterBackground)) {
                                tempList.add(dataClass);
                            }
                        }
                    }
                }
            }
        } /*else if (isRaceSelected && isAlignmentSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterRace: selectedRaceFilters) {
                    if(dataClass.getRace().contains(filterRace)) {
                        for (String filterAlignment: selectedAlignmentFilters) {
                            if(dataClass.getAlignment().contains(filterAlignment)) {
                                tempList.add(dataClass);
                            }
                        }
                    }
                }
            }
        }*/ else if (isCategoriaSelected && isTagliaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterM_Class: selectedCategoriaFilters) {
                    if(dataClass.getCategoria().contains(filterM_Class)) {
                        for (String filterBackground: selectedTagliaFilters) {
                            if(dataClass.getTaglia().contains(filterBackground)) {
                                tempList.add(dataClass);
                            }
                        }
                    }
                }
            }
        } /*else if (isM_ClassSelected && isAlignmentSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterM_Class: selectedM_ClassFilters) {
                    if(dataClass.getM_class().contains(filterM_Class)) {
                        for (String filterAlignment: selectedAlignmentFilters) {
                            if(dataClass.getAlignment().contains(filterAlignment)) {
                                tempList.add(dataClass);
                            }
                        }
                    }
                }
            }
        } else if (isBackgroundSelected && isAlignmentSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterBackground: selectedBackgroundFilters) {
                    if(dataClass.getBackground().contains(filterBackground)) {
                        for (String filterAlignment: selectedAlignmentFilters) {
                            if(dataClass.getAlignment().contains(filterAlignment)) {
                                tempList.add(dataClass);
                            }
                        }
                    }
                }
            }
        }*/ else if (isAmbieteSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterRace: selectedAmbieteFilters) {
                    if(dataClass.getAmbiete().contains(filterRace)) {
                        tempList.add(dataClass);
                    }
                }
            }
        } else if (isCategoriaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterM_Class: selectedCategoriaFilters) {
                    if(dataClass.getCategoria().contains(filterM_Class)) {
                        tempList.add(dataClass);
                    }
                }
            }
        } else if (isTagliaSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterBackground: selectedTagliaFilters) {
                    if(dataClass.getTaglia().contains(filterBackground)) {
                        tempList.add(dataClass);
                    }
                }
            }
        } /*else if (isAlignmentSelected) {
            for (DataClass dataClass : dataList) {
                for (String filterAlignment: selectedAlignmentFilters) {
                    if(dataClass.getAlignment().contains(filterAlignment)) {
                        tempList.add(dataClass);
                    }
                }
            }
        }*/

        adapter.searchDataList(tempList);
        selectedAmbieteFilters.clear();
        selectedCategoriaFilters.clear();
        selectedTagliaFilters.clear();
        //selectedAlignmentFilters.clear();
        isAmbieteSelected = false;
        isCategoriaSelected = false;
        isTagliaSelected = false;
        //isAlignmentSelected = false;
        isFiltersApplied = false;


    }

    /*@Override
    public void onResume() {
        super.onResume();
        if (isFiltersApplied) {
            ApplyFilters();
        }
    }*/

    /*public static void clearClicked() {
        selectedRaceFilters.clear();
        selectedM_ClassFilters.clear();
        selectedBackgroundFilters.clear();
        selectedAlignmentFilters.clear();
        isRaceSelected = false;
        isM_ClassSelected = false;
        isBackgroundSelected = false;
        isAlignmentSelected = false;
        isFiltersApplied = false;

    }*/
}