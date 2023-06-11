package com.example.monsterfestival;

import java.util.ArrayList;

public class ParentModelClass {

    String title;
    ArrayList<ChildModelClass> childModelClassList;

    public ParentModelClass(String title, ArrayList<ChildModelClass> childModelClassList) {
        this.title = title;
        this.childModelClassList = childModelClassList;
    }

}
