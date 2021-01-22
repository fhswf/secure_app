package com.example.secure_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

/**
 * Activity für die Schwachstelle ClientSideInjection, welche das Wischen zwischen mehreren Fragments ermöglicht.
 *
 * @author: Marcel Hopp
 */
public class ClientSideInjectionActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tabItem1, tabItem2, tabItem3;
    PageAdapter_client_side_injection PageAdapter_client_side_injection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_side_injection);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        tabLayout = (TabLayout)findViewById(R.id.client_side_injection);
        tabItem1 = (TabItem)findViewById(R.id.tab1);
        tabItem2 = (TabItem)findViewById(R.id.tab2);
        tabItem3 = (TabItem)findViewById(R.id.tab3);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        PageAdapter_client_side_injection = new PageAdapter_client_side_injection(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(PageAdapter_client_side_injection);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2)
                    PageAdapter_client_side_injection.notifyDataSetChanged();
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