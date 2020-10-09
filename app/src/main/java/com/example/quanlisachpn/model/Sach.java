package com.example.quanlisachpn.model;

public class Sach {
    private String ten;
    private String ma;
    private int soLuong;
    private String gia;
    private String theLoai;
    private String textButton;
    private int visibility;

       public Sach(String ten, String ma, int soLuong, String gia, String theLoai) {
        this.ten = ten;
        this.ma = ma;
        this.soLuong = soLuong;
        this.gia = gia;
        this.theLoai = theLoai;
    }

    public Sach(String ten, String ma, int soLuong, String gia, String theLoai, String textButton, int visibility) {
        this.ten = ten;
        this.ma = ma;
        this.soLuong = soLuong;
        this.gia = gia;
        this.theLoai = theLoai;
        this.textButton = textButton;
        this.visibility = visibility;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getTextButton() {
        return textButton;
    }

    public void setTextButton(String textButton) {
        this.textButton = textButton;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
}
