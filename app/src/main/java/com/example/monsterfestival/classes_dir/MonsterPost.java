package com.example.monsterfestival.classes_dir;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MonsterPost {
    private  String ID;
    public  Double Vote;
    private  String UidAutorePost;
    private  String PostTime;
    public ArrayList<Comment> Commenti= new ArrayList<Comment>();;
    public  MonsterClass Monster;
   public MonsterPost(DataSnapshot snapshot){

       this.PostTime=snapshot.child("PostTime").getValue(String.class);
       this.Vote=snapshot.child("Vote").getValue(Double.class);
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
