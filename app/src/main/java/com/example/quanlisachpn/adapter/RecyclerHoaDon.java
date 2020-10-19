package com.example.quanlisachpn.adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlisachpn.HoaDonActivity;
import com.example.quanlisachpn.TrangChuAcivity;
import com.example.quanlisachpn.model.HoaDon;
import com.example.quanlisachpn.R;
import com.example.quanlisachpn.model.HoaDonChiTiet;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecyclerHoaDon extends RecyclerView.Adapter<RecyclerHoaDon.ViewHoler> {
    List<HoaDon> hoaDonList;
    Context context;
    int layout;

    public static boolean checkRcvHd = false;

    public RecyclerHoaDon(List<HoaDon> hoaDonList, Context context, int layout) {
        this.hoaDonList = hoaDonList;
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, parent, false);
        final ViewHoler viewHoler = new ViewHoler(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<HoaDonChiTiet> thongKeHoaDon = new ArrayList<>();
                thongKeHoaDon = TrangChuAcivity.billDao.thongKeHoaDon();
                if (thongKeHoaDon.size()>0) {
                    int position = viewHoler.getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Hóa đơn");
                    builder.setMessage("Mã hóa đơn: " + thongKeHoaDon.get(position).getMaHoaDon()
                            + "\nSố lượng sách đã bán: " + thongKeHoaDon.get(position).getSoLuongMua()
                            + "\nSố lượng hóa đơn chi tiết: " + thongKeHoaDon.get(position).getId()
                    );
                    builder.show();
                }else{
                    Toast.makeText(context, "Hóa đơn này chưa có hóa đơn chi tiết", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoler holder, final int position) {
        final HoaDon hoaDon = hoaDonList.get(position);
        holder.textNgayMua.setText("Ngày mua: " + hoaDon.getNgayMua());
        holder.textMa.setText("Mã hóa đơn: " + hoaDon.getMaHoaDon());
        if (checkRcvHd == true) {
            holder.btn.setText(hoaDon.textButton);
            holder.btn.setVisibility(View.VISIBLE);
        }
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = hoaDonList.get(holder.getAdapterPosition()).textButton;
                final int position = holder.getAdapterPosition();
                if (ma.equalsIgnoreCase("edit")) {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                    builder.setTitle("Sửa hóa đơn");
                    builder.setCancelable(false);
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View view = inflater.inflate(R.layout.them_hoadon, null);
                    builder.setView(view);

                    final TextInputEditText txtMaHoaDon, txtSoLuong;
//                    final TextView textNgay = view.findViewById(R.id.textNgay);
//                    Button btnNgay = view.findViewById(R.id.btnChonNgay);
                    Spinner spinner = view.findViewById(R.id.spinnerThemHD);
                    txtMaHoaDon = view.findViewById(R.id.txtMaHDThemHD);
                    txtSoLuong = view.findViewById(R.id.txtSoLuongThemHD);
                    Button btnThem = view.findViewById(R.id.btnThemThemHd);
                    Button btnHuy = view.findViewById(R.id.btnHuyThemHD);
                    btnThem.setText("Lưu");

                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, HoaDonActivity.spinnerListMaSach);
                    spinner.setAdapter(arrayAdapter);
                    final String[] maSach = {null};
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            maSach[0] = HoaDonActivity.spinnerListMaSach.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

//                    btnNgay.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Calendar calendar = Calendar.getInstance();
//                            int year = calendar.get(Calendar.YEAR);
//                            int month = calendar.get(Calendar.MONTH);
//                            int day = calendar.get(Calendar.DAY_OF_MONTH);
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
//                                    @Override
//                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                                        textNgay.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
//                                    }
//                                }, year, month, day);
//                                datePickerDialog.show();
//                            }
//                        }
//                    });

                    final Dialog dialog = builder.create();
                    final String idHoaDon = hoaDonList.get(position).getMaHoaDon();
                    final String idHoaDonChiTiet = String.valueOf(TrangChuAcivity.hoaDonChiTietList.get(position).getId());
                    btnHuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    btnThem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String ma = txtMaHoaDon.getText().toString().trim();
                            String soLuong = txtSoLuong.getText().toString().trim();
                            String ngay = getDate();
                            boolean checkSoLuong = checkSoLuongSua(Integer.parseInt(soLuong), maSach[0], position);

                            if (checkMa(ma, position) < 0 && soLuong.length() > 0 && Integer.parseInt(soLuong) > 0 && ngay.length() > 0 && checkSoLuong == true) {
                                TrangChuAcivity.hoaDonList.remove(holder.getAdapterPosition());
                                TrangChuAcivity.hoaDonList.add(holder.getAdapterPosition(), new HoaDon(ma, ngay));
                                TrangChuAcivity.billDao.update(new HoaDon(ma, ngay), idHoaDon);
                                TrangChuAcivity.hoaDonChiTietList.remove(holder.getAdapterPosition());
                                TrangChuAcivity.hoaDonChiTietList.add(holder.getAdapterPosition(), new HoaDonChiTiet(0, ma, maSach[0], soLuong, getGia(maSach[0], soLuong)));
                                TrangChuAcivity.billDetailDao.upadate(new HoaDonChiTiet(0, ma, maSach[0], soLuong, getGia(maSach[0], soLuong)), idHoaDonChiTiet);
                                hoaDonList.get(position).textButton = "Edit";
                                notifyItemChanged(holder.getAdapterPosition());
                                dialog.cancel();
                                Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Bạn không được để trống,số lượng lớn hơn 0 (số lượng nhập không được vượt quá số lượng sách) và mã hóa đơn không được trùng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();
                } else {
                    if (checkXoaHoaDon() == false) {
                        TrangChuAcivity.billDao.delete(TrangChuAcivity.hoaDonList.get(position).getMaHoaDon());
                        TrangChuAcivity.hoaDonList.remove(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, "Bạn không thể xóa hóa đơn khi liên kết với hóa đơn chi tiết", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public int checkMa(String ma, int postion) {

        if (ma.equals("")) {
            return 0;
        } else if (ma.equals(hoaDonList.get(postion).getMaHoaDon())) {
            return -1;
        }

        for (int i = 0; i < TrangChuAcivity.hoaDonChiTietList.size(); i++) {
            if (ma.equals(TrangChuAcivity.hoaDonChiTietList.get(i).getMaHoaDon())) {
                return i;
            }
        }
        return -1;
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }

    public String getGia(String maSach, String soLuong) {
        int gia = 0;
        for (int i = 0; i < TrangChuAcivity.sachList.size(); i++) {
            if (maSach.equals(TrangChuAcivity.sachList.get(i).getMa())) {
                gia = Integer.parseInt(soLuong) * Integer.parseInt(giaSach(TrangChuAcivity.sachList.get(i).getGia()));
            }
        }
        return convertGia(String.valueOf(gia));
    }

    public String convertGia(String gia) {
        String total = "";
        if (gia.length() <= 3) {
            return gia + " VNĐ";
        }
        // đảo ngược chuỗi string
        String stringBuilder = new StringBuilder(gia).reverse().toString();
        int a = 0;
        for (int i = 3; i < stringBuilder.length(); i += 3) {
            total += stringBuilder.substring(a, i) + ",";
            a = i;
        }
        total += stringBuilder.substring(a, stringBuilder.length());
        return new StringBuilder(total).reverse().toString() + " VNĐ";
    }

    public String giaSach(String gia) {
        String[] gia1 = gia.split("[,]|\\D");
        String maMoi = "";
        for (int i = 0; i < gia1.length; i++) {
            maMoi += gia1[i];
        }
        return maMoi;
    }

    public boolean checkSoLuongSua(int soLuong, String maSach, int position) {
        for (int i = 0; i < TrangChuAcivity.sachList.size(); i++) {
            if (maSach.equals(TrangChuAcivity.sachList.get(i).getMa())) {

                int soLuongSach = TrangChuAcivity.sachList.get(i).getSoLuong();
                int soLuongHoaDon = Integer.parseInt(TrangChuAcivity.hoaDonChiTietList.get(position).getSoLuongMua());

                if (soLuong <= soLuongSach + soLuongHoaDon) {
                    TrangChuAcivity.bookDao.updateSoLuong(TrangChuAcivity.sachList.get(i).getMa(), soLuongSach + soLuongHoaDon - soLuong);
                    TrangChuAcivity.sachList.get(i).setSoLuong(soLuongSach + soLuongHoaDon - soLuong);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkXoaHoaDon() {
        for (int i = 0; i < TrangChuAcivity.hoaDonList.size(); i++) {
            for (int j = 0; j < TrangChuAcivity.hoaDonChiTietList.size(); j++) {
                if (TrangChuAcivity.hoaDonList.get(i).getMaHoaDon().equals(TrangChuAcivity.hoaDonChiTietList.get(j).getMaHoaDon())) {
                    return true;
                }
            }
        }

        return false;
    }


    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView textMa, textNgayMua;
        Button btn;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            textMa = itemView.findViewById(R.id.textMaHD);
            textNgayMua = itemView.findViewById(R.id.textNgay);
            btn = itemView.findViewById(R.id.btnHD);

        }

    }
}
