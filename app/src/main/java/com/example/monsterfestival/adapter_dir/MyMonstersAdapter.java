package com.example.monsterfestival.adapter_dir;

import static java.lang.Math.round;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.MonsterClass;
import com.example.monsterfestival.classes_dir.MonsterPost;
import com.example.monsterfestival.fragment_dir.DetailMonsterFragment;
import com.example.monsterfestival.fragment_dir.MonsterCreationFragment;
import com.example.monsterfestival.fragment_dir.MyMonsterFragment;
import com.example.monsterfestival.fragment_dir.MyPartiesFragment;
import com.example.monsterfestival.fragment_dir.PartyCreationFragment;
import com.example.monsterfestival.fragment_dir.SearchMonstersFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressLint("NotConstructor")
public class MyMonstersAdapter extends RecyclerView.Adapter<MyMonstersViewHolder> {
        private Context context;
        private MyMonsterFragment _parent;
        private final FragmentManager fragmentManager;

        private ArrayList<String> MyMonsters;

        private boolean visibilitaAttiva = true;

        private final Lock ThreadLock = new ReentrantLock();

    public  MyMonstersAdapter(Context context, MyMonsterFragment parent, FragmentManager fragmentManager) {
        this.context = context;
        this._parent = parent;
        this.fragmentManager = fragmentManager;
    }

        @NonNull
        @Override
        public MyMonstersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_monster_item_cart, parent, false);
            return new MyMonstersViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyMonstersViewHolder holder, int position) {

            holder.Nome.setText(MyMonsters.get(position));


            ImageButton modifyBtn= holder.itemView.findViewById(R.id.my_modify_botton);
            modifyBtn.setOnClickListener(v -> {
                if (ThreadLock.tryLock()) {
                    try {
                        MonsterCreationFragment newFragment = new MonsterCreationFragment();
                        Bundle bundle = new Bundle();
                        SharedPreferences sharedPreferences = _parent.requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

                        ArrayList<String> monster = objectNativeLib.getMyMonsters().get(position);
                        bundle.putStringArrayList("MyMonster", monster);
                        newFragment.setArguments(bundle);
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.add( R.id.frame_access_my_monster, newFragment);
                        transaction.commit();
                        _parent.nascondiElementi();
                    } finally {
                        ThreadLock.unlock();
                    }
                }
            });


            CardView Item= holder.itemView.findViewById(R.id.MyMonsterCard);
            Item.setOnClickListener(view -> {
                if (ThreadLock.tryLock()) {
                    try {

                        Bundle b = new Bundle();
                        SharedPreferences sharedPreferences = _parent.requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

                        ArrayList<String> monster = objectNativeLib.getMyMonsters().get(position);


                        b.putString("ID", monster.get(0));
                        b.putString("Nome", monster.get(1));
                        b.putString("Descrizione", monster.get(2));
                        b.putString("Ambiente", monster.get(3));
                        b.putString("Categoria", monster.get(4));
                        b.putString("Taglia", monster.get(5));
                        b.putString("Sfida", monster.get(6));
                        b.putString("PF", monster.get(7));
                        b.putString("CA", monster.get(8));
                        b.putString("FOR", monster.get(9));
                        b.putString("DES", monster.get(10));
                        b.putString("COST", monster.get(11));
                        b.putString("INT", monster.get(12));
                        b.putString("SAG", monster.get(13));
                        b.putString("CAR", monster.get(14));

                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        DetailMonsterFragment RecyclerFragment = new DetailMonsterFragment();
                        RecyclerFragment.setParent(_parent);
                        RecyclerFragment.setArguments(b);
                        _parent.nascondiElementi();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_access_my_monster, RecyclerFragment).addToBackStack(null).commit();
                    }finally {
                        ThreadLock.unlock();
                    }
                }
            });

            ImageButton deleteBtn= holder.itemView.findViewById(R.id.my_delete_botton);
            deleteBtn.setOnClickListener(view -> {
                if (ThreadLock.tryLock()) {
                    try {
                        Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.popup_conferma_cancellazione);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(true);
                        dialog.show();

                        dialog.findViewById(R.id.btnNo).setOnClickListener(view1 -> dialog.dismiss());
                        dialog.findViewById(R.id.btnSi).setOnClickListener(view1 -> {

                            SharedPreferences sharedPreferences = _parent.requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

                            Log.d("MyMonstersAdapter", "onBindViewHolder: "+position);
                            ArrayList<String> monster = objectNativeLib.getMyMonsters().get(holder.getAdapterPosition());

                            objectNativeLib.deleteMostro(holder.getAdapterPosition());
                            editor.putString("objectNativeLib", new Gson().toJson(objectNativeLib));
                            editor.apply();

                            dialog.dismiss();

                            MyMonsters.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());

                        });
                    } finally {
                        ThreadLock.unlock();
                    }
                }
            });

            ImageButton publishBtn = holder.itemView.findViewById(R.id.my_publish_botton);
            publishBtn.setOnClickListener(view->{
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_conferma_pubblicazione);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                dialog.show();

                dialog.findViewById(R.id.btnNo).setOnClickListener(view1 -> dialog.dismiss());
                dialog.findViewById(R.id.btnSi).setOnClickListener(view1 -> {

                    SharedPreferences sharedPreferences = _parent.requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    NativeLib objectNativeLib = new NativeLib(new Gson().fromJson(sharedPreferences.getString("objectNativeLib", ""), NativeLib.class));

                    Log.d("MyMonstersAdapter", "onBindViewHolder: "+position);
                    ArrayList<String> nativeMonster = objectNativeLib.getMyMonsters().get(holder.getAdapterPosition());

                    MonsterClass monster = new MonsterClass(nativeMonster);
                    String time= String.valueOf(System.currentTimeMillis());
                    String uidAutore= FirebaseAuth.getInstance().getCurrentUser().getUid();

                    MonsterPost newPost= new MonsterPost(uidAutore,time,monster);

                    DatabaseReference refMonsterPost = FirebaseDatabase.getInstance().getReference("Posts").child(time);
                    refMonsterPost.setValue(newPost);
                    DatabaseReference refUserInf = FirebaseDatabase.getInstance().getReference("UsersInformation").child(uidAutore);
                    refUserInf.child("nPost").get().addOnCompleteListener(read -> {
                        if (read.isSuccessful()) {
                            int old_nPost = read.getResult().getValue(int.class);
                            refUserInf.child("nPost").setValue(old_nPost+1);
                        }
                    });

                    dialog.dismiss();
                });
            });

        }

        @Override
        public int getItemCount() {
            return MyMonsters.size();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void updateCartItems(ArrayList<String> myMonsters) {
            this.MyMonsters=myMonsters;
            notifyDataSetChanged();
        }

}

class MyMonstersViewHolder extends RecyclerView.ViewHolder{
    TextView Nome;
    public MyMonstersViewHolder(@NonNull View itemView) {
        super(itemView);
        Nome=itemView.findViewById(R.id.tvNomeMyMonster);
    }
}
