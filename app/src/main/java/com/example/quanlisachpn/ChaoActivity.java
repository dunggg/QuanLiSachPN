package com.example.quanlisachpn;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.quanlisachpn.SQL.MySql;
import com.example.quanlisachpn.SQL.UserDao;
import com.example.quanlisachpn.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChaoActivity extends AppCompatActivity {
    public static List<User> userList;
    public static UserDao userDao;
    MySql mySql;
    ImageView img,img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao);

        img = findViewById(R.id.imgChao);
        img2 = findViewById(R.id.imgChao2);

        mySql = new MySql(this);
        userList = new ArrayList<>();
        userDao = new UserDao(mySql);
        userList = userDao.getAllUser();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Animation animation = AnimationUtils.loadAnimation(ChaoActivity.this,R.anim.animation);
                    animation.setDuration(5000);
                    img.startAnimation(animation);
                    img2.startAnimation(animation);
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(ChaoActivity.this,DangNhapActivity.class));
                finish();
            }
        }).start();
    }
}
