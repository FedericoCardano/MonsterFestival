package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;

import com.example.customsearchlibrary.NativeLib;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyPartiesFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    RecyclerView recyclerView;
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

        recyclerView = rootView.findViewById(R.id.rvPartyItems);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        final PartiesAdapter adapter = new PartiesAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        adapter.updateCartItems(getListItems());

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

    public void ripristinaVisibilitaElementi() {

    }

    private ArrayList<String> getListItems() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        NativeLib objectNativeLib = new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class);

        ArrayList<String> nomeParty = new ArrayList<>(objectNativeLib.getPartyNames());
        Log.d("ADebugTag", "Value: " + objectNativeLib.getPartyNames().size());
        return nomeParty;
    }

}