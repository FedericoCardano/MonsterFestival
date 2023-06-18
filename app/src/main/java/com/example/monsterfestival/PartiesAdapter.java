package com.example.monsterfestival;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customsearchlibrary.NativeLib;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PartiesAdapter extends RecyclerView.Adapter<PartiesViewHolder> {
    private final Context context;
    private final MyPartiesFragment fragment;

    private ArrayList<String> nomeParty;

    public PartiesAdapter(Context context, MyPartiesFragment parent) {
        this.context = context;
        this.fragment = parent;
    }
    @NonNull
    @Override
    public PartiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.party_item_cart, parent, false);
        return new PartiesViewHolder(view);
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull PartiesViewHolder holder, int position) {
        holder.recNome.setText(nomeParty.get(position));

        holder.itemView.setOnClickListener(view -> {
            Bundle b = new Bundle();
            SharedPreferences sharedPreferences = fragment.requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            NativeLib objectNativeLib = new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class);

            float totSfida = 0;
            int totPF = 0, totCA = 0, totFOR = 0, totDES = 0, totCOST = 0, totINT = 0, totSAG = 0, totCAR = 0;

            String nome = objectNativeLib.getPartyNames().get(holder.getAdapterPosition());
            ArrayList<ArrayList<String>> infoParty = objectNativeLib.getPartyWithName(nome);
            for (ArrayList<String> monster : infoParty)
            {
                if (Double.parseDouble(monster.get(7)) < 0)
                    totSfida += Math.pow(2, Double.parseDouble(monster.get(7)));
                else
                    totSfida += Double.parseDouble(monster.get(7));
                totPF += Integer.parseInt(monster.get(8));
                totCA += Integer.parseInt(monster.get(9));
                totFOR += Integer.parseInt(monster.get(10));
                totDES += Integer.parseInt(monster.get(11));
                totCOST += Integer.parseInt(monster.get(12));
                totINT += Integer.parseInt(monster.get(13));
                totSAG += Integer.parseInt(monster.get(14));
                totCAR += Integer.parseInt(monster.get(15));
            }

            b.putString("Nome", nome);
            b.putString("Sfida", String.valueOf(totSfida));
            b.putString("PF", String.valueOf(totPF));
            b.putString("CA", String.valueOf(totCA));
            b.putString("FOR", String.valueOf(totFOR));
            b.putString("DES", String.valueOf(totDES));
            b.putString("COST", String.valueOf(totCOST));
            b.putString("INT", String.valueOf(totINT));
            b.putString("SAG", String.valueOf(totSAG));
            b.putString("CAR", String.valueOf(totCAR));

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            DetailPartyFragment RecyclerFragment = new DetailPartyFragment();
            RecyclerFragment.setParent(fragment);
            RecyclerFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_access_party, RecyclerFragment ).addToBackStack(null).commit();
        });

        ImageButton deleteBtn = holder.itemView.findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(view -> {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.conferma_cancellazione);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.show();

            dialog.findViewById(R.id.btnNo).setOnClickListener(view1 -> dialog.dismiss());
            dialog.findViewById(R.id.btnSi).setOnClickListener(view1 -> {

                SharedPreferences sharedPreferences = fragment.requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                NativeLib objectNativeLib = new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class);
                objectNativeLib.deleteParty(holder.getAdapterPosition());
                editor.putString("objectNativeLib", new Gson().toJson(objectNativeLib));
                editor.apply();

                dialog.dismiss();

                nomeParty.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

            });
        });

        ImageButton modifyBtn = holder.itemView.findViewById(R.id.modify_btn);
        modifyBtn.setOnClickListener(view -> Toast.makeText(context, "Funzione non ancora disponibile, presto in arrivo!", Toast.LENGTH_SHORT).show());
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