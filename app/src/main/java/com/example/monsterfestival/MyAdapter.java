package com.example.monsterfestival;

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
    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
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

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("Ambiente", dataList.get(holder.getAdapterPosition()).getAmbiente());
                b.putString("CA", dataList.get(holder.getAdapterPosition()).getCa());
                b.putString("Categoria", dataList.get(holder.getAdapterPosition()).getCategoria());
                b.putString("Nome",dataList.get(holder.getAdapterPosition()).getNome());
                b.putString("PF", dataList.get(holder.getAdapterPosition()).getPf());
                b.putString("Sfida", dataList.get(holder.getAdapterPosition()).getSfida());
                b.putString("Taglia", dataList.get(holder.getAdapterPosition()).getTaglia());
                b.putString("Descrizione", dataList.get(holder.getAdapterPosition()).getDescrizione());
                b.putString("CAR", dataList.get(holder.getAdapterPosition()).getCar());
                b.putString("COST", dataList.get(holder.getAdapterPosition()).getCost());
                b.putString("DES", dataList.get(holder.getAdapterPosition()).getDes());
                b.putString("FOR", dataList.get(holder.getAdapterPosition()).getFor());
                b.putString("INT", dataList.get(holder.getAdapterPosition()).getInt());
                b.putString("SAG", dataList.get(holder.getAdapterPosition()).getSag());

                SearchMonstersFragment.filtersCard.setVisibility(View.INVISIBLE);
                SearchMonstersFragment.searchView.setVisibility(View.INVISIBLE);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new DetailFragment();
                myFragment.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_access_search, myFragment ).addToBackStack(null).commit();
            }
        });

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
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
