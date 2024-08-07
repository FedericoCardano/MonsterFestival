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

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class HomeFragment extends Fragment {

    CardView partyCreationCard, myPartiesCard, compareMonstersCard, comparePartiesCard,createMonstersCard,myMonstersCard;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView textView;
    EditText editText;
    ImageView imageView;
    View rootView;

    private final Lock ThreadLock = new ReentrantLock();

    private boolean isOnClickListenerEnabled = true;

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
        editText.setOnClickListener(view -> creaFragment(0));

        imageView = rootView.findViewById(R.id.search_imageView);
        imageView.setOnClickListener(view -> creaFragment(0));

        partyCreationCard = rootView.findViewById(R.id.partyCreationCard);
        partyCreationCard.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null || user.isAnonymous())
                printMessage(getResources().getString(R.string.creazione_party));
            else {
                creaFragment(1);
            }
        });

        myPartiesCard = rootView.findViewById(R.id.myPartiesCard);
        myPartiesCard.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null || user.isAnonymous())
                printMessage(getResources().getString(R.string.miei_party));
            else {
                creaFragment(2);
            }
        });

        compareMonstersCard = rootView.findViewById(R.id.compareMonstersCard);
        compareMonstersCard.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null || user.isAnonymous())
                printMessage(getResources().getString(R.string.confronto_mostri));
            else {
                creaFragment(3);
            }
        });

        comparePartiesCard = rootView.findViewById(R.id.comparePartiesCard);
        comparePartiesCard.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null || user.isAnonymous())
                printMessage(getResources().getString(R.string.commuity));
            else {
                //Toast.makeText(getActivity(), getResources().getString(R.string.funzione_in_arrivo), Toast.LENGTH_SHORT).show();
                creaFragment(4);
            }
        });

        createMonstersCard= rootView.findViewById(R.id.createMonstersCard);
        createMonstersCard.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null || user.isAnonymous())
                printMessage(getResources().getString(R.string.creazione_mostri));
            else {
                //Toast.makeText(getActivity(), getResources().getString(R.string.funzione_in_arrivo), Toast.LENGTH_SHORT).show();
                creaFragment(5);
            }
        });

        myMonstersCard= rootView.findViewById(R.id.myMonstersCard);
        myMonstersCard.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null || user.isAnonymous())
                printMessage(getResources().getString(R.string.i_miei_mostri));
            else {
                //Toast.makeText(getActivity(), getResources().getString(R.string.funzione_in_arrivo), Toast.LENGTH_SHORT).show();
                creaFragment(6);
            }
        });

        setAllVisibility(true);
        return rootView;
    }

    void printMessage(String nome_opzione) {
        Toast.makeText(getActivity(), getResources().getString(R.string.errore_login) + " '" + nome_opzione + "'", Toast.LENGTH_SHORT).show();
    }

    void creaFragment(int fragmentID) {
        if (ThreadLock.tryLock()) {
            try {
                if (isOnClickListenerEnabled)
                    switch (fragmentID)
                    {
                        case 0:
                            creaSearchMonsters();
                            break;

                        case 1:
                            creaPartyCreationFragment();
                            break;

                        case 2:
                            creaMyPartiesFragment();
                            break;

                        case 3:
                            creaCompareMonstersFragment();
                            break;

                        case 4:
                            creaComunityFragment();
                            break;
                        case 5:
                            creaMonsterCreationFragment();
                            break;
                        case 6:
                            creaMyMonsterFragment();
                            break;

                        default:
                            break;
                    }
            } finally {
                ThreadLock.unlock();
            }
        }
    }

    void creaSearchMonsters() {
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
        fragmentTransaction.commitNow();
    }

    void creaPartyCreationFragment() {
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
        fragmentTransaction.commitNow();
    }



    void creaMyPartiesFragment() {
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
        fragmentTransaction.commitNow();
    }

    void creaCompareMonstersFragment() {
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
        fragmentTransaction.commitNow();
    }

    void creaComunityFragment() {
        setAllVisibility(false);

        FrameLayout container = rootView.findViewById(R.id.frame_access_home);
        container.bringToFront();

        // Inizializza il Fragment
        CommunityFragment myFragment = new CommunityFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Aggiunti il Fragment al Container View
        if (fragmentManager.findFragmentById(R.id.frame_access_home) != null)
            fragmentTransaction.replace(container.getId(), myFragment);
        else
            fragmentTransaction.add(container.getId(), myFragment);

        // Esegui la transazione
        fragmentTransaction.commitNow();
    }

    void creaMonsterCreationFragment() {
        setAllVisibility(false);

        FrameLayout container = rootView.findViewById(R.id.frame_access_home);
        container.bringToFront();

        // Inizializza il Fragment
        MonsterCreationFragment myFragment = new MonsterCreationFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Aggiunti il Fragment al Container View
        if (fragmentManager.findFragmentById(R.id.frame_access_home) != null)
            fragmentTransaction.replace(container.getId(), myFragment);
        else
            fragmentTransaction.add(container.getId(), myFragment);

        // Esegui la transazione
        fragmentTransaction.commitNow();
    }
    void creaMyMonsterFragment() {
        setAllVisibility(false);

        FrameLayout container = rootView.findViewById(R.id.frame_access_home);
        container.bringToFront();

        // Inizializza il Fragment
        MyMonsterFragment myFragment = new MyMonsterFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Aggiunti il Fragment al Container View
        if (fragmentManager.findFragmentById(R.id.frame_access_home) != null)
            fragmentTransaction.replace(container.getId(), myFragment);
        else
            fragmentTransaction.add(container.getId(), myFragment);

        // Esegui la transazione
        fragmentTransaction.commitNow();
    }

    public void setAllVisibility(boolean value) {
        rootView.findViewById(R.id.constraintLayout).setVisibility(value ? View.VISIBLE : View.INVISIBLE);

        isOnClickListenerEnabled = value;
        editText.setClickable(value);
        imageView.setClickable(value);
        compareMonstersCard.setClickable(value);
        myPartiesCard.setClickable(value);
        partyCreationCard.setClickable(value);
        comparePartiesCard.setClickable(value);
    }

}