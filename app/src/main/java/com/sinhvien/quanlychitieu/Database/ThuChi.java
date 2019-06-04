package com.sinhvien.quanlychitieu.Database;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ThuChi {


    public int _id;
    public String sotien;
    private String imageHangMuc;
    private String tenHangMuc;
    private String mota;
    private String ngaythang;
    private String imageViTien;
    private String tenViTien;
    private int trangThai; //0 = chi, 1 = thu
    private int _idViTien;

    public ThuChi() {
        // Default constructor required for calls to DataSnapshot.getValue(ChiTien.class)
    }

    public ThuChi(int _idViTien, String sotien, String imageHangMuc, String tenHangMuc, String mota, String ngaythang, String imageViTien, String tenViTien, int trangThai) {
        this._idViTien = _idViTien;
        this.sotien = sotien;
        this.imageHangMuc = imageHangMuc;
        this.tenHangMuc = tenHangMuc;
        this.mota = mota;
        this.ngaythang = ngaythang;
        this.imageViTien = imageViTien;
        this.tenViTien = tenViTien;
        this.trangThai = trangThai;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    void setImageHangMuc(String imageHangMuc) {
        this.imageHangMuc = imageHangMuc;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public String getMota() {
        return mota;
    }

    void setMota(String mota) {
        this.mota = mota;
    }

    public String getNgaythang() {
        return ngaythang;
    }

    void setNgaythang(String ngaythang) {
        this.ngaythang = ngaythang;
    }

    public String getImageViTien() {
        return imageViTien;
    }

    void setImageViTien(String imageViTien) {
        this.imageViTien = imageViTien;
    }

    public String getTenViTien() {
        return tenViTien;
    }

    void setTenViTien(String tenViTien) {
        this.tenViTien = tenViTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int get_idViTien() {
        return _idViTien;
    }

    void set_idViTien(int _idViTien) {
        this._idViTien = _idViTien;
    }

}
