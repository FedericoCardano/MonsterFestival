package com.example.monsterfestival;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class UploadActivity extends AppCompatActivity {
    Button saveButton;
    EditText uploadAmbiete, uploadCA, uploadCategoria, uploadNome, uploadPF, uploadSfida, uploadTaglia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        uploadAmbiete = findViewById(R.id.uploadAmbiete);
        uploadCA = findViewById(R.id.uploadCA);
        uploadCategoria = findViewById(R.id.uploadCategoria);
        uploadNome = findViewById(R.id.uploadNome);
        uploadPF = findViewById(R.id.uploadPF);
        uploadSfida = findViewById(R.id.uploadSfida);
        uploadTaglia = findViewById(R.id.uploadTaglia);
        saveButton = findViewById(R.id.saveButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }
    public void saveData(){

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        uploadData();
        dialog.dismiss();

    }
    public void uploadData(){
        String ambiete = uploadAmbiete.getText().toString();
        String ca = uploadCA.getText().toString();
        String categoria = uploadCategoria.getText().toString();
        String nome = uploadNome.getText().toString();
        String pf = uploadPF.getText().toString();
        String sfida = uploadSfida.getText().toString();
        String taglia = uploadTaglia.getText().toString();
        DataClass dataClass = new DataClass(ambiete, ca, categoria, nome, pf, sfida, taglia);
        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Monster").child(currentDate)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}