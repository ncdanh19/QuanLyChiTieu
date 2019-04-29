package com.sinhvien.quanlychitieu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanHelper extends SQLiteOpenHelper {
    private static final String TEN_DATABASE = "QuanLyChiTieuDB";
    // Tên bảng
    public static final String TEN_BANG_TAIKHOAN = "TaiKhoan";
    // Bảng gồm 3 cột _id, _ten và _lop.
    public static final String COT_ID = "_id";
    public static final String COT_TEN_TAIKHOAN = "tenTaiKhoan";
    public static final String COT_SO_TIEN = "soTien";
    public static final String COT_LOAI_TAI_KHOAN = "loaiTaiKhoan";
    public static final String COT_CHU_THICH = "chuThich";
    public static final String COT_HINH_ANH = "image";

    public TaiKhoanHelper(Context context) {
        super(context, TEN_DATABASE, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    private static final String TAO_BANG_TAIKHOAN = ""
            + "create table " + TEN_BANG_TAIKHOAN + " ( "
            + COT_ID + " integer primary key autoincrement ,"
            + COT_TEN_TAIKHOAN + " text not null, "
            + COT_SO_TIEN + " text not null,"
            + COT_LOAI_TAI_KHOAN + " text not null,"
            + COT_CHU_THICH + " text,"
            + COT_HINH_ANH + " BLOB"
            + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TAO_BANG_TAIKHOAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_TAIKHOAN);
        //onUpgrade(db,1,1);
    }

    public boolean insertdata(String soTien, String tenTaiKhoan, String loaiTaiKhoan, String chuThich,byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COT_TEN_TAIKHOAN, tenTaiKhoan);
        contentValues.put(COT_LOAI_TAI_KHOAN, loaiTaiKhoan);
        contentValues.put(COT_SO_TIEN, soTien);
        contentValues.put(COT_CHU_THICH, chuThich);
        contentValues.put(COT_HINH_ANH,image);
        long result = db.insert(TEN_BANG_TAIKHOAN, null, contentValues);

        if (result == -1) {
            return false;
        } else {

            return true;
        }
    }

    public List<TaiKhoan> getdata() {
        // DataModel dataModel = new DataModel();
        List<TaiKhoan> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TEN_BANG_TAIKHOAN + " ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        TaiKhoan dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new TaiKhoan();
            byte[] image = cursor.getBlob(5);
            String tenTaikhoan = cursor.getString(cursor.getColumnIndexOrThrow("tenTaiKhoan"));
            String soTien = cursor.getString(cursor.getColumnIndexOrThrow("soTien"));
            dataModel.setTenTaiKhoan(tenTaikhoan);
            dataModel.setSoTien(soTien);
            dataModel.setImgage(image);
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;
    }

}