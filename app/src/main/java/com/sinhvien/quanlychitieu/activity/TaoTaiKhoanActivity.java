package com.sinhvien.quanlychitieu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.Database.TaiKhoanHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TaoTaiKhoanActivity extends AppCompatActivity {

    private ImageView mTroLai;
    private ImageView mIconItem;
    private TextView mTextItem;
    private LinearLayout btnLoaiTaiKhoan;
    private Button btnLuu;
    private EditText edtSoTien;
    private EditText edtTenTaiKhoan;
    private EditText edtChuThich;
    int pos, begin;

    private DatabaseReference mDatabase;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_tai_khoan);
        anhXa();
        formatSoTien();
        btnLoaiTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaoTaiKhoanActivity.this, LoaiTaiKhoanActivity.class);
                startActivity(i);
            }
        });

        mTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //writeNewTaiKhoan();
                them();
            }
        });

    }

    private void formatSoTien() {
        edtSoTien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //mSoTien.setSelection(mSoTien.getText().length());
                pos = edtSoTien.getText().length();
                begin = edtSoTien.getSelectionStart();
            }

            @Override
            public void afterTextChanged(Editable s) {

                edtSoTien.removeTextChangedListener(this);
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
                    edtSoTien.setText(formattedString);

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                if (begin == pos) {
                    edtSoTien.setSelection(edtSoTien.getText().length());

                }
                if (begin != pos) {
                    edtSoTien.setSelection(begin);
                }
                edtSoTien.addTextChangedListener(this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("data"));
        //Toast.makeText(this, "resume", Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle i = intent.getExtras();
            if (i != null) {
                final String text = i.getString("text");
                final int img = i.getInt("img");
                mTextItem.setText(text);
                mIconItem.setImageResource(img);
            }
        }
    };


    private void writeNewTaiKhoan() {
        int soTien = Integer.parseInt(edtSoTien.getText().toString());
        String tenTaiKhoan = edtTenTaiKhoan.getText().toString();
        mIconItem.buildDrawingCache();
        Bitmap bmap = mIconItem.getDrawingCache();
        String bmapViTien = ChuyenImage.getString(bmap);
        String loaiTaiKhoan = mTextItem.getText().toString();
        String chuThich = edtChuThich.getText().toString();
        TaiKhoan taiKhoan = new TaiKhoan(soTien, tenTaiKhoan, bmapViTien, loaiTaiKhoan, chuThich);
        mDatabase.child("TaiKhoan").push().setValue(taiKhoan);
    }

    public void them() {
        String formatSoTien = edtSoTien.getText().toString().replaceAll("\\.", "");

        String tenTaiKhoan = edtTenTaiKhoan.getText().toString();
        mIconItem.buildDrawingCache();
        Bitmap bmap = ((BitmapDrawable) mIconItem.getDrawable()).getBitmap();
        String Stringbmap = ChuyenImage.getString(bmap);
        String loaiTaiKhoan = mTextItem.getText().toString();
        String chuThich = edtChuThich.getText().toString();

        boolean flag = true;
        if (formatSoTien.equals("")) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập số tiền", Toast.LENGTH_SHORT).show();
            flag = false;
        }
        if (tenTaiKhoan.equals("")) {
            edtTenTaiKhoan.setTextColor(Color.RED);
            flag = false;
        }
        if (loaiTaiKhoan.equals("LOẠI TÀI KHOẢN")) {
            mTextItem.setTextColor(Color.RED);
            flag = false;
        }
        if (flag) {
            TaiKhoanHelper database = new TaiKhoanHelper(getApplicationContext());
            boolean trt = database.insertdata(formatSoTien, tenTaiKhoan, loaiTaiKhoan, chuThich, Stringbmap);
            Toast.makeText(getBaseContext(), "Thêm giao dịch thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, TaiKhoanActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void anhXa() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        mTroLai = (ImageView) findViewById(R.id.trolai);
        mIconItem = (ImageView) findViewById(R.id.image_vitien);
        mTextItem = (TextView) findViewById(R.id.text_vitien);
        btnLoaiTaiKhoan = (LinearLayout) findViewById(R.id.btn_loaitk);
        edtSoTien = (EditText) findViewById(R.id.edt_SoTien);
        edtTenTaiKhoan = (EditText) findViewById(R.id.edt_tentk);
        edtChuThich = (EditText) findViewById(R.id.edt_chuthich);
        btnLuu = (Button) findViewById(R.id.btn_Luu);
        TextView textCurrency = (TextView) findViewById(R.id.currency);
        textCurrency.setPaintFlags(textCurrency.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }


}
