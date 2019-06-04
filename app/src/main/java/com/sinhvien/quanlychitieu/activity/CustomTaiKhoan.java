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
import com.sinhvien.quanlychitieu.Database.HanMuc;
import com.sinhvien.quanlychitieu.Database.HanMucHelper;
import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.Database.TaiKhoanHelper;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;
import com.sinhvien.quanlychitieu.adapter.HanMucAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomTaiKhoan extends AppCompatActivity {

    private ImageButton mTroLai;
    private ImageView mIconItem;
    private TextView mTextItem;
    private LinearLayout btnLoaiTaiKhoan;
    private Button btnLuu;
    private EditText edtSoTien;
    private EditText edtTenTaiKhoan;
    private EditText edtChuThich;
    TaiKhoanHelper tk_database;
    ThuChiHelper tc_database;
    ChuyenImage chuyendoi;
    int idTaiKhoan = -1;
    int pos, begin;

    private DatabaseReference mDatabase;
    FirebaseStorage storage;
    private TextView textCurrency;
    Context thisContext;
    private Button btnXoa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_tai_khoan);
        anhXa();
        formatSoTien();
        truyenDuLieu();
        btnLoaiTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(thisContext, LoaiTaiKhoanActivity.class);
                startActivity(i);
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


        mTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("data"));
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

    private void xoaDuLieu() {
        boolean xoa_giaodich = tc_database.deleteByViTienID(idTaiKhoan);
        boolean xoa_taikhoan = tk_database.deleteTaiKhoan(idTaiKhoan);
        if (xoa_taikhoan) {
            HanMucHelper hm_database = new HanMucHelper(thisContext);
            hm_database.deleteHanMucByIDViTien(idTaiKhoan);
            finish();
            Toast.makeText(getBaseContext(),
                    "Xóa tài khoản thành công", Toast.LENGTH_LONG).show();
        }
    }

    private void luuDuLieu() {
        //update theo idThuChi
        String formatSoTien = edtSoTien.getText().toString().replaceAll("\\.", "");
        int soTien = Integer.parseInt(formatSoTien);
        String tenTaiKhoan = edtTenTaiKhoan.getText().toString();
        mIconItem.buildDrawingCache();
        Bitmap bmap = ((BitmapDrawable) mIconItem.getDrawable()).getBitmap();
        String imgLoaiTaiKhoan = ChuyenImage.getString(bmap);
        String loaiTaiKhoan = mTextItem.getText().toString();
        String chuThich = edtChuThich.getText().toString();

        boolean result = tk_database.updateTaiKhoan(idTaiKhoan, soTien, tenTaiKhoan, loaiTaiKhoan, imgLoaiTaiKhoan, chuThich);
        if (result) {
            finish();
            Toast.makeText(getBaseContext(), "Sửa tài khoản thành công", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(thisContext, "Sửa tài khoản thất bại", Toast.LENGTH_SHORT).show();
    }


    public void truyenDuLieu() {
        Intent extras = getIntent();
        idTaiKhoan = extras.getIntExtra("_id", -1);
        int soTien = extras.getIntExtra("soTien", -1);
        String tenTaiKhoan = extras.getStringExtra("tenTaiKhoan");
        String imgLoaiTaiKhoan = extras.getStringExtra("imgLoaiTaiKhoan");
        String tenLoaiTaiKhoan = extras.getStringExtra("tenLoaiTaiKhoan");
        String chuThich = extras.getStringExtra("chuThich");
        setDuLieu(soTien, tenTaiKhoan, tenLoaiTaiKhoan, imgLoaiTaiKhoan, chuThich);
    }

    public void setDuLieu(int soTien, String tenTaiKhoan, String tenLoaiTaiKhoan, String imgLoaiTaiKhoan, String chuThich) {
        edtSoTien.setText(String.valueOf(soTien));
        edtTenTaiKhoan.setText(tenTaiKhoan);
        mTextItem.setText(tenLoaiTaiKhoan);
        mIconItem.setImageBitmap(ChuyenImage.getStringtoImage(imgLoaiTaiKhoan));
        edtChuThich.setText(chuThich);
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


    private void anhXa() {
        thisContext = CustomTaiKhoan.this;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        mTroLai = (ImageButton) findViewById(R.id.trolai);
        mIconItem = (ImageView) findViewById(R.id.image_vitien);
        mTextItem = (TextView) findViewById(R.id.text_vitien);
        btnLoaiTaiKhoan = (LinearLayout) findViewById(R.id.btn_loaitk);
        edtSoTien = (EditText) findViewById(R.id.edt_SoTien);
        edtTenTaiKhoan = (EditText) findViewById(R.id.edt_tentk);
        edtChuThich = (EditText) findViewById(R.id.edt_chuthich);
        btnLuu = (Button) findViewById(R.id.btn_Luu);
        btnXoa = (Button) findViewById(R.id.btn_Xoa);
        textCurrency = (TextView) findViewById(R.id.currency);
        textCurrency.setPaintFlags(textCurrency.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tc_database = new ThuChiHelper(thisContext);
        tk_database = new TaiKhoanHelper(thisContext);

    }
}
