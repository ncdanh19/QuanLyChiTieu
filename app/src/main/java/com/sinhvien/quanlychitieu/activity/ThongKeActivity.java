package com.sinhvien.quanlychitieu.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.sinhvien.quanlychitieu.Database.ThuChi;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;
import com.sinhvien.quanlychitieu.adapter.ViewPagerAdapter;
import com.sinhvien.quanlychitieu.fragment.HangMucChiFragment;
import com.sinhvien.quanlychitieu.fragment.HangMucThuFragment;
import com.sinhvien.quanlychitieu.fragment.ThongKeChiFragment;
import com.sinhvien.quanlychitieu.fragment.ThongKeThuFragment;

import java.util.ArrayList;
import java.util.List;

public class ThongKeActivity extends AppCompatActivity  {

    ImageView mTrolai;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        anhXa();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_trolai);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mTrolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ThongKeChiFragment(), "Chi");
        adapter.addFragment(new ThongKeThuFragment(), "Thu");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        int defaultValue = 0;
        int page = getIntent().getIntExtra("page", defaultValue);
        viewPager.setCurrentItem(page);
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

    private void anhXa(){
        mTrolai=(ImageView) findViewById(R.id.trolai);
        viewPager = (ViewPager) findViewById(R.id.viewpaper);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }
}