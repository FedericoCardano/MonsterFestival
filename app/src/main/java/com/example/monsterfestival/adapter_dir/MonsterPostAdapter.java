package com.example.monsterfestival.adapter_dir;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.MonsterPost;
import com.example.monsterfestival.fragment_dir.CommunityFragment;
import com.example.monsterfestival.fragment_dir.DetailAuthorFragment;
import com.example.monsterfestival.fragment_dir.DetailMonsterPostFragment;

import java.util.List;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonsterPostAdapter extends RecyclerView.Adapter<PartyPostViewHolder> {

    private Fragment _parent;
    private final List<MonsterPost> PostList;
    private final Lock ThreadLock = new ReentrantLock();
    //private OnNameClickListener listener;



    public MonsterPostAdapter(ArrayList<MonsterPost> dataList, Fragment parent) {
        this._parent = parent;
        this.PostList = dataList;
    }

    @NonNull
    @Override
    public PartyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_post_item, parent, false);
        return new PartyPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyPostViewHolder holder, int position) {
        Log.d("onBindViewHolder","Position: "+position+" Holder: "+holder);
        MonsterPost post = PostList.get(position);
        holder.name.setText(post.Monster.getNome());
        if(_parent instanceof CommunityFragment)
        {
            int orderSelected = ((CommunityFragment) _parent).getCurrentOrder();
            switch (orderSelected) {
                case 1:
                    holder.vote.setText(String.valueOf(Math.round(post.voteCoerenza *100)/100.0));
                    break;
                case 2:
                    holder.vote.setText(String.valueOf(Math.round(post.voteOriginalita *100)/100.0));
                    break;
                case 3:
                    holder.vote.setText(String.valueOf(Math.round(post.voteBilanciamento *100)/100.0));
                    break;
                default:
                    holder.vote.setText(String.valueOf(Math.round(post.vote *100)/100.0));
                    break;
            }
        }
        else
            holder.vote.setText(String.valueOf(Math.round(post.vote *100)/100.0));
        holder.rank.setText(String.valueOf(position+1));

        holder.name.setOnClickListener(view -> {
            if (ThreadLock.tryLock()) {
                try {
                    ArrayList <String> UidAutoreComment_Array=new ArrayList<>();
                    ArrayList <String> Text_Array=new ArrayList<>();
                    ArrayList <String> CommentTime_Array=new ArrayList<>();
                    Bundle b = new Bundle();
                    MonsterPost selectedPost= PostList.get(position);

                    b.putString("PostTime",selectedPost.getPostTime());
                    b.putString("UidAutore",selectedPost.getUidAutorePost());
                    Log.d("PostAdapter","Taglia: "+selectedPost.Monster.getTaglia());
                    b.putStringArrayList("monster",selectedPost.Monster.toArrayListString());
                    for(int i = 0; i<selectedPost.commenti.size(); i++)
                    {
                        UidAutoreComment_Array.add(selectedPost.commenti.get(i).getUidComment());
                        Text_Array.add(selectedPost.commenti.get(i).comment);
                        CommentTime_Array.add(selectedPost.commenti.get(i).getTimestamp());
                    }

                    b.putStringArrayList("UidAutoreCommentArray",UidAutoreComment_Array);
                    b.putStringArrayList("TextCommentArray",Text_Array);
                    b.putStringArrayList("CommentTimeArray",CommentTime_Array);
                    if(_parent.getArguments()!=null &&_parent.getArguments().containsKey("comment"))
                        b.putBoolean("comment",true);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    DetailMonsterPostFragment RecyclerFragment = new DetailMonsterPostFragment();
                    RecyclerFragment.setParent(_parent);
                    RecyclerFragment.setArguments(b);
                    if(_parent instanceof CommunityFragment) {
                        ((CommunityFragment) _parent).nascondiElementi();
                        activity.getSupportFragmentManager().beginTransaction().replace(_parent.getId(), RecyclerFragment).addToBackStack(null).commit();
                    }
                    else if(_parent instanceof DetailAuthorFragment) {
                        ((DetailAuthorFragment) _parent).nascondiElementi();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.authorFrameLayout, RecyclerFragment).addToBackStack(null).commit();
                    }
                }finally {
                    ThreadLock.unlock();
                }
            }


        });

        if(_parent instanceof DetailAuthorFragment )
            holder.authorButton.setVisibility(View.INVISIBLE);
        holder.authorButton.setOnClickListener(view -> {
           String UidAutore=PostList.get(holder.getAdapterPosition()).getUidAutorePost();
           Bundle b = new Bundle();
           b.putString("UidAutore",UidAutore);
            if(_parent instanceof CommunityFragment)
                ((CommunityFragment) _parent).nascondiElementi();
           AppCompatActivity activity = (AppCompatActivity) view.getContext();
           DetailAuthorFragment RecyclerFragment = new DetailAuthorFragment();
           RecyclerFragment.setArguments(b);

           activity.getSupportFragmentManager().beginTransaction().replace(_parent.getId(), RecyclerFragment).addToBackStack(null).commit();

        });
    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }


}

 class MonsterPostViewHolder extends RecyclerView.ViewHolder{
    TextView rank, vote, name;
    ImageButton authorButton;
    //CardView postCard;
    public MonsterPostViewHolder(@NonNull View itemView) {

        super(itemView);
        Log.d("PostViewHolder","View: "+itemView);
        rank = itemView.findViewById(R.id.TvRank);
        vote = itemView.findViewById(R.id.TvVote);
        name = itemView.findViewById(R.id.TvName);
        authorButton = itemView.findViewById(R.id.author_btn);
        //postCard = itemView.findViewById(R.id.PostCard);


    }




}


