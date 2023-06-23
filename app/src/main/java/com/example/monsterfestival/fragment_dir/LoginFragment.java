package com.example.monsterfestival.fragment_dir;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsterfestival.R;
import com.example.monsterfestival.activity_dir.MainActivity;
import com.example.monsterfestival.activity_dir.WelcomeActivity;
import com.example.monsterfestival.fragment_dir.AccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView, passwordDimenticata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = view.findViewById(R.id.login_email);
        editTextPassword = view.findViewById(R.id.login_password);
        buttonLogin = view.findViewById(R.id.login_button);
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.registerRedirectText);
        passwordDimenticata = view.findViewById(R.id.passwordDimenticata);

        textView.setOnClickListener(view12 -> {
            if (getActivity() instanceof WelcomeActivity) {
                WelcomeActivity activity = (WelcomeActivity) getActivity();
                if (activity != null)
                    activity.mostraRegister();
            }
            else {
                AccountFragment fragment = (AccountFragment) getParentFragment();
                if (fragment != null)
                    fragment.mostraRegister();
            }
        });

        passwordDimenticata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email;
                email = String.valueOf(editTextEmail.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(), "Inserisci email", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Email inviata", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        buttonLogin.setOnClickListener(view1 -> {
            progressBar.setVisibility(View.VISIBLE);
            String email, password;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            if (TextUtils.isEmpty(email)){
                Toast.makeText(getActivity(), "Inserisci email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)){
                Toast.makeText(getActivity(), "Inserisci password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.login_riuscito), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.login_fallito), Toast.LENGTH_SHORT).show();
                        }
                    });

        });


        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        return view;
    }
}
