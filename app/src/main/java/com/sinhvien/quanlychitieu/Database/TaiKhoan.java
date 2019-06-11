package com.sinhvien.quanlychitieu.Database;


import android.graphics.Bitmap;

public class TaiKhoan {

    private int _id;
    private int soTien;
    private String tenTaiKhoan;
    private String Imgage;
    private String loaiTaiKhoan;
    private String chuThich;

    public TaiKhoan(int soTien, String tenTaiKhoan, String imgage, String loaiTaiKhoan, String chuThich) {
        this.soTien = soTien;
        this.tenTaiKhoan = tenTaiKhoan;
        this.Imgage = imgage;
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.chuThich = chuThich;
    }

    public TaiKhoan() {
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getImgage() {
        return Imgage;
    }

    public void setImgage(String imgage) {
        Imgage = imgage;
    }

    public String getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public String getChuThich() {
        return chuThich;
    }

    public void setChuThich(String chuThich) {
        this.chuThich = chuThich;
    }
}
