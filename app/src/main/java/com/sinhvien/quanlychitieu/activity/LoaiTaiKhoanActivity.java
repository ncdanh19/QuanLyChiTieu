package com.sinhvien.quanlychitieu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private final String tenloai[] = {
            "Tiền mặt",
            "Tài khoản ngân hàng"
    };

    private final int image[] = {
            R.drawable.ic_tienmat, R.drawable.ic_nganhang

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
                finish();
            }
        });
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.list_LoaiTaiKhoan);
        recyclerView.setHasFixedSize(true);
        //chia ngang recyclerview
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //gắn list vào adapter
        ArrayList<LoaiTaiKhoan> loaiTaiKhoans = prepareData();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), loaiTaiKhoans, new RecyclerViewAdapter.OnPagerItemSelected() {
            @Override
            public void pagerItemSelected() {
                finish();
            }
        });
        recyclerView.setAdapter(adapter);

    }

    //danh sách loại tài khoản
    private ArrayList<LoaiTaiKhoan> prepareData() {

        ArrayList<LoaiTaiKhoan> list = new ArrayList<>();
        for (int i = 0; i < tenloai.length; i++) {
            LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
            loaiTaiKhoan.setTenLoai(tenloai[i]);
            loaiTaiKhoan.setImg(image[i]);
            list.add(loaiTaiKhoan);
        }
        return list;
    }

    private void anhXa() {
        mTroLai = (ImageButton) findViewById(R.id.trolai);
    }
}
