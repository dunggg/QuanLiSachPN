package com.example.quanlisachpn.model;

public class TheLoai {
    private String ma;
    private String tenTheLoai;
    private int soLuong;
    public String textButton;
    public int visibility;

    public TheLoai(String ma, String tenTheLoai, int soLuong) {
        this.ma = ma;
        this.tenTheLoai = tenTheLoai;
        this.soLuong = soLuong;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
