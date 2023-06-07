package com.example.customsearchlibrary;

import java.util.ArrayList;
import java.util.HashSet;

public class NativeLib {

    // Used to load the 'customsearchlibrary' library on application startup.
    static {
        System.loadLibrary("customsearchlibrary");
    }

    private native HashSet<Integer> processTablesNative(ArrayList<HashSet<Integer>> filterTableList);

    private native HashSet<Integer> unifyTablesNative(ArrayList<HashSet<Integer>> filterTableList);

    public HashSet<Integer> unifyTables(ArrayList<HashSet<Integer>> filterTableList) {
        if (filterTableList.isEmpty())
            return new HashSet<>();
        if (filterTableList.size() == 1)
            return filterTableList.get(0);
        return unifyTablesNative(filterTableList);
    }

    public HashSet<Integer> processTables(ArrayList<HashSet<Integer>> filterTableList) {
        if (filterTableList.isEmpty())
            return new HashSet<>();
        if (filterTableList.size() == 1)
            return filterTableList.get(0);
        return processTablesNative(filterTableList);
    }

}