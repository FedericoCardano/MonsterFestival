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
        holder.recName.setText(dataList.get(position).getName());
        holder.recRace.setText(dataList.get(position).getRace());
        holder.recClass.setText(dataList.get(position).getM_class());
        holder.recBackground.setText(dataList.get(position).getBackground());
        holder.recAlignment.setText(dataList.get(position).getAlignment());
        holder.recLevel.setText(dataList.get(position).getLevel());
        holder.recPoints.setText(dataList.get(position).getPoints());

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
    TextView recName, recRace, recClass, recBackground, recAlignment, recLevel, recPoints;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recName = itemView.findViewById(R.id.tvName);
        recRace = itemView.findViewById(R.id.tvRace);
        recClass = itemView.findViewById(R.id.tvClass);
        recBackground = itemView.findViewById(R.id.tvBackground);
        recAlignment = itemView.findViewById(R.id.tvAlignment);
        recLevel = itemView.findViewById(R.id.tvLevel);
        recPoints = itemView.findViewById(R.id.tvPoints);

    }
}
