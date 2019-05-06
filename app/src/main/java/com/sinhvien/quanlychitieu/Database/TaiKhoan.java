package com.sinhvien.quanlychitieu.Database;


import android.graphics.Bitmap;

public class TaiKhoan {

    String soTien;
    String tenTaiKhoan;
    String Imgage;
    String loaiTaiKhoan;
    String chuThich;

    public TaiKhoan(String soTien, String tenTaiKhoan, String imgage, String loaiTaiKhoan, String chuThich) {
        this.soTien = soTien;
        this.tenTaiKhoan = tenTaiKhoan;
        this.Imgage = imgage;
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.chuThich = chuThich;
    }


    public TaiKhoan() {
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
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
