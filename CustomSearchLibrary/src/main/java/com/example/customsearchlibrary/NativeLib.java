package com.example.customsearchlibrary;

import java.util.ArrayList;

public class NativeLib {

    // Used to load the 'customsearchlibrary' library on application startup.
    static {
        System.loadLibrary("customsearchlibrary");
    }

    private native ArrayList<Integer> processTablesNative(ArrayList<ArrayList<Integer>> filterTableList);

    private native ArrayList<Integer> unifyTablesNative(ArrayList<ArrayList<Integer>> filterTableList);

    public ArrayList<Integer> unifyTables(ArrayList<ArrayList<Integer>> filterTableList) {
        if (filterTableList.isEmpty())
            return new ArrayList<>();
        if (filterTableList.size() == 1)
            return filterTableList.get(0);
        return unifyTablesNative(filterTableList);
    }

    public ArrayList<Integer> processTables(ArrayList<ArrayList<Integer>> filterTableList) {
        if (filterTableList.isEmpty())
            return new ArrayList<>();
        if (filterTableList.size() == 1)
            return filterTableList.get(0);
        return processTablesNative(filterTableList);
    }

}