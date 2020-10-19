package com.example.quanlisachpn.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.quanlisachpn.fragment.Fragment_ThongKeTab1;
import com.example.quanlisachpn.fragment.Fragment_ThongKeTab2;


public class PageAdapter extends FragmentStatePagerAdapter {


    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new Fragment_ThongKeTab1();
                break;
            case 1:
                fragment = new Fragment_ThongKeTab2();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Top 10 sách bán chạy";
                break;
            case 1:
                title = "Sách đã bán";
                break;
        }
        return title;
    }
}
