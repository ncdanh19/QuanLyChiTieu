package com.sinhvien.quanlychitieu.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinhvien.quanlychitieu.Database.ThuChi;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.TongQuanAdapter;

import java.util.List;

public class TongQuanActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    FloatingActionButton btnThuChi;
    TextView mSotien;
    RecyclerView recyclerView;
    TongQuanAdapter adapter;
    ThuChiHelper database;
    List<ThuChi> listThuChi ;
    private LinearLayoutManager linearLayoutManager;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_quan);
        anhXa();
        initViews();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayShowTitleEnabled(false);

        mDatabase = FirebaseDatabase.getInstance().getReference();




        //Button chuyển sang intend thu chi
        btnThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TongQuanActivity.this,ThuChiActivity.class);
                startActivity(intent);
            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // set item as selected to persist highlight
                menuItem.setChecked(true);
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();
                Fragment fragment = null;
                // Add code here to update the UI based on the item selected
                switch (menuItem.getItemId()) {
                    case R.id.tong_quan:
                        return true;
                    case R.id.hang_muc:
                        Intent i_hangmuc = new Intent(TongQuanActivity.this, HangMucActivity.class);
                        startActivity(i_hangmuc);
                        return true;
                    case R.id.tai_khoan:
                        Intent i_taikhoan = new Intent(TongQuanActivity.this, TaiKhoanActivity.class);
                        startActivity(i_taikhoan);
                        return true;
                    default:
                        Toast.makeText(getApplication(),
                                "Your Message", Toast.LENGTH_LONG).show();
                        return false;
                }
            }
        });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
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

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //gắn list vào adapter
        database = new ThuChiHelper(getApplicationContext());
        listThuChi = database.getdata();
        adapter = new TongQuanAdapter(getApplicationContext(), listThuChi);
        recyclerView.setAdapter(adapter);
    }
    private void anhXa() {
        btnThuChi=(FloatingActionButton) findViewById(R.id.btn_ThuChi);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mSotien=(TextView) findViewById(R.id.tv_SoTien);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

}
