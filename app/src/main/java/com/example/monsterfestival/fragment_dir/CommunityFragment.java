package com.example.monsterfestival.fragment_dir;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.monsterfestival.adapter_dir.MonsterPostAdapter;
import com.example.monsterfestival.classes_dir.MonsterPost;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CommunityFragment extends Fragment implements OnFragmentRemoveListener {

    static MonsterPostAdapter adapter;
    static RecyclerView recyclerView;
    static Spinner orderSpinner;
    public static AlertDialog dialog;
    static LinearLayout CommunityLayout;
    public final Lock ThreadLock = new ReentrantLock();
    ArrayList<MonsterPost> Posts = new ArrayList<MonsterPost>();
    OnFragmentVisibleListener fragmentVisibleListener;
    ArrayList<String> order = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order.add("Voto Totale");
        order.add("Coerenza");
        order.add("Originalità");
        order.add("Bilanciamento");
        order.add("Pubblicazione");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        recyclerView = view.findViewById(R.id.PostRank);
        orderSpinner = view.findViewById(R.id.spinner);

        ArrayAdapter<String> adapterSpinner=
                new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,order);
        adapterSpinner.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        orderSpinner.setAdapter(adapterSpinner);
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                update(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                update(0);
            }
        });

        update(0);
        CommunityLayout = view.findViewById(R.id.communityLayout);
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

        if (fragmentVisibleListener != null) {
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.commuity));
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void ripristinaVisibilitaElementi() {
        update(0);
    }
    public void nascondiElementi() {
        CommunityLayout.setVisibility(View.INVISIBLE);
    }

    public void update(int order){
        ArrayList<String> orderList = new ArrayList<>();
        orderList.add("vote");
        orderList.add("voteCoerenza");
        orderList.add("voteOriginalita");
        orderList.add("voteBilanciamento");
        orderList.add("PostTime");
        Log.d("firebase", "firebase start");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query topPost = db.child("Posts").orderByChild(orderList.get(order));
        Log.d("firebase", "firebase ready");

        topPost.get().addOnCompleteListener(task -> {
            //if (ThreadLock.tryLock()) {
            // try {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {

                Log.d("QueryFecthing", "inizio for di scomposizione");
                Posts.clear();
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
                    Log.d("Query-result", "Vote: " + Posts.get(i).vote + "Nome: " + Posts.get(i).Monster.getNome() + " Index: " + i);
                }
                Log.d("Query-Result","set adapter");

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                recyclerView.setLayoutManager(gridLayoutManager);
                adapter = new MonsterPostAdapter(Posts, this);
                recyclerView.setAdapter(adapter);
                CommunityLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}