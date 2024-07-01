package com.example.monsterfestival.classes_dir;

import com.google.firebase.database.DataSnapshot;

public class Comment{

    private String uidComment;
    public String comment;
    private String timestamp;

      public Comment(DataSnapshot snapshot){

        this.uidComment = snapshot.child("uidComment").getValue(String.class);
        this.comment =snapshot.child("comment").getValue(String.class);
        this.timestamp =snapshot.child("timestamp").getValue(String.class);
    }
    public Comment( String uidAutoreComment, String text, String commentTime) {

        this.uidComment = uidAutoreComment;
        this.comment =text;
        this.timestamp =commentTime;
    }


    public String getUidComment() {
        return uidComment;
    }

    public void setUidComment(String uidComment) {
        this.uidComment = uidComment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
