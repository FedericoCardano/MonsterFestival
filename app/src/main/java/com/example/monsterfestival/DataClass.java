package com.example.monsterfestival;

public class DataClass {
    private String Ambiete;
    private String CA;
    private String Categoria;
    private String Nome;
    private String PF;
    private String Sfida;
    private String Taglia;
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

    public DataClass(String Ambiete, String CA, String Categoria, String Nome, String PF, String Sfida, String Taglia) {
        this.Ambiete = Ambiete;
        this.CA = CA;
        this.Categoria = Categoria;
        this.Nome = Nome;
        this.PF = PF;
        this.Sfida = Sfida;
        this.Taglia = Taglia;
    }

    public DataClass(){
    }
}

