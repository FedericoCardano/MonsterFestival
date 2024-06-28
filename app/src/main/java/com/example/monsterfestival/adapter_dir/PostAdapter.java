package com.example.monsterfestival.adapter_dir;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.MonsterPost;
import com.example.monsterfestival.classes_dir.OnNameClickListener;
import com.example.monsterfestival.fragment_dir.CommunityFragment;
import com.example.monsterfestival.fragment_dir.DetailMonsterFragment;
import com.example.monsterfestival.fragment_dir.DetailMonsterPostFragment;
import com.example.monsterfestival.fragment_dir.MyMonsterFragment;

import java.util.List;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PostAdapter  extends RecyclerView.Adapter<PostViewHolder> {

    private CommunityFragment _parent;
    private final List<MonsterPost> PostList;
    private final Lock ThreadLock = new ReentrantLock();
    //private OnNameClickListener listener;



    public PostAdapter(ArrayList<MonsterPost> dataList, CommunityFragment parent) {
        this._parent = parent;
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



        holder.name.setOnClickListener(view -> {
            if (ThreadLock.tryLock()) {
                try {
                    ArrayList <String> ID_Array=new ArrayList<>();
                    ArrayList <String> UidAutoreComment_Array=new ArrayList<>();
                    ArrayList <String> Text_Array=new ArrayList<>();
                    ArrayList <String> CommentTime_Array=new ArrayList<>();
                    Bundle b = new Bundle();
                    MonsterPost selectedPost= PostList.get(position);
                    b.putDouble("Vote",selectedPost.Vote);
                    b.putString("PostTime",selectedPost.getPostTime());
                    b.putString("UidAutore",selectedPost.getUidAutorePost());
                    Log.d("PostAdapter","Taglia: "+selectedPost.Monster.getTaglia());
                    b.putStringArrayList("monster",selectedPost.Monster.toArrayListString());
                    for(int i=0;i<selectedPost.Commenti.size();i++)
                    {
                        ID_Array.add(selectedPost.Commenti.get(i).getID());
                        UidAutoreComment_Array.add(selectedPost.Commenti.get(i).getUidAutoreComment());
                        Text_Array.add(selectedPost.Commenti.get(i).Text);
                        CommentTime_Array.add(selectedPost.Commenti.get(i).getCommentTime());
                    }
                    b.putStringArrayList("IdCommentArray",ID_Array);
                    b.putStringArrayList("UidAutoreCommentArray",UidAutoreComment_Array);
                    b.putStringArrayList("TextCommentArray",Text_Array);
                    b.putStringArrayList("CommentTimeArray",CommentTime_Array);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    DetailMonsterPostFragment RecyclerFragment = new DetailMonsterPostFragment();
                    RecyclerFragment.setParent(_parent);
                    RecyclerFragment.setArguments(b);
                    _parent.nascondiElementi();
                    activity.getSupportFragmentManager().beginTransaction().replace(_parent.getId(), RecyclerFragment).addToBackStack(null).commit();


                }finally {
                    ThreadLock.unlock();
                }
            }

        });
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


