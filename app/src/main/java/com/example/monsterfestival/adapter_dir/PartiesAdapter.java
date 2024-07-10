package com.example.monsterfestival.adapter_dir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.EventClass;
import com.example.monsterfestival.classes_dir.MonsterClass;
import com.example.monsterfestival.fragment_dir.DetailPartyFragment;
import com.example.monsterfestival.fragment_dir.MyPartiesFragment;
import com.example.monsterfestival.fragment_dir.PartyCreationFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PartiesAdapter extends RecyclerView.Adapter<PartiesViewHolder> {
    private final Context context;
    private final MyPartiesFragment _parent;
    private final FragmentManager fragmentManager;


    private ArrayList<String> nomeParty;



    private final Lock ThreadLock = new ReentrantLock();

    public PartiesAdapter(Context context, MyPartiesFragment parent, FragmentManager fragmentManager) {
        this.context = context;
        this._parent = parent;
        this.fragmentManager = fragmentManager;
    }
    @NonNull
    @Override
    public PartiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_party_item_cart, parent, false);
        return new PartiesViewHolder(view);
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull PartiesViewHolder holder, int position) {

        holder.recNome.setText(nomeParty.get(position));

        holder.itemView.setOnClickListener(view -> {
            if (ThreadLock.tryLock()) {
                try {
                    Bundle b = new Bundle();
                    SharedPreferences sharedPreferences = _parent.requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

                    float totSfida = 0;
                    int totPF = 0, totCA = 0, totFOR = 0, totDES = 0, totCOST = 0, totINT = 0, totSAG = 0, totCAR = 0;
                    StringBuilder lista_mostri = new StringBuilder();
                    StringBuilder lista_eventi = new StringBuilder();

                    String nome = objectNativeLib.getPartyNames().get(holder.getAdapterPosition());
                    ArrayList<ArrayList<String>> infoMonsterParty = objectNativeLib.getMonsterPartyByName(nome);
                    ArrayList<ArrayList<String>> infoEventParty = objectNativeLib.getEventPartyByName(nome);

                    for (ArrayList<String> monster : infoMonsterParty)
                    {
                        int monsterMultiplier = Integer.parseInt(monster.get(0));
                        lista_mostri.append(monster.get(2)).append(" (x").append(monster.get(0)).append("), ");
                        if (Double.parseDouble(monster.get(7)) < 0)
                            totSfida += Math.pow(2, Float.parseFloat(monster.get(7))) * monsterMultiplier;
                        else
                            totSfida += Float.parseFloat(monster.get(7)) * monsterMultiplier;
                        totPF += Integer.parseInt(monster.get(8)) * monsterMultiplier;
                        totCA += Integer.parseInt(monster.get(9)) * monsterMultiplier;
                        totFOR += Integer.parseInt(monster.get(10)) * monsterMultiplier;
                        totDES += Integer.parseInt(monster.get(11)) * monsterMultiplier;
                        totCOST += Integer.parseInt(monster.get(12)) * monsterMultiplier;
                        totINT += Integer.parseInt(monster.get(13)) * monsterMultiplier;
                        totSAG += Integer.parseInt(monster.get(14)) * monsterMultiplier;
                        totCAR += Integer.parseInt(monster.get(15)) * monsterMultiplier;
                    }

                    for (ArrayList<String> event : infoEventParty)
                    {
                        lista_eventi.append(event.get(0)).append("\n"); //event.get(0) = nome evento
                    }

                    b.putString("NomeParty", nome);

                    b.putString("totSfida", String.valueOf(totSfida));
                    b.putString("totPF", String.valueOf(totPF));
                    b.putString("totCA", String.valueOf(totCA));
                    b.putString("totFOR", String.valueOf(totFOR));
                    b.putString("totDES", String.valueOf(totDES));
                    b.putString("totCOST", String.valueOf(totCOST));
                    b.putString("totINT", String.valueOf(totINT));
                    b.putString("totSAG", String.valueOf(totSAG));
                    b.putString("totCAR", String.valueOf(totCAR));
                    if (lista_mostri.length() > 0)
                        lista_mostri = new StringBuilder(lista_mostri.substring(0, lista_mostri.length() - 2)).append(".");
                    b.putString("Mostri", String.valueOf(lista_mostri));
                    if (lista_eventi.length() > 0)
                        lista_eventi = new StringBuilder(lista_eventi.substring(0, lista_eventi.length() -1 )).append(".");
                    b.putString("Eventi", String.valueOf(lista_eventi));

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    DetailPartyFragment RecyclerFragment = new DetailPartyFragment();
                    RecyclerFragment.setParent(_parent);
                    RecyclerFragment.setArguments(b);
                    _parent.nascondiElementi();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_access_party, RecyclerFragment ).addToBackStack(null).commit();
                } finally {
                    ThreadLock.unlock();
                }
            }
        });

        ImageButton deleteBtn = holder.itemView.findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(view -> {
            if (ThreadLock.tryLock()) {
                try {
                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.popup_conferma_cancellazione);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(true);
                    dialog.show();

                    dialog.findViewById(R.id.btnNo).setOnClickListener(view1 -> dialog.dismiss());
                    dialog.findViewById(R.id.btnSi).setOnClickListener(view1 -> {

                        SharedPreferences sharedPreferences = _parent.requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));
                        objectNativeLib.deleteParty(holder.getAdapterPosition());
                        editor.putString("objectNativeLib", new Gson().toJson(objectNativeLib));
                        editor.apply();

                        dialog.dismiss();

                        nomeParty.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());

                    });
                } finally {
                    ThreadLock.unlock();
                }
            }
        });

        ImageButton modifyBtn = holder.itemView.findViewById(R.id.modify_btn);
        modifyBtn.setOnClickListener(view -> {
            if (ThreadLock.tryLock()) {
                try {
                    PartyCreationFragment newFragment = new PartyCreationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("nomeParty", nomeParty.get(position));
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.add(R.id.frame_access_party, newFragment);
                    _parent.nascondiElementi();
                    transaction.commit();

                } finally {
                    ThreadLock.unlock();
                }
            }
        });

        ImageButton publishButton = holder.itemView.findViewById(R.id.my_publish_botton);
        publishButton.setOnClickListener(view -> {

            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.popup_conferma_pubblicazione);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.show();

            dialog.findViewById(R.id.btnNo).setOnClickListener(view1 -> dialog.dismiss());
            dialog.findViewById(R.id.btnSi).setOnClickListener(view1 -> {

                SharedPreferences sharedPreferences = _parent.requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));
                Integer totPF=0, totCA=0, totFOR=0, totDES=0, totCOST=0, totINT=0, totSAG=0, totCAR=0;
                Double totSfida=0.0;
                StringBuilder lista_mostri = new StringBuilder();

                Log.d("MyPartiesAdapter", "onBindViewHolder: "+position);
                ArrayList<ArrayList<String>> eventi = objectNativeLib.getEventParties().get(position);
                ArrayList<ArrayList<Integer>> mostri = objectNativeLib.getMosterParties().get(position);
                String time= String.valueOf(System.currentTimeMillis());
                String uidAutorePost= FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("PartyPosts").child(time);
                ArrayList<EventClass> listaEventi=new ArrayList<>();
                for (ArrayList<String> evento : eventi)
                {
                    listaEventi.add(new EventClass(evento.get(0),evento.get(1),evento.get(2)));
                }
                String nomeParty=objectNativeLib.getPartyNames().get(position);

                for (int i=0;i<listaEventi.size();i++)
                {
                    reference.child("Party").child("Event"+(i+1)).setValue(listaEventi.get(i));
                }

                for (ArrayList<Integer> mostro : mostri)
                {
                    MonsterClass m= new MonsterClass(objectNativeLib.getMostro(mostro.get(1)));

                    totFOR+=Integer.parseInt(m.getFor()) *mostro.get(0);
                    totDES+=Integer.parseInt(m.getDes()) *mostro.get(0);
                    totCOST+=Integer.parseInt(m.getCost()) *mostro.get(0);
                    totINT+=Integer.parseInt(m.getInt()) *mostro.get(0);
                    totSAG+=Integer.parseInt(m.getSag()) *mostro.get(0);
                    totCAR+=Integer.parseInt(m.getCar()) *mostro.get(0);

                    totPF+=Integer.parseInt(m.getPf()) *mostro.get(0);
                    totCA+=Integer.parseInt(m.getCa()) *mostro.get(0);
                    totSfida+=Double.parseDouble(m.getSfida()) *mostro.get(0);

                    lista_mostri.append(m.getNome()).append(" (x").append(mostro.get(0)).append("), ");
                }

                reference.child("Party").child("totSfida").setValue(totSfida);
                reference.child("Party").child("totPF").setValue(totPF);
                reference.child("Party").child("totCA").setValue(totCA);
                reference.child("Party").child("totFOR").setValue(totFOR);
                reference.child("Party").child("totDES").setValue(totDES);
                reference.child("Party").child("totCOST").setValue(totCOST);
                reference.child("Party").child("totINT").setValue(totINT);
                reference.child("Party").child("totSAG").setValue(totSAG);
                reference.child("Party").child("totCAR").setValue(totCAR);
                reference.child("Party").child("listaMostri").setValue(lista_mostri.toString());

                reference.child("nome").setValue(nomeParty);
                reference.child("postTime").setValue(time);
                reference.child("uidAutorePost").setValue(uidAutorePost);
                reference.child("nVoti").setValue(0);
                reference.child("vote").setValue(0.0);
                reference.child("voteBilanciamento").setValue(0.0);
                reference.child("voteCoerenza").setValue(0.0);
                reference.child("voteOriginalita").setValue(0.0);

                DatabaseReference refUserInf = FirebaseDatabase.getInstance().getReference("UsersInformation").child(uidAutorePost);
                refUserInf.child("nPost").get().addOnCompleteListener(read -> {
                    if (read.isSuccessful()) {
                        int old_nPost = read.getResult().getValue(int.class);
                        refUserInf.child("nPost").setValue(old_nPost+1);
                    }
                });

                dialog.dismiss();
            });
        });
    }




    @Override
    public int getItemCount() {
        return nomeParty.size();
    }



    @SuppressLint("NotifyDataSetChanged")
    public void updateCartItems(ArrayList<String> nomeParty) {
        this.nomeParty = nomeParty;
        notifyDataSetChanged();
    }

}
class PartiesViewHolder extends RecyclerView.ViewHolder{
    TextView recNome;

    public PartiesViewHolder(@NonNull View itemView) {
        super(itemView);
        recNome = itemView.findViewById(R.id.tvNome);
    }
}