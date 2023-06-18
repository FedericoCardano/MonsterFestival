package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PartyCreationFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    RecyclerView recyclerView;
    AppCompatButton btnAddMonster, btnSaveParty;
    View rootView;
    TextView numMostri;
    DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_party_creation, container, false);

        btnAddMonster = rootView.findViewById(R.id.btnAddMonster);
        btnAddMonster.setOnClickListener(view -> creaSearchMonsters());

        recyclerView = rootView.findViewById(R.id.rvCartItems);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        final Cart cart = CartHelper.getCart();

        changeTotalMonstersNumber(cart);

        final CartItemAdapter cartItemAdapter = new CartItemAdapter(getContext(), PartyCreationFragment.this);
        recyclerView.setAdapter(cartItemAdapter);
        cartItemAdapter.updateCartItems(getCartItems(cart));

        btnSaveParty = rootView.findViewById(R.id.btnSaveParty);
        btnSaveParty.setOnClickListener(view -> {
            if (cart.getTotalQuantity() > 0)
            {
                reference = FirebaseDatabase.getInstance().getReference("User");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null && !user.isAnonymous()) {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d("ADebugTag", "Value: " + 1);
                            if (snapshot.hasChild(user.getUid())) {
                                int numParty = Integer.parseInt(Objects.requireNonNull(snapshot.child(user.getUid()).child("NParty").getValue()).toString());
                                if (numParty < 5) {
                                    numParty += 1;
                                    reference.child(user.getUid()).child("NParty").setValue(numParty);

                                    HashMap<DataClass, Integer> itemMap = cart.getItemWithQuantity();

                                    int numMostro = 1;
                                    for (Map.Entry<DataClass, Integer> entry : itemMap.entrySet()) {
                                        reference.child(user.getUid()).child("Party" + numParty).child("Monster" + numMostro).child("ID").setValue(entry.getKey().getID());
                                        reference.child(user.getUid()).child("Party" + numParty).child("Monster" + numMostro).child("Qty").setValue(entry.getValue());
                                        numMostro += 1;
                                    }
                                    reference.child(user.getUid()).child("Party" + numParty);
                                    cart.setTotalQuantity(0);
                                    itemMap.clear();
                                    cart.changeCart(itemMap);
                                    changeTotalMonstersNumber(cart);
                                    cartItemAdapter.updateCartItems(getCartItems(cart));
                                    cartItemAdapter.notifyDataSetChanged();
                                }

                            } else {
                                reference.child(user.getUid()).child("NParty").setValue(1);
                                HashMap<DataClass, Integer> itemMap = cart.getItemWithQuantity();

                                int numMostro = 1;
                                for (Map.Entry<DataClass, Integer> entry : itemMap.entrySet()) {
                                    reference.child(user.getUid()).child("Party" + 1).child("Monster" + numMostro).child("ID").setValue(entry.getKey().getID());
                                    reference.child(user.getUid()).child("Party" + 1).child("Monster" + numMostro).child("Qty").setValue(entry.getValue());
                                    numMostro += 1;
                                }
                                cart.setTotalQuantity(0);
                                itemMap.clear();
                                cart.changeCart(itemMap);
                                changeTotalMonstersNumber(cart);
                                cartItemAdapter.updateCartItems(getCartItems(cart));
                                cartItemAdapter.notifyDataSetChanged();

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

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
        List<CartItem> cartItems = new ArrayList<>();

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

    @SuppressLint("SetTextI18n")
    public void changeTotalMonstersNumber(Cart cart) {
        numMostri = rootView.findViewById(R.id.tvNumeroMostri);
        numMostri.setText(Integer.toString(cart.getTotalQuantity()));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentVisibleListener) {
            fragmentVisibleListener = (OnFragmentVisibleListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fragmentVisibleListener != null) {
            fragmentVisibleListener.onFragmentVisible(getParentFragmentManager(), this, getResources().getString(R.string.nome_party_creation));
        }
    }

    public void ripristinaVisibilitaElementi() {
        setAllVisibility(true);

        final Cart cart = CartHelper.getCart();
        changeTotalMonstersNumber(cart);
        final CartItemAdapter cartItemAdapter = new CartItemAdapter(getContext(), PartyCreationFragment.this);
        recyclerView.setAdapter(cartItemAdapter);
        cartItemAdapter.updateCartItems(getCartItems(cart));
    }
}