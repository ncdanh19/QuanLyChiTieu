package com.sinhvien.quanlychitieu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;

public class TaoTaiKhoanActivity extends AppCompatActivity {

    ImageButton mTroLai;
    ImageView mIconItem;
    TextView mTextItem;
    LinearLayout btnLoaiTaiKhoan;
    Button btnLuu;
    EditText edtSoTien;
    EditText edtTenTaiKhoan;
    EditText edtChuThich;
    ArrayList<TaiKhoan> listTaiKhoan;
    ChuyenImage chuyendoi;
    private static long id = -1;

    private DatabaseReference mDatabase;
    FirebaseStorage storage;

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
                writeNewTaiKhoan();
                //taoTaiKhoan();
                them();
                finish();
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

    private void taoTaiKhoan() {
        Intent intent = new Intent(this, TaiKhoanActivity.class);
        String soTien = edtSoTien.getText().toString();
        String tenTaiKhoan = edtTenTaiKhoan.getText().toString();
        mIconItem.buildDrawingCache();
        Bitmap bmap = mIconItem.getDrawingCache();
        String loaiTaiKhoan = mTextItem.getText().toString();
        String chuThich = edtChuThich.getText().toString();
        // bundle data.
        intent.putExtra("bmap", bmap);
        intent.putExtra("tentaikhoan", tenTaiKhoan);
        intent.putExtra("sotien", soTien);
        intent.putExtra("loaitaikhoan", loaiTaiKhoan);
        intent.putExtra("chuthich", chuThich);
        startActivity(intent);
    }

    private void writeNewTaiKhoan() {
        String soTien = edtSoTien.getText().toString();
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
        mTroLai = (ImageButton) findViewById(R.id.trolai);
        mIconItem = (ImageView) findViewById(R.id.image_vitien);
        mTextItem = (TextView) findViewById(R.id.text_vitien);
        btnLoaiTaiKhoan = (LinearLayout) findViewById(R.id.btn_loaitk);
        edtSoTien = (EditText) findViewById(R.id.edt_SoTien);
        edtTenTaiKhoan = (EditText) findViewById(R.id.edt_tentk);
        edtChuThich = (EditText) findViewById(R.id.edt_chuthich);
        btnLuu = (Button) findViewById(R.id.btn_Luu);
    }


}
