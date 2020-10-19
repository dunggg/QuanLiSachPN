package com.example.quanlisachpn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlisachpn.SQL.BillDao;
import com.example.quanlisachpn.SQL.BillDetailDao;
import com.example.quanlisachpn.SQL.BookDao;
import com.example.quanlisachpn.SQL.BookTypeDao;
import com.example.quanlisachpn.SQL.MySql;
import com.example.quanlisachpn.SQL.UserDao;
import com.example.quanlisachpn.model.HoaDon;
import com.example.quanlisachpn.model.HoaDonChiTiet;
import com.example.quanlisachpn.model.Sach;
import com.example.quanlisachpn.model.TheLoai;
import com.example.quanlisachpn.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class TrangChuAcivity extends AppCompatActivity {
    LinearLayout layoutNguoiDung, layoutGioiThieu, layoutHoaDon, layoutTheLoai, layoutSach, layoutThongKe;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    public static List<Sach> sachList;
    public static List<TheLoai> theLoaiList;
    public static List<HoaDon> hoaDonList;
    public static List<HoaDonChiTiet> hoaDonChiTietList;
    public static BookTypeDao bookTypeDao;
    public static BookDao bookDao;
    public static BillDao billDao;
    public static BillDetailDao billDetailDao;
    MySql mySql;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu_acivity);

        anhXa();
        // khởi tạo các class và list
        initialization();
        theLoaiList = bookTypeDao.getData();
        sachList = bookDao.getData();
        hoaDonList = billDao.getData();
        hoaDonChiTietList = billDetailDao.getData();
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String user = bundle.getString("user");
        final String pass = bundle.getString("pass");
        final int index = bundle.getInt("index", -1);


        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHoTro:
                        Toast.makeText(TrangChuAcivity.this, "Chức năng đang trong quá trình hoàn thiện", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.itemDoiMK:
                        if (index == 0) {
                            Toast.makeText(TrangChuAcivity.this, "Đây là tài khoản admin hiện tại chưa thể đổi mật khẩu", Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(TrangChuAcivity.this);
                            builder.setTitle("Đổi mật khẩu");
                            builder.setCancelable(false);
                            View view = LayoutInflater.from(TrangChuAcivity.this).inflate(R.layout.doimatkhau, null);
                            final TextInputEditText textPass, textPassNew, textConfirmPass;
                            textPass = view.findViewById(R.id.textPass);
                            textPassNew = view.findViewById(R.id.textPassNew);
                            textConfirmPass = view.findViewById(R.id.textConfirmPass);
                            builder.setView(view);
                            builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String passDoiMK = textPass.getText().toString().trim();
                                    String passNew = textPassNew.getText().toString().trim();
                                    String confirmPass = textConfirmPass.getText().toString().trim();
                                    if (pass.equals(passDoiMK)) {
                                        if (passNew.equals(confirmPass)) {
                                            ChaoActivity.userList.get(index - 1).setPassword(passNew);
                                            ChaoActivity.userDao.updatePass(passNew, user);
                                            Toast.makeText(TrangChuAcivity.this, "Đổi mật khẩu thành công !", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(TrangChuAcivity.this, "Đổi mật khẩu thất bại. Mật khẩu mới bạn nhập không khớp nhau", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(TrangChuAcivity.this, " Đổi mật khẩu thất bại. Bạn đã nhập sai password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                        }
                        break;
                    case R.id.itemDangXuat:
                        startActivity(new Intent(TrangChuAcivity.this, DangNhapActivity.class));
                        finish();
                        break;
                    case R.id.itemThoat:
                        onBackPressed();
                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });


        layoutThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrangChuAcivity.this, ThongKeActivity.class));
            }
        });

        layoutTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrangChuAcivity.this, TheLoaiActivity.class));
            }
        });

        layoutSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrangChuAcivity.this, QuanLiSachActivity.class));
            }
        });

        layoutHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrangChuAcivity.this, HoaDonActivity.class));
            }
        });
        layoutGioiThieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrangChuAcivity.this, GioiThieuActivity.class));
            }
        });

        layoutNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.equals("admin") && pass.equals("admin")) {
                    startActivity(new Intent(TrangChuAcivity.this, QlNguoiDungActivity.class));
                } else {
                    Toast.makeText(TrangChuAcivity.this, "Bạn phải đăng nhập bằng tài khoản admin mới sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void anhXa() {
        layoutNguoiDung = findViewById(R.id.layoutNguoiDung);
        layoutGioiThieu = findViewById(R.id.layoutGioiThieu);
        layoutHoaDon = findViewById(R.id.layoutHoaDon);
        layoutSach = findViewById(R.id.layoutSach);
        layoutTheLoai = findViewById(R.id.layoutTheLoai);
        layoutThongKe = findViewById(R.id.layoutThongKe);
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbarTrangChu);
    }

    public void initialization() {
        sachList = new ArrayList<>();
        theLoaiList = new ArrayList<>();
        hoaDonList = new ArrayList<>();
        hoaDonChiTietList = new ArrayList<>();
        mySql = new MySql(this);
        bookDao = new BookDao(mySql);
        bookTypeDao = new BookTypeDao(mySql);
        billDao = new BillDao(mySql);
        billDetailDao = new BillDetailDao(mySql);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(TrangChuAcivity.this);
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
//        super.onBackPressed();
    }
}
