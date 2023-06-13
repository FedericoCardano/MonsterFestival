package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemViewHolder> {
    private final Context context;
    private List<DataClass> dataList;
    private List<CartItem> cartItems = Collections.emptyList();



    public CartItemAdapter(Context context) {
        this.context = context;

    }
    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartItemViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {

        final Cart cart = CartHelper.getCart();

        holder.recNome.setText(cartItems.get(position).getDataClass().getNome());

        holder.recPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart.getTotalQuantity() < 10) {
                    Map<DataClass, Integer> itemMap = cart.getItemWithQuantity();
                    itemMap.put(cartItems.get(holder.getAdapterPosition()).getDataClass(), itemMap.get(cartItems.get(holder.getAdapterPosition()).getDataClass()) + 1);
                    holder.recQty.setText(Integer.toString(itemMap.get(cartItems.get(holder.getAdapterPosition()).getDataClass())));
                    cart.changeCart(itemMap);
                    cart.setTotalQuantity(cart.getTotalQuantity() + 1);
                }
                else {
                    Toast.makeText(context,"Hai raggiunto il limite massimo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.recMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<DataClass, Integer> itemMap = cart.getItemWithQuantity();
                if (itemMap.get(cartItems.get(holder.getAdapterPosition()).getDataClass()) > 1) {

                    itemMap.put(cartItems.get(holder.getAdapterPosition()).getDataClass(), itemMap.get(cartItems.get(holder.getAdapterPosition()).getDataClass()) - 1);
                    holder.recQty.setText(Integer.toString(itemMap.get(cartItems.get(holder.getAdapterPosition()).getDataClass())));
                    cart.changeCart(itemMap);
                    cart.setTotalQuantity(cart.getTotalQuantity() - 1);
                }
            }
        });

        Map<DataClass, Integer> itemMap = cart.getItemWithQuantity();
        holder.recQty.setText(Integer.toString(itemMap.get(cartItems.get(holder.getAdapterPosition()).getDataClass())));


    }
    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

}
class CartItemViewHolder extends RecyclerView.ViewHolder{
    TextView recNome;
    CardView recCard;
    AppCompatButton recPlus;
    AppCompatButton recMinus;
    AppCompatTextView recQty;
    public CartItemViewHolder(@NonNull View itemView) {
        super(itemView);
        recNome = itemView.findViewById(R.id.tvNome);
        recPlus = itemView.findViewById(R.id.btnCartItemPlus);
        recMinus = itemView.findViewById(R.id.btnCartItemMinus);
        recQty = itemView.findViewById(R.id.tvCartItemCount);

    }
}
