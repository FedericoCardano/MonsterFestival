package com.example.monsterfestival.classes_dir;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

public class Comment{
    private String ID;
    private String UidAutoreComment;
    public String Text;
    private String CommentTime;

      public Comment(DataSnapshot snapshot){
        this.ID = snapshot.getKey();
        this.UidAutoreComment= snapshot.child("uidComment").getValue(String.class);
        this.Text=snapshot.child("comment").getValue(String.class);
        this.CommentTime=snapshot.child("timestamp").getValue(String.class);
    }
    public Comment(String id, String uidAutoreComment, String text, String commentTime) {
        this.ID = id;
        this.UidAutoreComment= uidAutoreComment;
        this.Text=text;
        this.CommentTime=commentTime;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUidAutoreComment() {
        return UidAutoreComment;
    }

    public void setUidAutoreComment(String uidAutoreComment) {
        UidAutoreComment = uidAutoreComment;
    }

    public String getCommentTime() {
        return CommentTime;
    }

    public void setCommentTime(String commentTime) {
        CommentTime = commentTime;
    }
}
