package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SearchFiltersFragment extends Fragment implements View.OnClickListener {

    View rootView;
    private TextView clearFilters;

    DatabaseReference databaseReference;
    public static AlertDialog dialog;
    RecyclerView recyclerView;
    ArrayList<ParentModelClass> parentModelClassArrayList;
    ParentAdapter parentAdapter;
    private final HashMap<View, ChildModelClass> filtri_ambiente = new HashMap<>();
    private final HashMap<View, ChildModelClass> filtri_categoria = new HashMap<>();
    private final HashMap<View, ChildModelClass> filtri_taglia = new HashMap<>();
    private final ArrayList<ChildModelClass> filtri_ambiente_ModelClass = new ArrayList<>();
    private final ArrayList<ChildModelClass> filtri_categoria_ModelClass = new ArrayList<>();
    private final ArrayList<ChildModelClass> filtri_taglia_ModelClass = new ArrayList<>();

    private int white;
    private int rossoPorpora;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search_filters, container, false);


        recyclerView = rootView.findViewById(R.id.rv_parent);
        parentModelClassArrayList = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("Monster");
        dialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot itemSnapshot = snapshot.child("Filtri");
                for (DataSnapshot i : itemSnapshot.getChildren()) {
                //TODO: salvarsi il nome dei filtri (che sono delle cartelle) dentro una stringa,
                // e in base a quale cartella appartengono (Ambiente, Categoria, Taglia) eseguire il metodo nomeLista.add(new ChildModelClass(Stringa))
                // (i nomi delle liste sono ambienteList, categoriaList, tagliaList)
                }
                parentModelClassArrayList.add(new ParentModelClass("Ambienti", filtri_ambiente_ModelClass));
                parentModelClassArrayList.add(new ParentModelClass("Categorie", filtri_categoria_ModelClass));
                parentModelClassArrayList.add(new ParentModelClass("Taglie", filtri_taglia_ModelClass));
                parentAdapter = new ParentAdapter(parentModelClassArrayList, getActivity());
                recyclerView.setAdapter(parentAdapter);
                parentAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });


        initColors();
        initAll();
        SearchMonstersFragment.areFiltersNotChanged = true;

        clearFilters.setOnClickListener(this);

        for (View view : filtri_ambiente.keySet()) {
            view.setOnClickListener(this);
            if (SearchMonstersFragment.isAmbienteSelected && SearchMonstersFragment.selectedAmbieteFilters.contains(filtri_ambiente.get(view)))
                lookSelected((LinearLayout) view);
        }

        for (View view : filtri_categoria.keySet()) {
            view.setOnClickListener(this);
            if (SearchMonstersFragment.isCategoriaSelected && SearchMonstersFragment.selectedCategoriaFilters.contains(filtri_categoria.get(view)))
                lookSelected((LinearLayout) view);
        }

        for (View view : filtri_taglia.keySet()) {
            view.setOnClickListener(this);
            if (SearchMonstersFragment.isTagliaSelected && SearchMonstersFragment.selectedTagliaFilters.contains(filtri_taglia.get(view)))
                lookSelected((LinearLayout) view);
        }

        return rootView;
    }

    private void initAll() {
        clearFilters = rootView.findViewById(R.id.id_clear_btn);



        /*

        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_artico), getResources().getString(R.string.artico).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_catacombe), getResources().getString(R.string.catacombe).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_caverna), getResources().getString(R.string.caverna).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_cittatina), getResources().getString(R.string.cittatina).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_collina), getResources().getString(R.string.collina).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_costa), getResources().getString(R.string.costa).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_deserto), getResources().getString(R.string.deserto).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_dungeon), getResources().getString(R.string.dungeon).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_errante), getResources().getString(R.string.errante).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_foresta), getResources().getString(R.string.foresta).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_giungla), getResources().getString(R.string.giungla).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_inferno), getResources().getString(R.string.inferno).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_lago), getResources().getString(R.string.lago).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_limbo), getResources().getString(R.string.limbo).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_montagna), getResources().getString(R.string.montagna).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_nomade), getResources().getString(R.string.nomade).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_ovunque), getResources().getString(R.string.ovunque).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_palude), getResources().getString(R.string.palude).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_paradiso), getResources().getString(R.string.paradiso).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_pianoDelFuoco), getResources().getString(R.string.piano_del_fuoco).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_pianoDellAcqua), getResources().getString(R.string.piano_dell_acqua).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_pianoDellAria), getResources().getString(R.string.piano_dell_aria).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_pianoDellaTerra), getResources().getString(R.string.piano_della_terra).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_pianura), getResources().getString(R.string.pianura).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_profonditaMarine), getResources().getString(R.string.profondit_marine).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_rovine), getResources().getString(R.string.rovine).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_servitore), getResources().getString(R.string.servitore).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_terreSelvaggie), getResources().getString(R.string.terre_selvaggie).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_underdark), getResources().getString(R.string.underdark).toLowerCase());
        filtri_ambiente.put(rootView.findViewById(R.id.ambiete_vulcano), getResources().getString(R.string.vulcano).toLowerCase());

        filtri_categoria.put(rootView.findViewById(R.id.categoria_aberrazione), getResources().getString(R.string.aberrazione).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_bestia), getResources().getString(R.string.bestia).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_celestiale), getResources().getString(R.string.celestiale).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_costrutto), getResources().getString(R.string.costrutto).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_drago), getResources().getString(R.string.drago).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_elementale), getResources().getString(R.string.elementale).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_folletto), getResources().getString(R.string.folletto).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_gigante), getResources().getString(R.string.gigante).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_immondo), getResources().getString(R.string.immondo).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_melma), getResources().getString(R.string.melma).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_mostruosita), getResources().getString(R.string.mostruosit).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_nonMorto), getResources().getString(R.string.non_morto).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_umanoide), getResources().getString(R.string.umanoide).toLowerCase());
        filtri_categoria.put(rootView.findViewById(R.id.categoria_vegetale), getResources().getString(R.string.vegetale).toLowerCase());

        filtri_taglia.put(rootView.findViewById(R.id.taglia_enorme), getResources().getString(R.string.enorme).toLowerCase());
        filtri_taglia.put(rootView.findViewById(R.id.taglia_grande), getResources().getString(R.string.grande).toLowerCase());
        filtri_taglia.put(rootView.findViewById(R.id.taglia_mastodontica), getResources().getString(R.string.mastodontica).toLowerCase());
        filtri_taglia.put(rootView.findViewById(R.id.taglia_media), getResources().getString(R.string.media).toLowerCase());
        filtri_taglia.put(rootView.findViewById(R.id.taglia_minuscola), getResources().getString(R.string.minuscola).toLowerCase());
        filtri_taglia.put(rootView.findViewById(R.id.taglia_piccola), getResources().getString(R.string.piccola).toLowerCase());

         */


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

        } else if (filtri_ambiente.containsKey(view)) {
            // Filtri Ambiente
            SearchMonstersFragment.areFiltersNotChanged = false;

            String testo = Objects.requireNonNull(filtri_ambiente.get(view)).getNomeFiltro();
            if (SearchMonstersFragment.selectedAmbieteFilters.contains(testo)) {
                SearchMonstersFragment.selectedAmbieteFilters.remove(testo);
                lookDeselected((LinearLayout) view);
            } else {
                SearchMonstersFragment.selectedAmbieteFilters.add(testo);
                lookSelected((LinearLayout) view);
            }

        } else if (filtri_categoria.containsKey(view)) {
            // Filtri Categoria
            SearchMonstersFragment.areFiltersNotChanged = false;

            String testo = Objects.requireNonNull(filtri_categoria.get(view)).getNomeFiltro();
            if (SearchMonstersFragment.selectedCategoriaFilters.contains(testo)) {
                SearchMonstersFragment.selectedCategoriaFilters.remove(testo);
                lookDeselected((LinearLayout) view);
            } else {
                SearchMonstersFragment.selectedCategoriaFilters.add(testo);
                lookSelected((LinearLayout) view);
            }

        } else if (filtri_taglia.containsKey(view)) {
            // Filtri Taglia
            SearchMonstersFragment.areFiltersNotChanged = false;

            String testo = Objects.requireNonNull(filtri_taglia.get(view)).getNomeFiltro();
            if (SearchMonstersFragment.selectedTagliaFilters.contains(testo)) {
                SearchMonstersFragment.selectedTagliaFilters.remove(testo);
                lookDeselected((LinearLayout) view);
            } else {
                SearchMonstersFragment.selectedTagliaFilters.add(testo);
                lookSelected((LinearLayout) view);
            }

        }

    }

    private void initColors()
    {
        white = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.white);
        rossoPorpora = ContextCompat.getColor(requireContext().getApplicationContext(), R.color.rossoPorpora);
    }

    private void lookSelected(LinearLayout selectedFilter)
    {
        selectedFilter.setBackgroundColor(rossoPorpora);
    }

    private void lookDeselected(LinearLayout selectedFilter)
    {
        selectedFilter.setBackgroundColor(white);
    }
}