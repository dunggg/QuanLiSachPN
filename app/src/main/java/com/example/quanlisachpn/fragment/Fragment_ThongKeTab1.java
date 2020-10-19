package com.example.quanlisachpn.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlisachpn.R;
import com.example.quanlisachpn.TrangChuAcivity;
import com.example.quanlisachpn.adapter.RecyclerThongKe;
import com.example.quanlisachpn.model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


public class Fragment_ThongKeTab1 extends Fragment {

    RecyclerView recyclerView;
    List<HoaDonChiTiet> hoaDonChiTietList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__thong_ke_tab1, container, false);
        recyclerView = view.findViewById(R.id.rcvTk1);
        topSachBanChay();
        RecyclerThongKe recyclerThongKe = new RecyclerThongKe(hoaDonChiTietList, getContext(), R.layout.recycler_thongketab1);
        recyclerView.setAdapter(new ScaleInAnimationAdapter(recyclerThongKe));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public void topSachBanChay() {
       hoaDonChiTietList = new ArrayList<>();
       hoaDonChiTietList = TrangChuAcivity.billDetailDao.sachBanChay();
        Collections.sort(hoaDonChiTietList, new Comparator<HoaDonChiTiet>() {
            @Override
            public int compare(HoaDonChiTiet o1, HoaDonChiTiet o2) {
                if (Integer.parseInt(o1.getSoLuongMua()) > Integer.parseInt(o2.getSoLuongMua())) {
                    return -1;
                } else if (Integer.parseInt(o1.getSoLuongMua()) < Integer.parseInt(o2.getSoLuongMua())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

}
