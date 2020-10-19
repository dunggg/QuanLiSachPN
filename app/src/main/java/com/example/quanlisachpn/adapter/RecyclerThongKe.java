package com.example.quanlisachpn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlisachpn.R;
import com.example.quanlisachpn.model.HoaDonChiTiet;

import java.util.List;

public class RecyclerThongKe extends RecyclerView.Adapter<RecyclerThongKe.ViewHolder> {

    List<HoaDonChiTiet> hoaDonChiTietList;
    Context context;
    int layout;

    public RecyclerThongKe(List<HoaDonChiTiet> hoaDonChiTietList, Context context, int layout) {
        this.hoaDonChiTietList = hoaDonChiTietList;
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textMa.setText("Mã sách: " + hoaDonChiTietList.get(position).getMaSach());
        holder.textSoLuongMua.setText("Số lượng bán ra: " + hoaDonChiTietList.get(position).getSoLuongMua());
    }

    @Override
    public int getItemCount() {
        if (hoaDonChiTietList.size() > 10) {
            return 10;
        } else {
            return hoaDonChiTietList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textMa, textSoLuongMua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMa = itemView.findViewById(R.id.textMaTK);
            textSoLuongMua = itemView.findViewById(R.id.textSoLuongMuaTK);
        }
    }
}
