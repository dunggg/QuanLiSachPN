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
import com.example.quanlisachpn.SQL.BillDetailDao;
import com.example.quanlisachpn.SQL.MySql;
import com.example.quanlisachpn.TrangChuAcivity;
import com.example.quanlisachpn.adapter.RecyclerHDCT;
import com.example.quanlisachpn.model.HoaDon;
import com.example.quanlisachpn.model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.List;

public class Fragment_ThongKeTab2 extends Fragment {
    RecyclerHDCT recyclerHDCT;
    List<HoaDonChiTiet> hoaDonChiTietList;
    RecyclerView recyclerView;
    BillDetailDao billDetailDao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__thong_ke_tab2, container, false);
        recyclerView = view.findViewById(R.id.rcvTab2);
        hoaDonChiTietList = new ArrayList<>();
        billDetailDao = new BillDetailDao(new MySql(getContext()));
        hoaDonChiTietList  = billDetailDao.getDateHoaDonChiTiet();
        RecyclerHDCT.checkButton = false;

        recyclerHDCT = new RecyclerHDCT( hoaDonChiTietList, getContext(), R.layout.recyclerview_hdct);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerHDCT);
        return view;
    }

}
