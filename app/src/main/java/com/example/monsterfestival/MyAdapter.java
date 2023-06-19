package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private final Context context;
    private List<DataClass> dataList;

    private final Fragment _parent;

    public MyAdapter(Context context, List<DataClass> dataList, Fragment parent) {
        this.context = context;
        this.dataList = dataList;
        this._parent = parent;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.recNome.setText(dataList.get(position).getNome());
        holder.recCategoria.setText(dataList.get(position).getCategoria());
        holder.recTaglia.setText(dataList.get(position).getTaglia());

        holder.recCard.setOnClickListener(view -> {
            Bundle b = new Bundle();
            DataClass monster = dataList.get(holder.getAdapterPosition());
            b.putString("ID", monster.getID());
            b.putString("Ambiente", monster.getAmbiente());
            b.putString("CA", monster.getCa());
            b.putString("Categoria", monster.getCategoria());
            b.putString("Nome", monster.getNome());
            b.putString("PF", monster.getPf());
            b.putString("Sfida", monster.getSfida());
            b.putString("Taglia", monster.getTaglia());
            b.putString("Descrizione", monster.getDescrizione());
            b.putString("CAR", monster.getCar());
            b.putString("COST", monster.getCost());
            b.putString("DES", monster.getDes());
            b.putString("FOR", monster.getFor());
            b.putString("INT", monster.getInt());
            b.putString("SAG", monster.getSag());

            SearchMonstersFragment.filtersCard.setVisibility(View.INVISIBLE);
            SearchMonstersFragment.searchView.setVisibility(View.INVISIBLE);

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            DetailMonsterFragment RecyclerFragment = new DetailMonsterFragment();
            RecyclerFragment.setParent(_parent);
            RecyclerFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_access_search, RecyclerFragment ).addToBackStack(null).commit();
        });

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView recNome, recCategoria, recTaglia;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recNome = itemView.findViewById(R.id.tvNome);
        recCategoria = itemView.findViewById(R.id.tvCategoria);
        recTaglia = itemView.findViewById(R.id.tvTaglia);
        recCard = itemView.findViewById(R.id.recCard);

    }
}
