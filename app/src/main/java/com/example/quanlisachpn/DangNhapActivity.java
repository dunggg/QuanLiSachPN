package com.example.quanlisachpn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlisachpn.model.HoaDon;
import com.example.quanlisachpn.model.Sach;
import com.example.quanlisachpn.model.TheLoai;
import com.example.quanlisachpn.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DangNhapActivity extends AppCompatActivity {
    TextView textView;
    Button btnDangKi, btnDangNhap;
    CheckBox checkBox;
    TextInputEditText txtUser, txtPass;
    SharedPreferences sharedPreferences;
    Boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);


        anhXa();
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        check = sharedPreferences.getBoolean("checkbox", false);
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhapActivity.this, DangKiActivity.class));

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")));
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtUser.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();
                if (checkUserPass(user, pass)>=0) {
                    Intent intent = new Intent(DangNhapActivity.this, TrangChuAcivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("user", user);
                    bundle.putString("pass", pass);
                    bundle.putInt("index",checkUserPass(user,pass));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else if (checkUserPass(user, pass)<0) {
                    Toast.makeText(DangNhapActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (check == true) {
            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
            txtUser.setText(sharedPreferences.getString("username", "null"));
            txtPass.setText(sharedPreferences.getString("password", "null"));
            checkBox.setChecked(true);
        }
    }

    public int checkUserPass(String user, String pass) {
        if (user.equals("admin") && pass.equals("admin")) {
            return 0;
        } else {
            for (int i = 1; i <=ChaoActivity.userList.size(); i++) {
                if (user.equals(ChaoActivity.userList.get(i-1).getName()) && pass.equals(ChaoActivity.userList.get(i-1).getPassword())) {
                    return i;
                }
            }
            return -1;
        }

    }

    private void anhXa() {
        textView = findViewById(R.id.textDangNhap);
        btnDangKi = findViewById(R.id.btnDangKi);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        checkBox = findViewById(R.id.cbDangNhap);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (checkBox.isChecked() == true) {
            editor.putString("username", txtUser.getText().toString().trim());
            editor.putString("password", txtPass.getText().toString().trim());
            editor.putBoolean("checkbox", true);
            editor.commit();
        } else {
            editor.clear();
            editor.commit();
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(DangNhapActivity.this);
        builder2.setTitle("Thoát");
        builder2.setIcon(R.drawable.warning);
        builder2.setCancelable(false);
        builder2.setMessage("Bạn có chắc chắn muốn thoát ứng dụng không ?");
        builder2.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder2.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder2.show();
    }
}
