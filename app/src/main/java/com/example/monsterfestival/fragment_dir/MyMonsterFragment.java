package com.example.monsterfestival.fragment_dir;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.adapter_dir.MyMonstersAdapter;
import com.example.monsterfestival.adapter_dir.PartiesAdapter;
import com.example.monsterfestival.classes_dir.MonsterClass;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class MyMonsterFragment extends Fragment implements OnFragmentRemoveListener
{
    OnFragmentVisibleListener fragmentVisibleListener;
    LinearLayout myMonstersLayout;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    MyMonstersAdapter adapter;
    ArrayList<MonsterClass> dataList=new ArrayList<>();
    View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_monters, container, false);

        myMonstersLayout=rootView.findViewById(R.id.myMonstersLayout);
        progressBar = rootView.findViewById(R.id.progressBarTemp);
        recyclerView = rootView.findViewById(R.id.rvMyMonsterItems);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setMotionEventSplittingEnabled(false);

        adapter = new MyMonstersAdapter(getActivity(), this, requireActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
        adapter.updateCartItems(getListItems());
        progressBar.setVisibility(View.GONE);

        rootView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));

        return rootView;
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

            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.my_monsters));
        }
    }

    public void ripristinaVisibilitaElementi() {       myMonstersLayout.setVisibility(View.VISIBLE);    }

    public void nascondiElementi() {        myMonstersLayout.setVisibility(View.INVISIBLE);    }

    private ArrayList<String> getListItems() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

        ArrayList<String> nomeMostri = new ArrayList<>(objectNativeLib.getMyMonstersNames());
        Log.d("ADebugTag", "Value: " + objectNativeLib.getMyMonstersNames().size());
        return nomeMostri;
    }

    public MyMonstersAdapter getAdapter() {
        return adapter;
    }


}

