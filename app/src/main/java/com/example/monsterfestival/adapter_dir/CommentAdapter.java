package com.example.monsterfestival.adapter_dir;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.Comment;
import com.example.monsterfestival.fragment_dir.DetailMonsterPostFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CommentAdapter  extends RecyclerView.Adapter<CommentViewHolder> {

    private DetailMonsterPostFragment _parent;
    private final List<Comment> CommentList;
    private final Lock ThreadLock = new ReentrantLock();
    //private OnNameClickListener listener;

    private String timePassed(String timestamp){

        long time = Long.parseLong(timestamp);
        Log.d("timePassed",time+"-"+System.currentTimeMillis());
        long delta = System.currentTimeMillis()-time;
        long seconds = delta/1000;
        if(seconds>60){
            long minutes = seconds/60;
            if(minutes>60) {
                long hours = minutes / 60;
                if (hours > 24) {
                    long days = hours / 24;
                    if (days > 365) {
                        long years = days / 365;
                        if(years==1)
                            return years + " anno fa";
                        return years+" anni fa";
                    } else if (days > 30) {
                        long months = days / 30;
                        if(months==1)
                            return months + " mese fa";
                        return months + " mesi fa";
                    } else if (days > 7) {
                        long weeks = days / 7;
                        if(weeks==1)
                            return weeks + " settimana fa";
                        return weeks + " settimane fa";
                    } else {
                        if(days==1)
                            return days + " giorno fa";
                        return days + " giorni fa";
                    }
                } else {
                    if(hours==1)
                        return hours + " ora fa";
                    return hours + " ore fa";
                }
            }else {
                return minutes + "min fa";
            }
        }else if(seconds>0){
            return seconds + "s fa";
        }else return "now";





    }

    public CommentAdapter(ArrayList<Comment> dataList, DetailMonsterPostFragment parent) {
        this._parent = parent;
        this.CommentList = dataList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_post_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Log.d("onBindViewHolder","Position: "+position+" Holder: "+holder);
        Comment comment = CommentList.get(position);


        holder.time.setText(timePassed(comment.getTimestamp()));
        holder.text.setText(comment.comment);

        DatabaseReference rf = FirebaseDatabase.getInstance().getReference().child("UsersInformation").child(comment.getUidComment()).child("username");
        rf.get().addOnCompleteListener(task ->  {
            if(task.isSuccessful()){
                Log.d("firebase", "data: "+ task.getResult().getValue());
                holder.nameAuthor.setText(String.valueOf(task.getResult().getValue()));
            }
            else{
                Log.e("firebase", "Error getting data", task.getException());
            }

        });



    }

    @Override
    public int getItemCount() {
        return CommentList.size();
    }

}

 class CommentViewHolder extends RecyclerView.ViewHolder{
    TextView time, text, nameAuthor;
    //CardView postCard;
    public CommentViewHolder(@NonNull View itemView) {

        super(itemView);
        Log.d("PostViewHolder","View: "+itemView);
        time = itemView.findViewById(R.id.TvTime);
        text = itemView.findViewById(R.id.TvText);
        nameAuthor = itemView.findViewById(R.id.TvNameAuthor);
        //postCard = itemView.findViewById(R.id.PostCard);

    }




}


