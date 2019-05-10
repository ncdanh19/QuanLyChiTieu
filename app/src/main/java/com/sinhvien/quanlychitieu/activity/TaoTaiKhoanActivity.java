package com.sinhvien.quanlychitieu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Paint;
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
    ArrayList<TaiKhoan> listTaiKhoan;
    ChuyenImage chuyendoi;
    private static long id = -1;
    int pos,begin;

    private DatabaseReference mDatabase;
    FirebaseStorage storage;
    private TextView textCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_tai_khoan);
        anhXa();
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
                //taoTaiKhoan();
                them();
                finish();
            }
        });

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
                String text = edtSoTien.getText().toString();

                edtSoTien.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();

                    Long longval;
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
        String bmapViTien= chuyendoi.getString(bmap);
        String loaiTaiKhoan = mTextItem.getText().toString();
        String chuThich = edtChuThich.getText().toString();
        TaiKhoan taiKhoan = new TaiKhoan(soTien, tenTaiKhoan, bmapViTien, loaiTaiKhoan, chuThich);
        mDatabase.child("TaiKhoan").push().setValue(taiKhoan);
    }
    public void them() {
        String soTien = edtSoTien.getText().toString();
        soTien = soTien.replaceAll("\\.", "");
        String tenTaiKhoan = edtTenTaiKhoan.getText().toString();
        mIconItem.buildDrawingCache();
        Bitmap bmap = mIconItem.getDrawingCache();
        String Stringbmap = chuyendoi.getString(bmap);
        String loaiTaiKhoan = mTextItem.getText().toString();
        String chuThich = edtChuThich.getText().toString();

        TaiKhoanHelper database = new TaiKhoanHelper(getApplicationContext());
        boolean trt = database.insertdata(soTien, tenTaiKhoan, loaiTaiKhoan, chuThich, Stringbmap);
        Intent intent = new Intent(this, TaiKhoanActivity.class);
        startActivity(intent);
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
        textCurrency=(TextView) findViewById(R.id.currency);
        textCurrency.setPaintFlags(textCurrency.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }


}
