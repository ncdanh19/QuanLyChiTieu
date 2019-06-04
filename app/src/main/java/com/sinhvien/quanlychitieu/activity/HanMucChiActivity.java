package com.sinhvien.quanlychitieu.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sinhvien.quanlychitieu.Database.HanMuc;
import com.sinhvien.quanlychitieu.Database.HanMucHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.HanMucAdapter;

import java.util.List;

public class HanMucChiActivity extends AppCompatActivity {

    ImageButton btnTroLai;
    ImageButton btnThem;
    TextView noItem;
    public RecyclerView recyclerView;
    HanMucHelper hm_database;
    List<HanMuc> listHanMuc;
    HanMucAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        checkNoItem();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_han_muc_chi);
        anhXa();

        //Ẩn thông báo khi đang ở intent
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);

        //button Trở lại
        btnTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HanMucChiActivity.this, TaoHanMucActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkNoItem() {
        if (listHanMuc.size() < 1)
            noItem.setVisibility(View.VISIBLE);
        else {
            noItem.setVisibility(View.GONE);
        }
    }

    //Recycler view
    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //chia ngang mỗi item
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        hm_database = new HanMucHelper(this);
        listHanMuc = hm_database.getdata();
        //gắn list vào adapter
        adapter = new HanMucAdapter(getApplicationContext(), listHanMuc);
        recyclerView.setAdapter(adapter);
    }

    private void anhXa() {
        btnTroLai = (ImageButton) findViewById(R.id.trolai);
        btnThem = (ImageButton) findViewById(R.id.btn_them);
        noItem = (TextView) findViewById(R.id.noItem);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }
}
