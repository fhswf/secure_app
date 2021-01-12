package com.example.secure_app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter_client_side_injection extends FragmentPagerAdapter
{
    int tabcount;

    public PageAdapter_client_side_injection(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0 : return new client_side_injection_beschreibung();
            case 1 : return new client_side_injection_vermeidung();
            case 2 : return new client_side_injection_test();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}