package com.example.monsterfestival.adapter_dir;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.monsterfestival.classes_dir.Cart;
import com.example.monsterfestival.classes_dir.CartHelper;
import com.example.monsterfestival.classes_dir.CartItem;
import com.example.monsterfestival.classes_dir.DataClass;
import com.example.monsterfestival.R;
import com.example.monsterfestival.fragment_dir.PartyCreationFragment;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemViewHolder> {
    private final Context context;
    private final PartyCreationFragment fragment;
    private List<CartItem> cartItems = Collections.emptyList();

    public CartItemAdapter(Context context, PartyCreationFragment fragment) {
        this.context = context;
        this.fragment = fragment;

    }
    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_cart, parent, false);
        return new CartItemViewHolder(view);
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {

        final Cart cart = CartHelper.getCart();

        holder.recNome.setText(cartItems.get(position).getDataClass().getNome());
        Map<DataClass, Integer> itemMap = cart.getItemWithQuantity();
        if (itemMap.get(cartItems.get(holder.getAdapterPosition()).getDataClass()) != null) {
            holder.recQty.setText(String.valueOf(itemMap.get(cartItems.get(holder.getAdapterPosition()).getDataClass())));
        }
        holder.removeMonster.setOnClickListener(view -> {
            HashMap<DataClass, Integer> itemMap1 = cart.getItemWithQuantity();
            cart.setTotalQuantity(cart.getTotalQuantity() - Objects.requireNonNull(itemMap1.get(cartItems.get(holder.getAdapterPosition()).getDataClass())));
            itemMap1.remove(cartItems.get(holder.getAdapterPosition()).getDataClass());
            cart.changeCart(itemMap1);
            fragment.changeTotalMonstersNumber(cart);
            cartItems.remove(cartItems.get(position));
            updateCartItems(cartItems);
            notifyDataSetChanged();

        });

        holder.recPlus.setOnClickListener(view -> {
            if (cart.getTotalQuantity() < 20) {
                HashMap<DataClass, Integer> itemMap12 = cart.getItemWithQuantity();
                itemMap12.put(cartItems.get(holder.getAdapterPosition()).getDataClass(), Objects.requireNonNull(itemMap12.get(cartItems.get(holder.getAdapterPosition()).getDataClass())) + 1);
                holder.recQty.setText(String.valueOf(itemMap12.get(cartItems.get(holder.getAdapterPosition()).getDataClass())));
                cart.changeCart(itemMap12);
                cart.setTotalQuantity(cart.getTotalQuantity() + 1);
                fragment.changeTotalMonstersNumber(cart);
            }
            else {
                Toast.makeText(context,"Hai raggiunto il limite massimo", Toast.LENGTH_SHORT).show();
            }
        });
        holder.recMinus.setOnClickListener(view -> {
            HashMap<DataClass, Integer> itemMap1 = cart.getItemWithQuantity();
            if (Objects.requireNonNull(itemMap1.get(cartItems.get(holder.getAdapterPosition()).getDataClass())) > 1) {

                itemMap1.put(cartItems.get(holder.getAdapterPosition()).getDataClass(), Objects.requireNonNull(itemMap1.get(cartItems.get(holder.getAdapterPosition()).getDataClass())) - 1);
                holder.recQty.setText(String.valueOf(itemMap1.get(cartItems.get(holder.getAdapterPosition()).getDataClass())));
                cart.changeCart(itemMap1);
                cart.setTotalQuantity(cart.getTotalQuantity() - 1);
                fragment.changeTotalMonstersNumber(cart);
            }
        });



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
    AppCompatButton recPlus;
    AppCompatButton recMinus;
    AppCompatTextView recQty;
    ImageView removeMonster;
    public CartItemViewHolder(@NonNull View itemView) {
        super(itemView);
        recNome = itemView.findViewById(R.id.tvNome);
        recPlus = itemView.findViewById(R.id.btnCartItemPlus);
        recMinus = itemView.findViewById(R.id.btnCartItemMinus);
        recQty = itemView.findViewById(R.id.tvCartItemCount);
        removeMonster = itemView.findViewById(R.id.removeMonster);

    }
}
