package com.example.monsterfestival.fragment_dir;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monsterfestival.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonsterCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterCreationFragment extends Fragment {



    public MonsterCreationFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MonsterCreationFragment newInstance(String param1, String param2) {
        MonsterCreationFragment fragment = new MonsterCreationFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monster_creation, container, false);
    }
}