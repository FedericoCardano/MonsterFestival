package com.example.monsterfestival.classes_dir;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class DataClass implements Comparable<DataClass> {
    private final String ID;
    private final String Ambiete;
    private final String CA;
    private final String Categoria;
    private final String Nome;
    private final String PF;
    private final String Sfida;
    private final String Taglia;
    private final String Descrizione;
    private final String CAR;
    private final String COST;
    private final String DES;
    private final String FOR;
    private final String INT;
    private final String SAG;


    public String getID() { return ID; }
    public String getAmbiente() { return Ambiete; }
    public String getCa() { return CA; }
    public String getCategoria() {
        return Categoria;
    }
    public String getNome() {
        return Nome;
    }
    public String getPf() {
        return PF;
    }
    public String getSfida() {
        return Sfida;
    }
    public String getTaglia() {
        return Taglia;
    }
    public String getDescrizione() {
        return Descrizione;
    }
    public String getCar() {
        return CAR;
    }
    public String getCost() {
        return COST;
    }
    public String getDes() {
        return DES;
    }
    public String getFor() {
        return FOR;
    }
    public String getInt() {
        return INT;
    }
    public String getSag() {
        return SAG;
    }

    public DataClass(ArrayList<String> dati) {

        this.ID = dati.get(0);
        this.Nome = dati.get(1);
        this.Descrizione = dati.get(2);
        this.Ambiete = dati.get(3);
        this.Categoria = dati.get(4);
        this.Taglia = dati.get(5);
        if (Double.parseDouble(dati.get(6)) < 0)
            this.Sfida = Double.toString(Math.pow(2, Double.parseDouble(dati.get(6))));
        else
            this.Sfida = dati.get(6);
        this.PF = dati.get(7);
        this.CA = dati.get(8);
        this.FOR = dati.get(9);
        this.DES = dati.get(10);
        this.COST = dati.get(11);
        this.INT = dati.get(12);
        this.SAG = dati.get(13);
        this.CAR = dati.get(14);

    }

    public DataClass(DataSnapshot snapshot) {

        this.ID = snapshot.getKey();
        this.Ambiete = snapshot.child("Ambiente").getValue(String.class);
        this.CA = String.valueOf(snapshot.child("CA").getValue(long.class));
        this.Categoria = snapshot.child("Categoria").getValue(String.class);
        this.Nome = snapshot.child("Nome").getValue(String.class);
        this.PF = String.valueOf(snapshot.child("PF").getValue(long.class));
        this.Sfida = String.valueOf(snapshot.child("Sfida").getValue(long.class));
        this.Taglia = snapshot.child("Taglia").getValue(String.class);
        this.Descrizione = snapshot.child("Descrizione").getValue(String.class);
        this.CAR = snapshot.child("CAR").getValue(String.class);
        this.COST = snapshot.child("COST").getValue(String.class);
        this.DES = snapshot.child("DES").getValue(String.class);
        this.FOR = snapshot.child("FOR").getValue(String.class);
        this.INT = snapshot.child("INT").getValue(String.class);
        this.SAG = snapshot.child("SAG").getValue(String.class);

    }

    @Override
    public int compareTo(DataClass otherData) {
        return this.Nome.compareTo(otherData.getNome());
    }
}

