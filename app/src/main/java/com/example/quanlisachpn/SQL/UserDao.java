package com.example.quanlisachpn.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.quanlisachpn.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private MySql mySql;
    SQLiteDatabase sqLiteDatabase;

    public UserDao(MySql mySql) {
        this.mySql = mySql;
        sqLiteDatabase = mySql.getWritableDatabase();
    }

    public void insertUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", user.getName());
        contentValues.put("password", user.getPassword());
        contentValues.put("sdt", user.getPhoneNumber());
        sqLiteDatabase.insert("user", null, contentValues);
    }

    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("user", null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                String password = cursor.getString(1);
                String sdt = cursor.getString(2);
                userList.add(new User(name, sdt, password));
            }
            cursor.close();
        }
        return userList;
    }

    public void delete(String username) {
        sqLiteDatabase.delete("user", "userName = ?", new String[]{username});
    }

    public void update(User user, String userName) {
        ContentValues values = new ContentValues();
        values.put("userName", user.getName());
        values.put("password", user.getPassword());
        values.put("sdt", user.getPhoneNumber());
        sqLiteDatabase.update("user", values, "userName=?", new String[]{userName});
    }

    public void updatePass(String pass, String userName) {
        ContentValues values = new ContentValues();
        values.put("password", pass);
        sqLiteDatabase.update("user", values, "userName=?", new String[]{userName});
    }

}
