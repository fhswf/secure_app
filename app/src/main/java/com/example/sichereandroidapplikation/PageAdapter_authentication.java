package com.example.sichereandroidapplikation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter_authentication extends FragmentPagerAdapter
{
    int tabcount;

    public PageAdapter_authentication(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0 : return new insecure_authentication_beschreibung();
            case 1 : return new insecure_authentication_vermeidung();
            case 2 : return new ftab3();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
