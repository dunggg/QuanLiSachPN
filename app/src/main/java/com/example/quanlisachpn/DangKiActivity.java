package com.example.quanlisachpn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlisachpn.model.User;
import com.example.quanlisachpn.SQL.MySql;
import com.example.quanlisachpn.SQL.UserDao;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class DangKiActivity extends AppCompatActivity {
    Button btnHuy, btnDangKi, btnGetImage;
    ImageView img;
    TextInputEditText txtUser, txtSdt, txtPass, txtConfimPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);

        anhXa();
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKiActivity.this, DangNhapActivity.class));
            }
        });

        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtUser.getText().toString().trim();
                String sdt = txtSdt.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();
                String confrimPass = txtConfimPass.getText().toString().trim();

                // kiểm tra điều kiện để thêm người dùng
                if (checkPass(pass, confrimPass) && pass.equals("")==false && confrimPass.equals("") == false && user.equals("") == false && checkSdt(sdt) && checkUser(user)) {
                    Toast.makeText(DangKiActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    ChaoActivity.userDao.insertUser(new User(user, sdt, pass));
                    ChaoActivity.userList.add(new User(user, sdt, pass));
                    startActivity(new Intent(getApplicationContext(), DangNhapActivity.class));
                } else if (user.length() == 0 || sdt.length() == 0 || pass.length() == 0 || confrimPass.length() == 0) {
                    Toast.makeText(DangKiActivity.this, "Bạn không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkUser(user) == false) {
                        Toast.makeText(DangKiActivity.this, "Username đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else if (checkSdt(sdt) == false) {
                        Toast.makeText(DangKiActivity.this, "Số điện thoại phải nhập đủ 10 số", Toast.LENGTH_SHORT).show();
                    } else if (checkPass(pass, confrimPass) == false) {
                        Toast.makeText(DangKiActivity.this, "Mật khẩu không khớp nhau", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public boolean checkSdt(String sdt) {
        String regax = "[0-9]{10}";
        return sdt.matches(regax);
    }

    public boolean checkPass(String pass1, String pass2) {
        if (pass1.equals(pass2)) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String user) {
        for (int i = 0; i < ChaoActivity.userList.size(); i++) {
            if (user.equals(ChaoActivity.userList.get(i).getName())) {
                return false;
            }
        }
        return true;
    }


    public void anhXa() {
        btnHuy = findViewById(R.id.btnDangKiHuy);
        btnDangKi = findViewById(R.id.btnDangKi2);
        img = findViewById(R.id.imagFile);
        txtUser = findViewById(R.id.txtDangKiUser);
        txtSdt = findViewById(R.id.txtDangKiSdt);
        txtPass = findViewById(R.id.txtDangKiPass);
        txtConfimPass = findViewById(R.id.txtDangKiConfimPass);
    }

}
