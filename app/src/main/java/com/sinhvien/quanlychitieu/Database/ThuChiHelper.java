package com.sinhvien.quanlychitieu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ThuChiHelper  extends SQLiteOpenHelper {
    private static final String TEN_DATABASE = "QuanLyChiTieuDB";
    // Tên bảng
    public static final String TEN_BANG_THUCHI = "ThuChi";
    // Bảng gồm cột
    public static final String COT_ID = "_id";
    public static final String COT_SOTIEN = "sotien";
    public static final String COT_IMAGE_HANGMUC = "imageHangMuc";
    public static final String COT_LOAI_HANGMUC = "tenHangMuc";
    public static final String COT_MOTA = "mota";
    public static final String COT_NGAYTHANG = "ngaythang";
    public static final String COT_IMAGE_VITIEN= "imageViTien";
    public static final String COT_TEN_VITIEN = "tenViTien";
    public static final String COT_TRANGTHAI = "trangthai";

    public ThuChiHelper(Context context) {
        super(context, TEN_DATABASE, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        //onCreate(db);
        //onUpgrade(db,1,1);
    }
    private static final String TAO_BANG_THUCHI = ""
            + "create table " + TEN_BANG_THUCHI + " ( "
            + COT_ID + " integer primary key autoincrement ,"
            + COT_SOTIEN + " text not null, "
            + COT_IMAGE_HANGMUC + " text not null,"
            + COT_LOAI_HANGMUC + " text not null,"
            + COT_MOTA + " text,"
            + COT_NGAYTHANG + " text not null,"
            + COT_IMAGE_VITIEN + " text not null,"
            + COT_TEN_VITIEN + " text not null,"
            + COT_TRANGTHAI + " int default 0"
            + ");";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TAO_BANG_THUCHI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_THUCHI);
        onCreate(db);
    }

    public boolean insertdata(String sotien, String imageHangMuc,
                              String tenHangMuc, String mota,
                              String ngaythang, String imageViTien,
                              String tenViTien,int trangThai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COT_SOTIEN, sotien);
        contentValues.put(COT_IMAGE_HANGMUC, imageHangMuc);
        contentValues.put(COT_LOAI_HANGMUC, tenHangMuc);
        contentValues.put(COT_MOTA, mota);
        contentValues.put(COT_NGAYTHANG,ngaythang);
        contentValues.put(COT_IMAGE_VITIEN,imageViTien);
        contentValues.put(COT_TEN_VITIEN,tenViTien);
        contentValues.put(COT_TRANGTHAI,trangThai);
        long result = db.insert(TEN_BANG_THUCHI, null, contentValues);

        if (result == -1) {
            return false;
        } else {

            return true;
        }
    }

    public List<ThuChi> getdata() {
        // DataModel dataModel = new DataModel();
        List<ThuChi> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TEN_BANG_THUCHI + " ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        ThuChi dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new ThuChi();
            String imageHangMuc = cursor.getString(cursor.getColumnIndexOrThrow("imageHangMuc"));
            String tenHangMuc = cursor.getString(cursor.getColumnIndexOrThrow("tenHangMuc"));
            String ngayTao = cursor.getString(cursor.getColumnIndexOrThrow("ngaythang"));
            String soTien = cursor.getString(cursor.getColumnIndexOrThrow("sotien"));
            String tenViTien = cursor.getString(cursor.getColumnIndexOrThrow("tenViTien"));

            dataModel.setImageHangMuc(imageHangMuc);
            dataModel.setTenHangMuc(tenHangMuc);
            dataModel.setNgaythang(ngayTao);
            dataModel.setSotien(soTien);
            dataModel.setTenViTien(tenViTien);
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;
    }
}
