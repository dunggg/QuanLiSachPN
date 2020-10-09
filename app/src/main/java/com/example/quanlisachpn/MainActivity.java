package com.example.quanlisachpn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.quanlisachpn.adapter.RecyclerHoaDon;
import com.example.quanlisachpn.model.HoaDon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerHoaDon recyclerHoaDon;
    List<HoaDon> hoaDonList;
    RecyclerView recyclerView;
    List<HoaDon> hoaDons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hoaDonList = new ArrayList<>();
        hoaDons = new ArrayList<>();
        hoaDonList.add(new HoaDon("a", "dfs"));
        hoaDonList.add(new HoaDon("ad", "dfs"));
        hoaDonList.add(new HoaDon("adf", "dfs"));
        hoaDonList.add(new HoaDon("adff", "dfs"));
//        for (int i = 0;i<hoaDonList.size();i++){
//            hoaDons.add(hoaDonList.get(i));
//        }
        searchView = findViewById(R.id.search);
        recyclerView = findViewById(R.id.rcvMain);
        recyclerHoaDon = new RecyclerHoaDon(hoaDons, this, R.layout.recycler_hoadon);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerHoaDon);

        searchView.setQueryHint("Tìm kiếm");
        // khi nhấn vào tìm kiếm
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (checkMa(query.trim())==true){
                    searchView.setQuery("", true);
                }else {
                    Toast.makeText(MainActivity.this, "Mã bạn tìm không có trong danh sách", Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                if (hoaDons.size()==0){
                    recyclerView.setVisibility(View.GONE);
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

    }

    public void filter(String text) {
        if (text.trim().length() == 0) {
            hoaDons.clear();
        } else {
            hoaDons.clear();
            for (int i = 0; i < hoaDonList.size(); i++) {
                hoaDons.add(hoaDonList.get(i));
            }
            for (int i = 0; i < hoaDons.size(); i++) {
                String ma = hoaDons.get(i).getMaHoaDon();
                if (hoaDons.get(i).getMaHoaDon().contains(text) == false) {
                    hoaDons.remove(i);
                    i--;
                }
            }
        }
        recyclerHoaDon.notifyDataSetChanged();

    }

    public boolean checkMa(String ma){
        for (int i = 0;i<hoaDonList.size();i++){
            if (ma.equals(hoaDonList.get(i).getMaHoaDon())){
                return true;
            }
        }
        return false;
    }
}
