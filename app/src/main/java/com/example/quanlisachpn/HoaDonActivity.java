package com.example.quanlisachpn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlisachpn.adapter.RecyclerHDCT;
import com.example.quanlisachpn.fragment.FragmentHdct;
import com.example.quanlisachpn.fragment.FragmentHoaDon;
import com.example.quanlisachpn.fragment.MyViewPage;
import com.example.quanlisachpn.model.HoaDon;
import com.example.quanlisachpn.adapter.RecyclerHoaDon;
import com.example.quanlisachpn.model.HoaDonChiTiet;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HoaDonActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static List<String> spinnerListMaSach;
    public static List<String> spinnerListHoaDon;
    SearchView searchView;
    List<String> listMa;
    CardView cardView;
    ListView lv;
    ArrayAdapter arrayAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    MyViewPage myViewPage;
    List<HoaDonChiTiet> thongKeHoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        anhXa();
        setSupportActionBar(toolbar);
        setDataSpinner();
        RecyclerHoaDon.checkRcvHd = false;
        RecyclerHDCT.checkButton = false;
        listMa = new ArrayList<>();

        myViewPage = new MyViewPage(getSupportFragmentManager(), MyViewPage.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(myViewPage);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listMa);
        lv.setAdapter(arrayAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int position = checkMa(query.trim());
                if (position < 0) {
                    Toast.makeText(HoaDonActivity.this, "Không tìm thấy mã hóa đơn", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog(thongKeHoaDon.get(position).getMaHoaDon(), thongKeHoaDon.get(position).getSoLuongMua(), thongKeHoaDon.get(position).getId());
                    searchView.setQuery("", true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                if (listMa.size() == 0) {
                    cardView.setVisibility(View.GONE);
                } else {
                    cardView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    cardView.setVisibility(View.GONE);
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ma = listMa.get(position);
                int i = checkMa(ma);
                showDialog(thongKeHoaDon.get(i).getMaHoaDon(), thongKeHoaDon.get(i).getSoLuongMua(), thongKeHoaDon.get(i).getId());
                // set thanh tìm kiếm về giá trị ban đầu
                searchView.setQuery("", true);
            }
        });

    }

    public void showDialog(String maHd, String soluong, int soLuongHDCT) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonActivity.this);
        builder.setMessage("Mã hóa đơn: " + maHd
                + "\nSố lượng sách đã bán: " + soluong
                + "\nSố lượng hóa đơn chi tiết: " + soLuongHDCT
        );
        builder.show();
    }

    public void filter(String text) {
        if (text.trim().length() == 0) {
            listMa.clear();
        } else {
            listMa.clear();
            for (int i = 0; i < TrangChuAcivity.hoaDonList.size(); i++) {
                listMa.add(TrangChuAcivity.hoaDonList.get(i).getMaHoaDon());
            }
            for (int i = 0; i < listMa.size(); i++) {
                String ma = listMa.get(i);
                if (listMa.get(i).contains(text) == false) {
                    listMa.remove(i);
                    i--;
                }
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }

    public int checkMa(String ma) {
        thongKeHoaDon = new ArrayList<>();
        thongKeHoaDon = TrangChuAcivity.billDao.thongKeHoaDon();
        for (int i = 0; i < thongKeHoaDon.size(); i++) {
            if (ma.equals(thongKeHoaDon.get(i).getMaHoaDon())) {
                return i;
            }
        }
        return -1;
    }


    public void anhXa() {
        toolbar = findViewById(R.id.toolbarHD);
        searchView = findViewById(R.id.searchHD);
        lv = findViewById(R.id.lvHD);
        cardView = findViewById(R.id.carviewHD);
        viewPager = findViewById(R.id.viewpageHoaDon);
        tabLayout = findViewById(R.id.tablayoutHoaDon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.opiton_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    String textButton = "abc";

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemThem:
                if (viewPager.getCurrentItem() == 0) {
                    if (spinnerListMaSach.size() == 0) {
                        Toast.makeText(this, "Bạn cần thêm sách trước khi thêm hóa đơn", Toast.LENGTH_SHORT).show();
                    } else {
                        // thêm hóa đơn
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Thêm hóa đơn");
                        builder.setCancelable(false);
                        LayoutInflater inflater = LayoutInflater.from(this);
                        View view = inflater.inflate(R.layout.them_hoadon, null);
                        builder.setView(view);

                        final TextInputEditText txtMaHoaDon, txtSoLuong;
                        Spinner spinner = view.findViewById(R.id.spinnerThemHD);
                        txtMaHoaDon = view.findViewById(R.id.txtMaHDThemHD);
                        txtSoLuong = view.findViewById(R.id.txtSoLuongThemHD);
                        Button btnThem = view.findViewById(R.id.btnThemThemHd);
                        Button btnHuy = view.findViewById(R.id.btnHuyThemHD);

                        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerListMaSach);
                        spinner.setAdapter(arrayAdapter);
                        final String[] maSach = {null};
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                maSach[0] = spinnerListMaSach.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                        final Dialog dialog = builder.create();

                        btnThem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String ma = txtMaHoaDon.getText().toString().trim();
                                String soLuong = txtSoLuong.getText().toString().trim();
                                String ngay = getDate();
                                boolean checkSoLuong = checkSoLuong(Integer.parseInt(soLuong), maSach[0]);

                                if (checkMa(ma) < 0 && soLuong.length() > 0 && Integer.parseInt(soLuong) > 0 && ngay.length() > 0 && checkSoLuong == true) {
                                    TrangChuAcivity.billDetailDao.insert(new HoaDonChiTiet(0, ma, maSach[0], soLuong, String.valueOf(getGia(maSach[0], soLuong))));

                                    // lấy id từ csdl về để truyền vào list
                                    TrangChuAcivity.hoaDonChiTietList = TrangChuAcivity.billDetailDao.getData();
                                    int id = TrangChuAcivity.hoaDonChiTietList.get(TrangChuAcivity.hoaDonChiTietList.size() - 1).getId();

                                    // lấy giá đã chuyển đổi truyền vào list
                                    String gia = convertGia(String.valueOf(getGia(maSach[0], soLuong)));

                                    TrangChuAcivity.hoaDonList.add(new HoaDon(ma, ngay));
                                    TrangChuAcivity.billDao.insert(new HoaDon(ma, ngay));
                                    FragmentHdct.hoaDonChiTietList.add(new HoaDonChiTiet(id, ma, maSach[0], soLuong, gia, ngay));
                                    TrangChuAcivity.hoaDonList.get(TrangChuAcivity.hoaDonList.size() - 1).textButton = textButton;
                                    FragmentHdct.hoaDonChiTietList.get(FragmentHdct.hoaDonChiTietList.size() - 1).textButton = textButton;
                                    FragmentHoaDon.recyclerHoaDon.notifyItemInserted(TrangChuAcivity.hoaDonList.size() - 1);
                                    FragmentHdct.recyclerHDCT.notifyItemInserted(FragmentHdct.hoaDonChiTietList.size() - 1);
                                    Toast.makeText(HoaDonActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                } else {
                                    Toast.makeText(HoaDonActivity.this, "Bạn không được để trống,số lượng lớn hơn 0 (không nhập nhiều hơn số lượng sách) và mã hóa đơn không được trùng", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        btnHuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                        dialog.show();

                    }
                } else {
                    if (spinnerListMaSach.size() != 0 && spinnerListHoaDon.size() != 0) {
                        // thêm hóa đơn chi tiết
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Thêm hóa đơn chi tiết");
                        builder.setCancelable(false);
                        LayoutInflater inflater = LayoutInflater.from(this);
                        View view = inflater.inflate(R.layout.them_hdct, null);
                        builder.setView(view);

                        final TextInputEditText txtSoLuong;
                        Spinner spinner = view.findViewById(R.id.spinnerMaSachThemHDCT);
                        Spinner spinner1 = view.findViewById(R.id.spinnerMaHDhemHDCT);
                        txtSoLuong = view.findViewById(R.id.txtSoLuongThemHDCT);
                        Button btnThem = view.findViewById(R.id.btnThemThemHDCT);
                        Button btnHuy = view.findViewById(R.id.btnHuyThemHDCT);

                        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerListHoaDon);
                        spinner1.setAdapter(arrayAdapter1);

                        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerListMaSach);
                        spinner.setAdapter(arrayAdapter);
                        final String[] maSach = new String[1];
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                maSach[0] = spinnerListMaSach.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        final String maHoaDon[] = new String[1];
                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                maHoaDon[0] = spinnerListHoaDon.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                        final Dialog dialog = builder.create();

                        btnThem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String soLuong = txtSoLuong.getText().toString().trim();
                                String ngay = getDate();
                                boolean checkSoLuong = checkSoLuong(Integer.parseInt(soLuong), maSach[0]);

                                if (soLuong.length() > 0 && Integer.parseInt(soLuong) > 0 && checkSoLuong == true) {
                                    TrangChuAcivity.billDetailDao.insert(new HoaDonChiTiet(0, maHoaDon[0], maSach[0], soLuong, String.valueOf(getGia(maSach[0], soLuong))));
                                    // lấy id để add vào list
                                    TrangChuAcivity.hoaDonChiTietList = TrangChuAcivity.billDetailDao.getData();
                                    int id = TrangChuAcivity.hoaDonChiTietList.get(TrangChuAcivity.hoaDonChiTietList.size() - 1).getId();

                                    // add gia da chuyen doi vao list k add vao db
                                    String gia = convertGia(String.valueOf(getGia(maSach[0], soLuong)));

                                    FragmentHdct.hoaDonChiTietList.add(new HoaDonChiTiet(id, maHoaDon[0], maSach[0], soLuong, gia, ngay));
                                    FragmentHdct.hoaDonChiTietList.get(FragmentHdct.hoaDonChiTietList.size() - 1).textButton = textButton;
                                    FragmentHdct.recyclerHDCT.notifyItemInserted(FragmentHdct.hoaDonChiTietList.size() - 1);
                                    Toast.makeText(HoaDonActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                } else {
                                    Toast.makeText(HoaDonActivity.this, "Bạn không được để trống,số lượng lớn hơn 0 (không nhập nhiều hơn số lượng sách)", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        btnHuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                        dialog.show();
                    } else {
                        Toast.makeText(this, "Bạn phải có hóa đơn và sách mới có thể thêm hóa đơn chi tiết", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.itemSua:
                showView("Edit");
                textButton = "edit";
                FragmentHoaDon.recyclerHoaDon.notifyDataSetChanged();
                FragmentHdct.recyclerHDCT.notifyDataSetChanged();
                break;
            case R.id.itemXoa:
                showView("Delete");
                textButton = "delete";
                FragmentHoaDon.recyclerHoaDon.notifyDataSetChanged();
                FragmentHdct.recyclerHDCT.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setDataSpinner() {
        spinnerListMaSach = new ArrayList<>();
        for (int i = 0; i < TrangChuAcivity.sachList.size(); i++) {
            spinnerListMaSach.add(TrangChuAcivity.sachList.get(i).getMa());
        }
        spinnerListHoaDon = new ArrayList<>();
        for (int i = 0; i < TrangChuAcivity.hoaDonList.size(); i++) {
            spinnerListHoaDon.add(TrangChuAcivity.hoaDonList.get(i).getMaHoaDon());
        }
    }

    public void showView(String textButton) {
        for (int i = 0; i < FragmentHdct.hoaDonChiTietList.size(); i++) {
            FragmentHdct.hoaDonChiTietList.get(i).textButton = textButton;
        }
        for (int i = 0; i < TrangChuAcivity.hoaDonList.size(); i++) {
            TrangChuAcivity.hoaDonList.get(i).textButton = textButton;
        }
        RecyclerHoaDon.checkRcvHd = true;
        RecyclerHDCT.checkButton = true;
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static int getGia(String maSach, String soLuong) {
        int gia = TrangChuAcivity.bookDao.getGia(maSach);
        int tong = gia * Integer.parseInt(soLuong);
        return tong;
    }

    public static String convertGia(String gia) {
        String total = "";
        if (gia.length() <= 3) {
            return gia;
        }
        // đảo ngược chuỗi string
        String stringBuilder = new StringBuilder(gia).reverse().toString();
        int a = 0;
        for (int i = 3; i < stringBuilder.length(); i += 3) {
            // thêm dấu phẩy vào sau mỗi 3 số
            total += stringBuilder.substring(a, i) + ",";
            a = i;
        }
        // cộng nốt các số còn lại vào chuỗi
        total += stringBuilder.substring(a, stringBuilder.length());
        return new StringBuilder(total).reverse().toString();
    }

    // get data từ db về có cả chữ vnd nên phải dùng hàm này để bỏ chữ vnd đi
//    public static String giaSach(String gia) {
//        String[] gia1 = gia.split("[,]|\\D");
//        String maMoi = "";
//        for (int i = 0; i < gia1.length; i++) {
//            maMoi += gia1[i];
//        }
//        return maMoi;
//    }

    public static boolean checkSoLuong(int soLuong, String maSach) {
        for (int i = 0; i < TrangChuAcivity.sachList.size(); i++) {
            if (maSach.equals(TrangChuAcivity.sachList.get(i).getMa())) {
                if (soLuong <= TrangChuAcivity.sachList.get(i).getSoLuong()) {
                    TrangChuAcivity.bookDao.updateSoLuong(TrangChuAcivity.sachList.get(i).getMa(), TrangChuAcivity.sachList.get(i).getSoLuong() - soLuong);
                    TrangChuAcivity.sachList.get(i).setSoLuong(TrangChuAcivity.sachList.get(i).getSoLuong() - soLuong);
                    return true;
                }
            }
        }
        return false;
    }

}
