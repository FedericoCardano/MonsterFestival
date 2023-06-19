package com.example.monsterfestival.classes_dir;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public interface OnFragmentVisibleListener {
    void onFragmentVisible(FragmentManager fragmentManager, Fragment fragmentInstance, String fragmentTag);
}
