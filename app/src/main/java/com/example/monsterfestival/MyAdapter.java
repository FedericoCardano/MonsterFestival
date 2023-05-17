package com.example.monsterfestival;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
        holder.recAmbiete.setText(dataList.get(position).getAmbiente());
        holder.recCA.setText(dataList.get(position).getCa());
        holder.recCategoria.setText(dataList.get(position).getCategoria());
        holder.recNome.setText(dataList.get(position).getNome());
        holder.recPF.setText(dataList.get(position).getPf());
        holder.recSfida.setText(dataList.get(position).getSfida());
        holder.recTaglia.setText(dataList.get(position).getTaglia());

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
    TextView recAmbiete, recCA, recCategoria, recNome, recPF, recSfida, recTaglia;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recAmbiete = itemView.findViewById(R.id.tvAmbiete);
        recCA = itemView.findViewById(R.id.tvCA);
        recCategoria = itemView.findViewById(R.id.tvCategoria);
        recNome = itemView.findViewById(R.id.tvNome);
        recPF = itemView.findViewById(R.id.tvPF);
        recSfida = itemView.findViewById(R.id.tvSfida);
        recTaglia = itemView.findViewById(R.id.tvTaglia);

    }
}
