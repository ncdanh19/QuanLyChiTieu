package com.sinhvien.quanlychitieu.Database;


import android.graphics.Bitmap;

public class TaiKhoan {


    private int _id;
    String soTien;
    String tenTaiKhoan;
    byte[] Imgage;
    String loaiTaiKhoan;
    String chuThich;

    public TaiKhoan(String soTien, String tenTaiKhoan, byte[]  imgage, String loaiTaiKhoan, String chuThich) {
        this.soTien = soTien;
        this.tenTaiKhoan = tenTaiKhoan;
        this.Imgage = imgage;
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.chuThich = chuThich;
    }
    public TaiKhoan(int id,String soTien, String tenTaiKhoan,byte[]  imgage, String loaiTaiKhoan, String chuThich) {
        this._id=id;
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

    public byte[]  getImgage() {
        return Imgage;
    }

    public void setImgage(byte[]  imgage) {
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
