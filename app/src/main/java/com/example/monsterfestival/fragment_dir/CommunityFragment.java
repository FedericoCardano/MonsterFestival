package com.example.monsterfestival.fragment_dir;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monsterfestival.classes_dir.MonsterPost;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class CommunityFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        DatabaseReference db = FirebaseDatabase.getInstance().getReference();


        Query topPost = db.child("Posts").orderByChild("Vote").limitToLast(3);
        topPost.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                   Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    ArrayList<MonsterPost> Posts= new ArrayList<>();
                    Log.d("QueryFecthing","inizio for di scomposizione");
                    for(DataSnapshot child : task.getResult().getChildren()) {
                        Log.d("QueryFecthing",String.valueOf(child));
                        Posts.add(new MonsterPost(child));
                    }
                    Log.d("QueryFecthing","fine for di scomposizione");

                    for (int i=Posts.size()-1;i>=0;i--) {
                        Log.d("Query-result", "Vote: "+Posts.get(i).Vote+"Nome: "+Posts.get(i).Monster.getNome());

                    }
                }
            }
        });

        return inflater.inflate(R.layout.fragment_comunity, container, false);
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
        if (fragmentVisibleListener != null) {
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.nome_compare_parties));
        }
    }

    public void ripristinaVisibilitaElementi() {

    }
}