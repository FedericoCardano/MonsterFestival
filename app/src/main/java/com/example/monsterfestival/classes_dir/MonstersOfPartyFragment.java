package com.example.monsterfestival.classes_dir;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monsterfestival.R;

public class MonstersOfPartyFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    RecyclerView recyclerView;
    View rootView;

    public static MonstersOfPartyFragment newInstance(String param1, String param2) {
        MonstersOfPartyFragment fragment = new MonstersOfPartyFragment();
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
        rootView = inflater.inflate(R.layout.fragment_monsters_of_party, container, false);

        recyclerView = rootView.findViewById(R.id.rvMonstersOfParty);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

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
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, "Dettagli Party");
        }
    }

    public void ripristinaVisibilitaElementi() {

    }
}