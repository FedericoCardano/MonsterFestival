package com.example.monsterfestival;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Compare {
    private DataClass monster1;
    private DataClass monster2;

    public void add(DataClass dataClass, int numMostro, Context context) {
        if (numMostro == 1) {
            monster1 = dataClass;
        } else {
            monster2 = dataClass;
        }
    }

    public DataClass getMonster1() {return this.monster1;}
    public DataClass getMonster2() {return this.monster2;}
}
