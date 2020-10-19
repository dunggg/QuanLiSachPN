package com.example.quanlisachpn.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlisachpn.HoaDonActivity;
import com.example.quanlisachpn.R;
import com.example.quanlisachpn.TrangChuAcivity;
import com.example.quanlisachpn.fragment.FragmentHdct;
import com.example.quanlisachpn.model.HoaDon;
import com.example.quanlisachpn.model.HoaDonChiTiet;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class RecyclerHDCT extends RecyclerView.Adapter<RecyclerHDCT.ViewHolder> {

    List<HoaDonChiTiet> hoaDonChiTietList;
    Context context;
    int layout;
    public static boolean checkButton = false;

    public RecyclerHDCT(List<HoaDonChiTiet> hoaDonChiTietList, Context context, int layout) {
        this.hoaDonChiTietList = hoaDonChiTietList;
        this.context = context;
        this.layout = layout;

        for (int i = 0; i < hoaDonChiTietList.size(); i++) {
            String gia = HoaDonActivity.convertGia(String.valueOf(hoaDonChiTietList.get(i).getGia()));
            hoaDonChiTietList.get(i).setGia(gia);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietList.get(position);
        holder.textMaHDCT.setText("Mã hóa đơn chi tiết: " + hoaDonChiTiet.getId());
        holder.textMaHoaDon.setText("Mã hóa đơn: " + hoaDonChiTiet.getMaHoaDon());
        holder.textMaSach.setText("Mã sách: " + hoaDonChiTiet.getMaSach());
        holder.textSoLuong.setText("Số lượng: " + hoaDonChiTiet.getSoLuongMua());
        holder.textTongTien.setText("Tổng tiền: " + hoaDonChiTiet.getGia() + " VNĐ");
        holder.textNgayMua.setText("Ngày mua: " + hoaDonChiTiet.getNgayMua());

        if (checkButton == true) {
            holder.btn.setText(hoaDonChiTiet.textButton);
            holder.btn.setVisibility(View.VISIBLE);
        }


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = holder.btn.getText().toString();
                final int position = holder.getAdapterPosition();
                if (text.equalsIgnoreCase("edit")) {
                    if (HoaDonActivity.spinnerListMaSach.size() != 0 && HoaDonActivity.spinnerListHoaDon.size() != 0) {
                        // thêm hóa đơn chi tiết
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Sửa hóa đơn chi tiết");
                        builder.setCancelable(false);
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View view = inflater.inflate(R.layout.them_hdct, null);
                        builder.setView(view);

                        final TextInputEditText txtSoLuong;
                        Spinner spinner = view.findViewById(R.id.spinnerMaSachThemHDCT);
                        Spinner spinner1 = view.findViewById(R.id.spinnerMaHDhemHDCT);
                        txtSoLuong = view.findViewById(R.id.txtSoLuongThemHDCT);
                        Button btnThem = view.findViewById(R.id.btnThemThemHDCT);
                        Button btnHuy = view.findViewById(R.id.btnHuyThemHDCT);
                        btnThem.setText("Lưu");

                        ArrayAdapter arrayAdapter1 = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, HoaDonActivity.spinnerListHoaDon);
                        spinner1.setAdapter(arrayAdapter1);

                        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, HoaDonActivity.spinnerListMaSach);
                        spinner.setAdapter(arrayAdapter);
                        final String[] maSach = new String[1];
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                maSach[0] = HoaDonActivity.spinnerListMaSach.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        final String maHoaDon[] = new String[1];
                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                maHoaDon[0] = HoaDonActivity.spinnerListHoaDon.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                        final Dialog dialog = builder.create();

                        btnThem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String soLuong = txtSoLuong.getText().toString().trim();
                                String ngay = HoaDonActivity.getDate();
                                boolean checkSoLuong = HoaDonActivity.checkSoLuong(Integer.parseInt(soLuong), maSach[0]);

                                if (soLuong.length() > 0 && Integer.parseInt(soLuong) > 0 && checkSoLuong == true) {
                                    // lấy id của hóa đơn chi tiết
                                    int id = hoaDonChiTietList.get(position).getId();

                                    // lấy giá sashc đã chuyển đổi
                                    String gia = HoaDonActivity.convertGia(String.valueOf(HoaDonActivity.getGia(maSach[0], soLuong)));

                                    FragmentHdct.hoaDonChiTietList.remove(position);
                                    FragmentHdct.hoaDonChiTietList.add(position, new HoaDonChiTiet(id, maHoaDon[0], maSach[0], soLuong, gia, ngay));
                                    TrangChuAcivity.billDetailDao.upadate(new HoaDonChiTiet(0, maHoaDon[0], maSach[0], soLuong, String.valueOf(HoaDonActivity.getGia(maSach[0], soLuong)), ngay), String.valueOf(id));
                                    FragmentHdct.hoaDonChiTietList.get(position).textButton = FragmentHdct.hoaDonChiTietList.get(0).textButton;
                                    notifyItemChanged(position);
                                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                } else {
                                    Toast.makeText(context, "Bạn không được để trống,số lượng lớn hơn 0 (không nhập nhiều hơn số lượng sách)", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        btnHuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                        dialog.show();
                    } else {
                        Toast.makeText(context, "Bạn phải có hóa đơn và sách mới có thể sửa hóa đơn chi tiết", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    TrangChuAcivity.billDetailDao.delete(String.valueOf(FragmentHdct.hoaDonChiTietList.get(position).getId()));
                    FragmentHdct.hoaDonChiTietList.remove(position);
                    notifyItemRemoved(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return hoaDonChiTietList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textMaHDCT, textMaHoaDon, textMaSach, textSoLuong, textTongTien, textNgayMua;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMaHDCT = itemView.findViewById(R.id.textMaHDCTTK);
            textMaHoaDon = itemView.findViewById(R.id.textMaHoaDonTK);
            textMaSach = itemView.findViewById(R.id.textMaSachTK);
            textSoLuong = itemView.findViewById(R.id.textSoLuongMuaTK);
            textTongTien = itemView.findViewById(R.id.textTongTienTK);
            textNgayMua = itemView.findViewById(R.id.textNgayMuaTK);
            btn = itemView.findViewById(R.id.btnRcvHDCT);
        }
    }
}
