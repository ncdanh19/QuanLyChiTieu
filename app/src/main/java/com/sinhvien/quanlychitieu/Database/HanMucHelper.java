package com.sinhvien.quanlychitieu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HanMucHelper extends SQLiteOpenHelper {
    private static final String TEN_DATABASE = "QuanLyChiTieuDB";
    // Tên bảng
    public static final String TEN_BANG_HANMUC = "HanMuc";
    // Bảng gồm cột
    public static final String COT_ID = "_id";
    public static final String COT_SOTIEN = "soTien";
    public static final String COT_TEN_HAN_MUC = "tenHanMuc";
    public static final String COT_IMAGE_HANGMUC = "imageHangMuc";
    public static final String COT_TEN_HANGMUC = "tenHangMuc";
    public static final String COT_IMAGE_VITIEN = "imageViTien";
    public static final String COT_TEN_VITIEN = "tenViTien";
    public static final String COT_NGAY_BATDAU = "ngayBatDau";
    public static final String COT_NGAY_KETTHUC = "ngayKetThuc";
    public static final String COT_TRANGTHAI = "trangThai";
    public static final String COT_ID_VITIEN = "_idViTien";


    public HanMucHelper(Context context) {
        super(context, TEN_DATABASE, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    private static final String TAO_BANG_HANMUC = ""
            + "CREATE TABLE IF NOT EXISTS " + TEN_BANG_HANMUC + " ( "
            + COT_ID + " integer primary key autoincrement ,"
            + COT_SOTIEN + " int not null, "
            + COT_TEN_HAN_MUC + " text not null,"
            + COT_IMAGE_HANGMUC + " text not null,"
            + COT_TEN_HANGMUC + " text not null,"
            + COT_IMAGE_VITIEN + " text not null,"
            + COT_TEN_VITIEN + " text not null,"
            + COT_NGAY_BATDAU + " text not null,"
            + COT_NGAY_KETTHUC + " text not null,"
            + COT_TRANGTHAI + " int not null,"
            + COT_ID_VITIEN + " int not null"

            + ");";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TAO_BANG_HANMUC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_HANMUC);
        onCreate(db);
    }

    //lấy dữ liệu
    public List<HanMuc> getdata() {
        // DataModel dataModel = new DataModel();
        List<HanMuc> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TEN_BANG_HANMUC + " ORDER BY " + COT_ID + "," + COT_TRANGTHAI + " DESC ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        HanMuc dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new HanMuc();
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COT_ID));
            int soTien = cursor.getInt(cursor.getColumnIndexOrThrow(COT_SOTIEN));
            String tenHanMuc = cursor.getString(cursor.getColumnIndexOrThrow(COT_TEN_HAN_MUC));
            String imageHangMuc = cursor.getString(cursor.getColumnIndexOrThrow(COT_IMAGE_HANGMUC));
            String tenHangMuc = cursor.getString(cursor.getColumnIndexOrThrow(COT_TEN_HANGMUC));
            String imageViTien = cursor.getString(cursor.getColumnIndexOrThrow(COT_IMAGE_VITIEN));
            String tenViTien = cursor.getString(cursor.getColumnIndexOrThrow(COT_TEN_VITIEN));
            String ngayBatDau = cursor.getString(cursor.getColumnIndexOrThrow(COT_NGAY_BATDAU));
            String ngayKetThuc = cursor.getString(cursor.getColumnIndexOrThrow(COT_NGAY_KETTHUC));
            int trangThai = cursor.getInt(cursor.getColumnIndexOrThrow(COT_TRANGTHAI));
            int idViTien = cursor.getInt(cursor.getColumnIndexOrThrow(COT_ID_VITIEN));


            dataModel.set_id(id);
            dataModel.setSoTien(soTien);
            dataModel.setTenHanMuc(tenHanMuc);
            dataModel.setImageHangMuc(imageHangMuc);
            dataModel.setTenHangMuc(tenHangMuc);
            dataModel.setImageViTien(imageViTien);
            dataModel.setTenViTien(tenViTien);
            dataModel.setNgayBatDau(ngayBatDau);
            dataModel.setNgayKetThuc(ngayKetThuc);
            dataModel.setTrangThai(trangThai);
            dataModel.set_idViTien(idViTien);
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;
    }

    //thêm hạn mức
    public boolean insertHanMuc(int soTien, String tenHanMuc, String imageHangMuc, String tenHangMuc, String imageViTien, String tenViTien, String ngayBatDau, String ngayKetThuc, int trangThai, int _idViTien) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COT_SOTIEN, soTien);
        contentValues.put(COT_TEN_HAN_MUC, tenHanMuc);
        contentValues.put(COT_IMAGE_HANGMUC, imageHangMuc);
        contentValues.put(COT_TEN_HANGMUC, tenHangMuc);
        contentValues.put(COT_IMAGE_VITIEN, imageViTien);
        contentValues.put(COT_TEN_VITIEN, tenViTien);
        contentValues.put(COT_NGAY_BATDAU, ngayBatDau);
        contentValues.put(COT_NGAY_KETTHUC, ngayKetThuc);
        contentValues.put(COT_TRANGTHAI, trangThai);
        contentValues.put(COT_ID_VITIEN, _idViTien);

        long result = db.insert(TEN_BANG_HANMUC, null, contentValues);

        return result != -1;
    }

    public boolean updateTrangThaiHanMuc(int id, int trangThai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COT_TRANGTHAI, trangThai);

        long result = db.update(TEN_BANG_HANMUC, contentValues, COT_ID + "=" + id, null);

        if (result == -1) {
            return false;
        } else {

            return true;
        }
    }

    public boolean deleteHanMucByIDViTien(long idTaiKhoan) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TEN_BANG_HANMUC, COT_ID_VITIEN + " = " + (long) idTaiKhoan, null) > 0;
    }

}

