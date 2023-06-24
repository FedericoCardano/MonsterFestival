package com.example.monsterfestival.fragment_dir;

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

import com.example.monsterfestival.classes_dir.Compare;
import com.example.monsterfestival.R;
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

    private final Object ThreadLock = new Object();

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
        editText.setOnClickListener(view -> creaSearchMonsters());

        imageView = rootView.findViewById(R.id.search_imageView);
        imageView.setOnClickListener(view -> creaSearchMonsters());

        partyCreationCard = rootView.findViewById(R.id.partyCreationCard);
        partyCreationCard.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                if (user.isAnonymous())
                    printMessage(getResources().getString(R.string.creazione_party));
                else {
                    creaPartyCreationFragment();
                }
            }
        });

        myPartiesCard = rootView.findViewById(R.id.myPartiesCard);
        myPartiesCard.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                if (user.isAnonymous())
                    printMessage(getResources().getString(R.string.miei_party));
                else {
                    creaMyPartiesFragment();
                }
            }
        });

        compareMonstersCard = rootView.findViewById(R.id.compareMonstersCard);
        compareMonstersCard.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                if (user.isAnonymous())
                    printMessage(getResources().getString(R.string.confronto_mostri));
                else {
                    creaCompareMonstersFragment();
                }
            }
        });

        comparePartiesCard = rootView.findViewById(R.id.comparePartiesCard);
        comparePartiesCard.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                if (user.isAnonymous())
                    printMessage(getResources().getString(R.string.confronto_party));
                else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.funzione_in_arrivo), Toast.LENGTH_SHORT).show();
                    //creaComparePartiesFragment();
                }
            }
        });

        setAllVisibility(true);
        return rootView;
    }

    void printMessage(String nome_opzione) {
        Toast.makeText(getActivity(), getResources().getString(R.string.errore_login) + " '" + nome_opzione + "'", Toast.LENGTH_SHORT).show();
    }

    void creaSearchMonsters() {
        synchronized (ThreadLock) {
            setAllVisibility(false);

            FrameLayout container = rootView.findViewById(R.id.frame_access_home);

            // Inizializza il Fragment
            SearchMonstersFragment myFragment = new SearchMonstersFragment();

            // Ottieni il FragmentManager e inizia la transazione
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Aggiunti il Fragment al Container View
            if (fragmentManager.findFragmentById(R.id.frame_access_home) != null)
                fragmentTransaction.replace(container.getId(), myFragment);
            else
                fragmentTransaction.add(container.getId(), myFragment);

            // Esegui la transazione
            fragmentTransaction.commit();
        }
    }

    void creaPartyCreationFragment() {
        synchronized (ThreadLock) {
            setAllVisibility(false);

            FrameLayout container = rootView.findViewById(R.id.frame_access_home);
            container.bringToFront();

            // Inizializza il Fragment
            PartyCreationFragment myFragment = new PartyCreationFragment();

            // Ottieni il FragmentManager e inizia la transazione
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Aggiunti il Fragment al Container View
            if (fragmentManager.findFragmentById(R.id.frame_access_home) != null)
                fragmentTransaction.replace(container.getId(), myFragment);
            else
                fragmentTransaction.add(container.getId(), myFragment);

            // Esegui la transazione
            fragmentTransaction.commit();
        }
    }

    void creaMyPartiesFragment() {
        synchronized (ThreadLock) {
            setAllVisibility(false);

            FrameLayout container = rootView.findViewById(R.id.frame_access_home);
            container.bringToFront();

            // Inizializza il Fragment
            MyPartiesFragment myFragment = new MyPartiesFragment();

            // Ottieni il FragmentManager e inizia la transazione
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Aggiunti il Fragment al Container View
            if (fragmentManager.findFragmentById(R.id.frame_access_home) != null)
                fragmentTransaction.replace(container.getId(), myFragment);
            else
                fragmentTransaction.add(container.getId(), myFragment);

            // Esegui la transazione
            fragmentTransaction.commit();
        }
    }

    void creaCompareMonstersFragment() {
        synchronized (ThreadLock) {
            setAllVisibility(false);

            final Compare compare = Compare.getCompare();
            compare.setMonster1();
            compare.setMonster2();

            FrameLayout container = rootView.findViewById(R.id.frame_access_home);
            container.bringToFront();

            // Inizializza il Fragment
            CompareMonstersFragment myFragment = new CompareMonstersFragment();

            // Ottieni il FragmentManager e inizia la transazione
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Aggiunti il Fragment al Container View
            if (fragmentManager.findFragmentById(R.id.frame_access_home) != null)
                fragmentTransaction.replace(container.getId(), myFragment);
            else
                fragmentTransaction.add(container.getId(), myFragment);

            // Esegui la transazione
            fragmentTransaction.commit();
        }
    }

    void creaComparePartiesFragment() {
        synchronized (ThreadLock) {
            setAllVisibility(false);

            FrameLayout container = rootView.findViewById(R.id.frame_access_home);
            container.bringToFront();

            // Inizializza il Fragment
            ComparePartiesFragment myFragment = new ComparePartiesFragment();

            // Ottieni il FragmentManager e inizia la transazione
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Aggiunti il Fragment al Container View
            if (fragmentManager.findFragmentById(R.id.frame_access_home) != null)
                fragmentTransaction.replace(container.getId(), myFragment);
            else
                fragmentTransaction.add(container.getId(), myFragment);

            // Esegui la transazione
            fragmentTransaction.commit();
        }
    }

    public void setAllVisibility(boolean value) {
        rootView.findViewById(R.id.constraintLayout).setVisibility(value ? View.VISIBLE : View.INVISIBLE);

        editText.setClickable(value);
        imageView.setClickable(value);
        compareMonstersCard.setClickable(value);
        myPartiesCard.setClickable(value);
        partyCreationCard.setClickable(value);
        comparePartiesCard.setClickable(value);
    }

}