package com.example.quanlisachpn.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySql extends SQLiteOpenHelper {

    public MySql(@Nullable Context context) {
        super(context, "data.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_user = "CREATE TABLE user (userName TEXT primary key, password TEXT NOT NULL, sdt TEXT NOT NULL)";
        String table_book = "CREATE TABLE book (maSach TEXT primary key, tenSach TEXT NOT NULL, soLuong INTEGER, gia FLOAT NOT NULL, theloai TEXT NOT NULL)";
        String table_type = "CREATE TABLE type (maTheLoai TEXT primary key, tenTheLoai TEXT, soLuong TEXT)";
        String table_bill = "CREATE TABLE bill (maHoaDon TEXT primary key, ngayMua DATE NOT NULL )";
        String table_bill_detail = "CREATE TABLE billDetail (maHDCT INTEGER  PRIMARY KEY AUTOINCREMENT,  maHoaDon TEXT, maSach TEXT, soLuongMua INTERGER)";
        db.execSQL(table_user);
        db.execSQL(table_book);
        db.execSQL(table_type);
        db.execSQL(table_bill);
        db.execSQL(table_bill_detail);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
