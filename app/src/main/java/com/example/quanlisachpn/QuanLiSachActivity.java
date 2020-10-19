package com.example.quanlisachpn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlisachpn.model.Sach;
import com.example.quanlisachpn.adapter.RecyclerSach;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;

public class QuanLiSachActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerSach recyclerSach;
    List<Sach> sachList;
    Toolbar toolbar;
    Set<String> data;
    public static List<String> spinnerList;
    ListView lv;
    List<String> listMa;
    SearchView searchView;
    CardView cardView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_sach);

        anhXa();
        setSupportActionBar(toolbar);
        setDataSpinner();
        sachList = new ArrayList<>();
        sachList = TrangChuAcivity.sachList;
        RecyclerSach.checkVisibility = false;

        recyclerSach = new RecyclerSach(sachList, this, R.layout.recyclerview_sach);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new ScaleInAnimator());
        recyclerView.setAdapter(new ScaleInAnimationAdapter(recyclerSach));

        listMa = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listMa);
        lv.setAdapter(arrayAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int position = checkMa(query.trim());
                if (position < 0) {
                    Toast.makeText(QuanLiSachActivity.this, "Không tìm thấy mã sách", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog(sachList.get(position).getTen(), sachList.get(position).getMa(), sachList.get(position).getTheLoai(), sachList.get(position).getSoLuong(), sachList.get(position).getGia());
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
                showDialog(sachList.get(i).getTen(), sachList.get(i).getMa(), sachList.get(i).getTheLoai(), sachList.get(i).getSoLuong(), sachList.get(i).getGia());
                searchView.setQuery("", true);
            }
        });

    }

    public void showDialog(String ten, String ma, String theLoai, int soluong, String gia) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuanLiSachActivity.this);
        builder.setMessage("Tên sách: " + ten
                + "\nMã sách: " + ma
                + "\nThể loại: " + theLoai
                + "\nSố lượng: " + soluong
                + "\nGiá: " + gia
        );
        builder.show();
    }

    public void filter(String text) {
        if (text.trim().length() == 0) {
            listMa.clear();
        } else {
            listMa.clear();
            for (int i = 0; i < sachList.size(); i++) {
                listMa.add(sachList.get(i).getMa());
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
        for (int i = 0; i < sachList.size(); i++) {
            if (ma.equals(sachList.get(i).getMa())) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkTen(String ten) {
        for (int i = 0; i < sachList.size(); i++) {
            if (ten.equals(sachList.get(i).getTen())) {
                return false;
            }
        }
        return true;
    }

    public void anhXa() {
        toolbar = findViewById(R.id.toolbarSach);
        recyclerView = findViewById(R.id.rcvSach);
        lv = findViewById(R.id.lvSach);
        searchView = findViewById(R.id.searchSach);
        cardView = findViewById(R.id.carviewSach);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.opiton_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    String textButton = null;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemThem:
                if (spinnerList.size() == 0) {
                    Toast.makeText(this, "Chưa có thể loại bạn cần thêm thể loại để thêm sách", Toast.LENGTH_SHORT).show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = LayoutInflater.from(this);
                    View view = inflater.inflate(R.layout.them_sach, null);
                    builder.setView(view);
                    builder.setCancelable(false);
                    builder.setTitle("Thêm sách ");


                    final Spinner spinner = view.findViewById(R.id.spinnerThemSach);

                    ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerList);
                    spinner.setAdapter(arrayAdapter);
                    final TextInputEditText textTen, textMa, textSoLuong, textGia;
                    textTen = view.findViewById(R.id.textThemTenSach);
                    textMa = view.findViewById(R.id.textThemMaSach);
                    textSoLuong = view.findViewById(R.id.textThemSoLuong);
                    textGia = view.findViewById(R.id.textThemGia);
                    Button btnHuy, btnThem;
                    btnHuy = view.findViewById(R.id.btnHuyThemSach);
                    btnThem = view.findViewById(R.id.btnThemThemSach);

                    // lấy giá trị của spinner
                    final String[] theloai = new String[1];
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            theloai[0] = spinnerList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    final Dialog dialog = builder.create();
                    btnHuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    btnThem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String ten = textTen.getText().toString().trim();
                            String ma = textMa.getText().toString().trim();
                            String soLuong = textSoLuong.getText().toString().trim();
                            String gia = textGia.getText().toString().trim();

                            // kiểm tra điều kiện để thêm sách
                            if (checkMa(ma) < 0 && checkTen(ten) && soLuong.length() != 0 && gia.length() != 0 && Integer.parseInt(soLuong) > 0 && Integer.parseInt(gia) > 0 && spinnerList.size() != 0) {
                                Toast.makeText(QuanLiSachActivity.this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                                sachList.add(new Sach(ten, ma, Integer.parseInt(soLuong), convertGia(gia), theloai[0]));
                                sachList.get(sachList.size() - 1).setTextButton(textButton);
                                recyclerSach.notifyItemInserted(sachList.size()-1);
                                TrangChuAcivity.bookDao.insert(new Sach(ten, ma, Integer.parseInt(soLuong), convertGia(gia), theloai[0]));
                                dialog.cancel();
                            } else if (ma.length() == 0 || ten.length() == 0 | soLuong.length() == 0 || gia.length() == 0) {
                                Toast.makeText(QuanLiSachActivity.this, "Thêm thất bại. Bạn không được để trống", Toast.LENGTH_SHORT).show();
                            } else {
                                if (checkTen(ten) == false) {
                                    Toast.makeText(QuanLiSachActivity.this, "Thêm thất bại. Tên sách đã tồn tại", Toast.LENGTH_SHORT).show();
                                } else if (checkMa(ma) >= 0) {
                                    Toast.makeText(QuanLiSachActivity.this, "Thêm thất bại. Mã sách đã tồn tại", Toast.LENGTH_SHORT).show();
                                } else if (Integer.parseInt(soLuong) > 0 || Integer.parseInt(gia) > 0) {
                                    Toast.makeText(QuanLiSachActivity.this, "Thêm thất bại. Số lượng và giá phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                    dialog.show();

                }
                break;

            case R.id.itemSua:
                if (sachList.size() > 0) {
                    showView("Edit");
                    recyclerSach.notifyDataSetChanged();
                    textButton = sachList.get(0).getTextButton();
                    break;
                }

            case R.id.itemXoa:
                if (sachList.size() > 0) {
                    showView("Delete");
                    recyclerSach.notifyDataSetChanged();
                    textButton = sachList.get(0).getTextButton();
                    break;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    public void showView(String textButton) {
        for (int i = 0; i < sachList.size(); i++) {
            sachList.get(i).setVisibility(View.VISIBLE);
            sachList.get(i).setTextButton(textButton);
        }
        RecyclerSach.checkVisibility = true;
    }

    public void setDataSpinner() {
        data = new HashSet<>();
        spinnerList = new ArrayList<>();
        for (int i = 0; i < TrangChuAcivity.theLoaiList.size(); i++) {
            data.add(TrangChuAcivity.theLoaiList.get(i).getTenTheLoai());
        }
        spinnerList.addAll(data);
    }

    public String convertGia(String gia) {
        String total = "";
        if (gia.length() <= 3) {
            return gia;
        }
        // đảo ngược chuỗi string
        String stringBuilder = new StringBuilder(gia).reverse().toString();
        int a = 0;
        for (int i = 3; i < stringBuilder.length(); i += 3) {
            total += stringBuilder.substring(a, i) + ",";
            a = i;
        }
        total += stringBuilder.substring(a, stringBuilder.length());
        return new StringBuilder(total).reverse().toString();
    }


}
