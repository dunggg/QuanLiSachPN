package com.example.quanlisachpn.SQL;

        import android.content.ContentValues;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.drawable.Icon;

        import com.example.quanlisachpn.model.Sach;

        import java.util.ArrayList;
        import java.util.List;

public class BookDao {
    private MySql mySql;
    SQLiteDatabase db;

    public BookDao(MySql mySql) {
        this.mySql = mySql;
        db = mySql.getWritableDatabase();
    }

    public void insert(Sach sach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maSach", sach.getMa());
        contentValues.put("tenSach", sach.getTen());
        contentValues.put("soLuong", sach.getSoLuong());
        contentValues.put("gia", sach.getGia());
        contentValues.put("theLoai", sach.getTheLoai());
        db.insert("book", null, contentValues);
    }

    public List<Sach> getData() {
        List<Sach> sachList = new ArrayList<>();
        Cursor cursor = db.query("book", null, null, null, null, null, null);
        String ma, ten, gia, theLoai;
        int soLuong;
        while (cursor.moveToNext()) {
            ma = cursor.getString(cursor.getColumnIndex("maSach"));
            ten = cursor.getString(cursor.getColumnIndex("tenSach"));
            soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
            gia = String.valueOf(cursor.getString(cursor.getColumnIndex("gia")));
            theLoai = cursor.getString(cursor.getColumnIndex("theloai"));
            sachList.add(new Sach(ten,ma,soLuong,gia,theLoai));
        }
        cursor.close();
        return sachList;
    }

    public void delete(String maSach){
        db.delete("book","maSach=?",new String[]{maSach});
    }

    public void update(Sach sach,String maSach){
        ContentValues values = new ContentValues();
        values.put("maSach",sach.getMa());
        values.put("tenSach",sach.getTen());
        values.put("soLuong",sach.getSoLuong());
        values.put("gia",sach.getGia());
        values.put("theloai",sach.getTheLoai());
        db.update("book",values,"maSach=?",new String[]{maSach});

    }

    public void updateTheLoai(String maSach,String theLoaiMoi){
        ContentValues values = new ContentValues();
        values.put("theloai",theLoaiMoi);
        db.update("book",values,"maSach=?",new String[]{maSach});
    }

    public void updateSoLuong(String maSach, int soLuong){
        ContentValues values = new ContentValues();
        values.put("soLuong",soLuong);
        db.update("book",values,"maSach=?",new String[]{maSach});
    }

    public int getGia(String maSach){
        String sql = "SELECT maSach, gia FROM book WHERE maSach = '"+maSach+"'";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        int gia = cursor.getInt(cursor.getColumnIndex("gia"));
        return gia;
    }
}
