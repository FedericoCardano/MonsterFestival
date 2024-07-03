package com.example.monsterfestival.fragment_dir;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.monsterfestival.R;
import com.example.monsterfestival.activity_dir.MainActivity;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;


public class EventCreationFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;
    EditText Nome,Causa,Reazione;
    Button Salva;

    public EventCreationFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_event_creation, container, false);
        Nome=view.findViewById(R.id.editNomeEvento);
        Causa=view.findViewById(R.id.editCausaEvento);
        Reazione=view.findViewById(R.id.editReazioneEvento);
        Salva=view.findViewById(R.id.btnSalva);
        Salva.setOnClickListener(task->{
            if(controlli()) {
                String nome = Nome.getText().toString();
                String causa = Causa.getText().toString();
                String reazione = Reazione.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("NomeEvento", nome);
                bundle.putString("CausaEvento", causa);
                bundle.putString("ReazioneEvento", reazione);
                ((PartyCreationFragment) getParentFragment()).setArguments(bundle);

                ((MainActivity) requireActivity()).tornaIndietro(1);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fragmentVisibleListener != null) {
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.crea_evento));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentVisibleListener) {
            fragmentVisibleListener = (OnFragmentVisibleListener) context;
        }
    }

    @Override
    public void ripristinaVisibilitaElementi() {

    }

    boolean controlli(){
       String strNome = Nome.getText().toString();
       String strCausa = Causa.getText().toString();
       String strReazione = Reazione.getText().toString();

       if(strNome.isEmpty()||strReazione.isEmpty()||strCausa.isEmpty())
       {
           Toast.makeText(getActivity(), getResources().getString(R.string.info_evento_mancante), Toast.LENGTH_SHORT).show();
           return false;
       }
       if (strNome.length()>20) {
           Toast.makeText(getActivity(), getResources().getString(R.string.nome_evento_lungo), Toast.LENGTH_SHORT).show();
           return false;
       }
       if (strNome.contains("\n")||strNome.contains(".")) {
           Toast.makeText(getActivity(), getResources().getString(R.string.nome_evento_caratteri), Toast.LENGTH_SHORT).show();
           return false;
       }

       if (strCausa.length()>200) {
           Toast.makeText(getActivity(), getResources().getString(R.string.causa_evento_lungo), Toast.LENGTH_SHORT).show();
           return false;
        }

       if(strReazione.length()>200)
       {
           Toast.makeText(getActivity(), getResources().getString(R.string.reazione_evento_lungo), Toast.LENGTH_SHORT).show();
           return false;
       }
        return true;
    }
}