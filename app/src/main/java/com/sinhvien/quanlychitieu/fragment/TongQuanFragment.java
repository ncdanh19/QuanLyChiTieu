package com.sinhvien.quanlychitieu.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.activity.ThuChiActivity;
import com.sinhvien.quanlychitieu.activity.TongQuanActivity;


public class TongQuanFragment extends Fragment {

    View view;
    FloatingActionButton btnThuChi;

    public TongQuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tong_quan, container, false);
        anhXa();
        btnThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThuChiActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void anhXa(){
        btnThuChi=(FloatingActionButton) view.findViewById(R.id.btn_ThuChi);
    }

}
