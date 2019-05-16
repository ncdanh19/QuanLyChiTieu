package com.sinhvien.quanlychitieu.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ChuyenImage {
    // chuyển bitmap thành String
    public static String getString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yourDrawableImage);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    //Chuyển String thành bitmap
    public static Bitmap getStringtoImage(String imageString) {
        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    //chuyển bitmap thành byte
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    //chuyển byte thành bitmap
    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
