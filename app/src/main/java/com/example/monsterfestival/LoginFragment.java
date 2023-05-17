package com.example.monsterfestival;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsterfestival.R;
import com.example.monsterfestival.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = view.findViewById(R.id.login_email);
        editTextPassword = view.findViewById(R.id.login_password);
        buttonLogin = view.findViewById(R.id.login_button);
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.registerRedirectText);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof WelcomeActivity) {
                    WelcomeActivity activity = (WelcomeActivity) getActivity();
                    if (activity != null)
                        activity.mostraRegister();
                }
                else {
                    AccountFragment activity = (AccountFragment) getParentFragment();
                    if (activity != null)
                        activity.mostraRegister();
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(), "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(), "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), getResources().getString(R.string.login_riuscito), Toast.LENGTH_SHORT).show();

                                    if (getActivity() instanceof WelcomeActivity) {
                                        WelcomeActivity activity = (WelcomeActivity) getActivity();
                                        if (activity != null)
                                            activity.mostraRegister();
                                    }
                                    else {
                                        AccountFragment activity = (AccountFragment) getParentFragment();
                                        if (activity != null)
                                            activity.mostraRegister();
                                    }

                                } else {
                                    Toast.makeText(getActivity(), getResources().getString(R.string.login_fallito), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        return view;
    }
}
