package com.example.customsearchlibrary;

import java.util.HashSet;
import java.util.List;

public class NativeLib {

    // Used to load the 'customsearchlibrary' library on application startup.
    static {
        System.loadLibrary("customsearchlibrary");
    }

    public native HashSet<Integer> processTables(List<HashSet<Integer>> filterTableList);

}