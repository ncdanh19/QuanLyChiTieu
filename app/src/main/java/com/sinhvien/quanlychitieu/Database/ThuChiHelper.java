package com.sinhvien.quanlychitieu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThuChiHelper extends SQLiteOpenHelper {
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
    public static final String COT_IMAGE_VITIEN = "imageViTien";
    public static final String COT_TEN_VITIEN = "tenViTien";
    public static final String COT_TRANGTHAI = "trangthai";
    public static final String COT_ID_VITIEN = "_idViTien";


    public ThuChiHelper(Context context) {
        super(context, TEN_DATABASE, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
//        onUpgrade(db,1,1);
    }

    private static final String TAO_BANG_THUCHI = ""
            + "CREATE TABLE IF NOT EXISTS " + TEN_BANG_THUCHI + " ( "
            + COT_ID + " integer primary key autoincrement ,"
            + COT_SOTIEN + " int not null, "
            + COT_IMAGE_HANGMUC + " text not null,"
            + COT_LOAI_HANGMUC + " text not null,"
            + COT_MOTA + " text,"
            + COT_NGAYTHANG + " text not null,"
            + COT_IMAGE_VITIEN + " text not null,"
            + COT_TEN_VITIEN + " text not null,"
            + COT_TRANGTHAI + " int not null,"
            + COT_ID_VITIEN + " int not null"
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

    //lấy dữ liệu
    public List<ThuChi> getdata() {
        // DataModel dataModel = new DataModel();
        List<ThuChi> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TEN_BANG_THUCHI + " ORDER BY " + COT_ID + " DESC ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        ThuChi dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new ThuChi();
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COT_ID));
            String soTien = cursor.getString(cursor.getColumnIndexOrThrow(COT_SOTIEN));
            String imageHangMuc = cursor.getString(cursor.getColumnIndexOrThrow(COT_IMAGE_HANGMUC));
            String tenHangMuc = cursor.getString(cursor.getColumnIndexOrThrow(COT_LOAI_HANGMUC));
            String moTa = cursor.getString(cursor.getColumnIndexOrThrow(COT_MOTA));
            String ngayTao = cursor.getString(cursor.getColumnIndexOrThrow(COT_NGAYTHANG));
            String imageViTien = cursor.getString(cursor.getColumnIndexOrThrow(COT_IMAGE_VITIEN));
            String tenViTien = cursor.getString(cursor.getColumnIndexOrThrow(COT_TEN_VITIEN));
            int trangThai = cursor.getInt(cursor.getColumnIndexOrThrow(COT_TRANGTHAI));
            int idViTien = cursor.getInt(cursor.getColumnIndexOrThrow(COT_ID_VITIEN));

            dataModel.set_id(id);
            dataModel.setSotien(soTien);
            dataModel.setImageHangMuc(imageHangMuc);
            dataModel.setTenHangMuc(tenHangMuc);
            dataModel.setMota(moTa);
            dataModel.setNgaythang(ngayTao);
            dataModel.setImageViTien(imageViTien);
            dataModel.setTenViTien(tenViTien);
            dataModel.setTrangThai(trangThai);
            dataModel.set_idViTien(idViTien);
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;
    }

    //tính tổng tiền của mỗi mục thu chi
    public List<ThuChi> getTongTien(int flagtrangThai) {
        // DataModel dataModel = new DataModel();
        List<ThuChi> data = new ArrayList<>();
        ThuChi dataModel = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select *,Sum(" + COT_SOTIEN + ") as TongTien"
                + " from " + TEN_BANG_THUCHI
                + " where " + COT_TRANGTHAI + " =" + flagtrangThai //0=chi, 1=thu
                + " group by " + COT_LOAI_HANGMUC + " ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String soTien = cursor.getString(cursor.getColumnIndexOrThrow("sotien"));
            String imageHangMuc = cursor.getString(cursor.getColumnIndexOrThrow("imageHangMuc"));
            String tenHangMuc = cursor.getString(cursor.getColumnIndexOrThrow("tenHangMuc"));
            String moTa = cursor.getString(cursor.getColumnIndexOrThrow("mota"));
            String ngayTao = cursor.getString(cursor.getColumnIndexOrThrow("ngaythang"));
            String imageViTien = cursor.getString(cursor.getColumnIndexOrThrow(COT_IMAGE_VITIEN));
            String tenViTien = cursor.getString(cursor.getColumnIndexOrThrow("tenViTien"));
            int trangThai = cursor.getInt(cursor.getColumnIndexOrThrow(COT_TRANGTHAI));
            int idViTien = cursor.getInt(cursor.getColumnIndexOrThrow(COT_ID_VITIEN));
            int TongTien = cursor.getInt(10);

            dataModel = new ThuChi();
            dataModel.set_id(id);
            dataModel.setSotien(String.valueOf(TongTien));
            dataModel.setImageHangMuc(imageHangMuc);
            dataModel.setTenHangMuc(tenHangMuc);
            dataModel.setNgaythang(ngayTao);
            dataModel.setImageViTien(imageViTien);
            dataModel.setTenViTien(tenViTien);
            dataModel.setTrangThai(trangThai);
            dataModel.set_idViTien(idViTien);
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;
    }

    //thêm mục thu chi
    public boolean insertThuChi(int _idViTien, int sotien, String imageHangMuc,
                                String tenHangMuc, String mota,
                                String ngaythang, String imageViTien,
                                String tenViTien, int trangThai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COT_SOTIEN, sotien);
        contentValues.put(COT_IMAGE_HANGMUC, imageHangMuc);
        contentValues.put(COT_LOAI_HANGMUC, tenHangMuc);
        contentValues.put(COT_MOTA, mota);
        contentValues.put(COT_NGAYTHANG, ngaythang);
        contentValues.put(COT_IMAGE_VITIEN, imageViTien);
        contentValues.put(COT_TEN_VITIEN, tenViTien);
        contentValues.put(COT_TRANGTHAI, trangThai);
        contentValues.put(COT_ID_VITIEN, _idViTien);
        long result = db.insert(TEN_BANG_THUCHI, null, contentValues);

        return result != -1;
    }

    //update thu chi
    public boolean updateThuChi(int idThuChi, int _idViTien, int sotien, String imageHangMuc,
                                String tenHangMuc, String mota,
                                String ngaythang, String imageViTien,
                                String tenViTien, int trangThai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COT_SOTIEN, sotien);
        contentValues.put(COT_IMAGE_HANGMUC, imageHangMuc);
        contentValues.put(COT_LOAI_HANGMUC, tenHangMuc);
        contentValues.put(COT_MOTA, mota);
        contentValues.put(COT_NGAYTHANG, ngaythang);
        contentValues.put(COT_IMAGE_VITIEN, imageViTien);
        contentValues.put(COT_TEN_VITIEN, tenViTien);
        contentValues.put(COT_TRANGTHAI, trangThai);
        contentValues.put(COT_ID_VITIEN, _idViTien);
        long result = db.update(TEN_BANG_THUCHI, contentValues, COT_ID + "=" + idThuChi, null);

        return result != -1;
    }

    //xóa item trong bảng thu chi
    public boolean deleteThuChi(int idThuChi) {
        SQLiteDatabase db = this.getWritableDatabase();
        long longId = (long) idThuChi;
        return db.delete(TEN_BANG_THUCHI, COT_ID + " = " + longId, null) > 0;
    }

    //xóa item trong ví
    public boolean deleteByViTienID(int idViTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        long longId = (long) idViTien;
        return db.delete(TEN_BANG_THUCHI, COT_ID_VITIEN + " = " + longId, null) > 0;
    }

    public int getTienHanMuc(String loaiHangMuc, int idViTien, Calendar ngayBD, Calendar ngayKT) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * "
                + " from " + TEN_BANG_THUCHI
                + " where " + COT_ID_VITIEN + " = " + idViTien
                + " and " + COT_LOAI_HANGMUC + " = '" + loaiHangMuc + "'" + " ;", null);
        //  + " group by " + COT_ID_VITIEN + "," + COT_ID_VITIEN + " ;", null);
        int tongTien = 0;
        while (cursor.moveToNext()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngayThang = cursor.getString(cursor.getColumnIndexOrThrow(COT_NGAYTHANG));
            Calendar date = Calendar.getInstance();
            try {
                date.setTime(simpleDateFormat.parse(ngayThang));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (ngayBD.getTimeInMillis() <= date.getTimeInMillis() && date.getTimeInMillis() <= ngayKT.getTimeInMillis())
                tongTien += cursor.getInt(cursor.getColumnIndexOrThrow(COT_SOTIEN));
        }
        return tongTien;
    }
}
