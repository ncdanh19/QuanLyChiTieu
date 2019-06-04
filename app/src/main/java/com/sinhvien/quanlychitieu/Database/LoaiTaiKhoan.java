package com.sinhvien.quanlychitieu.Database;

import java.io.Serializable;

public class LoaiTaiKhoan implements Serializable {
    private String tenLoai;
    private int img_URL;

    public void LoaiTaiKhoan() {
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public int getImg() {
        return img_URL;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public void setImg(int img_URL) {
        this.img_URL = img_URL;
    }

}
