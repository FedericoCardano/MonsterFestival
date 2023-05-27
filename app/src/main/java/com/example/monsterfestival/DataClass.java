package com.example.monsterfestival;

public class DataClass {
    private String Ambiete;
    private String CA;
    private String Categoria;
    private String Nome;
    private String PF;
    private String Sfida;
    private String Taglia;
    private String Descrizione;
    private String CAR;
    private String COST;
    private String DES;
    private String FOR;
    private String INT;
    private String SAG;
    private String key;



    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
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

    public DataClass(String Ambiete, String CA, String Categoria, String Nome, String PF, String Sfida, String Taglia, String Descrizione, String CAR, String COST, String DES, String FOR, String INT, String SAG) {
        this.Ambiete = Ambiete;
        this.CA = CA;
        this.Categoria = Categoria;
        this.Nome = Nome;
        this.PF = PF;
        this.Sfida = Sfida;
        this.Taglia = Taglia;
        this.Descrizione = Descrizione;
        this.CAR = CAR;
        this.COST = COST;
        this.DES = DES;
        this.FOR = FOR;
        this.INT = INT;
        this.SAG = SAG;
    }
}

