package com.example.monsterfestival;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildViewHolder> {

    List<ChildModelClass> childModelClassList;
    Context context;

    public ChildAdapter(List<ChildModelClass> childModelClassList, Context context) {
        this.childModelClassList = childModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_rv_layout, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        holder.tv_child_text.setText(childModelClassList.get(position).getNomeFiltro());
    }

    @Override
    public int getItemCount() {
        return childModelClassList.size();
    }
}

class ChildViewHolder extends RecyclerView.ViewHolder{
    TextView tv_child_text;
    public ChildViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_child_text = itemView.findViewById(R.id.tv_child_item);

    }
}
