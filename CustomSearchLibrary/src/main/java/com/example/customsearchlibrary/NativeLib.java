package com.example.customsearchlibrary;

import java.util.ArrayList;
import java.util.Objects;

import com.google.firebase.database.DataSnapshot;

public class NativeLib {

    private ArrayList<ArrayList<String>> ID;
    private ArrayList<ArrayList<ArrayList<Integer>>> Filtri;
    public ArrayList<ArrayList<String>> nomiFiltri;

    // Used to load the 'customsearchlibrary' library on application startup.
    static {
        System.loadLibrary("customsearchlibrary");
    }

    private native ArrayList<Integer> processTablesNative(ArrayList<ArrayList<Integer>> filterTableList);

    private native ArrayList<Integer> unifyTablesNative(ArrayList<ArrayList<Integer>> filterTableList);

    @SuppressWarnings("unused")
    public ArrayList<Integer> unifyTables(ArrayList<ArrayList<Integer>> filterTableList) {
        if (filterTableList.isEmpty())
            return new ArrayList<>();
        if (filterTableList.size() == 1)
            return filterTableList.get(0);
        return unifyTablesNative(filterTableList);
    }

    @SuppressWarnings("unused")
    public ArrayList<Integer> processTables(ArrayList<ArrayList<Integer>> filterTableList) {
        if (filterTableList.isEmpty())
            return new ArrayList<>();
        if (filterTableList.size() == 1)
            return filterTableList.get(0);
        return processTablesNative(filterTableList);
    }

    private native void updateDatabaseNative(ArrayList<ArrayList<String>> ID, ArrayList<ArrayList<ArrayList<Integer>>> Filtri, ArrayList<ArrayList<String>> nomiFiltri);

    public void updateDatabase() {
        updateDatabaseNative(ID, Filtri, nomiFiltri);
    }

    public void setID(DataSnapshot dataSnapshot) {
        ID = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            ArrayList<String> monster = new ArrayList<>();
            monster.add(Objects.requireNonNull(snapshot.child("Nome").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("Descrizione").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("Ambiente").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("Categoria").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("Taglia").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("Sfida").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("PF").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("CA").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("FOR").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("DES").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("COST").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("INT").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("SAG").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("CAR").getValue()).toString());
            ID.add(monster);
        }
    }

    public void setFiltri(DataSnapshot dataSnapshot) {
        Filtri = new ArrayList<>();
        nomiFiltri = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

            ArrayList<ArrayList<Integer>> _Filtri = new ArrayList<>();
            ArrayList<String> _nomiFiltri = new ArrayList<>();

            _nomiFiltri.add(snapshot.getKey());

            for (DataSnapshot innerSnapshot : snapshot.getChildren()) {

                _nomiFiltri.add(innerSnapshot.getKey());
                ArrayList<Integer> idFiltro = new ArrayList<>();
                for (DataSnapshot mostInnerSnapshot : innerSnapshot.getChildren()) {
                    idFiltro.add(mostInnerSnapshot.getValue(Integer.class));
                }
                _Filtri.add(idFiltro);

            }

            Filtri.add(_Filtri);
            nomiFiltri.add(_nomiFiltri);

        }
    }

    public ArrayList<ArrayList<String>> getID() {
        return ID;
    };

    @SuppressWarnings("unused")
    public native ArrayList<String> getMostro(Integer ID);

    private native ArrayList<ArrayList<String>> execSearchNative(String text, ArrayList<ArrayList<String>> filterList);

    public ArrayList<ArrayList<String>> execSearch(String text, ArrayList<ArrayList<String>> filterList) {
        if (filterList.size() > 0)
            return execSearchNative(text == null ? "" : text, filterList);
        return ID;
    }
}