package com.sinhvien.quanlychitieu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sinhvien.quanlychitieu.fragment.ChiTienFragment;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.ViewPagerAdapter;
import com.sinhvien.quanlychitieu.fragment.ThuTienFragment;

public class ThuChiActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Toolbar toolbar;
    ActionBar actionBar;
    ImageButton mTroLai;
    int page;

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("hangmucchi"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("hangmucthu"));
        viewPager.setCurrentItem(page);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle i = intent.getExtras();
            String action = intent.getAction();
            if (i != null) {
                if (action.equals("hangmucchi")) { //nếu chọn hạng mục chi sẽ trả về trang chi tiền
                    page = i.getInt("page");
                }
                if (action.equals("hangmucthu")) {
                    page = i.getInt("page");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_chi);
        anhXa();

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChiTienFragment(), "Chi");
        adapter.addFragment(new ThuTienFragment(), "Thu");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //int defaultValue = 0;
        mTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpaper);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mTroLai = (ImageButton) findViewById(R.id.trolai);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
