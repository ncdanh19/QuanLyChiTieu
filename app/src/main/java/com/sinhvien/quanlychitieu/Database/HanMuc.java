package com.sinhvien.quanlychitieu.Database;

public class HanMuc {

    int _id;
    int soTien;
    String tenHanMuc;
    String imageHangMuc;
    String tenHangMuc;
    String imageViTien;
    String tenViTien;
    String ngayBatDau;
    String ngayKetThuc;
    int trangThai;
    int _idViTien;

    public HanMuc() {
    }

    public HanMuc(int _id, int soTien, String tenHanMuc, String imageHangMuc, String tenHangMuc, String imageViTien, String tenViTien, String ngayBatDau, String ngayKetThuc, int _idHangMuc, int _idViTien) {
        this._id = _id;
        this.soTien = soTien;
        this.tenHanMuc = tenHanMuc;
        this.imageHangMuc = imageHangMuc;
        this.tenHangMuc = tenHangMuc;
        this.imageViTien = imageViTien;
        this.tenViTien = tenViTien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = _idHangMuc;
        this._idViTien = _idViTien;
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

    public String getTenHanMuc() {
        return tenHanMuc;
    }

    public void setTenHanMuc(String tenHanMuc) {
        this.tenHanMuc = tenHanMuc;
    }

    public String getImageHangMuc() {
        return imageHangMuc;
    }

    public void setImageHangMuc(String imageHangMuc) {
        this.imageHangMuc = imageHangMuc;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public String getImageViTien() {
        return imageViTien;
    }

    public void setImageViTien(String imageViTien) {
        this.imageViTien = imageViTien;
    }

    public String getTenViTien() {
        return tenViTien;
    }

    public void setTenViTien(String tenViTien) {
        this.tenViTien = tenViTien;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int get_idViTien() {
        return _idViTien;
    }

    public void set_idViTien(int _idViTien) {
        this._idViTien = _idViTien;
    }

}
