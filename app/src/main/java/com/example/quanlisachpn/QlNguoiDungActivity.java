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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quanlisachpn.adapter.RecyclerSach;
import com.example.quanlisachpn.model.User;
import com.example.quanlisachpn.SQL.MySql;
import com.example.quanlisachpn.SQL.UserDao;
import com.example.quanlisachpn.adapter.RecyclerUser;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class QlNguoiDungActivity extends AppCompatActivity {

    RecyclerUser recyclerUser;
    RecyclerView recyclerView;
    Toolbar toolbar;
    SearchView searchView;
    ListView lv;
    List<String> listMa;
    CardView cardView;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_nguoi_dung);

        anhXa();
        setSupportActionBar(toolbar);
        RecyclerUser.flag = false;

        listMa = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listMa);
        lv.setAdapter(arrayAdapter);

        recyclerUser = new RecyclerUser(ChaoActivity.userList, this, R.layout.recyclerview_user);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerUser);

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
                int position = checkMa(query.trim());
                if (position < 0) {
                    Toast.makeText(QlNguoiDungActivity.this, "Không tìm thấy user", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog(ChaoActivity.userList.get(position).getName(), ChaoActivity.userList.get(position).getPhoneNumber(), ChaoActivity.userList.get(position).getPassword());
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
                showDialog(ChaoActivity.userList.get(i).getName(), ChaoActivity.userList.get(i).getPhoneNumber(), ChaoActivity.userList.get(i).getPassword());
                searchView.setQuery("", true);
            }
        });


    }

    public void showDialog(String ten, String phoneNumber, String password) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(QlNguoiDungActivity.this);
        builder.setMessage("Username: " + ten
                + "\nSố điện thoại: " + phoneNumber
                + "\nPassword: " + password);
        builder.show();
    }

    public void filter(String text) {
        if (text.trim().length() == 0) {
            listMa.clear();
        } else {
            listMa.clear();
            for (int i = 0; i < ChaoActivity.userList.size(); i++) {
                listMa.add(ChaoActivity.userList.get(i).getName());
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
        for (int i = 0; i < ChaoActivity.userList.size(); i++) {
            if (ma.equals(ChaoActivity.userList.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }


    public void anhXa() {
        recyclerView = findViewById(R.id.rcvUser);
        toolbar = findViewById(R.id.toolbarUser);
        searchView = findViewById(R.id.searchUser);
        cardView = findViewById(R.id.carviewUser);
        lv = findViewById(R.id.lvUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSuaUser:
                showView("Edit");
                recyclerUser.notifyDataSetChanged();
                break;
            case R.id.itemXoaUser:
                showView("Delete");
                recyclerUser.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showView(String textButton) {
        for (int i = 0; i < ChaoActivity.userList.size(); i++) {
            ChaoActivity.userList.get(i).textButton = textButton;
            ChaoActivity.userList.get(i).visibility = View.VISIBLE;
        }
        RecyclerUser.flag = true;
    }

}
