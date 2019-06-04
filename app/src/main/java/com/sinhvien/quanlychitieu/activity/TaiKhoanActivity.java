package com.sinhvien.quanlychitieu.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    List<TaiKhoan> listTaiKhoan;
    private TextView noItem;

    @Override
    protected void onResume() {
        super.onResume();
        database = new TaiKhoanHelper(getApplicationContext());
        listTaiKhoan = database.getdata();
        initViews();

    }

    public void checkNull() {
        if (listTaiKhoan.size() < 1) {
            recyclerView.setVisibility(View.GONE);
            noItem.setVisibility(View.VISIBLE);
        } else {
            noItem.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan);
        anhXa();
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
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initViews() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //chia ngang mỗi item
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        database = new TaiKhoanHelper(getApplicationContext());
        listTaiKhoan = database.getdata();
        //gắn list vào adapter
        adapter = new TaiKhoanAdapter(getApplicationContext(), listTaiKhoan, new OnPagerItemSelected() {
            @Override
            public void pagerItemSelected() {
                //finish();
                checkNull();
            }
        });
        checkNull();
        recyclerView.setAdapter(adapter);
    }


    private void anhXa() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mTroLai = (ImageButton) findViewById(R.id.trolai);
        mTaoVi = (ImageButton) findViewById(R.id.btn_them);
        noItem = (TextView) findViewById(R.id.noItem);
    }
}
