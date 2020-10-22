package com.example.quanlisachpn.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.quanlisachpn.model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class BookTypeDao {
    private MySql mySql;
    SQLiteDatabase database;

    public BookTypeDao(MySql mySql) {
        this.mySql = mySql;
        database = mySql.getWritableDatabase();
    }

    public void insert(TheLoai theLoai) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenTheLoai", theLoai.getTenTheLoai());
        contentValues.put("maTheLoai", theLoai.getMa());
        contentValues.put("soLuong", theLoai.getSoLuong());
        database.insert("type", null, contentValues);
    }

    public List<TheLoai> getData() {
        List<TheLoai> theLoaiList = new ArrayList<>();
        Cursor cursor = database.query("type", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String ten = cursor.getString(cursor.getColumnIndex("tenTheLoai"));
            String ma = cursor.getString(cursor.getColumnIndex("maTheLoai"));
            int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
            theLoaiList.add(new TheLoai(ma, ten, soLuong));
        }
        cursor.close();
        return theLoaiList;
    }

    public void updateSoLuong(String maTheLoai, int soLuong) {
        ContentValues values = new ContentValues();
        values.put("soLuong", soLuong);
        database.update("type", values, "maTheLoai = ?", new String[]{String.valueOf(maTheLoai)});
    }

    public void update(TheLoai theLoai, String maTheLoai) {
        ContentValues values = new ContentValues();
        values.put("maTheLoai", theLoai.getMa());
        values.put("tenTheLoai", theLoai.getTenTheLoai());
        values.put("soLuong", theLoai.getMa());
        database.update("type", values, "maTheLoai=?", new String[]{maTheLoai});
    }

    public void delete(String maTheLoai) {
        database.delete("type", "maTheLoai=?", new String[]{maTheLoai});
    }
}
