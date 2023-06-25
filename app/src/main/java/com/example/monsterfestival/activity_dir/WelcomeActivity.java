package com.example.monsterfestival.activity_dir;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout;

import com.example.monsterfestival.fragment_dir.LoginFragment;
import com.example.monsterfestival.R;
import com.example.monsterfestival.fragment_dir.RegisterFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    Button buttonPopUp;
    TextView textView;

    FirebaseAuth mAuth;
    Dialog dialog;

    RegisterFragment FragmentR;
    LoginFragment FragmentL;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        buttonPopUp = findViewById(R.id.pop_up_button);
        dialog = new Dialog(WelcomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_registrazione);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        Button register = dialog.findViewById(R.id.btnRegister);
        Button maybeLater = dialog.findViewById(R.id.btnMaybeLater);
        textView = findViewById(R.id.login_textview);
        mAuth = FirebaseAuth.getInstance();

        buttonPopUp.setOnClickListener(view -> dialog.show());

        register.setOnClickListener(view -> {
            dialog.dismiss();
            mostraRegister();
            buttonPopUp.setVisibility(View.GONE);
        });

        maybeLater.setOnClickListener(view -> {
            dialog.dismiss();
            loginAnonymous();
        });

        textView.setOnClickListener(view -> {
            mostraLogin();
            textView.setVisibility(View.INVISIBLE);
            buttonPopUp.setVisibility(View.INVISIBLE);
        });

    }

    private void loginAnonymous() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful())
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }).addOnFailureListener(e -> Toast.makeText(WelcomeActivity.this, "Access error: " + e, Toast.LENGTH_SHORT).show());
    }

    public void mostraLogin() {
        FrameLayout container = findViewById(R.id.frame_access_search);

        // Inizializza il Fragment
        FragmentL = new LoginFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (getSupportFragmentManager().findFragmentById(R.id.frame_access_search) != null)
            fragmentTransaction.remove(FragmentR);

        // Aggiunti il Fragment al Container View
        fragmentTransaction.add(container.getId(), FragmentL);

        // Esegui la transazione
        fragmentTransaction.commit();
    }

    public void mostraRegister() {
        FrameLayout container = findViewById(R.id.frame_access_search);

        // Inizializza il Fragment
        FragmentR = new RegisterFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (getSupportFragmentManager().findFragmentById(R.id.frame_access_search) != null)
            fragmentTransaction.remove(FragmentL);

        // Aggiunti il Fragment al Container View
        fragmentTransaction.add(container.getId(), FragmentR);

        // Esegui la transazione
        fragmentTransaction.commit();
    }

}