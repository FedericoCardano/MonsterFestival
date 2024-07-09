package com.example.monsterfestival.classes_dir;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class PartyPost {
    private  String ID;
    public  Double vote, voteCoerenza, voteOriginalita, voteBilanciamento;
    public int nVoti;
    private  String uidAutorePost;
    private  String postTime;
    public ArrayList<Comment> commenti = new ArrayList<Comment>();;
    private  String nome;

    private String listaMostri,totCA, totCAR, totCOST, totDES, totFOR, totINT, totPF, totSAG,totSfida;


    private ArrayList<EventClass> eventi = new ArrayList<EventClass>();
    //public  MonsterClass Monster;

    public PartyPost(DataSnapshot snapshot){
        this.postTime =snapshot.child("postTime").getValue(String.class);
        this.voteCoerenza =snapshot.child("voteCoerenza").getValue(Double.class);
        this.voteOriginalita =snapshot.child("voteOriginalita").getValue(Double.class);
        this.voteBilanciamento =snapshot.child("voteBilanciamento").getValue(Double.class);
        this.nVoti=snapshot.child("nVoti").getValue(int.class);
        this.ID = this.postTime;
        this.vote = snapshot.child("vote").getValue(Double.class);
        this.uidAutorePost = snapshot.child("uidAutorePost").getValue(String.class);
        this.nome = snapshot.child("nome").getValue(String.class);


        for(DataSnapshot child : snapshot.child("commenti").getChildren()){
            Comment C= new Comment(child);
            this.commenti.add(C);///????????????
        }
        for(DataSnapshot child : snapshot.child("Party").getChildren()){
            if(child.getKey().startsWith("Event"))
            {
                EventClass event = new EventClass(child);
                this.eventi.add(event);
            }
        }
        this.listaMostri = String.valueOf(snapshot.child("Party").child("listaMostri").getValue());
        this.totCA = String.valueOf(snapshot.child("Party").child("totCA").getValue());
        this.totCAR = String.valueOf(snapshot.child("Party").child("totCAR").getValue());
        this.totCOST = String.valueOf(snapshot.child("Party").child("totCOST").getValue());
        this.totDES = String.valueOf(snapshot.child("Party").child("totDES").getValue());
        this.totFOR = String.valueOf(snapshot.child("Party").child("totFOR").getValue());
        this.totINT = String.valueOf(snapshot.child("Party").child("totINT").getValue());
        this.totPF = String.valueOf(snapshot.child("Party").child("totPF").getValue());
        this.totSAG = String.valueOf(snapshot.child("Party").child("totSAG").getValue());
        this.totSfida = String.valueOf(snapshot.child("Party").child("totSfida").getValue());





    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUidAutorePost() {
        return uidAutorePost;
    }

    public void setUidAutorePost(String uidAutorePost) {
        this.uidAutorePost = uidAutorePost;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<EventClass> getEventi() {
        return eventi;
    }

    public void setEventi(ArrayList<EventClass> eventi) {
        this.eventi = eventi;
    }

    public String getListaMostri() {
        return listaMostri;
    }

    public void setListaMostri(String listaMostri) {
        this.listaMostri = listaMostri;
    }

    public String getTotCA() {
        return totCA;
    }

    public void setTotCA(String totCA) {
        this.totCA = totCA;
    }

    public String getTotCAR() {
        return totCAR;
    }

    public void setTotCAR(String totCAR) {
        this.totCAR = totCAR;
    }

    public String getTotCOST() {
        return totCOST;
    }

    public void setTotCOST(String totCOST) {
        this.totCOST = totCOST;
    }

    public String getTotDES() {
        return totDES;
    }

    public void setTotDES(String totDES) {
        this.totDES = totDES;
    }

    public String getTotFOR() {
        return totFOR;
    }

    public void setTotFOR(String totFOR) {
        this.totFOR = totFOR;
    }

    public String getTotINT() {
        return totINT;
    }

    public void setTotINT(String totINT) {
        this.totINT = totINT;
    }

    public String getTotPF() {
        return totPF;
    }

    public void setTotPF(String totPF) {
        this.totPF = totPF;
    }

    public String getTotSAG() {
        return totSAG;
    }

    public void setTotSAG(String totSAG) {
        this.totSAG = totSAG;
    }

    public String getTotSfida() {
        return totSfida;
    }

    public void setTotSfida(String totSfida) {
        this.totSfida = totSfida;
    }
}
