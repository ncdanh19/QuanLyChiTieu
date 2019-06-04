package com.sinhvien.quanlychitieu.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.fragment.HangMucChiFragment;

public class HangMucChiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_muc_chi);
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.content_frame, new HangMucChiFragment());

        ft.commit();

        ImageButton btnXoa = (ImageButton) findViewById(R.id.trolai);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
