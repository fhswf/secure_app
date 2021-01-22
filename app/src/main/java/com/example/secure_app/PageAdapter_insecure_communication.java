package com.example.secure_app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Diese Klasse implementiert einen Pageadapter für die Schwachstelle Insecure Communication
 *
 * @author Marcel Hopp
 */
public class PageAdapter_insecure_communication extends FragmentPagerAdapter
{
    int tabcount;

    /**
     * Konstruktor
     * @param fm
     * @param behavior
     */
    public PageAdapter_insecure_communication(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0 : return new InsecureCommunication_Beschreibung();
            case 1 : return new InsecureCommuncation_Vermeidung();
            case 2 : return new InsecureCommunication_Test();
            default: return null;
        }
    }

    /**
     * @return Anzahl der initialisierten Tabs.
     */
    @Override
    public int getCount() {
        return tabcount;
    }
}
