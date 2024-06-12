package com.example.monsterfestival.classes_dir;

import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;

public class Cart {
    private HashMap<MonsterClass, Integer> cartItemMap = new HashMap<>();
    private int totalQuantity = 0;

    public void add(MonsterClass monsterClass, int quantity, Context context) {
        if (cartItemMap.size() == 10)
            Toast.makeText(context,"Party al completo (10 diversi mostri massimo)", Toast.LENGTH_SHORT).show();
        else if (cartItemMap.size() == 0 || !doesContain(monsterClass)) {
            cartItemMap.put(monsterClass, quantity);
            totalQuantity += quantity;
            //Toast.makeText(context,"Mostro aggiunto al party", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(context,"Il mostro è già presente nel party", Toast.LENGTH_SHORT).show();
    }

    public int getTotalQuantity() {return this.totalQuantity;}

    public void setTotalQuantity(int totalQuantity) {this.totalQuantity = totalQuantity;}

    public HashMap<MonsterClass, Integer> getItemWithQuantity() {
        return new HashMap<>(this.cartItemMap);
    }

    public void changeCart(HashMap<MonsterClass, Integer> itemMap) {
        this.cartItemMap = new HashMap<>(itemMap);
    }

    public boolean doesContain(MonsterClass monsterClass) {
        for (MonsterClass _monsterClass : cartItemMap.keySet())
            if (_monsterClass.getNome().equals(monsterClass.getNome()))
                return true;
        return false;
    }
    public void removeAll() {
        cartItemMap.clear();
        totalQuantity = 0;
    }
}
