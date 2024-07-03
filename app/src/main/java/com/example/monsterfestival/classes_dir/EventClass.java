package com.example.monsterfestival.classes_dir;

public class EventClass {
    private String nome;
    private String causa;
    private String reazione;

    public EventClass(String nome, String causa, String reazione) {
        this.nome = nome;
        this.causa = causa;
        this.reazione = reazione;
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
