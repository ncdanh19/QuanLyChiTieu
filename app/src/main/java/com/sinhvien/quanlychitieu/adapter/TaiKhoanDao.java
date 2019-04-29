package com.sinhvien.quanlychitieu.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.Database.TaiKhoanHelper;

public class TaiKhoanDao {
    SQLiteDatabase database;
    TaiKhoanHelper taiKhoanHelper;
    public TaiKhoanDao(Context context){
        taiKhoanHelper=new TaiKhoanHelper(context);
        database=taiKhoanHelper.getWritableDatabase();
    }

    public Cursor layTatCaDuLieu(){
        String[] cot = {
                TaiKhoanHelper.COT_TEN_TAIKHOAN,
                TaiKhoanHelper.COT_SO_TIEN,
                TaiKhoanHelper.COT_LOAI_TAI_KHOAN,
                TaiKhoanHelper.COT_CHU_THICH
        };
        Cursor cursor = null;
        cursor = database.query(TaiKhoanHelper.
                        TEN_BANG_TAIKHOAN, cot, null, null, null, null,
                TaiKhoanHelper.COT_ID + " DESC");

        return cursor;
    }

    public long them(TaiKhoan taiKhoan){
        ContentValues values = new ContentValues();
        values.put(TaiKhoanHelper.COT_TEN_TAIKHOAN,
                taiKhoan.getTenTaiKhoan());
        values.put(TaiKhoanHelper.COT_SO_TIEN,
                taiKhoan.getSoTien());
        values.put(TaiKhoanHelper.COT_LOAI_TAI_KHOAN,
                taiKhoan.getLoaiTaiKhoan());
        values.put(TaiKhoanHelper.COT_CHU_THICH,
                taiKhoan.getChuThich());
        return database.insert(TaiKhoanHelper.
                TEN_BANG_TAIKHOAN, null, values);
    }
}
