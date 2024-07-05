package com.example.monsterfestival.fragment_dir;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.MonsterClass;

import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;


public class MonsterCreationFragment extends Fragment implements OnFragmentRemoveListener {

    EditText Nome,For,Des,Cost,Int,Sag,Car,Sfida,Ca,Pf,Descrizione;
    Spinner Ambiente,Categoria,Taglia;
    ArrayList<String> Ambienti=new ArrayList<String>(),
            Categorie=new ArrayList<String>(),
            Taglie=new ArrayList<String>();
    Button Salva;
    View view;
    OnFragmentVisibleListener fragmentVisibleListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {   Categorie.add("aberrazione");
            Categorie.add("bestia");
            Categorie.add("celestiale");
            Categorie.add("costrutto");
            Categorie.add("drago");
            Categorie.add("elementale");
            Categorie.add("folletto");
            Categorie.add("gigante");
            Categorie.add("immondo");
            Categorie.add("melma");
            Categorie.add("mostruosità");
            Categorie.add("non morto");
            Categorie.add("umanoide");
            Categorie.add("vegetale"); } //aggiunta categorie

        {Taglie.add("minuscola") ;
        Taglie.add("piccola") ;
        Taglie.add("media") ;
        Taglie.add("grande") ;
        Taglie.add("enorme") ;
        Taglie.add("mastodontica") ;}//aggiunta taglie

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_monster_creation, container, false);

        Nome= view.findViewById(R.id.editName);
        Descrizione= view.findViewById(R.id.editDescrizione);
        For= view.findViewById(R.id.editFor);
        Des= view.findViewById(R.id.editDes);
        Cost= view.findViewById(R.id.editCost);
        Int= view.findViewById(R.id.editInt);
        Sag= view.findViewById(R.id.editSag);
        Car= view.findViewById(R.id.editCar);
        Sfida= view.findViewById(R.id.editSfida);
        Ca= view.findViewById(R.id.editCa);
        Pf= view.findViewById(R.id.editPf);
        Salva= view.findViewById(R.id.buttonSalva);
        Bundle bundle = this.getArguments();
        Salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Nome.getText().toString().isEmpty() || Descrizione.getText().toString().isEmpty() || Sfida.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                    return;
                }
                Double num = Double.parseDouble(String.valueOf(Sfida.getText()));
                if (num < 0.125) {
                    Sfida.setText("0.125");
                    Toast.makeText(getActivity(), "Il valore minimo è 0.125", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (num >50) {
                    Sfida.setText("50");
                    Toast.makeText(getActivity(), "Il valore massimo è 50", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Integer.parseInt(String.valueOf(Sfida.getText()));
                }
                catch (NumberFormatException e) {
                    if (num < 1) {
                        if (!(num == 0.125 || num == 0.25 || num == 0.5)) {
                            Sfida.setText("0.125");
                            Toast.makeText(getActivity(), "I numeri decimali ammessi sono:\n 0.125, 0.25, 0.5", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        int a = (int) Math.round(num);
                        Sfida.setText(String.valueOf(a));
                        Toast.makeText(getActivity(), "I numeri decimali ammessi sono:\n 0.125, 0.25, 0.5", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                ArrayList<String> dati = new ArrayList<>();
                dati.add("myMonsterId");//non viene usato per ora
                dati.add(Nome.getText().toString());
                dati.add(Descrizione.getText().toString());
                dati.add(Ambiente.getSelectedItem().toString());
                dati.add(Categoria.getSelectedItem().toString());
                dati.add(Taglia.getSelectedItem().toString());
                dati.add(Sfida.getText().toString());
                dati.add(Pf.getText().toString());
                dati.add(Ca.getText().toString());
                dati.add(For.getText().toString());
                dati.add(Des.getText().toString());
                dati.add(Cost.getText().toString());
                dati.add(Int.getText().toString());
                dati.add(Sag.getText().toString());
                dati.add(Car.getText().toString());

                Log.d("PushQuery","start");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId=user.getUid();
                MonsterClass Monster= new  MonsterClass(dati);


                if(bundle!=null && bundle.containsKey("MyMonster")){
                    ArrayList<String> mon =bundle.getStringArrayList("MyMonster");
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User/"+userId+"/MyMonsters/"+mon.get(0));
                    Log.d("UpdateQuery","to User/"+userId+"/MyMonsters/"+mon.get(0)+" is ready");
                    ref.setValue(Monster);
                    Toast.makeText(getActivity(), "Monster modificato con successo", Toast.LENGTH_SHORT).show();
                    updateLocalMyMonsters();
                }
                else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User/"+userId+"/MyMonsters");
                    Log.d("PushQuery","ready");
                    ref.push().setValue(Monster);
                    Toast.makeText(getActivity(), "Monster creato con successo", Toast.LENGTH_SHORT).show();
                    updateLocalMyMonsters();
                }

            }
        });

        controlli();


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
                ArrayList<String> mon =bundle.getStringArrayList("MyMonster");
                Ambiente.setSelection(Ambienti.indexOf(mon.get(3)));

            }
        });
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!=null && bundle.containsKey("MyMonster")){
            ArrayList<String> mon =bundle.getStringArrayList("MyMonster");

            Nome.setText(mon.get(1));
            Descrizione.setText(mon.get(2));
            Ambiente.setSelection(Ambienti.indexOf(mon.get(3)));
            Categoria.setSelection(Categorie.indexOf(mon.get(4)));
            Taglia.setSelection(Taglie.indexOf(mon.get(5)));
            Sfida.setText(mon.get(6));
            Pf.setText(mon.get(7));
            Ca.setText(mon.get(8));
            For.setText(mon.get(9));
            Des.setText(mon.get(10));
            Cost.setText(mon.get(11));
            Int.setText(mon.get(12));
            Sag.setText(mon.get(13));
            Car.setText(mon.get(14));
        }
        if (fragmentVisibleListener != null) {

            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.my_monsters));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentVisibleListener) {
            fragmentVisibleListener = (OnFragmentVisibleListener) context;
        }
    }

    private void updateLocalMyMonsters() {

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FirebaseDatabase.getInstance().getReference("User").child(currentUser.getUid()+"/MyMonsters").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    objectNativeLib.setMyMonsters(dataSnapshot);
                    objectNativeLib.updateDatabase();
                    editor.putString("objectNativeLib", new Gson().toJson(objectNativeLib));
                    editor.apply();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        } else {
            objectNativeLib.invalidateUid();
            objectNativeLib.updateDatabase();
            editor.putString("objectNativeLib", new Gson().toJson(objectNativeLib));
            editor.apply();
        }

    }

    @Override
    public void ripristinaVisibilitaElementi() {

    }

    private void controlli(){
        For.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (String.valueOf(s).isEmpty()) {
                    For.setText(String.valueOf(1));
                }
                else {
                    int num = Integer.parseInt(String.valueOf(s));
                    if (num < 1) {
                        For.setText(String.valueOf(1));
                        num = 1;
                    }
                    else if(num>30)
                    {
                        For.setText(String.valueOf(30));
                        num=30;
                    }
                    else if(num != Double.parseDouble(String.valueOf(s)))
                    {
                        For.setText(String.valueOf(num));
                    }
                }
            }
        });

        Des.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (String.valueOf(s).isEmpty()) {
                    Des.setText(String.valueOf(1));
                }
                else {
                    int num = Integer.parseInt(String.valueOf(s));
                    if (num < 1) {
                        Des.setText(String.valueOf(1));
                        num = 1;
                    }
                    else if(num>30)
                    {
                        Des.setText(String.valueOf(30));
                    }
                    else if(num != Double.parseDouble(String.valueOf(s)))
                    {
                        Des.setText(String.valueOf(num));
                    }
                }
            }
        });

        Cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (String.valueOf(s).isEmpty()) {
                    Cost.setText(String.valueOf(1));
                }
                else {
                    int num = Integer.parseInt(String.valueOf(s));
                    if (num < 1) {
                        Cost.setText(String.valueOf(1));
                        num = 1;
                    }
                    else if(num>30)
                    {
                        Cost.setText(String.valueOf(30));
                        num=30;
                    }
                    else if(num != Double.parseDouble(String.valueOf(s)))
                    {
                        Cost.setText(String.valueOf(num));
                    }
                }
            }
        });

        Int.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (String.valueOf(s).isEmpty()) {
                    Int.setText(String.valueOf(1));
                }
                else {
                    int num = Integer.parseInt(String.valueOf(s));
                    if (num < 1) {
                        Int.setText(String.valueOf(1));
                        num = 1;
                    }
                    else if(num>30)
                    {
                        Int.setText(String.valueOf(30));
                        num=30;
                    }
                    else if(num != Double.parseDouble(String.valueOf(s)))
                    {
                        Int.setText(String.valueOf(num));
                    }
                }
            }
        });

        Sag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (String.valueOf(s).isEmpty()) {
                    Sag.setText(String.valueOf(1));
                }
                else {
                    int num = Integer.parseInt(String.valueOf(s));
                    if (num < 1) {
                        Sag.setText(String.valueOf(1));
                        num = 1;
                    }
                    else if(num>30)
                    {
                        Sag.setText(String.valueOf(30));
                        num=30;
                    }
                    else if(num != Double.parseDouble(String.valueOf(s)))
                    {
                        Sag.setText(String.valueOf(num));
                    }
                }
            }
        });;

        Car.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (String.valueOf(s).isEmpty()) {
                    Car.setText(String.valueOf(1));
                }
                else {
                    int num = Integer.parseInt(String.valueOf(s));
                    if (num < 1) {
                        Car.setText(String.valueOf(1));
                        num = 1;
                    }
                    else if(num>30)
                    {
                        Car.setText(String.valueOf(30));
                        num=30;
                    }
                    else if(num != Double.parseDouble(String.valueOf(s)))
                    {
                        Car.setText(String.valueOf(num));
                    }
                }
            }
        });

        Ca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (String.valueOf(s).isEmpty()) {
                    Ca.setText(String.valueOf(1));
                }
                else {
                    int num = Integer.parseInt(String.valueOf(s));
                    if (num < 1) {
                        Ca.setText("1");

                    }
                    else if(num>30)
                    {
                        Ca.setText("30");

                    }
                    else if(num != Double.parseDouble(String.valueOf(s)))
                    {
                        Ca.setText(String.valueOf(num));
                    }
                }
            }
        });

        Pf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (String.valueOf(s).isEmpty()) {
                    Pf.setText("1");
                }
                else {
                    int num = Integer.parseInt(String.valueOf(s));
                    if (num < 1) {
                        Pf.setText("1");
                    }
                    else if(num>999)
                    {
                        Pf.setText("999");
                    }
                    else if(num != Double.parseDouble(String.valueOf(s)))
                    {
                        Pf.setText(String.valueOf(num));
                    }
                }
            }
        });

    }

}