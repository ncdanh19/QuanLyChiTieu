package com.sinhvien.quanlychitieu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.sinhvien.quanlychitieu.activity.HangMucActivity;
import com.sinhvien.quanlychitieu.R;

import java.util.Calendar;


public class ThuChiFragment extends Fragment {
    View view;
    int dayOfMonth,month,year;
    Calendar calendar;
    Button mNgay;
    Button mChonHangMuc;
    TextView chonNgay;
    DatePicker datePickerDialog;
    public ThuChiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_thu_chi, container, false);

        mNgay = (Button)view.findViewById(R.id.btn_Ngay);
        chonNgay=(TextView)view.findViewById(R.id.tv_ChonNgay);
        mChonHangMuc=(Button) view.findViewById(R.id.btn_HangMuc);


        //Button Ngày
        mNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
            }
        });

        //Button chọn hạng mục
        mChonHangMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHangMuc(v);
            }
        });

        return view;
    }

    //Intend chuyển sang hạng mục
    public void goToHangMuc(View v)
    {
        Intent intent = new Intent(getActivity().getApplication(), HangMucActivity.class);
        startActivity(intent);
    }
}
