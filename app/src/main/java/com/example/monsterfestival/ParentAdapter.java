package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ParentAdapter extends RecyclerView.Adapter<ParentViewHolder> {

    ArrayList<ParentModelClass> parentModelClassList;
    Context context;

    public ParentAdapter(ArrayList<ParentModelClass> parentModelClassList, Context context) {
        this.parentModelClassList = parentModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    @SuppressLint("InflateParams")
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parent_rv_layout, null, false);
        return new ParentViewHolder(view);
    }

    @Override
    @SuppressLint("NotifyDataSetChanged")
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        holder.tv_parent_title.setText(parentModelClassList.get(position).title);

        ChildAdapter childAdapter;
        childAdapter = new ChildAdapter(parentModelClassList.get(position).childModelClassList, context);
        holder.rv_child.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rv_child.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return parentModelClassList.size();
    }
}

class ParentViewHolder extends RecyclerView.ViewHolder{
    RecyclerView rv_child;
    TextView tv_parent_title;
    public ParentViewHolder(@NonNull View itemView) {
        super(itemView);
        rv_child = itemView.findViewById(R.id.rv_child);
        tv_parent_title = itemView.findViewById(R.id.tv_parent_title);

    }
}
