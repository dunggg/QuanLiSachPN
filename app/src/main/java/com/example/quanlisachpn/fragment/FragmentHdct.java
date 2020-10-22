package com.example.quanlisachpn.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlisachpn.R;
import com.example.quanlisachpn.SQL.BillDetailDao;
import com.example.quanlisachpn.SQL.MySql;
import com.example.quanlisachpn.TrangChuAcivity;
import com.example.quanlisachpn.adapter.RecyclerHDCT;
import com.example.quanlisachpn.model.HoaDon;
import com.example.quanlisachpn.model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;


public class FragmentHdct extends Fragment {
    public static RecyclerHDCT recyclerHDCT;
    public static List<HoaDonChiTiet> hoaDonChiTietList;
    RecyclerView recyclerView;
    BillDetailDao billDetailDao;
    public static boolean checkFragemt = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hdct, container, false);
        hoaDonChiTietList = new ArrayList<>();
        billDetailDao = new BillDetailDao(new MySql(getContext()));
        hoaDonChiTietList = billDetailDao.getDateHoaDonChiTiet();
        recyclerView = view.findViewById(R.id.rcvHDCT);
        recyclerHDCT = new RecyclerHDCT(hoaDonChiTietList, getContext(), R.layout.recyclerview_hdct);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new ScaleInAnimator());
        recyclerView.setAdapter(new ScaleInAnimationAdapter(recyclerHDCT));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkFragemt==true){
                    hoaDonChiTietList = billDetailDao.getDateHoaDonChiTiet();
                    for (int i = 0;i<hoaDonChiTietList.size();i++){
                        hoaDonChiTietList.get(i).textButton = "Edit";
                    }
                    recyclerHDCT = new RecyclerHDCT(hoaDonChiTietList, getContext(), R.layout.recyclerview_hdct);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                    recyclerView.setItemAnimator(new ScaleInAnimator());
                    recyclerView.setAdapter(new ScaleInAnimationAdapter(recyclerHDCT));
                    checkFragemt=false;
                }
                new Handler().postDelayed(this,1000);
            }
        },1000);
        return view;
    }

}