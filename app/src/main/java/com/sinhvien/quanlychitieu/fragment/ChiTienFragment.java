package com.sinhvien.quanlychitieu.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinhvien.quanlychitieu.Database.ThuChi;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.activity.HangMucChi;
import com.sinhvien.quanlychitieu.activity.TaiKhoanActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ChiTienFragment extends Fragment {
    View view;
    Button mNgay;
    Button mChonHangMuc;
    Button mChonVi;
    Button mLuu;
    TextView chonNgay;
    EditText mSoTien;
    EditText mMoTa;
    private DatabaseReference mDatabase;
    final Calendar calendar = Calendar.getInstance();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public ChiTienFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chi_tien, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        anhXa();

        //Button Ngày
        mNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });

        //Button chọn hạng mục
        mChonHangMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHangMuc(v);
            }
        });

        //Button chọn ví
        mChonVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToViTien(v);
            }
        });

        //Button Luu
        mLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewData();
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    //Intend chuyển sang hạng mục
    public void goToHangMuc(View v)
    {
        Intent intent = new Intent(getActivity().getApplication(), HangMucChi.class);
        startActivity(intent);
    }


    //Intent chuyển sang chọn ví
    public void goToViTien(View v)
    {
        Intent intent = new Intent(getActivity().getApplication(), TaiKhoanActivity.class);
        startActivity(intent);
    }

    private void chonNgay(){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                mNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();
    }

    //Lưu dữ liệu
    public void writeNewData() {
        int soTien= Integer.parseInt(mSoTien.getText().toString());
        String moTa = mMoTa.getText().toString();
        String ngayThang = mNgay.getText().toString();
        ThuChi thuChi = new ThuChi(soTien,moTa,ngayThang);
        mDatabase.child("ChiTien").push().setValue(thuChi);
    }

    public void anhXa(){
        mNgay = (Button)view.findViewById(R.id.btn_Ngay);
        chonNgay=(TextView)view.findViewById(R.id.tv_ChonNgay);
        mChonHangMuc=(Button) view.findViewById(R.id.btn_HangMuc);
        mChonVi=(Button) view.findViewById(R.id.btn_ChonVi);
        mSoTien=(EditText) view.findViewById(R.id.edt_SoTien);
        mMoTa=(EditText) view.findViewById(R.id.edt_MoTa);
        mLuu=(Button)view.findViewById(R.id.btn_Luu);
        mNgay.setText(simpleDateFormat.format(calendar.getTime()));
    }
}

