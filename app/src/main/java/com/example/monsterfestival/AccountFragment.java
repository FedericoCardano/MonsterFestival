package com.example.monsterfestival;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AccountFragment extends Fragment {

    View rootView;
    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;

    RegisterFragment FragmentR;
    LoginFragment FragmentL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        auth = FirebaseAuth.getInstance();
        button = rootView.findViewById(R.id.logout);
        textView = rootView.findViewById(R.id.user_details);

        user = auth.getCurrentUser();
        if (user == null){
            button.setText(getResources().getString(R.string.login));
            mostraLogin();
        }
        else{
            textView.setText(user.getEmail());
            button.setText(getResources().getString(R.string.logout));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                button.setText(getResources().getString(R.string.login));
                mostraLogin();
            }
        });

        return rootView;
    }

    public void mostraLogin() {
        FrameLayout container = rootView.findViewById(R.id.frame_access_account);

        // Inizializza il Fragment
        FragmentL = new LoginFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (getChildFragmentManager().findFragmentById(R.id.frame_access_account) != null)
            fragmentTransaction.remove(FragmentR);

        // Aggiunti il Fragment al Container View
        fragmentTransaction.add(container.getId(), FragmentL);

        // Esegui la transazione
        fragmentTransaction.commit();
    }

    public void mostraRegister() {
        FrameLayout container = rootView.findViewById(R.id.frame_access_account);

        // Inizializza il Fragment
        FragmentR = new RegisterFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (getChildFragmentManager().findFragmentById(R.id.frame_access_account) != null)
            fragmentTransaction.remove(FragmentL);

        // Aggiunti il Fragment al Container View
        fragmentTransaction.add(container.getId(), FragmentR);

        // Esegui la transazione
        fragmentTransaction.commit();
    }
}