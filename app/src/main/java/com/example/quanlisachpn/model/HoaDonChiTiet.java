package com.example.quanlisachpn.model;

public class HoaDonChiTiet {
    private int id;
    private String maHoaDon;
    private String maSach;
    private String soLuongMua;
    private String gia;
    private String ngayMua;
    public String textButton;


    public HoaDonChiTiet(int id, String maHoaDon, String maSach, String soLuongMua, String gia) {
        this.id = id;
        this.maHoaDon = maHoaDon;
        this.maSach = maSach;
        this.soLuongMua = soLuongMua;
        this.gia = gia;
    }

    public HoaDonChiTiet(int id, String maHoaDon, String maSach, String soLuongMua, String gia, String ngayMua) {
        this.id = id;
        this.maHoaDon = maHoaDon;
        this.maSach = maSach;
        this.soLuongMua = soLuongMua;
        this.gia = gia;
        this.ngayMua = ngayMua;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
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

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
