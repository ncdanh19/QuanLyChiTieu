package com.sinhvien.quanlychitieu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinhvien.quanlychitieu.Database.LoaiTaiKhoan;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.RecyclerViewAdapter;

import java.util.ArrayList;


public class LoaiTaiKhoanActivity extends AppCompatActivity {

    ImageButton mTroLai;
    private final String tenloai[] = {
            "Tiền mặt"
    };

    private final int imagerls[] = {
            R.drawable.ic_tienmat
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_tai_khoan);
        anhXa();
        initViews();
        mTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list_LoaiTaiKhoan);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<LoaiTaiKhoan> androidVersions = prepareData();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(),androidVersions);
        recyclerView.setAdapter(adapter);

    }
    private ArrayList<LoaiTaiKhoan> prepareData(){

        ArrayList<LoaiTaiKhoan> android_version = new ArrayList<>();
        for(int i=0;i<tenloai.length;i++){
            LoaiTaiKhoan androidVersion = new LoaiTaiKhoan();
            androidVersion.setTenLoai(tenloai[i]);
            androidVersion.setImg_URL(imagerls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }

    private void anhXa()
    {
        mTroLai=(ImageButton) findViewById(R.id.trolai);
    }
}
