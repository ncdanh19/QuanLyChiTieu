package com.sinhvien.quanlychitieu.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sinhvien.quanlychitieu.fragment.ChiTienFragment;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.ViewPagerAdapter;
import com.sinhvien.quanlychitieu.fragment.ThuTienFragment;

public class ThuChiActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    Toolbar toolbar;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_chi);
        anhXa();
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_trolai);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }


        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChiTienFragment(), "Chi");
        adapter.addFragment(new ThuTienFragment(), "Thu");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void anhXa() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpaper);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            return true;
    }
        return super.onOptionsItemSelected(item);
    }


}
