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
import com.example.monsterfestival.classes_dir.PartyPost;
import com.example.monsterfestival.fragment_dir.CommunityFragment;
import com.example.monsterfestival.fragment_dir.DetailAuthorFragment;
import com.example.monsterfestival.fragment_dir.DetailMonsterPostFragment;
import com.example.monsterfestival.fragment_dir.DetailPartyPostFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PartyPostAdapter extends RecyclerView.Adapter<PartyPostViewHolder> {

    private Fragment _parent;
    private final List<PartyPost> PostList;
    private final Lock ThreadLock = new ReentrantLock();
    //private OnNameClickListener listener;



    public PartyPostAdapter(ArrayList<PartyPost> dataList, Fragment parent) {
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
        PartyPost post = PostList.get(position);
        holder.name.setText(post.getNome());
        holder.vote.setText(String.valueOf(Math.round(post.vote *100)/100.0));
        holder.rank.setText(String.valueOf(position+1));

        holder.name.setOnClickListener(view -> {
            if (ThreadLock.tryLock()) {
                try {
                    ArrayList <String> UidAutoreComment_Array=new ArrayList<>();
                    ArrayList <String> Text_Array=new ArrayList<>();
                    ArrayList <String> CommentTime_Array=new ArrayList<>();

                    ArrayList<String> nomiEventi=new ArrayList<>();
                    ArrayList<String> causeEventi=new ArrayList<>();
                    ArrayList<String> reazioniEventi=new ArrayList<>();

                    Bundle b = new Bundle();
                    StringBuilder lista_eventi = new StringBuilder();
                    PartyPost selectedPost= PostList.get(position);



                    for (int i =0; i< selectedPost.getEventi().size();i++)
                    {
                        lista_eventi.append(selectedPost.getEventi().get(i).getNome()).append("\n"); //event.get(0) = nome evento
                        nomiEventi.add(selectedPost.getEventi().get(i).getNome());
                        causeEventi.add(selectedPost.getEventi().get(i).getCausa());
                        reazioniEventi.add(selectedPost.getEventi().get(i).getReazione());
                    }

                    for(int i = 0; i<selectedPost.commenti.size(); i++)
                    {
                        UidAutoreComment_Array.add(selectedPost.commenti.get(i).getUidComment());
                        Text_Array.add(selectedPost.commenti.get(i).comment);
                        CommentTime_Array.add(selectedPost.commenti.get(i).getTimestamp());
                    }

                    b.putString("PostTime",selectedPost.getPostTime());
                    b.putString("UidAutore",selectedPost.getUidAutorePost());
                    b.putString("Nome",selectedPost.getNome());
                    b.putString("ListaEventi",lista_eventi.toString());
                    b.putString("ListaMostri",selectedPost.getListaMostri());
                    b.putString("totCa",selectedPost.getTotCA());
                    b.putString("totSfida",selectedPost.getTotSfida());
                    b.putString("totPf",selectedPost.getTotPF());
                    b.putString("totFor",selectedPost.getTotFOR());
                    b.putString("totDes",selectedPost.getTotDES());
                    b.putString("totCost",selectedPost.getTotCOST());
                    b.putString("totInt",selectedPost.getTotINT());
                    b.putString("totSag",selectedPost.getTotSAG());
                    b.putString("totCar",selectedPost.getTotCAR());


                    b.putStringArrayList("nomiEventi",nomiEventi);
                    b.putStringArrayList("causeEventi",causeEventi);
                    b.putStringArrayList("reazioniEventi",reazioniEventi);

                    b.putStringArrayList("UidAutoreCommentArray",UidAutoreComment_Array);
                    b.putStringArrayList("TextCommentArray",Text_Array);
                    b.putStringArrayList("CommentTimeArray",CommentTime_Array);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    DetailPartyPostFragment RecyclerFragment = new DetailPartyPostFragment();
                    RecyclerFragment.setParent(_parent);
                    RecyclerFragment.setArguments(b);
                    if(_parent.getClass().equals(CommunityFragment.class)) {
                        ((CommunityFragment) _parent).nascondiElementi();
                        activity.getSupportFragmentManager().beginTransaction().replace(_parent.getId(), RecyclerFragment).addToBackStack(null).commit();
                    }
                    else if(_parent.getClass().equals(DetailAuthorFragment.class)) {
                        ((DetailAuthorFragment) _parent).nascondiElementi();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.authorFrameLayout, RecyclerFragment).addToBackStack(null).commit();
                    }
                }finally {
                    ThreadLock.unlock();
                }
            }


        });

        if(_parent.getClass().equals(DetailAuthorFragment.class))
            holder.authorButton.setVisibility(View.INVISIBLE);
        holder.authorButton.setOnClickListener(view -> {
           String UidAutore=PostList.get(holder.getAdapterPosition()).getUidAutorePost();
           Bundle b = new Bundle();
           b.putString("UidAutore",UidAutore);
            if(_parent.getClass().equals(CommunityFragment.class))
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

 class PartyPostViewHolder extends RecyclerView.ViewHolder{
    TextView rank, vote, name;
    ImageButton authorButton;
    //CardView postCard;
    public PartyPostViewHolder(@NonNull View itemView) {

        super(itemView);
        Log.d("PostViewHolder","View: "+itemView);
        rank = itemView.findViewById(R.id.TvRank);
        vote = itemView.findViewById(R.id.TvVote);
        name = itemView.findViewById(R.id.TvName);
        authorButton = itemView.findViewById(R.id.author_btn);
        //postCard = itemView.findViewById(R.id.PostCard);


    }




}


