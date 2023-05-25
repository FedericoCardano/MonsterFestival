package com.example.monsterfestival;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    CardView partyCreationCard, myPartiesCard, compareMonstersCard, comparePartiesCard;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView textView;
    EditText editText;
    ImageView imageView;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        auth = FirebaseAuth.getInstance();
        textView = rootView.findViewById(R.id.textUsername);

        user = auth.getCurrentUser();
        if (user != null) {
            if (user.isAnonymous())
                textView.setText(getResources().getString(R.string.default_utente));
            else
                textView.setText(user.getDisplayName());
        }

        editText = rootView.findViewById(R.id.search_editText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaSearchMonsters();
            }
        });

        imageView = rootView.findViewById(R.id.search_imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaSearchMonsters();
            }
        });

        partyCreationCard = rootView.findViewById(R.id.partyCreationCard);
        partyCreationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { creaPartyCreationFragment(); }
        });

        myPartiesCard = rootView.findViewById(R.id.myPartiesCard);
        myPartiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    if (user.isAnonymous())
                        printMessage(getResources().getString(R.string.miei_party));
                    else {
                        creaMyPartiesFragment();
                    }
                }
            }
        });

        compareMonstersCard = rootView.findViewById(R.id.compareMonstersCard);
        compareMonstersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    if (user.isAnonymous())
                        printMessage(getResources().getString(R.string.confronto_mostri));
                    else {
                        creaCompareMonstersFragment();
                    }
                }
            }
        });

        comparePartiesCard = rootView.findViewById(R.id.comparePartiesCard);
        comparePartiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    if (user.isAnonymous())
                        printMessage(getResources().getString(R.string.confronto_party));
                    else {
                        creaComparePartiesFragment();
                    }
                }
            }
        });

        return rootView;
    }

    void printMessage(String nome_opzione) {
        Toast.makeText(getActivity(), getResources().getString(R.string.errore_login) + " '" + nome_opzione + "'", Toast.LENGTH_SHORT).show();
    }

    void creaSearchMonsters() {
        FrameLayout container = rootView.findViewById(R.id.frame_access_home);

        // Inizializza il Fragment
        SearchMonstersFragment myFragment = new SearchMonstersFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Aggiunti il Fragment al Container View
        fragmentTransaction.add(container.getId(), myFragment);

        // Esegui la transazione
        fragmentTransaction.commit();
    }

    void creaPartyCreationFragment() {
        FrameLayout container = rootView.findViewById(R.id.frame_access_home);
        container.bringToFront();

        // Inizializza il Fragment
        PartyCreationFragment myFragment = new PartyCreationFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Aggiunti il Fragment al Container View
        fragmentTransaction.add(container.getId(), myFragment);

        // Esegui la transazione
        fragmentTransaction.commit();
    }

    void creaMyPartiesFragment() {
        FrameLayout container = rootView.findViewById(R.id.frame_access_home);
        container.bringToFront();

        // Inizializza il Fragment
        MyPartiesFragment myFragment = new MyPartiesFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Aggiunti il Fragment al Container View
        fragmentTransaction.add(container.getId(), myFragment);

        // Esegui la transazione
        fragmentTransaction.commit();
    }

    void creaCompareMonstersFragment() {
        FrameLayout container = rootView.findViewById(R.id.frame_access_home);
        container.bringToFront();

        // Inizializza il Fragment
        CompareMonstersFragment myFragment = new CompareMonstersFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Aggiunti il Fragment al Container View
        fragmentTransaction.add(container.getId(), myFragment);

        // Esegui la transazione
        fragmentTransaction.commit();
    }

    void creaComparePartiesFragment() {
        FrameLayout container = rootView.findViewById(R.id.frame_access_home);
        container.bringToFront();

        // Inizializza il Fragment
        ComparePartiesFragment myFragment = new ComparePartiesFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Aggiunti il Fragment al Container View
        fragmentTransaction.add(container.getId(), myFragment);

        // Esegui la transazione
        fragmentTransaction.commit();
    }

}