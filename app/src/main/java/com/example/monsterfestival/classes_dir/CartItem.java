package com.example.monsterfestival.classes_dir;

public class CartItem {
    private MonsterClass monsterClass;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MonsterClass getDataClass() {
        return monsterClass;
    }

    public void setDataClass(MonsterClass monsterClass) {
        this.monsterClass = monsterClass;
    }
}
