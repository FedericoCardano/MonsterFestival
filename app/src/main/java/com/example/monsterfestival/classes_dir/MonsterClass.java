package com.example.monsterfestival.classes_dir;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class MonsterClass implements Comparable<MonsterClass> {
    private  String ID;
    private  String Ambiete;
    private  String CA;
    private  String Categoria;
    private  String Nome;
    private  String PF;
    private  String Sfida;
    private  String Taglia;
    private  String Descrizione;
    private  String CAR;
    private  String COST;
    private  String DES;
    private  String FOR;
    private  String INT;
    private  String SAG;

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setAmbiete(String ambiete) {
        Ambiete = ambiete;
    }

    public void setCA(String CA) {
        this.CA = CA;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setPF(String PF) {
        this.PF = PF;
    }

    public void setSfida(String sfida) {
        Sfida = sfida;
    }

    public void setTaglia(String taglia) {
        Taglia = taglia;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public void setCAR(String CAR) {
        this.CAR = CAR;
    }

    public void setCOST(String COST) {
        this.COST = COST;
    }

    public void setDES(String DES) {
        this.DES = DES;
    }

    public void setFOR(String FOR) {
        this.FOR = FOR;
    }

    public void setINT(String INT) {
        this.INT = INT;
    }

    public void setSAG(String SAG) {
        this.SAG = SAG;
    }

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

    public MonsterClass(ArrayList<String> dati) {

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

    public MonsterClass(DataSnapshot snapshot) {

        if(snapshot.child("Ambiente").exists()) {
            this.ID = snapshot.getKey();
            this.Ambiete = snapshot.child("Ambiente").getValue(String.class);
            this.CA = snapshot.child("CA").getValue(String.class);
            this.Categoria = snapshot.child("Categoria").getValue(String.class);
            this.Nome = snapshot.child("Nome").getValue(String.class);
            this.PF = snapshot.child("PF").getValue(String.class);
            this.Sfida = snapshot.child("Sfida").getValue(String.class);
            this.Taglia = snapshot.child("Taglia").getValue(String.class);
            this.Descrizione = snapshot.child("Descrizione").getValue(String.class);
            this.CAR = snapshot.child("CAR").getValue(String.class);
            this.COST = snapshot.child("COST").getValue(String.class);
            this.DES = snapshot.child("DES").getValue(String.class);
            this.FOR = snapshot.child("FOR").getValue(String.class);
            this.INT = snapshot.child("INT").getValue(String.class);
            this.SAG = snapshot.child("SAG").getValue(String.class);
        }else{
            this.ID = snapshot.getKey();
            this.Ambiete = snapshot.child("ambiente").getValue(String.class);
            this.CA = snapshot.child("ca").getValue(String.class);
            this.Categoria = snapshot.child("categoria").getValue(String.class);
            this.Nome = snapshot.child("nome").getValue(String.class);
            this.PF = snapshot.child("pf").getValue(String.class);
            this.Sfida = snapshot.child("sfida").getValue(String.class);
            this.Taglia = snapshot.child("taglia").getValue(String.class);
            this.Descrizione = snapshot.child("descrizione").getValue(String.class);
            this.CAR = snapshot.child("car").getValue(String.class);
            this.COST = snapshot.child("cost").getValue(String.class);
            this.DES = snapshot.child("des").getValue(String.class);
            this.FOR = snapshot.child("for").getValue(String.class);
            this.INT = snapshot.child("int").getValue(String.class);
            this.SAG = snapshot.child("sag").getValue(String.class);
        }
    }
    public ArrayList<String> toArrayListString(){
        ArrayList<String> mon=new ArrayList<>();
        mon.add(this.ID);           //0
        mon.add(this.Nome);         //1
        mon.add(this.Descrizione);  //2
        mon.add(this.Ambiete);      //3
        mon.add(this.Categoria);    //4
        mon.add(this.Taglia);       //5
        mon.add(this.Sfida);        //6
        mon.add(this.PF);           //7
        mon.add(this.CA);           //8
        mon.add(this.FOR);          //9
        mon.add(this.DES);          //10
        mon.add(this.COST);         //11
        mon.add(this.INT);          //12
        mon.add(this.SAG);          //13
        mon.add(this.CAR);          //14

        return mon;
    }
        @Override
    public int compareTo(MonsterClass otherData) {
        return this.Nome.compareTo(otherData.getNome());
    }
}

