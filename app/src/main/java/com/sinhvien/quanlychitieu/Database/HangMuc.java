package com.sinhvien.quanlychitieu.Database;

public class HangMuc {
    public int image;
    public String tenHangMuc;

    public HangMuc( String tenHangMuc, int image) {
        this.image = image;
        this.tenHangMuc = tenHangMuc;
    }
    public HangMuc() {
    }

    public int getImage() {
        return image;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }
}
