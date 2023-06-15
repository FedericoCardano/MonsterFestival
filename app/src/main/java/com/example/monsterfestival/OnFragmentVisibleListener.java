package com.example.monsterfestival;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public interface OnFragmentVisibleListener {
    void onFragmentVisible(FragmentManager fragmentManager, Fragment fragmentInstance, String fragmentTag);
}
