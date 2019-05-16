package com.sinhvien.quanlychitieu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.quanlychitieu.Database.TaiKhoanHelper;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomThuChi extends AppCompatActivity {

    private TextView mNgay;
    private LinearLayout mChonHangMuc;
    private EditText mSoTien;
    private EditText mMoTa;
    private Button btnLuu;
    private ImageView imageViTien;
    private TextView textViTien;
    private LinearLayout btnLoaiTaiKhoan;
    private ImageView imageHangMuc;
    private TextView textHangMuc;
    private TextView textCurrency;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    final Calendar calendar = Calendar.getInstance();
    int idThuChi,idViTien,trangThai;
    String soTien;
    ThuChiHelper tc_database;
    TaiKhoanHelper tk_database;
    private Button btnXoa;
    int pos,begin;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_thu_chi);
        anhXa();
        truyenDuLieu();

        mSoTien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //mSoTien.setSelection(mSoTien.getText().length());
                pos = mSoTien.getText().length();
                begin = mSoTien.getSelectionStart();
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mSoTien.getText().toString();

                mSoTien.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();

                    long longval;
                    if (originalString.contains(".")) {
                        originalString = originalString.replaceAll("\\.", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
                    formatter.applyPattern("#,###,###.###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    mSoTien.setText(formattedString);

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                if (begin == pos) {
                    mSoTien.setSelection(mSoTien.getText().length());

                }
                if (begin != pos) {
                    mSoTien.setSelection(begin);
                }
                mSoTien.addTextChangedListener(this);


            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luuDuLieu();
            }
        });
        
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaDuLieu();
            }
        });
    }

    private void xoaDuLieu() {
        tc_database = new ThuChiHelper(getBaseContext());
        tk_database = new TaiKhoanHelper(getBaseContext());
        boolean result = tc_database.deleteThuChi(idThuChi);
        if(result) {
            tk_database.xuLy(idViTien,trangThai,Integer.parseInt(soTien));
            finish();
            Toast.makeText(getBaseContext(), "Xóa giao dịch thành công", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
    }

    private void luuDuLieu() {
    }


    public void truyenDuLieu() {
        Intent extras = getIntent();
        idThuChi = extras.getIntExtra("idThuChi",-1);
        soTien = extras.getStringExtra("soTien");
        String tenHangMuc = extras.getStringExtra("tenHangMuc");
        String imageHangMuc = extras.getStringExtra("imageHangMuc");
        String moTa = extras.getStringExtra("moTa");
        String ngayThang = extras.getStringExtra("ngayThang");
        String tenViTien = extras.getStringExtra("tenViTien");
        String imageViTien = extras.getStringExtra("imageViTien");
        idViTien=extras.getIntExtra("idViTien",-1);
        trangThai=extras.getIntExtra("trangThai",-1);
        setDuLieu(soTien,tenHangMuc,imageHangMuc,moTa,ngayThang,tenViTien,imageViTien,trangThai);
    }

    public void setDuLieu(String soTien, String tenHangMuc, String imgHangMuc, String moTa,
                          String ngayThang, String tenViTien, String imgViTien,int trangThai){
        mSoTien.setText(soTien);
        textHangMuc.setText(tenHangMuc);
        imageHangMuc.setImageBitmap(ChuyenImage.getStringtoImage(imgHangMuc));
        mMoTa.setText(moTa);
        mNgay.setText(ngayThang);
        textViTien.setText(tenViTien);
        imageViTien.setImageBitmap(ChuyenImage.getStringtoImage(imgViTien));
        if (trangThai == 0) {
            mSoTien.setTextColor(Color.RED);
            textCurrency.setTextColor(Color.RED);
        }
        if (trangThai == 1) {
            mSoTien.setTextColor(Color.parseColor("#33cc33"));
            textCurrency.setTextColor(Color.parseColor("#33cc33"));
        }
    }

    private void anhXa() {
        mNgay = (TextView) findViewById(R.id.btn_Ngay);
        mChonHangMuc = (LinearLayout) findViewById(R.id.btn_HangMuc);
        mSoTien = (EditText) findViewById(R.id.edt_SoTien);
        mMoTa = (EditText) findViewById(R.id.edt_MoTa);
        btnLuu = (Button) findViewById(R.id.btn_Luu);
        btnXoa=(Button)findViewById(R.id.btn_Xoa);
        imageViTien = (ImageView) findViewById(R.id.image_vitien);
        textViTien = (TextView) findViewById(R.id.text_vitien);
        btnLoaiTaiKhoan = (LinearLayout) findViewById(R.id.btn_chonvi);
        imageHangMuc = (ImageView) findViewById(R.id.image_hangmuc);
        textHangMuc = (TextView) findViewById(R.id.text_hangmuc);
        textCurrency = (TextView) findViewById(R.id.currency);
        textCurrency.setPaintFlags(textCurrency.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }
}
