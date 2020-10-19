package com.example.quanlisachpn.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlisachpn.model.HoaDon;
import com.example.quanlisachpn.model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.List;

public class BillDao {
    private MySql mySql;
    SQLiteDatabase db;

    public BillDao(MySql mySql) {
        this.mySql = mySql;
        db = mySql.getWritableDatabase();
    }

    public void insert(HoaDon hoaDon){
        ContentValues values = new ContentValues();
        values.put("maHoaDon",hoaDon.getMaHoaDon());
        values.put("ngayMua",hoaDon.getNgayMua());
        db.insert("bill",null,values);
    }

    public List<HoaDon> getData(){
        List<HoaDon> hoaDonList = new ArrayList<>();
        Cursor cursor = db.query("bill",null,null,null,null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String ma = cursor.getString(cursor.getColumnIndex("maHoaDon"));
                String date = cursor.getString(cursor.getColumnIndex("ngayMua"));
                hoaDonList.add(new HoaDon(ma,date));
            }
        }
        cursor.close();
        return hoaDonList;
    }

    public void delete(String maHoaDon){
        db.delete("bill","maHoaDon=?",new String[]{maHoaDon});
    }

    public void update(HoaDon hoaDon,String maHoaDon){
        ContentValues values = new ContentValues();
        values.put("maHoaDon",hoaDon.getMaHoaDon());
        db.update("bill",values,"maHoaDon=?",new String[]{maHoaDon});
    }

    public List<HoaDonChiTiet> thongKeHoaDon(){
        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
        String sql = "SELECT bill.maHoaDon, sum(billDetail.soLuongMua) as tongSoLuongMua, count(billDetail.maHDCT) as soHDCT FROM bill INNER JOIN billDetail on bill.maHoaDon = billDetail.maHoaDon GROUP By bill.maHoaDon;";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            String maHoaDon = cursor.getString(cursor.getColumnIndex("maHoaDon"));
            int soLuongMua = cursor.getInt(cursor.getColumnIndex("tongSoLuongMua"));
            int count = cursor.getInt(cursor.getColumnIndex("soHDCT"));
            hoaDonChiTietList.add(new HoaDonChiTiet(count,maHoaDon,"",String.valueOf(soLuongMua),""));
        }
        return hoaDonChiTietList;
    }

}
