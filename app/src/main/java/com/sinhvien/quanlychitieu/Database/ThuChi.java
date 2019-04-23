package com.sinhvien.quanlychitieu.Database;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ThuChi {

    public int sotien;
    public String mota;
    public String ngaythang;


    public ThuChi() {
        // Default constructor required for calls to DataSnapshot.getValue(ChiTien.class)
    }

    public ThuChi(int soTien, String moTa,String ngayThang) {
        this.sotien = soTien;
        this.mota = moTa;
        this.ngaythang=ngayThang;
    }

}
