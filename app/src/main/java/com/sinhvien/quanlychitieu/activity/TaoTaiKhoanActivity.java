package com.sinhvien.quanlychitieu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.sinhvien.quanlychitieu.R;

public class TaoTaiKhoanActivity extends AppCompatActivity {

    ImageButton mTroLai;
    Button mChonTaiKhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_tai_khoan);
        anhXa();

        mChonTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaoTaiKhoanActivity.this, LoaiTaiKhoanActivity.class);
                startActivity(i);
            }
        });
        mTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void anhXa() {
        mTroLai=(ImageButton)findViewById(R.id.trolai) ;
        mChonTaiKhoan = (Button)findViewById(R.id.btn_loaitk);
    }
}
