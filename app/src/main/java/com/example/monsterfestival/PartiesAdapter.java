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
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PartiesAdapter extends RecyclerView.Adapter<PartiesViewHolder> {
    private final Context context;
    private final PartyCreationFragment fragment;

    private List<CartItem> cartItems = Collections.emptyList();

    public PartiesAdapter(Context context, PartyCreationFragment fragment) {
        this.context = context;
        this.fragment = fragment;

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

        //nome del party dalla lista dei party
        //holder.recNome.setText();


    }
    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateCartItems(List<CartItem> cartItems) {
        //lista dei party
        //this.cartItems = cartItems;
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