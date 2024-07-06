package com.example.monsterfestival.fragment_dir;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monsterfestival.R;
import com.example.monsterfestival.adapter_dir.MonsterPostAdapter;
import com.example.monsterfestival.classes_dir.MonsterPost;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class DetailAuthorFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;
    TextView tvNomeAutore,tvDataCreazione,tvNPosts,tvNCommenti,tvNVoti;
    RecyclerView monsterPostRw;
    ArrayList<MonsterPost> monsterPosts = new ArrayList<>();
    MonsterPostAdapter MonsterPostAdapter;
    LinearLayout authorLayout;
    CardView authorCard;
    Fragment _parent;
    String UidAutore;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_author, container, false);
        Bundle bundle = this.getArguments();
        tvNomeAutore = view.findViewById(R.id.tvNomeAutore);
        tvDataCreazione = view.findViewById(R.id.tvDataCreazione);
        tvNPosts = view.findViewById(R.id.tvNPosts);
        tvNCommenti = view.findViewById(R.id.tvNCommenti);
        tvNVoti = view.findViewById(R.id.tvNVoti);
        monsterPostRw = view.findViewById(R.id.PostRank);

        authorLayout = view.findViewById(R.id.authorLayout);
        authorCard = view.findViewById(R.id.cvAutore);


        UidAutore = bundle.getString("UidAutore");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UsersInformation").child(UidAutore);
        ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tvNomeAutore.setText(task.getResult().child("username").getValue(String.class));
                tvNCommenti.setText(task.getResult().child("nCommenti").getValue(String.class));
                tvNPosts.setText(task.getResult().child("nPosts").getValue(String.class));
                tvNVoti.setText(task.getResult().child("nVoti").getValue(String.class));
                //tvDataCreazione.setText(task.getResult().child("dataCreazione").getValue(String.class));
                DatabaseReference refMonsterPost = FirebaseDatabase.getInstance().getReference("Posts");
                refMonsterPost.orderByChild("UidAutorePost").equalTo(UidAutore).get().addOnCompleteListener(task1 -> {
                   if (task1.isSuccessful()) {
                       monsterPosts.clear();
                       for (DataSnapshot child : task1.getResult().getChildren()) {
                           monsterPosts.add(new MonsterPost(child));
                           Log.d("DetailAuthorFragment","Post: "+child.getValue());
                       }
                        Log.d("DetailAuthorFragment","PostList: "+monsterPosts);
                       GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                       monsterPostRw.setLayoutManager(gridLayoutManager);
                       MonsterPostAdapter = new MonsterPostAdapter(monsterPosts, this);
                       monsterPostRw.setAdapter(MonsterPostAdapter);

                   }
                   else {
                       //TODO messaggio errore
                   }
                });

            }
            else {
                //TODO messaggio errore
            }

        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fragmentVisibleListener != null) {
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.dettagli_utente));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentVisibleListener) {
            fragmentVisibleListener = (OnFragmentVisibleListener) context;
        }
    }

    @Override
    public void ripristinaVisibilitaElementi() {
        authorLayout.setVisibility(View.VISIBLE);
    }

    public void setParent(Fragment parent) {
        this._parent = parent;
    }

    public void nascondiElementi() {
        authorLayout.setVisibility(View.INVISIBLE);
    }
}