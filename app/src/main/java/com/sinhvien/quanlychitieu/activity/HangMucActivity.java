package com.sinhvien.quanlychitieu.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.ViewPagerAdapter;
import com.sinhvien.quanlychitieu.fragment.ChiTienFragment;
import com.sinhvien.quanlychitieu.fragment.HangMucChiFragment;
import com.sinhvien.quanlychitieu.fragment.HangMucThuFragment;
import com.sinhvien.quanlychitieu.fragment.ThuTienFragment;
public class HangMucActivity extends AppCompatActivity {

    ImageView mTrolai;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_muc);
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
        adapter.addFragment(new HangMucChiFragment(), "Mục chi");
        adapter.addFragment(new HangMucThuFragment(), "Mục thu");
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
