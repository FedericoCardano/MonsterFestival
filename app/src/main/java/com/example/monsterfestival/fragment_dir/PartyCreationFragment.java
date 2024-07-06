package com.example.monsterfestival.fragment_dir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
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
import com.example.monsterfestival.adapter_dir.EventAdapter;
import com.example.monsterfestival.classes_dir.Cart;
import com.example.monsterfestival.classes_dir.CartHelper;
import com.example.monsterfestival.classes_dir.CartItem;
import com.example.monsterfestival.adapter_dir.CartItemAdapter;
import com.example.monsterfestival.classes_dir.Compare;
import com.example.monsterfestival.classes_dir.EventClass;
import com.example.monsterfestival.classes_dir.MonsterClass;
import com.example.monsterfestival.classes_dir.OnFragmentRemoveListener;
import com.example.monsterfestival.classes_dir.OnFragmentVisibleListener;
import com.example.monsterfestival.R;
import com.example.monsterfestival.activity_dir.MainActivity;
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

    RecyclerView recyclerView, rvEventi;
    AppCompatButton btnAddMonster, btnSaveParty,btnAddEvent;
    View rootView;
    TextView numMostri;
    DatabaseReference reference;
    ArrayList<EventClass> listaEventi= new ArrayList<>();

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

        btnAddEvent = rootView.findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(view -> {
            creaEventCreation();
        });

        recyclerView = rootView.findViewById(R.id.rvCartItems);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setMotionEventSplittingEnabled(false);

        rvEventi=rootView.findViewById(R.id.rvEventi);
        rvEventi.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvEventi.setMotionEventSplittingEnabled(false);


        final Cart cart = CartHelper.getCart();
        cart.removeAll();

        changeTotalMonstersNumber(cart);

        final CartItemAdapter cartItemAdapter = new CartItemAdapter(getContext(), PartyCreationFragment.this);
        recyclerView.setAdapter(cartItemAdapter);
        cartItemAdapter.updateCartItems(getCartItems(cart));

        EventAdapter eventAdapter = new EventAdapter(getContext(), this, getChildFragmentManager());
        rvEventi.setAdapter(eventAdapter);
        eventAdapter.updateCartItems(listaEventi);

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

            ArrayList<ArrayList<String>> mostri = objectNativeLib.getMonsterPartyByName(bundle.getString("nomeParty"));
            for (ArrayList<String> dato : mostri)
                cart.add(new MonsterClass(new ArrayList<>(dato.subList(1, dato.size()))), Integer.parseInt(dato.get(0)), getContext());

            ArrayList<ArrayList<String>> eventi = objectNativeLib.getEventPartyByName(bundle.getString("nomeParty"));
            for (ArrayList<String> evento : eventi)
            {
                listaEventi.add(new EventClass(evento.get(0),evento.get(1),evento.get(2)));
            }

            ripristinaVisibilitaElementi();
        }

        btnSaveParty = rootView.findViewById(R.id.btnSaveParty);
        btnSaveParty.setOnClickListener(view -> {

            if (!isNetworkConnected(requireContext())) {
                 Toast.makeText(getActivity(), getResources().getString(R.string.connessione_assente), Toast.LENGTH_SHORT).show();
                 return;
            }

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
                    salvaParty(null, dialog, cart, cartItemAdapter);
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
                reference= FirebaseDatabase.getInstance().getReference("User").child(user.getUid()+"/MyParties");
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("ADebugTag", "Value: " + 1);
                        if (snapshot.hasChild("NParty")) {
                            int numParty = Integer.parseInt(Objects.requireNonNull(snapshot.child("NParty").getValue()).toString());
                            if (bundle != null || numParty < 5) {
                                if (bundle == null) {
                                    numParty += 1;
                                    reference.child("NParty").setValue(numParty);
                                }

                                HashMap<MonsterClass, Integer> itemMap = cart.getItemWithQuantity();

                                int numMostro = 1;
                                for (Map.Entry<MonsterClass, Integer> entry : itemMap.entrySet()) {
                                    reference.child(nomeParty).child("Monster" + numMostro).child("ID").setValue(entry.getKey().getID());
                                    reference.child(nomeParty).child("Monster" + numMostro).child("Qty").setValue(entry.getValue());
                                    numMostro += 1;
                                }
                                for(int i=0;i<listaEventi.size();i++)
                                {
                                    reference.child(nomeParty).child("Event"+(i+1)).setValue(listaEventi.get(i));
                                }
                                reference.child("Party" + numParty);
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

                        }else {
                            if (bundle == null)
                                reference.child("NParty").setValue(1);
                            HashMap<MonsterClass, Integer> itemMap = cart.getItemWithQuantity();

                            int numMostro = 1;
                            for (Map.Entry<MonsterClass, Integer> entry : itemMap.entrySet()) {
                                reference.child(nomeParty).child("Monster" + numMostro).child("ID").setValue(entry.getKey().getID());
                                reference.child(nomeParty).child("Monster" + numMostro).child("Qty").setValue(entry.getValue());
                                numMostro += 1;
                            }
                            for(int i=0;i<listaEventi.size();i++)
                            {
                                reference.child(nomeParty).child("Event"+(i+1)).setValue(listaEventi.get(i));
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
                    reference.child(bundle.getString("nomeParty")).removeValue().addOnSuccessListener(unused ->
                            reference.addListenerForSingleValueEvent(valueEventListener)).addOnFailureListener(e -> dialog.dismiss()).addOnCanceledListener(dialog::dismiss);
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

    void creaEventCreation() {
        setAllVisibility(false);

        FrameLayout container = rootView.findViewById(R.id.frame_access_party_creation);

        // Inizializza il Fragment
        EventCreationFragment myFragment = new EventCreationFragment();

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

        Map<MonsterClass, Integer> itemMap = cart.getItemWithQuantity();

        for (Map.Entry<MonsterClass, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setDataClass(entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }
        for(CartItem item : cartItems)
            Log.d("ADebugTag", "Value: " + item.getDataClass().getNome());
        return cartItems;
    }


    public void setAllVisibility(boolean value) {
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

        Bundle bundle = this.getArguments();
        if(bundle != null && bundle.containsKey("NomeEvento"))
        {
            EventClass evento = new EventClass(bundle.getString("NomeEvento"),
                    bundle.getString("CausaEvento"),
                    bundle.getString("ReazioneEvento"));
            listaEventi.add(evento);
            bundle.remove("NomeEvento");
            bundle.remove("CausaEvento");
            bundle.remove("ReazioneEvento");

        }

        final Cart cart = CartHelper.getCart();
        changeTotalMonstersNumber(cart);
        final CartItemAdapter cartItemAdapter = new CartItemAdapter(getContext(), PartyCreationFragment.this);
        recyclerView.setAdapter(cartItemAdapter);
        cartItemAdapter.updateCartItems(getCartItems(cart));

        EventAdapter eventAdapter = new EventAdapter(getContext(), this, getChildFragmentManager());
        rvEventi.setAdapter(eventAdapter);
        eventAdapter.updateCartItems(listaEventi);

    }

    private void updateLocalParties() {

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FirebaseDatabase.getInstance().getReference("User").child(currentUser.getUid()+"/MyParties").addListenerForSingleValueEvent(new ValueEventListener() {
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

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork != null) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
                return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
            }
        }

        return false;
    }

    private boolean partyExists(String name) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));
        return objectNativeLib.getPartyNames().contains(name);
    }
}