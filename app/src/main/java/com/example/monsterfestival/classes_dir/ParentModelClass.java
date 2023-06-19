package com.example.monsterfestival.classes_dir;

import java.util.ArrayList;

public class ParentModelClass {

    public String title;
    public ArrayList<ChildModelClass> childModelClassList;

    public ParentModelClass(String title, ArrayList<ChildModelClass> childModelClassList) {
        this.title = title;
        this.childModelClassList = childModelClassList;
    }

}
