package com.example.quanlisachpn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.quanlisachpn.fragment.PageAdapter;
import com.google.android.material.tabs.TabLayout;

public class ThongKeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        anhXa();

        FragmentManager fragmentManager = getSupportFragmentManager();
        PageAdapter pageAdapter = new PageAdapter(fragmentManager);
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(pageAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public void anhXa() {
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpage);
    }

}
