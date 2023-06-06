package com.example.customsearchlibrary;

import java.util.ArrayList;
import java.util.HashSet;

public class NativeLib {

    // Used to load the 'customsearchlibrary' library on application startup.
    static {
        System.loadLibrary("customsearchlibrary");
    }

    public native HashSet<Integer> processTables(ArrayList<HashSet<Integer>> filterTableList);

    public native HashSet<Integer> unifyTables(ArrayList<HashSet<Integer>> filterTableList);

}