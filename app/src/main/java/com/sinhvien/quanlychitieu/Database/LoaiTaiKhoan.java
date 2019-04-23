package com.sinhvien.quanlychitieu.Database;

public class LoaiTaiKhoan {
    private String tenLoai;
    private int img_URL;

    public void  LoaiTaiKhoan(){}

    public String getTenLoai(){
        return tenLoai;
    }

    public int getImg_Url(){
        return  img_URL;
    }

    public void setTenLoai(String tenLoai){
        this.tenLoai=tenLoai;
    }

    public void setImg_URL(int img_URL){
        this.img_URL=img_URL;
    }

}
