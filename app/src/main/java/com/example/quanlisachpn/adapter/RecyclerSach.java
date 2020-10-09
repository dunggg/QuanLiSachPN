package com.example.quanlisachpn.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlisachpn.QuanLiSachActivity;
import com.example.quanlisachpn.TrangChuAcivity;
import com.example.quanlisachpn.model.Sach;
import com.example.quanlisachpn.R;

import java.util.List;

public class RecyclerSach extends RecyclerView.Adapter<RecyclerSach.ViewHolder> {

    List<Sach> sachList;
    Context context;
    int layout;
    public static boolean checkVisibility = false;

    public RecyclerSach(List<Sach> sachList, Context context, int layout) {
        this.sachList = sachList;
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Sach sach = sachList.get(position);
        holder.textTheLoai.setText("Thể loại: " + sach.getTheLoai());
        holder.textTen.setText("Tên sách: " + sach.getTen());
        holder.textSoLuong.setText("Số lượng: " + String.valueOf(sach.getSoLuong()));
        holder.textMa.setText("Mã sách: " + sach.getMa());
        holder.textGia.setText("Giá: " + sach.getGia());
        holder.btn.setText(sach.getTextButton());
        if (checkVisibility == true) {
            holder.btn.setVisibility(sach.getVisibility());
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int i = holder.getAdapterPosition();
                String text = holder.btn.getText().toString().trim();
                if (text.equalsIgnoreCase("Edit")) {

                    final String id = sachList.get(i).getMa();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View view = inflater.inflate(R.layout.them_sach, null);
                    builder.setView(view);
                    builder.setCancelable(false);
                    builder.setTitle("Sửa sách có mã là : " + id);

                    final Spinner spinner = view.findViewById(R.id.spinnerThemSach);

                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, QuanLiSachActivity.spinnerList);
                    spinner.setAdapter(arrayAdapter);
                    final TextView textTen, textMa, textSoLuong, textGia;
                    textTen = view.findViewById(R.id.textThemTenSach);
                    textMa = view.findViewById(R.id.textThemMaSach);
                    textSoLuong = view.findViewById(R.id.textThemSoLuong);
                    textGia = view.findViewById(R.id.textThemGia);

                    // lấy giá trị của spinner
                    final String[] theloai = new String[1];
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            theloai[0] = QuanLiSachActivity.spinnerList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    builder.setNegativeButton("Lưu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String ten = textTen.getText().toString().trim();
                            String ma = textMa.getText().toString().trim();
                            String soLuong = textSoLuong.getText().toString().trim();
                            String gia = textGia.getText().toString().trim();

                            if (checkMa(ma, i) < 0 && checkTen(ten,i) && soLuong.length() != 0 && gia.length() != 0 && Integer.parseInt(soLuong) > 0 && Integer.parseInt(gia) > 0) {
                                Toast.makeText(context, "Sửa sách thành công", Toast.LENGTH_SHORT).show();
                                sachList.remove(holder.getAdapterPosition());
                                sachList.add(holder.getAdapterPosition(), new Sach(ten, ma, Integer.parseInt(soLuong), gia, theloai[0]));
                                sachList.get(i).setTextButton("Edit");
                                notifyItemChanged(holder.getAdapterPosition());
                                TrangChuAcivity.bookDao.update(new Sach(ten, ma, Integer.parseInt(soLuong), gia, theloai[0]), id);
                            } else if (ma.length() == 0 || ten.length() == 0 | soLuong.length() == 0 || gia.length() == 0) {
                                Toast.makeText(context, "Sửa thất bại. Bạn không được để trống", Toast.LENGTH_SHORT).show();
                            } else {
                                if (checkTen(ten,i) == false) {
                                    Toast.makeText(context, "Sửa thất bại. Tên sách đã tồn tại", Toast.LENGTH_SHORT).show();
                                } else if (checkMa(ma, 1) >= 0) {
                                    Toast.makeText(context, "Sửa thất bại. Mã sách đã tồn tại", Toast.LENGTH_SHORT).show();
                                } else if (Integer.parseInt(soLuong) > 0 || Integer.parseInt(gia) > 0) {
                                    Toast.makeText(context, "Sửa thất bại. Số lượng và giá phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                } else {
                    TrangChuAcivity.bookDao.delete(sachList.get(holder.getAdapterPosition()).getMa());
                    TrangChuAcivity.sachList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sachList.size();
    }

    public int checkMa(String ma, int position) {
        if (ma.equals(sachList.get(position).getMa())) {
            return -1;
        }

        for (int i = 0; i < sachList.size(); i++) {
            if (ma.equals(sachList.get(i).getMa())) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkTen(String ten,int position) {

        if (ten.equals(sachList.get(position).getTen())){
            return true;
        }

        for (int i = 0; i < sachList.size(); i++) {
            if (ten.equals(sachList.get(i).getTen())) {
                return false;
            }
        }
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTen, textMa, textSoLuong, textGia, textTheLoai;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textGia = itemView.findViewById(R.id.textGiaSach);
            textMa = itemView.findViewById(R.id.textMaSach);
            textSoLuong = itemView.findViewById(R.id.textSoLuongSach);
            textTen = itemView.findViewById(R.id.textTenSach);
            textTheLoai = itemView.findViewById(R.id.textTheLoai);
            btn = itemView.findViewById(R.id.rcvSachBtn);
        }
    }
}
