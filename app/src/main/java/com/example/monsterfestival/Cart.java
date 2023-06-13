package com.example.monsterfestival;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<DataClass, Integer> cartItemMap = new HashMap<DataClass, Integer>();
    private int totalQuantity = 0;

    public void add(DataClass dataClass, int quantity, Context context) {
        if (cartItemMap.size() == 0) {
            cartItemMap.put(dataClass, quantity);
            totalQuantity += quantity;
            Toast.makeText(context,"Mostro aggiunto al party", Toast.LENGTH_SHORT).show();
        }
        else {
            for (Map.Entry<DataClass, Integer> entry : cartItemMap.entrySet()) {
                Log.d("ADebugTag", "Value: " + entry.getKey().getNome());
                if (entry.getKey().getNome().equals(dataClass.getNome())) {
                    Toast.makeText(context,"Mostro già presente nel party", Toast.LENGTH_SHORT).show();
                } else {
                    cartItemMap.put(dataClass, quantity);
                    totalQuantity += quantity;
                    Toast.makeText(context,"Mostro aggiunto al party", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public int getTotalQuantity() {return this.totalQuantity;}

    public void setTotalQuantity(int totalQuantity) {this.totalQuantity = totalQuantity;}

    public Map<DataClass, Integer> getItemWithQuantity() {
        Map<DataClass, Integer> cartItemMap = new HashMap<DataClass, Integer>();
        cartItemMap.putAll(this.cartItemMap);
        return cartItemMap;
    }

    public void changeCart(Map<DataClass, Integer> itemMap) {
        Map<DataClass, Integer> cartItemMap = new HashMap<DataClass, Integer>();
        cartItemMap.putAll(this.cartItemMap);
        this.cartItemMap = itemMap;
    }
}
