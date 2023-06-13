package com.example.monsterfestival;

import android.nfc.Tag;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PartyCreationFragment extends Fragment {

    RecyclerView recyclerView;
    AppCompatButton btnAddMonster;
    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_party_creation, container, false);

        btnAddMonster = rootView.findViewById(R.id.btnAddMonster);
        btnAddMonster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaSearchMonsters();
            }
        });

        recyclerView = rootView.findViewById(R.id.rvCartItems);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        final Cart cart = CartHelper.getCart();


        final CartItemAdapter cartItemAdapter = new CartItemAdapter(getContext());
        recyclerView.setAdapter(cartItemAdapter);
        cartItemAdapter.updateCartItems(getCartItems(cart));




        return rootView;
    }

    void creaSearchMonsters() {
        setAllVisibility(false);

        FrameLayout container = rootView.findViewById(R.id.frame_access_party_creation);

        // Inizializza il Fragment
        SearchMonstersFragment myFragment = new SearchMonstersFragment();

        // Ottieni il FragmentManager e inizia la transazione
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Aggiunti il Fragment al Container View
        fragmentTransaction.add(container.getId(), myFragment);

        // Esegui la transazione
        fragmentTransaction.commit();
    }

    private List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = new ArrayList<CartItem>();

        Map<DataClass, Integer> itemMap = cart.getItemWithQuantity();

        for (Map.Entry<DataClass, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setDataClass((DataClass) entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }
        for(CartItem item : cartItems)
            Log.d("ADebugTag", "Value: " + item.getDataClass().getNome());
        return cartItems;
    }

    void setAllVisibility(boolean value) {
        rootView.findViewById(R.id.appBarLayout).setVisibility(value ? View.VISIBLE : View.INVISIBLE);
        rootView.findViewById(R.id.bottomBox).setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }
}