package com.example.customsearchlibrary;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NativeLib implements Serializable {

    private ArrayList<ArrayList<String>> ID;
    private ArrayList<ArrayList<ArrayList<Integer>>> Filtri;
    public ArrayList<ArrayList<String>> nomiFiltri;


    private String userUid = "";
    private ArrayList<ArrayList<ArrayList<Integer>>> Party;
    private ArrayList<String> nomiParty;


    // Used to load the 'customsearchlibrary' library on application startup.
    static {
        System.loadLibrary("customsearchlibrary");
    }

    public NativeLib() {
        this.ID = new ArrayList<>();
        this.Filtri = new ArrayList<>();
        this.nomiFiltri = new ArrayList<>();
        this.userUid = "";
        this.Party = new ArrayList<>();
        this.nomiParty = new ArrayList<>();
    }

    public NativeLib(NativeLib obj) {
        this.ID = obj.ID;
        this.Filtri = obj.Filtri;
        this.nomiFiltri = obj.nomiFiltri;
        this.userUid = obj.userUid;
        this.Party = obj.Party;
        this.nomiParty = obj.nomiParty;

        updateDatabase();
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
            monster.add(snapshot.getKey());
            monster.add(Objects.requireNonNull(snapshot.child("Nome").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("Descrizione").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("Ambiente").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("Categoria").getValue()).toString());
            monster.add(Objects.requireNonNull(snapshot.child("Taglia").getValue()).toString());
            double value = Double.parseDouble(Objects.requireNonNull(snapshot.child("Sfida").getValue()).toString());
            if (value < 0)
                value = Math.pow(2, value);
            monster.add(Double.toString(value));
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

    public ArrayList<ArrayList<String>> getID() {
        return ID;
    };

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

    public void setParties(DataSnapshot dataSnapshot) {
        invalidateUid();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            userUid = user.getUid();

        Party = new ArrayList<>();
        nomiParty = new ArrayList<>();
        boolean nParty = true;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            if (nParty) {
                nParty = false;
                continue;
            }
            nomiParty.add(snapshot.getKey());

            ArrayList<ArrayList<Integer>> _party = new ArrayList<>();
            for (DataSnapshot innerSnapshot : snapshot.getChildren()) {
                ArrayList<Integer> mostro = new ArrayList<>();
                mostro.add(Integer.valueOf(Objects.requireNonNull(innerSnapshot.child("Qty").getValue()).toString()));
                mostro.add(Integer.valueOf(Objects.requireNonNull(innerSnapshot.child("ID").getValue()).toString()));
                _party.add(mostro);
            }

            Party.add(_party);
        }
    }

    public ArrayList<ArrayList<ArrayList<Integer>>> getParties() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && !userUid.isEmpty() && user.getUid().equals(userUid))
            return Party;
        return new ArrayList<>();
    }

    public ArrayList<String> getPartyNames() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && !userUid.isEmpty() && user.getUid().equals(userUid))
            return nomiParty;
        return new ArrayList<>();
    }

    public ArrayList<ArrayList<String>> getPartyWithName(String nomeParty) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null || userUid.isEmpty() || !user.getUid().equals(userUid))
            return new ArrayList<>();

        int index = 0;
        for (String nome : nomiParty) {
            if (nome.equals(nomeParty))
                break;
            index++;
        }

        if (index < nomiParty.size())
        {
            ArrayList<ArrayList<String>> mostriParty = new ArrayList<>();

            for (int i = 0; i < Party.get(index).size(); i++)
            {
                ArrayList<String> mostro = ID.get(Party.get(index).get(i).get(1));
                mostro.add(0, String.valueOf(Party.get(index).get(i).get(0)));
                mostriParty.add(mostro);
            }

            return mostriParty;
        }

        return new ArrayList<>();

    }

    public void deleteParty(int adapterPosition) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null || userUid.isEmpty() || !user.getUid().equals(userUid))
            return;

        Party.remove(adapterPosition);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userUid);
        databaseReference.child(nomiParty.remove(adapterPosition)).removeValue().addOnSuccessListener(aVoid -> databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("NParty").getValue() == null)
                    return;

                int NParty = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("NParty").getValue()).toString()) - 1;

                if (NParty > 0)
                {
                    setParties(dataSnapshot);
                    databaseReference.child("NParty").setValue(NParty);
                }
                else
                    databaseReference.child("NParty").removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        }));
    }

    public void invalidateUid() {
        userUid = "";
    }

    public native ArrayList<String> getMostro(Integer ID);

    public native ArrayList<String> getMostro(String nome);

    private native ArrayList<ArrayList<String>> execSearchNative(String text, ArrayList<ArrayList<String>> filterList);

    public ArrayList<ArrayList<String>> execSearch(String text, ArrayList<ArrayList<String>> filterList) {
        return execSearchNative(text == null ? "" : text, filterList);
    }
}