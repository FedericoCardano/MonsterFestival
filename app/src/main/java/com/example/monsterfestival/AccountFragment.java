package com.example.monsterfestival;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.NotNull;

import java.util.Objects;


public class AccountFragment extends Fragment {

    View rootView;
    FirebaseAuth auth;
    Button button;
    Button changeEmail;
    Button changePsw;

    EditText editTextEmail;
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
            changeEmail= rootView.findViewById(R.id.changeEmailButton);
            changePsw= rootView.findViewById(R.id.changePswButton);
            textView.setText(user.getEmail());
            button.setText(getResources().getString(R.string.logout));
            editTextEmail = rootView.findViewById(R.id.new_email);
            changeEmail.setText(getResources().getString(R.string.cambia_email));
            changePsw.setText(getResources().getString(R.string.cambia_psw));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                button.setText(getResources().getString(R.string.login));
                mostraLogin();
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editTextEmail.getText().toString().isEmpty()) {
                    user.updateEmail(editTextEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), R.string.nuova_email_poup, Toast.LENGTH_SHORT).show();
                                    textView.setText(user.getEmail());
                                } else {
                                    Toast.makeText(getActivity(), R.string.cambia_email_fallito, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();//TODO eliminare
                                }
                            }
                        });
                }
            }
        });

        changePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.sendPasswordResetEmail(Objects.requireNonNull(user.getEmail()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(),R.string.nuova_psw_poup, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),R.string.cambia_psw_fallito, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();//TODO eliminare
                                }
                            }
                        });
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