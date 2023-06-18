package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customsearchlibrary.NativeLib;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPartiesFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    RecyclerView recyclerView;
    View rootView;
    DatabaseReference reference;
    ArrayList<String> prova;



    private NativeLib objectNativeLib;

    public static MyPartiesFragment newInstance(String param1, String param2) {
        MyPartiesFragment fragment = new MyPartiesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        String temp = sharedPreferences.getString("objectNativeLib", "");
        NativeLib objectNativeLib;
        if (temp.equals(""))
            objectNativeLib = new NativeLib();
        else
            objectNativeLib = new Gson().fromJson(temp, NativeLib.class);
        ArrayList<String> nomeParty = new ArrayList<>();

        for (String element : objectNativeLib.getPartyNames())
            nomeParty.add(element);
        Log.d("ADebugTag", "Value: " + objectNativeLib.getPartyNames().size());
        return nomeParty;

    }

}