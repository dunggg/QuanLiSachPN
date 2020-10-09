package com.example.quanlisachpn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlisachpn.SQL.BookTypeDao;
import com.example.quanlisachpn.SQL.MySql;
import com.example.quanlisachpn.model.TheLoai;
import com.example.quanlisachpn.adapter.RecyclerTheLoai;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerTheLoai recyclerTheLoai;
    Toolbar toolbar;
    MySql mySql;
    BookTypeDao bookTypeDao;
    SearchView searchView;
    CardView cardView;
    ListView lv;
    List<String> listMa;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);

        anhXa();
        setSupportActionBar(toolbar);
        mySql = new MySql(this);
        bookTypeDao = new BookTypeDao(mySql);

        updateSoLuongTheLoai();
        RecyclerTheLoai.checkTL = false;

        recyclerTheLoai = new RecyclerTheLoai(TrangChuAcivity.theLoaiList, this, R.layout.recycler_theloai);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerTheLoai);

        listMa = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listMa);
        lv.setAdapter(arrayAdapter);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    cardView.setVisibility(View.GONE);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int i = checkMa(query.trim());
                if (i < 0) {
                    Toast.makeText(TheLoaiActivity.this, "Không tìm thấy mã", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog(TrangChuAcivity.theLoaiList.get(i).getTenTheLoai(), TrangChuAcivity.theLoaiList.get(i).getMa(), TrangChuAcivity.theLoaiList.get(i).getSoLuong());
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ma = listMa.get(position);
                int i = checkMa(ma);
                showDialog(TrangChuAcivity.theLoaiList.get(i).getTenTheLoai(), TrangChuAcivity.theLoaiList.get(i).getMa(), TrangChuAcivity.theLoaiList.get(i).getSoLuong());
                searchView.setQuery("", true);
            }
        });
    }

    public void showDialog(String ten, String ma, int soluong) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TheLoaiActivity.this);
        builder.setMessage("Tên thể loại: " + ten
                + "\nMã thể loại: " + ma
                + "\nSố lượng: " + soluong
        );
        builder.show();
    }

    public void filter(String text) {
        if (text.trim().length() == 0) {
            listMa.clear();
        } else {
            listMa.clear();
            for (int i = 0; i < TrangChuAcivity.theLoaiList.size(); i++) {
                listMa.add(TrangChuAcivity.theLoaiList.get(i).getMa());
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

        if (ma.equals("")) {
            return 0;
        }

        for (int i = 0; i < TrangChuAcivity.theLoaiList.size(); i++) {
            if (ma.equals(TrangChuAcivity.theLoaiList.get(i).getMa())) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkTen(String ten) {

        if (ten.equals("")) {
            return false;
        }

        for (int i = 0; i < TrangChuAcivity.theLoaiList.size(); i++) {
            if (ten.equals(TrangChuAcivity.theLoaiList.get(i).getTenTheLoai())) {
                return false;
            }
        }
        return true;
    }


    public void anhXa() {
        recyclerView = findViewById(R.id.rcvTL);
        toolbar = findViewById(R.id.toolbarTL);
        searchView = findViewById(R.id.searchTheLoai);
        cardView = findViewById(R.id.carviewTheLoai);
        lv = findViewById(R.id.lvTheLoai);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opiton_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    String textButton = null;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemThem:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                final View view = layoutInflater.inflate(R.layout.them_theloai, null);
                builder.setView(view);
                builder.setCancelable(false);
                builder.setTitle("Thêm thể loại");
                final TextInputEditText txtName = view.findViewById(R.id.txtThemTenTL);
                final TextInputEditText txtMa = view.findViewById(R.id.txtThemMaTL);
                Button btnHuy,btnThem;
                btnHuy = view.findViewById(R.id.btnHuyThemTL);
                btnThem = view.findViewById(R.id.btnThemThemTL);

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
                        String name = txtName.getText().toString().trim();
                        String ma = txtMa.getText().toString().trim();
                        if (checkTen(name) && checkMa(ma) < 0) {
                            TrangChuAcivity.theLoaiList.add(new TheLoai(ma, name, 0));
                            TrangChuAcivity.theLoaiList.get(TrangChuAcivity.theLoaiList.size() - 1).textButton = textButton;
                            recyclerTheLoai.notifyItemInserted(TrangChuAcivity.theLoaiList.size());
                            bookTypeDao.insert(new TheLoai(ma, name, 0));
                            Toast.makeText(TheLoaiActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }else {
                            Toast.makeText(TheLoaiActivity.this, "Thêm thất bại. Bạn không được để trống hoặc mã,tên đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
                break;

            case R.id.itemSua:
                if (TrangChuAcivity.theLoaiList.size() > 0) {
                    showView("Edit");
                    recyclerTheLoai.notifyDataSetChanged();
                    textButton = TrangChuAcivity.theLoaiList.get(0).textButton;
                    break;
                }

            case R.id.itemXoa:
                if (TrangChuAcivity.theLoaiList.size() > 0) {
                    showView("Delete");
                    recyclerTheLoai.notifyDataSetChanged();
                    textButton = TrangChuAcivity.theLoaiList.get(0).textButton;
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public void showView(String textButton) {
        for (int i = 0; i < TrangChuAcivity.theLoaiList.size(); i++) {
            TrangChuAcivity.theLoaiList.get(i).visibility = View.VISIBLE;
            TrangChuAcivity.theLoaiList.get(i).textButton = textButton;
        }
        RecyclerTheLoai.checkTL = true;
    }

    public void updateSoLuongTheLoai() {
        if (TrangChuAcivity.theLoaiList.size() > 0) {
            for (int i = 0; i < TrangChuAcivity.theLoaiList.size(); i++) {
                int soLuong = 0;
                for (int j = 0; j < TrangChuAcivity.sachList.size(); j++) {
                    if (TrangChuAcivity.theLoaiList.get(i).getTenTheLoai().equals(TrangChuAcivity.sachList.get(j).getTheLoai())) {
                        soLuong += TrangChuAcivity.sachList.get(j).getSoLuong();
                    }

                }
                TrangChuAcivity.theLoaiList.get(i).setSoLuong(soLuong);
                TrangChuAcivity.bookTypeDao.updateSoLuong(TrangChuAcivity.theLoaiList.get(i).getMa(), soLuong);
            }
        }
    }

}
