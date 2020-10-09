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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlisachpn.QuanLiSachActivity;
import com.example.quanlisachpn.TheLoaiActivity;
import com.example.quanlisachpn.TrangChuAcivity;
import com.example.quanlisachpn.model.Sach;
import com.example.quanlisachpn.model.TheLoai;
import com.example.quanlisachpn.R;

import java.util.List;

public class RecyclerTheLoai extends RecyclerView.Adapter<RecyclerTheLoai.ViewHolder> {
    List<TheLoai> theLoaiList;
    Context context;
    int layout;
    public static boolean checkTL = false;

    public RecyclerTheLoai(List<TheLoai> theLoaiList, Context context, int layout) {
        this.theLoaiList = theLoaiList;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TheLoai theLoai = theLoaiList.get(position);
        holder.textSoluong.setText("Số lượng: " + String.valueOf(theLoai.getSoLuong()));
        holder.textMa.setText("Mã thể loại: " + theLoai.getMa());
        holder.textTen.setText("Tên thể loại: " + theLoai.getTenTheLoai());
        if (checkTL == true) {
            holder.btnTL.setText(theLoai.textButton);
            holder.btnTL.setVisibility(theLoai.visibility);
        }

        holder.btnTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int i = holder.getAdapterPosition();
                String text = holder.btnTL.getText().toString().trim();
                final int soLuong = theLoaiList.get(holder.getAdapterPosition()).getSoLuong();
                if (text.equalsIgnoreCase("Edit")) {

                    final String id = theLoaiList.get(i).getMa();
                    final String tenTheLoai = theLoaiList.get(i).getTenTheLoai();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View view = inflater.inflate(R.layout.them_theloai, null);
                    builder.setView(view);
                    builder.setCancelable(false);
                    builder.setTitle("Sửa thể loại có mã là : " + id);

                    final TextView txtName = view.findViewById(R.id.txtThemTenTL);
                    final TextView txtMa = view.findViewById(R.id.txtThemMaTL);


                    builder.setNegativeButton("Lưu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = txtName.getText().toString().trim();
                            String ma = txtMa.getText().toString().trim();

                            if (checkTen(name,i) && checkMa(ma,i) < 0) {
                                theLoaiList.remove(holder.getAdapterPosition());
                                theLoaiList.add(holder.getAdapterPosition(), new TheLoai(ma, name, soLuong));
                                theLoaiList.get(i).textButton = "Edit";
                                updateTheLoaiSach(tenTheLoai, name);
                                notifyItemChanged(holder.getAdapterPosition());
                                TrangChuAcivity.bookTypeDao.update(new TheLoai(ma, name, soLuong), id);
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Sửa thất bại. Bạn không được để trống hoặc mã,tên đã tồn tại", Toast.LENGTH_SHORT).show();
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
                    if (TrangChuAcivity.theLoaiList.get(i).getSoLuong() == 0) {
                        TrangChuAcivity.bookTypeDao.delete(theLoaiList.get(holder.getAdapterPosition()).getMa());
                        TrangChuAcivity.theLoaiList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    } else {
                        Toast.makeText(context, "Bạn chỉ có thể xóa thể loại khi số lượng bằng 0", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return theLoaiList.size();
    }

    public int checkMa(String ma,int position) {

        if (ma.equals("")) {
            return 0;
        }

        else if (ma.equals(theLoaiList.get(position).getMa())){
            return -1;
        }

        for (int i = 0; i < TrangChuAcivity.theLoaiList.size(); i++) {
            if (ma.equals(TrangChuAcivity.theLoaiList.get(i).getMa())) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkTen(String ten,int position) {

        if (ten.equals("")) {
            return false;
        }

        else if (ten.equals(theLoaiList.get(position).getTenTheLoai())){
            return true;
        }

        for (int i = 0; i < TrangChuAcivity.theLoaiList.size(); i++) {
            if (ten.equals(TrangChuAcivity.theLoaiList.get(i).getTenTheLoai())) {
                return false;
            }
        }
        return true;
    }

    public void updateTheLoaiSach(String tenTheLoai, String tenTheLoaiMoi) {
        for (int i = 0; i < TrangChuAcivity.sachList.size(); i++) {
            if (tenTheLoai.equals(TrangChuAcivity.sachList.get(i).getTheLoai())) {
                TrangChuAcivity.bookDao.updateTheLoai(TrangChuAcivity.sachList.get(i).getMa(), tenTheLoaiMoi);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTen, textSoluong, textMa;
        Button btnTL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTen = itemView.findViewById(R.id.textTenTL);
            textMa = itemView.findViewById(R.id.textMaTL);
            textSoluong = itemView.findViewById(R.id.textSoLuongTL);
            btnTL = itemView.findViewById(R.id.btnTL);
        }
    }
}
