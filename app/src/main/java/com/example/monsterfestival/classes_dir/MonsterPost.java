package com.example.monsterfestival.classes_dir;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class MonsterPost {
    private  String ID;
    public  Double vote, voteCoerenza, voteOriginalita, voteBilanciamento;
    public int nVoti;
    private  String uidAutorePost;
    private  String postTime;
    public ArrayList<Comment> commenti = new ArrayList<Comment>();;
    public  MonsterClass Monster;
   public MonsterPost(DataSnapshot snapshot){
       this.postTime =snapshot.child("postTime").getValue(String.class);
       this.voteCoerenza =snapshot.child("voteCoerenza").getValue(Double.class);
       this.voteOriginalita =snapshot.child("voteOriginalita").getValue(Double.class);
       this.voteBilanciamento =snapshot.child("voteBilanciamento").getValue(Double.class);
       this.nVoti=snapshot.child("nVoti").getValue(int.class);
       if(this.nVoti<=0)
           this.vote =0.0;
       else
           this.vote = (this.voteCoerenza +this.voteOriginalita +this.voteBilanciamento)/3;
       this.Monster= new MonsterClass(snapshot.child("Monster"));
       this.ID = this.Monster.getID();
       this.uidAutorePost = snapshot.child("uidAutorePost").getValue(String.class);
       for(DataSnapshot child : snapshot.child("commenti").getChildren()){
           Comment C= new Comment(child);
           this.commenti.add(C);///????????????
       }
   }

   public MonsterPost(String uidAutorePost,String postTime,MonsterClass monster){
       this.uidAutorePost =uidAutorePost;
       this.postTime =postTime;
       this.Monster=monster;
       this.vote =0.0;
       this.voteBilanciamento =0.0;
       this.voteCoerenza =0.0;
       this.voteOriginalita =0.0;
       this.nVoti=0;
       this.ID = this.Monster.getID();
   }

    public String getID() {return ID;}

    public void setID(String ID) {this.ID = ID;}

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
}
