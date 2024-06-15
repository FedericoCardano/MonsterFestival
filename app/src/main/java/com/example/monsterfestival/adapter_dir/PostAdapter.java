package com.example.monsterfestival.adapter_dir;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.MonsterPost;
import com.example.monsterfestival.classes_dir.OnNameClickListener;

import java.util.List;

import java.util.ArrayList;

public class PostAdapter  extends RecyclerView.Adapter<PostViewHolder> {

    private final List<MonsterPost> PostList;

    //private OnNameClickListener listener;



    public PostAdapter(ArrayList<MonsterPost> dataList, OnNameClickListener listener) {
        //this.listener = listener;
        this.PostList = dataList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Log.d("onBindViewHolder","Position: "+position+" Holder: "+holder);
        MonsterPost post = PostList.get(position);
        holder.name.setText(post.Monster.getNome());
        holder.vote.setText(String.valueOf(post.Vote));
        holder.rank.setText(String.valueOf(position+1));



       /* holder.name.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // Chiama il metodo dell'interfaccia
                listener.onNameClick(post);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }

}

 class PostViewHolder extends RecyclerView.ViewHolder{
    TextView rank, vote, name;
    //CardView postCard;
    public PostViewHolder(@NonNull View itemView) {

        super(itemView);
        Log.d("PostViewHolder","View: "+itemView);
        rank = itemView.findViewById(R.id.TvRank);
        vote = itemView.findViewById(R.id.TvVote);
        name = itemView.findViewById(R.id.TvName);
        //postCard = itemView.findViewById(R.id.PostCard);

    }




}


