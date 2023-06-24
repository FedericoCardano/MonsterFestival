package com.example.monsterfestival.fragment_dir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.classes_dir.Cart;
import com.example.monsterfestival.classes_dir.CartHelper;
import com.example.monsterfestival.classes_dir.CartItem;
import com.example.monsterfestival.adapter_dir.CartItemAdapter;
import com.example.monsterfestival.classes_dir.Compare;
import com.example.monsterfestival.classes_dir.DataClass;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.example.monsterfestival.activity_dir.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PartyCreationFragment extends Fragment implements OnFragmentRemoveListener {

    OnFragmentVisibleListener fragmentVisibleListener;

    RecyclerView recyclerView;
    AppCompatButton btnAddMonster, btnSaveParty, btnConfermaSaveParty;
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
        cart.removeAll();

        changeTotalMonstersNumber(cart);

        final CartItemAdapter cartItemAdapter = new CartItemAdapter(getContext(), PartyCreationFragment.this);
        recyclerView.setAdapter(cartItemAdapter);
        cartItemAdapter.updateCartItems(getCartItems(cart));

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

            ArrayList<ArrayList<String>> dati = objectNativeLib.getPartyWithName(bundle.getString("nomeParty"));
            for (ArrayList<String> dato : dati)
                cart.add(new DataClass(new ArrayList<>(dato.subList(1, dato.size()))), Integer.parseInt(dato.get(0)), getContext());
            ripristinaVisibilitaElementi();
        }

        btnSaveParty = rootView.findViewById(R.id.btnSaveParty);
        btnSaveParty.setOnClickListener(view -> {

            Dialog dialog = new Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.popup_salva_party);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            if (bundle != null)
                ((EditText) dialog.findViewById(R.id.editNomeParty)).setText(bundle.getString("nomeParty"));
            dialog.show();

            dialog.findViewById(R.id.btnAnnulla).setOnClickListener(view1 -> dialog.dismiss());
            dialog.findViewById(R.id.btnSalva).setOnClickListener(view1 -> {
                if (bundle != null)
                {
                    Dialog dialog1 = new Dialog(requireContext());
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.popup_conferma_modifica);
                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog1.setCancelable(true);
                    dialog1.show();

                    dialog1.findViewById(R.id.btnNo).setOnClickListener(view3 -> {
                        dialog.dismiss();
                        dialog1.dismiss();
                    });
                    dialog1.findViewById(R.id.btnSi).setOnClickListener(view3 -> {
                        salvaParty(bundle, dialog, cart, cartItemAdapter);
                        dialog1.dismiss();
                    });
                }
                else
                    salvaParty(bundle, dialog, cart, cartItemAdapter);
            });
        });

        rootView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        return rootView;
    }

    private void salvaParty(Bundle bundle, Dialog dialog, Cart cart, CartItemAdapter cartItemAdapter) {
        String nomeParty = ((TextView) dialog.findViewById(R.id.editNomeParty)).getText().toString();

        if (nomeParty.isEmpty())
            return;

        if (bundle == null && (partyExists(nomeParty) || nomeParty.equals("NParty"))) {
            Toast.makeText(requireContext(), requireContext().getResources().getString(R.string.nome_usato), Toast.LENGTH_SHORT).show();
            return;
        }

        if (cart.getTotalQuantity() > 0) {
            reference = FirebaseDatabase.getInstance().getReference("User");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null && !user.isAnonymous()) {

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("ADebugTag", "Value: " + 1);
                        if (snapshot.hasChild(user.getUid())) {
                            int numParty = Integer.parseInt(Objects.requireNonNull(snapshot.child(user.getUid()).child("NParty").getValue()).toString());
                            if (numParty < 5) {
                                numParty += 1;
                                if (bundle == null)
                                    reference.child(user.getUid()).child("NParty").setValue(numParty);

                                HashMap<DataClass, Integer> itemMap = cart.getItemWithQuantity();

                                int numMostro = 1;
                                for (Map.Entry<DataClass, Integer> entry : itemMap.entrySet()) {
                                    reference.child(user.getUid()).child(nomeParty).child("Monster" + numMostro).child("ID").setValue(entry.getKey().getID());
                                    reference.child(user.getUid()).child(nomeParty).child("Monster" + numMostro).child("Qty").setValue(entry.getValue());
                                    numMostro += 1;
                                }
                                reference.child(user.getUid()).child("Party" + numParty);
                                cart.setTotalQuantity(0);
                                itemMap.clear();
                                cart.changeCart(itemMap);
                                changeTotalMonstersNumber(cart);
                                cartItemAdapter.updateCartItems(getCartItems(cart));
                                cartItemAdapter.notifyDataSetChanged();
                                updateLocalParties();
                                dialog.dismiss();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                            else
                                Toast.makeText(requireContext(), "Attenzione: Numero Massimo di Party raggiunto (5).", Toast.LENGTH_SHORT).show();

                        } else {
                            if (bundle == null)
                                reference.child(user.getUid()).child("NParty").setValue(1);
                            HashMap<DataClass, Integer> itemMap = cart.getItemWithQuantity();

                            int numMostro = 1;
                            for (Map.Entry<DataClass, Integer> entry : itemMap.entrySet()) {
                                reference.child(user.getUid()).child(nomeParty).child("Monster" + numMostro).child("ID").setValue(entry.getKey().getID());
                                reference.child(user.getUid()).child(nomeParty).child("Monster" + numMostro).child("Qty").setValue(entry.getValue());
                                numMostro += 1;
                            }
                            cart.setTotalQuantity(0);
                            itemMap.clear();
                            cart.changeCart(itemMap);
                            changeTotalMonstersNumber(cart);
                            cartItemAdapter.updateCartItems(getCartItems(cart));
                            cartItemAdapter.notifyDataSetChanged();
                            updateLocalParties();
                            dialog.dismiss();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialog.dismiss();
                    }
                };

                if (bundle != null) {
                    reference.child(user.getUid()).child(bundle.getString("nomeParty")).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            reference.addListenerForSingleValueEvent(valueEventListener);
                        }
                    }).addOnFailureListener(e -> dialog.dismiss()).addOnCanceledListener(dialog::dismiss);
                }
                else
                    reference.addListenerForSingleValueEvent(valueEventListener);
            }
        }
    }

    void creaSearchMonsters() {
        setAllVisibility(false);

        final Compare compare = Compare.getCompare();
        compare.setFlag(false);

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

    private void updateLocalParties() {

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FirebaseDatabase.getInstance().getReference("User").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    objectNativeLib.setParties(dataSnapshot);
                    objectNativeLib.updateDatabase();
                    editor.putString("objectNativeLib", new Gson().toJson(objectNativeLib));
                    editor.apply();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        } else {
            objectNativeLib.invalidateUid();
            objectNativeLib.updateDatabase();
            editor.putString("objectNativeLib", new Gson().toJson(objectNativeLib));
            editor.apply();
        }

    }

    private boolean partyExists(String name) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));
        return objectNativeLib.getPartyNames().contains(name);
    }
}