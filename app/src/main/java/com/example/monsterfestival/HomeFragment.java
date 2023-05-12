package com.example.monsterfestival;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    CardView partyCreationCard, myPartiesCard, compareMonstersCard, comparePartiesCard;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView textView;
    EditText editText;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        auth = FirebaseAuth.getInstance();
        textView = view.findViewById(R.id.textUsername);

        user = auth.getCurrentUser();
        textView.setText(user.getDisplayName());

        editText = view.findViewById(R.id.search_editText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchMonstersActivity.class);
                startActivity(intent);
            }
        });

        imageView = view.findViewById(R.id.search_imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchMonstersActivity.class);
                startActivity(intent);
            }
        });

        partyCreationCard = view.findViewById(R.id.partyCreationCard);
        partyCreationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PartyCreationActivity.class);
                startActivity(intent);
            }
        });

        myPartiesCard = view.findViewById(R.id.myPartiesCard);
        myPartiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FirebaseAuth.getInstance().getCurrentUser().isAnonymous()){
                    Intent intent = new Intent(getActivity(), MyPartiesActivity.class);
                    startActivity(intent);
                }
            }
        });

        compareMonstersCard = view.findViewById(R.id.compareMonstersCard);
        compareMonstersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FirebaseAuth.getInstance().getCurrentUser().isAnonymous()) {
                    Intent intent = new Intent(getActivity(), CompareMonstersActivity.class);
                    startActivity(intent);
                }
            }
        });

        comparePartiesCard = view.findViewById(R.id.comparePartiesCard);
        comparePartiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FirebaseAuth.getInstance().getCurrentUser().isAnonymous()) {
                    Intent intent = new Intent(getActivity(), ComparePartiesActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

}