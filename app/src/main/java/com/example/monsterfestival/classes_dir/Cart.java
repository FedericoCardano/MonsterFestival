package com.example.monsterfestival.classes_dir;

import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;

public class Cart {
    private HashMap<DataClass, Integer> cartItemMap = new HashMap<>();
    private int totalQuantity = 0;

    public void add(DataClass dataClass, int quantity, Context context) {
        if (cartItemMap.size() == 10)
            Toast.makeText(context,"Party al completo (10 diversi mostri massimo)", Toast.LENGTH_SHORT).show();
        else if (cartItemMap.size() == 0 || !doesContain(dataClass)) {
            cartItemMap.put(dataClass, quantity);
            totalQuantity += quantity;
            Toast.makeText(context,"Mostro aggiunto al party", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(context,"Il mostro è già presente nel party", Toast.LENGTH_SHORT).show();
    }

    public int getTotalQuantity() {return this.totalQuantity;}

    public void setTotalQuantity(int totalQuantity) {this.totalQuantity = totalQuantity;}

    public HashMap<DataClass, Integer> getItemWithQuantity() {
        return new HashMap<>(this.cartItemMap);
    }

    public void changeCart(HashMap<DataClass, Integer> itemMap) {
        this.cartItemMap = new HashMap<>(itemMap);
    }

    public boolean doesContain(DataClass dataClass) {
        for (DataClass _dataClass : cartItemMap.keySet())
            if (_dataClass.getNome().equals(dataClass.getNome()))
                return true;
        return false;
    }

    public void removeAll() {
        cartItemMap.clear();
        totalQuantity = 0;
    }
}
