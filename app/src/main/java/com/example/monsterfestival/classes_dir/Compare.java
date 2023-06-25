package com.example.monsterfestival.classes_dir;

import android.content.Context;

public class Compare {
    private DataClass monster1;
    private DataClass monster2;

    private boolean flag;
    private int numMostro;
    private static Compare compare = new Compare();


    public static Compare getCompare() {
        if (compare == null)
            compare = new Compare();
        return compare;
    }

    public void add(Context context, DataClass dataClass, int numMostro) {
        if (numMostro == 1)
            monster1 = dataClass;
        else
            monster2 = dataClass;
        //Toast.makeText(context, context.getResources().getString(R.string.mostro_selezionato), Toast.LENGTH_SHORT).show();
    }

    public DataClass getMonster1() {return this.monster1;}
    public DataClass getMonster2() {return this.monster2;}
    public void setMonster1() {this.monster1 = null;}
    public void setMonster2() {this.monster2 = null;}
    public boolean getFlag() {return this.flag;}
    public void setFlag(boolean flag) {this.flag = flag;}
    public int getNumero() {return this.numMostro;}
    public void setNumero(int numMostro) {this.numMostro = numMostro;}

}
