package com.example.monsterfestival.adapter_dir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customsearchlibrary.NativeLib;
import com.example.monsterfestival.R;
import com.example.monsterfestival.classes_dir.EventClass;
import com.example.monsterfestival.fragment_dir.DetailMonsterFragment;
import com.example.monsterfestival.fragment_dir.EventCreationFragment;
import com.example.monsterfestival.fragment_dir.MonsterCreationFragment;
import com.example.monsterfestival.fragment_dir.MyMonsterFragment;
import com.example.monsterfestival.fragment_dir.PartyCreationFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressLint("NotConstructor")
public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {
        private Context context;
        private PartyCreationFragment _parent;
        private final FragmentManager fragmentManager;

        private ArrayList<EventClass> MyEvents;

        private boolean visibilitaAttiva = true;

        private final Lock ThreadLock = new ReentrantLock();

    public  EventAdapter(Context context, PartyCreationFragment parent, FragmentManager fragmentManager) {
        this.context = context;
        this._parent = parent;
        this.fragmentManager = fragmentManager;
    }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_event_item_cart, parent, false);
            return new EventViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            if (visibilitaAttiva)
                holder.itemView.setVisibility(View.VISIBLE);
            else {
                holder.itemView.setVisibility(View.INVISIBLE);
                return;
            }
            holder.Nome.setText(MyEvents.get(position).getNome());


            ImageButton modifyBtn= holder.itemView.findViewById(R.id.my_modify_botton);
            modifyBtn.setOnClickListener(v -> {
                if (ThreadLock.tryLock()) {
                    try {
                        EventCreationFragment newFragment = new EventCreationFragment();
                        Bundle bundle = new Bundle();
                        EventClass event = MyEvents.get(holder.getAdapterPosition());
                        bundle.putStringArrayList("MyEvent", event.Event2ArrayString());
                        newFragment.setArguments(bundle);
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.add( R.id.frame_access_party_creation, newFragment);
                        _parent.setAllVisibility(false);
                        MyEvents.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        transaction.commit();


                    } finally {
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
                            Log.d("MyEvents",MyEvents.toString());
                            MyEvents.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            dialog.dismiss();
                        });
                    } finally {
                        ThreadLock.unlock();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return MyEvents.size();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void updateCartItems(ArrayList<EventClass> myEvents) {
            this.MyEvents =myEvents;
            notifyDataSetChanged();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setVisibilitaElementi(boolean value) {
            visibilitaAttiva = value;
            notifyDataSetChanged();
        }

}

class EventViewHolder extends RecyclerView.ViewHolder{
    TextView Nome;
    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        Nome=itemView.findViewById(R.id.tvNomeEvento);
    }
}
