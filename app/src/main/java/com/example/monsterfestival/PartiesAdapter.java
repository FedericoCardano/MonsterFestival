package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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