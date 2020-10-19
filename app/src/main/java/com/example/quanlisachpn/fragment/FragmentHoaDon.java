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
import com.example.quanlisachpn.adapter.RecyclerHoaDon;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;


public class FragmentHoaDon extends Fragment {
    RecyclerView recyclerView;
    public static RecyclerHoaDon recyclerHoaDon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        recyclerView = view.findViewById(R.id.rcvHD);
        recyclerHoaDon = new RecyclerHoaDon(TrangChuAcivity.hoaDonList, getContext(), R.layout.recycler_hoadon);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new ScaleInAnimator());
        recyclerView.setAdapter(new ScaleInAnimationAdapter(recyclerHoaDon));
        return view;
    }
}
