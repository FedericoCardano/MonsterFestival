package com.example.monsterfestival.adapter_dir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monsterfestival.classes_dir.ParentModelClass;
import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.WrapLayoutManager;
import com.example.monsterfestival.fragment_dir.SearchFiltersFragment;

import java.util.ArrayList;

public class ParentAdapter extends RecyclerView.Adapter<ParentViewHolder> {

    ArrayList<ParentModelClass> parentModelClassList;
    Context context;

    private final SearchFiltersFragment parentFragment;

    public ParentAdapter(ArrayList<ParentModelClass> parentModelClassList, Context context, SearchFiltersFragment parentFragment) {
        this.parentModelClassList = parentModelClassList;
        this.context = context;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    @SuppressLint("InflateParams")
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_parent_rv_layout, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    @SuppressLint("NotifyDataSetChanged")
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        holder.tv_parent_title.setText(parentModelClassList.get(position).title);

        Log.d("Test2", String.valueOf(position));
        ChildAdapter childAdapter;
        childAdapter = new ChildAdapter(parentModelClassList.get(position).childModelClassList, context);
        holder.rv_child.setLayoutManager(new WrapLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rv_child.setAdapter(childAdapter);

        holder.rv_child.requestLayout();
        childAdapter.notifyDataSetChanged();

        holder.rv_child.postDelayed(() -> {
            ArrayList<View> childView = new ArrayList<>();
            for (int i = 0; i < holder.rv_child.getChildCount(); i++)
                childView.add(holder.rv_child.getChildAt(i));
            parentFragment.addFilters(childView);
        }, 100);

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
