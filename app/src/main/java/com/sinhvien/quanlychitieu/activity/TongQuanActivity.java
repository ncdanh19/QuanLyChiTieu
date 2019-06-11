package com.sinhvien.quanlychitieu.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinhvien.quanlychitieu.Database.HanMuc;
import com.sinhvien.quanlychitieu.Database.HanMucHelper;
import com.sinhvien.quanlychitieu.Database.TaiKhoanHelper;
import com.sinhvien.quanlychitieu.Database.ThuChi;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;
import com.sinhvien.quanlychitieu.adapter.HanMucAdapter;
import com.sinhvien.quanlychitieu.adapter.TongQuanAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TongQuanActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    FloatingActionButton btnThuChi;
    TextView textSoTien;
    TextView textCurrency;
    RecyclerView recyclerView;
    TongQuanAdapter adapter;
    ThuChiHelper tc_database;
    TaiKhoanHelper tk_database;
    HanMucHelper hm_database;
    List<ThuChi> listThuChi;
    List<HanMuc> listHanMuc;

    private DatabaseReference mDatabase;
    private TextView text_no_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_quan);
        anhXa();
        // tk_database=  new TaiKhoanHelper(getApplicationContext());
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
                Intent intent = new Intent(TongQuanActivity.this, ThuChiActivity.class);
                startActivity(intent);
                finish();
            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // set item as selected to persist highlight
                menuItem.setChecked(false);
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();
                // Add code here to update the UI based on the item selected
                switch (menuItem.getItemId()) {
                    case R.id.tong_quan:
                        return true;
                    case R.id.tai_khoan:
                        Intent i_taikhoan = new Intent(TongQuanActivity.this, TaiKhoanActivity.class);
                        startActivity(i_taikhoan);
                        finish();
                        return true;
                    case R.id.thong_ke:
                        Intent i_thongke = new Intent(TongQuanActivity.this, ThongKeActivity.class);
                        startActivity(i_thongke);
                        finish();
                        return true;
                    case R.id.han_muc:
                        Intent i_hanmuc = new Intent(TongQuanActivity.this, HanMucChiActivity.class);
                        startActivity(i_hanmuc);
                        finish();
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
    protected void onResume() {
        super.onResume();
        tk_database = new TaiKhoanHelper(getApplication());
        textSoTien.setText(formatCurrency(tk_database.tongTien()));
        initViews();
        checkNoItem();
        checkHanMuc();
    }

    public String formatCurrency(String string) {
        String originalString = string;
        Long longval = Long.parseLong(originalString);

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
        formatter.applyPattern("#,###,###.###");

        //setting text after format to EditText
        return formatter.format(longval);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

        //gắn list vào adapter
        tc_database = new ThuChiHelper(getApplicationContext());
        listThuChi = tc_database.getdata();

        adapter = new TongQuanAdapter(this, listThuChi);
        recyclerView.setAdapter(adapter);
    }

    private void checkNoItem() {
        if (listThuChi.size() < 1) {
            text_no_item.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            text_no_item.setVisibility(View.GONE);
        }
    }

    private void checkHanMuc() {
        hm_database = new HanMucHelper(getApplication());
        listHanMuc = hm_database.getdata();
        HanMucAdapter adapter = new HanMucAdapter(getApplicationContext(), listHanMuc);
        adapter.updateData(listHanMuc);
        for (int i = 0; i < listHanMuc.size(); i++) {
            HanMuc hanMuc = listHanMuc.get(i);
            if (hanMuc.getTrangThai() == 1) {
                displayNotification(hanMuc);
                return;
            }
        }
    }

    private int notificationID = createID();

    protected void displayNotification(HanMuc item) {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "channel-id";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Cảnh báo")
                .setContentText("Số tiền của bạn đã vượt quá hạn mức cho phép")
                .setSmallIcon(R.drawable.ic_warning)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_stat_name))
                .setLargeIcon(ChuyenImage.getStringtoImage(item.getImageHangMuc()))
                .setVibrate(new long[]{100, 250})
                .setLights(Color.RED, 500, 5000)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary));


        Intent resultIntent = new Intent(this, HanMucChiActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(TongQuanActivity.class);
        //stackBuilder.addNextIntent(resultIntent);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        //PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(0, mBuilder.build());
    }

    public int createID() {
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.ENGLISH).format(now));
        return id;
    }

    private void anhXa() {
        btnThuChi = (FloatingActionButton) findViewById(R.id.btn_ThuChi);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        textSoTien = (TextView) findViewById(R.id.tv_SoTien);
        textCurrency = (TextView) findViewById(R.id.currency);
        textCurrency.setPaintFlags(textCurrency.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        text_no_item = (TextView) findViewById(R.id.tv_no_item);

    }

}
