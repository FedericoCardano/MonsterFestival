package com.example.monsterfestival.classes_dir;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class EventClass {
    private String nome;
    private String causa;
    private String reazione;

    public EventClass(String nome, String causa, String reazione) {
        this.nome = nome;
        this.causa = causa;
        this.reazione = reazione;
    }

    public EventClass(DataSnapshot snapshot) {
        this.nome = snapshot.child("nome").getValue(String.class);
        this.causa = snapshot.child("causa").getValue(String.class);
        this.reazione = snapshot.child("reazione").getValue(String.class);
    }

    public ArrayList<String> Event2ArrayString()
    {
        ArrayList<String> e = new ArrayList<>();
        e.add(this.nome);
        e.add(this.causa);
        e.add(this.reazione);
        return e;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getReazione() {
        return reazione;
    }

    public void setReazione(String reazione) {
        this.reazione = reazione;
    }
}
