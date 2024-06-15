package com.example.monsterfestival.fragment_dir;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monsterfestival.adapter_dir.PostAdapter;
import com.example.monsterfestival.classes_dir.MonsterPost;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.classes_dir.OnNameClickListener;
import com.example.monsterfestival.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CommunityFragment extends Fragment implements OnFragmentRemoveListener, OnNameClickListener {


    static PostAdapter adapter;
    static RecyclerView recyclerView;
    final CommunityFragment myself=this;
    public static AlertDialog dialog;
    public final Lock ThreadLock = new ReentrantLock();
    ArrayList<MonsterPost> Posts = new ArrayList<MonsterPost>();
    public interface onNameClickListener {
        void onNameClick(MonsterPost item);
    }

    OnFragmentVisibleListener fragmentVisibleListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onNameClick(MonsterPost post) {

        Bundle bundle = new Bundle();
        // Compilazione del bundle
        bundle.putString("ID", post.Monster.getID());
        bundle.putString("Nome", post.Monster.getNome());
        bundle.putString("Descrizione", post.Monster.getDescrizione());
        bundle.putString("Ambiente", post.Monster.getAmbiente());
        bundle.putString("Categoria",post.Monster.getCategoria());
        bundle.putString("Taglia", post.Monster.getTaglia());
        bundle.putString("Sfida", post.Monster.getSfida());
        bundle.putString("PF", post.Monster.getPf());
        bundle.putString("CA", post.Monster.getCa());
        bundle.putString("FOR", post.Monster.getFor());
        bundle.putString("DES", post.Monster.getDes());
        bundle.putString("COST", post.Monster.getCost());
        bundle.putString("INT", post.Monster.getInt());
        bundle.putString("SAG", post.Monster.getSag());
        bundle.putString("CAR", post.Monster.getCar());

        AppCompatActivity activity = (AppCompatActivity)this.getActivity();
        DetailMonsterFragment RecyclerFragment = new DetailMonsterFragment();
        //RecyclerFragment.setParent(_parent);
        RecyclerFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(this.getId(), RecyclerFragment).addToBackStack(null).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        // Inflate the layout for this fragment
        Log.d("firebase", "firebase start");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query topPost = db.child("Posts").orderByChild("Vote").limitToLast(3);
        Log.d("firebase", "firebase ready");

        topPost.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                //if (ThreadLock.tryLock()) {
                   // try {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        } else {

                            Log.d("QueryFecthing", "inizio for di scomposizione");
                            for (DataSnapshot child : task.getResult().getChildren()) {
                                Log.d("QueryFecthing", String.valueOf(child));
                                Posts.add(new MonsterPost(child));
                            }
                            Log.d("QueryFecthing", "riordino Posts");
                            ArrayList<MonsterPost> temp = new ArrayList<>();
                            for (int i = Posts.size() - 1; i >= 0; i--) {
                                temp.add(Posts.get(i));
                            }
                            Posts = temp;

                            Log.d("QueryFecthing", "fine for di scomposizione");

                            for (int i = 0; i < Posts.size(); i++) {
                                Log.d("Query-result", "Vote: " + Posts.get(i).Vote + "Nome: " + Posts.get(i).Monster.getNome() + " Index: " + i);
                            }
                            Log.d("Query-Result","set adapter");
                            recyclerView = view.findViewById(R.id.PostRank);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            adapter = new PostAdapter(Posts, myself);
                            recyclerView.setAdapter(adapter);

                        }
                   // }finally {
                   //     ThreadLock.unlock();
                   // }
               // }
            }
        });

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentVisibleListener) {
            fragmentVisibleListener = (OnFragmentVisibleListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setCancelable(false);
        //builder.setView(R.layout.recyclerview_post_item);
        //dialog = builder.create();
        //dialog.show();

        if (fragmentVisibleListener != null) {
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.commuity));
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
    public void ripristinaVisibilitaElementi() {   }


}