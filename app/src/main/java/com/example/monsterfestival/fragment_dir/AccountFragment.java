package com.example.monsterfestival.fragment_dir;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsterfestival.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.NotNull;

import java.util.Objects;


public class AccountFragment extends Fragment {

    View rootView;
    FirebaseAuth auth;
    Button button;
    Button changeEmail;
    Button changePsw;
    Button delateAccount;

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

        changeEmail= rootView.findViewById(R.id.changeEmailButton);
        changePsw= rootView.findViewById(R.id.changePswButton);
        editTextEmail = rootView.findViewById(R.id.new_email);
        delateAccount = rootView.findViewById(R.id.delate_account);

        user = auth.getCurrentUser();
        if (user == null){
            button.setText(getResources().getString(R.string.login));
            mostraLogin();
        }
        else{

            changeEmail.setText(getResources().getString(R.string.cambia_email));
            textView.setText(user.getEmail());
            button.setText(getResources().getString(R.string.logout));
            changePsw.setText(getResources().getString(R.string.cambia_psw));
            delateAccount.setText(getResources().getString(R.string.cancella_account));

            changeEmail.setVisibility(View.VISIBLE);
            changePsw.setVisibility(View.VISIBLE);
            delateAccount.setVisibility(View.VISIBLE);
            editTextEmail.setVisibility(View.VISIBLE);
        }

        button.setOnClickListener(view -> {
            auth.signOut();
            button.setText(getResources().getString(R.string.login));
            mostraLogin();
        });

        changeEmail.setOnClickListener(v -> {

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
                                if(Objects.requireNonNull(task.getException()).getClass().equals(FirebaseAuthRecentLoginRequiredException.class))
                                {
                                    auth.signOut();
                                    button.setText(getResources().getString(R.string.login));
                                    mostraLogin();
                                }
                                else {
                                    Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
            }
        });

        changePsw.setOnClickListener(v -> auth.sendPasswordResetEmail(Objects.requireNonNull(user.getEmail()))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(),R.string.nuova_psw_poup, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),R.string.cambia_psw_fallito, Toast.LENGTH_SHORT).show();

                        if(Objects.requireNonNull(task.getException()).getClass().equals(FirebaseAuthRecentLoginRequiredException.class))
                        {
                            auth.signOut();
                            button.setText(getResources().getString(R.string.login));
                            mostraLogin();
                        }
                        else {
                            Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
        delateAccount.setOnClickListener(v -> user.delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(),R.string.account_cancellato, Toast.LENGTH_SHORT).show();
                        mostraRegister();
                    }
                    else {
                        Toast.makeText(getActivity(), R.string.cambia_email_fallito, Toast.LENGTH_SHORT).show();
                        if(Objects.requireNonNull(task.getException()).getClass().equals(FirebaseAuthRecentLoginRequiredException.class))
                        {
                            auth.signOut();
                            button.setText(getResources().getString(R.string.login));

                            mostraLogin();
                        }
                        else {
                            Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

        return rootView;
    }

    public void mostraLogin() {

        changeEmail.setVisibility(View.INVISIBLE);
        changePsw.setVisibility(View.INVISIBLE);
        editTextEmail.setVisibility(View.INVISIBLE);
        delateAccount.setVisibility(View.INVISIBLE);

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

        changeEmail.setVisibility(View.INVISIBLE);
        changePsw.setVisibility(View.INVISIBLE);
        editTextEmail.setVisibility(View.INVISIBLE);
        delateAccount.setVisibility(View.INVISIBLE);
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