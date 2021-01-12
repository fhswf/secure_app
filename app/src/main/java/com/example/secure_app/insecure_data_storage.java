package com.example.sichereandroidapplikation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class insecure_data_storage extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tabItem1, tabItem2, tabItem3;
    PageAdapter_data_storage PageAdapter_data_storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insecure_data_storage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        tabLayout = (TabLayout)findViewById(R.id.insecure_data_storage);
        tabItem1 = (TabItem)findViewById(R.id.tab1);
        tabItem2 = (TabItem)findViewById(R.id.tab2);
        tabItem3 = (TabItem)findViewById(R.id.tab3);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        PageAdapter_data_storage = new PageAdapter_data_storage(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(PageAdapter_data_storage);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2)
                    PageAdapter_data_storage.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}