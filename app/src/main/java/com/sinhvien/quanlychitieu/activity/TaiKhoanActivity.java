package com.sinhvien.quanlychitieu.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.Database.TaiKhoanHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.OnPagerItemSelected;
import com.sinhvien.quanlychitieu.adapter.TaiKhoanAdapter;

import java.util.ArrayList;
import java.util.List;


public class TaiKhoanActivity extends AppCompatActivity {

    ImageButton mTaoVi;
    ImageButton mTroLai;
    RecyclerView recyclerView;
    TaiKhoanAdapter adapter;
    TaiKhoanHelper database;
    private LinearLayoutManager linearLayoutManager;
    List<TaiKhoan> listTaiKhoan ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan);
        anhXa();
        initViews();
        //taoTaiKhoan();
        listTaiKhoan =new ArrayList<TaiKhoan>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTaoVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaiKhoanActivity.this, TaoTaiKhoanActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void taoTaiKhoan() {
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            String soTien = bundle.getString("sotien");
//            String tenTaiKhoan = bundle.getString("tentaikhoan");
//            Bitmap bmap = (Bitmap) bundle.getParcelable("bmap");
//            String loaiTaiKhoan = bundle.getString("loaitaikhoan");
//            String chuThich = bundle.getString("chuthich");
//            TaiKhoan dataToAdd = new TaiKhoan(soTien, tenTaiKhoan, bmap, loaiTaiKhoan, chuThich,bmap);
//            // Update adapter.
//            adapter.taoTaiKhoan(adapter.getItemCount(), dataToAdd);
//        }
//    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //chia ngang mỗi item
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //gắn list vào adapter
        database = new TaiKhoanHelper(getApplicationContext());
        listTaiKhoan = database.getdata();
        adapter = new TaiKhoanAdapter(getApplicationContext(), listTaiKhoan, new OnPagerItemSelected() {
            @Override
            public void pagerItemSelected() {
                finish();
            }
        });
        recyclerView.setAdapter(adapter);
    }


    private void anhXa() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mTroLai = (ImageButton) findViewById(R.id.trolai);
        mTaoVi = (ImageButton) findViewById(R.id.btn_them);
    }
}
