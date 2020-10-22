package com.example.quanlisachpn.SQL;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.quanlisachpn.model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.List;

public class BillDetailDao {
    private MySql mySql;
    SQLiteDatabase db;

    public BillDetailDao(MySql mySql) {
        this.mySql = mySql;
        db = mySql.getWritableDatabase();
    }

    public void insert(HoaDonChiTiet hoaDonChiTiet) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", hoaDonChiTiet.getMaHoaDon());
        values.put("maSach", hoaDonChiTiet.getMaSach());
        values.put("soLuongMua", hoaDonChiTiet.getSoLuongMua());
        values.put("gia", hoaDonChiTiet.getGia());
        db.insert("billDetail", null, values);
    }

    public List<HoaDonChiTiet> getData() {
        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
        Cursor cursor = db.query("billDetail", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int maHDCT = cursor.getInt(cursor.getColumnIndex("maHDCT"));
                String maHoaDon = cursor.getString(cursor.getColumnIndex("maHoaDon"));
                String maSach = cursor.getString(cursor.getColumnIndex("maSach"));
                int soLuong = cursor.getInt(cursor.getColumnIndex("soLuongMua"));
                String gia = cursor.getString(cursor.getColumnIndex("gia"));
                hoaDonChiTietList.add(new HoaDonChiTiet(maHDCT, maHoaDon, maSach, String.valueOf(soLuong), gia));
            }
        }
        cursor.close();
        return hoaDonChiTietList;
    }

    public void delete(String maHDCT) {
        db.delete("billDetail", "maHDCT=?", new String[]{maHDCT});
    }

    public void upadate(HoaDonChiTiet hoaDonChiTiet, String maHDCT) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", hoaDonChiTiet.getMaHoaDon());
        values.put("maSach", hoaDonChiTiet.getMaSach());
        values.put("soLuongMua", hoaDonChiTiet.getSoLuongMua());
        values.put("gia", hoaDonChiTiet.getGia());
        db.update("billDetail", values, "maHDCT=?", new String[]{maHDCT});
    }

    public List<HoaDonChiTiet> getDateHoaDonChiTiet() {
        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT billDetail.maHDCT, billDetail.maSach,billDetail.soLuongMua,bill.ngayMua, billDetail.gia, billDetail.maHoaDon " +
                "FROM billDetail INNER JOIN bill on billDetail.maHoaDon = bill.maHoaDon", null);
        while (cursor.moveToNext()) {
            int maHDCT = cursor.getInt(cursor.getColumnIndex("maHDCT"));
            String maHoaDon = cursor.getString(cursor.getColumnIndex("maHoaDon"));
            String maSach = cursor.getString(cursor.getColumnIndex("maSach"));
            int soLuong = cursor.getInt(cursor.getColumnIndex("soLuongMua"));
            String gia = cursor.getString(cursor.getColumnIndex("gia"));
            String ngaymua = cursor.getString(cursor.getColumnIndex("ngayMua"));
            hoaDonChiTietList.add(new HoaDonChiTiet(maHDCT, maHoaDon, maSach, String.valueOf(soLuong), gia, ngaymua));
        }
        cursor.close();
        return hoaDonChiTietList;
    }

    public List<HoaDonChiTiet> sachBanChay() {
        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
        String sql = "SELECT maSach, sum(soLuongMua) as tongSoLuongMua FROM billDetail GROUP by maSach";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String maSach = cursor.getString(cursor.getColumnIndex("maSach"));
            int tongSoLuongMua = cursor.getInt(cursor.getColumnIndex("tongSoLuongMua"));
            hoaDonChiTietList.add(new HoaDonChiTiet(0, "", maSach, String.valueOf(tongSoLuongMua), ""));
        }
        cursor.close();
        return hoaDonChiTietList;
    }

    public void updateMaHoaDon(String maHoaDon, String maHoaDonMoi) {
        List<String> stringList = new ArrayList<>();
        String sql = "SELECT maHDCT from billDetail WHERE maHoaDon = '" + maHoaDon + "'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            stringList.add(cursor.getString(cursor.getColumnIndex("maHDCT")));
        }
        cursor.close();
        for (int i = 0; i < stringList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("maHoaDon", maHoaDonMoi);
            db.update("billDetail", values, "maHDCT=?", new String[]{stringList.get(i)});
        }
    }

}
