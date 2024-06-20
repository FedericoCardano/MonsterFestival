package com.example.monsterfestival.fragment_dir;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.MonsterPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Objects;


public class MonsterCreationFragment extends Fragment {

    EditText Nome,For,Des,Cost,Int,Sag,Car,Sfida,Ca,Pf;
    Spinner Ambiente,Categoria,Taglia;
    ArrayList<String> Ambienti=new ArrayList<String>(),
            Categorie=new ArrayList<String>(),
            Taglie=new ArrayList<String>();


    public MonsterCreationFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Categorie.add("aberrazione") ;
        Categorie.add("bestia") ;
        Categorie.add("celestiale") ;
        Categorie.add("costrutto") ;
        Categorie.add("drago") ;
        Categorie.add("elementale") ;
        Categorie.add("folletto") ;
        Categorie.add("gigante") ;
        Categorie.add("immondo") ;
        Categorie.add("melma") ;
        Categorie.add("mostruosità") ;
        Categorie.add("non morto") ;
        Categorie.add("umanoide") ;
        Categorie.add("vegetale") ;

        Taglie.add("minuscola") ;
        Taglie.add("piccola") ;
        Taglie.add("media") ;
        Taglie.add("grande") ;
        Taglie.add("enorme") ;
        Taglie.add("mastodontica") ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monster_creation, container, false);

        Nome= view.findViewById(R.id.editName);
        For= view.findViewById(R.id.editFor);
        Des= view.findViewById(R.id.editDes);
        Cost= view.findViewById(R.id.editCost);
        Int= view.findViewById(R.id.editInt);
        Sag= view.findViewById(R.id.editSag);
        Car= view.findViewById(R.id.editCar);
        Sfida= view.findViewById(R.id.editSfida);
        Ca= view.findViewById(R.id.editCa);
        Pf= view.findViewById(R.id.editPf);


        Ambiente= view.findViewById(R.id.ambienteSpinner);

        Categoria= view.findViewById(R.id.categoriaSpinner);
        ArrayAdapter<String> adapterC=
                new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,Categorie);
        adapterC.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        Categoria.setAdapter(adapterC);


        Taglia= view.findViewById(R.id.tagliaSpinner);
        ArrayAdapter<String> adapterT=
                new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,Taglie);
        adapterT.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        Taglia.setAdapter(adapterT);


        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query Habitat = db.child("Monster/Filtri/Ambiente");

        Habitat.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());

            } else {
                Log.d("QueryFecthing", "inizio for di scomposizione");

                for (DataSnapshot child : task.getResult().getChildren()) {
                    Log.d("QueryFecthing", String.valueOf(child.getKey()));
                    Ambienti.add(child.getKey());
                }
                Log.d("QueryFecthing", "fine for di scomposizione");


                Log.d("Ambienti InQuery", "isEmpty: " + Ambienti.isEmpty());

                ArrayAdapter<String> adapterA=
                        new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,Ambienti);
                adapterA.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                Ambiente.setAdapter(adapterA);

            }
        });

        return view;
    }




}