package com.sinhvien.quanlychitieu.Database;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ThuChi {

    public String sotien;
    public String imageHangMuc;
    public String tenHangMuc;
    public String mota;
    public String ngaythang;
    public String imageViTien;
    public String tenViTien;
    public int trangThai; //0 = chi, 1 = thu

    public ThuChi() {
        // Default constructor required for calls to DataSnapshot.getValue(ChiTien.class)
    }

    public ThuChi(String sotien, String imageHangMuc, String tenHangMuc, String mota, String ngaythang, String imageViTien, String tenViTien,int trangThai) {
        this.sotien = sotien;
        this.imageHangMuc = imageHangMuc;
        this.tenHangMuc = tenHangMuc;
        this.mota = mota;
        this.ngaythang = ngaythang;
        this.imageViTien = imageViTien;
        this.tenViTien = tenViTien;
        this.trangThai=trangThai;
    }

    public String getSotien() {
        return sotien;
    }

    public void setSotien(String sotien) {
        this.sotien = sotien;
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

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getNgaythang() {
        return ngaythang;
    }

    public void setNgaythang(String ngaythang) {
        this.ngaythang = ngaythang;
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

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
