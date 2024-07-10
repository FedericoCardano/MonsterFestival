package com.example.monsterfestival.fragment_dir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.adapter_dir.PartiesAdapter;
import com.example.monsterfestival.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyPartiesFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    RecyclerView recyclerView;
    ConstraintLayout myPartiesLayout;
    PartiesAdapter adapter;
    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_parties, container, false);
        myPartiesLayout = rootView.findViewById(R.id.appBarLayout);
        recyclerView = rootView.findViewById(R.id.rvPartyItems);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setMotionEventSplittingEnabled(false);

        adapter = new PartiesAdapter(getActivity(), this, requireActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
        adapter.updateCartItems(getListItems());

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
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.nome_my_parties));
        }
    }

    public void ripristinaVisibilitaElementi() {myPartiesLayout.setVisibility(View.VISIBLE);    }
    public void nascondiElementi() { myPartiesLayout.setVisibility(View.INVISIBLE);    }

    private ArrayList<String> getListItems() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

        ArrayList<String> nomeParty = new ArrayList<>(objectNativeLib.getPartyNames());
        Log.d("ADebugTag", "Value: " + objectNativeLib.getPartyNames().size());
        return nomeParty;
    }

    public PartiesAdapter getAdapter() {
        return adapter;
    }

}