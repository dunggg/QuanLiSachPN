package com.example.quanlisachpn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.quanlisachpn.SQL.MySql;
import com.example.quanlisachpn.SQL.UserDao;
import com.example.quanlisachpn.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChaoActivity extends AppCompatActivity {
    public static List<User> userList;
    public static UserDao userDao;
    MySql mySql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao);

        mySql = new MySql(this);
        userList = new ArrayList<>();
        userDao = new UserDao(mySql);
        userList = userDao.getAllUser();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(ChaoActivity.this,DangNhapActivity.class));
                finish();
            }
        }).start();
    }
}
