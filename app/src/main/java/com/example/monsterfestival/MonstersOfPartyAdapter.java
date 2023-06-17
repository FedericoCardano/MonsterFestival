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

public class MonstersOfPartyAdapter extends RecyclerView.Adapter<MonstersOfPartyViewHolder> {
    private final Context context;
    private final MonstersOfPartyFragment fragment;

    private List<CartItem> cartItems = Collections.emptyList();

    public MonstersOfPartyAdapter(Context context, MonstersOfPartyFragment fragment) {
        this.context = context;
        this.fragment = fragment;

    }
    @NonNull
    @Override
    public MonstersOfPartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.monster_item_cart, parent, false);
        return new MonstersOfPartyViewHolder(view);
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MonstersOfPartyViewHolder holder, int position) {

        //nome del mostro dalla lista dei mostri del party corrente
        //holder.recNome.setText();

        //quantità del mostro dalla lista dei mostri del party corrente
        //holder.recQty.setText();




    }
    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateCartItems(List<CartItem> cartItems) {
        //lista dei mostri che fanno parte di questo party
        //this.cartItems = cartItems;
        notifyDataSetChanged();
    }

}
class MonstersOfPartyViewHolder extends RecyclerView.ViewHolder{
    TextView recNome;
    AppCompatTextView recQty;

    public MonstersOfPartyViewHolder(@NonNull View itemView) {
        super(itemView);
        recNome = itemView.findViewById(R.id.tvNome);
        recQty = itemView.findViewById(R.id.tvCartItemCount);


    }
}
