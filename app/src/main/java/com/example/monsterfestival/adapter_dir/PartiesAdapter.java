package com.example.monsterfestival.adapter_dir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.monsterfestival.fragment_dir.DetailPartyFragment;
import com.example.monsterfestival.fragment_dir.MyPartiesFragment;
import com.example.monsterfestival.fragment_dir.PartyCreationFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PartiesAdapter extends RecyclerView.Adapter<PartiesViewHolder> {
    private final Context context;
    private final MyPartiesFragment fragment;
    private final FragmentManager fragmentManager;

    private ArrayList<String> nomeParty;

    private boolean visibilitaAttiva = true;

    private final Lock ThreadLock = new ReentrantLock();

    public PartiesAdapter(Context context, MyPartiesFragment parent, FragmentManager fragmentManager) {
        this.context = context;
        this.fragment = parent;
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

        if (visibilitaAttiva)
            holder.itemView.setVisibility(View.VISIBLE);
        else {
            holder.itemView.setVisibility(View.INVISIBLE);
            return;
        }

        holder.recNome.setText(nomeParty.get(position));

        holder.itemView.setOnClickListener(view -> {
            if (ThreadLock.tryLock()) {
                try {
                    Bundle b = new Bundle();
                    SharedPreferences sharedPreferences = fragment.requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

                    float totSfida = 0;
                    int totPF = 0, totCA = 0, totFOR = 0, totDES = 0, totCOST = 0, totINT = 0, totSAG = 0, totCAR = 0;
                    StringBuilder lista_mostri = new StringBuilder();

                    String nome = objectNativeLib.getPartyNames().get(holder.getAdapterPosition());
                    ArrayList<ArrayList<String>> infoParty = objectNativeLib.getPartyWithName(nome);
                    for (ArrayList<String> monster : infoParty)
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

                    b.putString("NomeParty", nome);
                    //b.putString("Difficoltà", String.valueOf(difficoltà));
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

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    DetailPartyFragment RecyclerFragment = new DetailPartyFragment();
                    RecyclerFragment.setParent(fragment);
                    RecyclerFragment.setArguments(b);
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

                        SharedPreferences sharedPreferences = fragment.requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
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
                    transaction.commit();
                    fragment.getAdapter().setVisibilitaElementi(false);
                } finally {
                    ThreadLock.unlock();
                }
            }
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

    @SuppressLint("NotifyDataSetChanged")
    public void setVisibilitaElementi(boolean value) {
        visibilitaAttiva = value;
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