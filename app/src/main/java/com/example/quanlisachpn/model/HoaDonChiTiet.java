package com.example.quanlisachpn.model;

public class HoaDonChiTiet {
    private int id;
    private String maHoaDon;
    private String maSach;
    private String soLuongMua;

    public HoaDonChiTiet(String maHoaDon, String maSach, String soLuongMua) {
        this.maHoaDon = maHoaDon;
        this.maSach = maSach;
        this.soLuongMua = soLuongMua;
    }

    public HoaDonChiTiet(int id, String maHoaDon, String maSach, String soLuongMua) {
        this.id = id;
        this.maHoaDon = maHoaDon;
        this.maSach = maSach;
        this.soLuongMua = soLuongMua;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(String soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
}
