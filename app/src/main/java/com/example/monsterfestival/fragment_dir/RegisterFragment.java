package com.example.monsterfestival.fragment_dir;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.monsterfestival.R;
import com.example.monsterfestival.activity_dir.WelcomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterFragment extends Fragment {

    private EditText editTextName, editTextEmail, editTextPassword, editTextCPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editTextName = view.findViewById(R.id.register_name);
        editTextEmail = view.findViewById(R.id.register_email);
        editTextPassword = view.findViewById(R.id.register_password);
        editTextCPassword = view.findViewById(R.id.register_cpassword);
        Button buttonReg = view.findViewById(R.id.register_button);
        progressBar = view.findViewById(R.id.progressBar);
        TextView textView = view.findViewById(R.id.loginRedirectText);

        textView.setOnClickListener(view1 -> {
            if (getActivity() instanceof WelcomeActivity) {
                WelcomeActivity activity = (WelcomeActivity) getActivity();
                if (activity != null)
                    activity.mostraLogin();
            }
            else {
                AccountFragment fragment = (AccountFragment) getParentFragment();
                if (fragment != null)
                    fragment.mostraLogin();
            }
        });

        buttonReg.setOnClickListener(view12 -> {
            progressBar.setVisibility(View.VISIBLE);
            String email, password, cpassword, name;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());
            cpassword = String.valueOf(editTextCPassword.getText());
            name = String.valueOf(editTextName.getText());

            if (TextUtils.isEmpty(email)){
                Toast.makeText(getActivity(), getResources().getString(R.string.email_mancante), Toast.LENGTH_SHORT).show();
                return;
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getActivity(), R.string.email_errata, Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)){
                Toast.makeText(getActivity(), getResources().getString(R.string.password_mancante), Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(name)){
                Toast.makeText(getActivity(), getResources().getString(R.string.nome_mancante), Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(cpassword)){
                Toast.makeText(getActivity(), getResources().getString(R.string.cpassword_diversa), Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length()<8){
                Toast.makeText(getActivity(), getResources().getString(R.string.password_corta), Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();

                    if (user != null)
                        user.updateProfile(profileUpdates);

                    Toast.makeText(getActivity(), getResources().getString(R.string.account_creato), Toast.LENGTH_SHORT).show();

                    if (getActivity() instanceof WelcomeActivity) {
                        WelcomeActivity activity = (WelcomeActivity) getActivity();
                        if (activity != null)
                            activity.mostraLogin();
                    }
                    else {
                        AccountFragment fragment = (AccountFragment) getParentFragment();
                        if (fragment != null)
                            fragment.mostraLogin();
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getActivity(), getResources().getString(R.string.registrazione_fallita), Toast.LENGTH_SHORT).show();
                }
            });
        });

        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        return view;
    }
}
