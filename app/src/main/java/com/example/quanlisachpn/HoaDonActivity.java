package com.example.quanlisachpn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlisachpn.adapter.RecyclerTheLoai;
import com.example.quanlisachpn.model.HoaDon;
import com.example.quanlisachpn.adapter.RecyclerHoaDon;
import com.example.quanlisachpn.model.HoaDonChiTiet;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HoaDonActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerHoaDon recyclerHoaDon;
    Toolbar toolbar;
    public static List<String> spinnerList;
    SearchView searchView;
    List<String> listMa;
    CardView cardView;
    ListView lv;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        anhXa();
        setSupportActionBar(toolbar);
        setDataSpinner();
        RecyclerHoaDon.checkRcvHd = false;
        listMa = new ArrayList<>();

        recyclerHoaDon = new RecyclerHoaDon(TrangChuAcivity.hoaDonList, this, R.layout.recycler_hoadon);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerHoaDon);

        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listMa);
        lv.setAdapter(arrayAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int position = checkMa(query.trim());
                if (position < 0) {
                    Toast.makeText(HoaDonActivity.this, "Không tìm thấy mã hóa đơn", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog(TrangChuAcivity.hoaDonChiTietList.get(position).getMaHoaDon(), TrangChuAcivity.hoaDonChiTietList.get(position).getMaSach(), TrangChuAcivity.hoaDonChiTietList.get(position).getSoLuongMua(), TrangChuAcivity.hoaDonList.get(position).getNgayMua());
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
                showDialog(TrangChuAcivity.hoaDonChiTietList.get(i).getMaHoaDon(), TrangChuAcivity.hoaDonChiTietList.get(i).getMaSach(), TrangChuAcivity.hoaDonChiTietList.get(i).getSoLuongMua(), TrangChuAcivity.hoaDonList.get(i).getNgayMua());
                searchView.setQuery("", true);
            }
        });

    }

    public void showDialog(String maHd, String maSach, String soluong, String ngayMua) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HoaDonActivity.this);
        builder.setMessage("Tên sách: " + maHd
                + "\nMã sách: " + maSach
                + "\nSố lượng: " + soluong
                + "\nNgày: " + ngayMua
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

        if (ma.equals("")){
            return 0;
        }

        for (int i = 0; i < TrangChuAcivity.hoaDonChiTietList.size(); i++) {
            if (ma.equals(TrangChuAcivity.hoaDonChiTietList.get(i).getMaHoaDon())) {
                return i;
            }
        }
        return -1;
    }


    public void anhXa() {
        recyclerView = findViewById(R.id.rcvHD);
        toolbar = findViewById(R.id.toolbarHD);
        searchView = findViewById(R.id.searchHD);
        lv = findViewById(R.id.lvHD);
        cardView = findViewById(R.id.carviewHD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.opiton_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    String textButton;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemThem:
                if (spinnerList.size() == 0) {
                    Toast.makeText(this, "Bạn cần thêm sách trước khi thêm hóa đơn", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Thêm hóa đơn");
                    builder.setCancelable(false);
                    LayoutInflater inflater = LayoutInflater.from(this);
                    View view = inflater.inflate(R.layout.them_hoadon, null);
                    builder.setView(view);

                    final TextInputEditText txtMaHoaDon, txtSoLuong;
//                    final TextView textNgay = view.findViewById(R.id.textNgay);
//                    Button btnNgay = view.findViewById(R.id.btnChonNgay);
                    Spinner spinner = view.findViewById(R.id.spinnerThemHD);
                    txtMaHoaDon = view.findViewById(R.id.txtMaHDThemHD);
                    txtSoLuong = view.findViewById(R.id.txtSoLuongThemHD);

                    ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerList);
                    spinner.setAdapter(arrayAdapter);
                    final String[] maSach = {null};
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            maSach[0] = spinnerList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

//                    btnNgay.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Calendar calendar = Calendar.getInstance();
//                            int year = calendar.get(Calendar.YEAR);
//                            int month = calendar.get(Calendar.MONTH);
//                            int day = calendar.get(Calendar.DAY_OF_MONTH);
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                                DatePickerDialog datePickerDialog = new DatePickerDialog(HoaDonActivity.this, new DatePickerDialog.OnDateSetListener() {
//                                    @Override
//                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                                        textNgay.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
//                                    }
//                                }, year, month, day);
//                                datePickerDialog.show();
//                            }
//                        }
//                    });

                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String ma = txtMaHoaDon.getText().toString().trim();
                            String soLuong = txtSoLuong.getText().toString().trim();
                            String ngay = getDate();

                            if (checkMa(ma)<0&&soLuong.length()>0&&Integer.parseInt(soLuong)>0&&ngay.length()>0){
                                TrangChuAcivity.hoaDonList.add(new HoaDon(ma, ngay));
                                TrangChuAcivity.billDao.insert(new HoaDon(ma, ngay));
                                TrangChuAcivity.hoaDonChiTietList.add(new HoaDonChiTiet(ma, maSach[0], soLuong));
                                TrangChuAcivity.billDetailDao.insert(new HoaDonChiTiet(ma, maSach[0], soLuong));
                                TrangChuAcivity.hoaDonList.get(TrangChuAcivity.hoaDonList.size() - 1).textButton = textButton;
                                recyclerHoaDon.notifyItemInserted(TrangChuAcivity.hoaDonList.size() - 1);
                                Toast.makeText(HoaDonActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(HoaDonActivity.this, "Bạn không được để trống,số lượng lớn hơn 0 và mã hóa đơn không được trùng", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show();
                }
                break;
            case R.id.itemSua:
                showView("Edit");
                textButton = TrangChuAcivity.hoaDonList.get(0).textButton;
                recyclerHoaDon.notifyDataSetChanged();
                break;
            case R.id.itemXoa:
                showView("Delete");
                textButton = TrangChuAcivity.hoaDonList.get(0).textButton;
                recyclerHoaDon.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setDataSpinner() {
        Set<String> stringSet = new HashSet<>();
        spinnerList = new ArrayList<>();
        for (int i = 0; i < TrangChuAcivity.sachList.size(); i++) {
            stringSet.add(TrangChuAcivity.sachList.get(i).getMa());
        }
        spinnerList.addAll(stringSet);
    }

    public void showView(String textButton) {
        for (int i = 0; i < TrangChuAcivity.hoaDonList.size(); i++) {
            TrangChuAcivity.hoaDonList.get(i).visibility = View.VISIBLE;
            TrangChuAcivity.hoaDonList.get(i).textButton = textButton;
        }
        RecyclerHoaDon.checkRcvHd = true;
    }

    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }

}
