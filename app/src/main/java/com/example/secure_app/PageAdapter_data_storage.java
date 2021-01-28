package com.example.secure_app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Diese Klasse implementiert einen Pageadapter für die Schwachstelle Insecure Data Storage
 *
 * @author Marcel Hopp
 */
public class PageAdapter_data_storage extends FragmentPagerAdapter
{
    int tabcount;

    /**
     * Konstruktor
     * @param fm
     * @param behavior
     */
    public PageAdapter_data_storage(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0 : return new InsecureDataStorage_Beschreibung();
            case 1 : return new InsecureDataStorage_Vermeidung();
            case 2 : return new InsecureDataStorage_Test();
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
