package com.example.monsterfestival.classes_dir;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class MonsterPost {
    private  String ID;
    public  Double Vote,votoCoerenza, votoOriginalita, votoBilanciamento;
    public int nVoti;
    private  String UidAutorePost;
    private  String PostTime;
    public ArrayList<Comment> Commenti= new ArrayList<Comment>();;
    public  MonsterClass Monster;
   public MonsterPost(DataSnapshot snapshot){
       this.PostTime=snapshot.child("PostTime").getValue(String.class);
       this.votoCoerenza =snapshot.child("voteCoerenza").getValue(Double.class);
       this.votoOriginalita =snapshot.child("voteOriginalita").getValue(Double.class);
       this.votoBilanciamento =snapshot.child("voteBilanciamento").getValue(Double.class);
       this.nVoti=snapshot.child("nVoti").getValue(int.class);
       if(this.nVoti<=0)
           this.Vote=0.0;
       else
           this.Vote= (this.votoCoerenza +this.votoOriginalita +this.votoBilanciamento)/3;
       this.Monster= new MonsterClass(snapshot);
       this.ID = this.Monster.getID();
       this.UidAutorePost= snapshot.child("UidAutorePost").getValue(String.class);
       for(DataSnapshot child : snapshot.child("commenti").getChildren()){
           Comment C= new Comment(child);
           this.Commenti.add(C);///????????????
       }
   }

    public String getID() {return ID;}

    public void setID(String ID) {this.ID = ID;}

    public String getUidAutorePost() {
        return UidAutorePost;
    }

    public void setUidAutorePost(String uidAutorePost) {
        UidAutorePost = uidAutorePost;
    }

    public String getPostTime() {
        return PostTime;
    }

    public void setPostTime(String postTime) {
        PostTime = postTime;
    }
}
