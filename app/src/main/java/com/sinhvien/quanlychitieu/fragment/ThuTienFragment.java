package com.sinhvien.quanlychitieu.fragment;


import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinhvien.quanlychitieu.Database.ThuChi;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.activity.HangMucActivity;
import com.sinhvien.quanlychitieu.activity.TaiKhoanActivity;
import com.sinhvien.quanlychitieu.activity.TongQuanActivity;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThuTienFragment extends Fragment {
    View view;
    TextView mNgay;
    LinearLayout mChonHangMuc;
    Button mLuu;
    ImageView imageViTien;
    TextView textViTien;
    LinearLayout btnLoaiTaiKhoan;
    TextView chonNgay;
    EditText mSoTien;
    EditText mMoTa;
    ImageView imageHangMuc;
    TextView textHangMuc;
    ChuyenImage chuyendoi;
    private DatabaseReference mDatabase;
    final Calendar calendar = Calendar.getInstance();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ThuTienFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_thu_tien, container, false);
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
        btnLoaiTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToViTien(v);
            }
        });

        //Button Luu
        mLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //writeNewData();
                them();
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, new IntentFilter("taikhoan"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, new IntentFilter("hangmucthu"));

    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle i = intent.getExtras();
            String action = intent.getAction();
            if (i != null) {
                if (action.equals("taikhoan")) {
                    final String text = i.getString("text");
                    final String img = i.getString("img");
                    textViTien.setText(text);
                    imageViTien.setImageBitmap(chuyendoi.getStringtoImage(img));
                    imageViTien.setDrawingCacheEnabled(true);

                }
                if(action.equals("hangmucthu")) {
                    final String text = i.getString("text");
                    final int img = i.getInt("img");
                    textHangMuc.setText(text);
                    imageHangMuc.setImageResource(img);
                    imageHangMuc.setDrawingCacheEnabled(true);
                }
            }
        }
    };


    //Intent chuyển sang hạng mục
    public void goToHangMuc(View v)
    {
        Intent intent = new Intent(getContext(), HangMucActivity.class);
        intent.putExtra("page", 1);
        startActivity(intent);
    }

    //Intent chuyển sang chọn ví
    public void goToViTien(View v)
    {
        Intent intent = new Intent(getActivity().getApplication(), TaiKhoanActivity.class);
        startActivity(intent);
    }

    //Popup dialog chọn ngày
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
        String soTien= mSoTien.getText().toString();
        imageViTien.getDrawingCache();
        imageHangMuc.getDrawingCache();
        Bitmap bmapViTien = imageViTien.getDrawingCache();
        Bitmap bmapHangMuc = imageHangMuc.getDrawingCache();
        String imageViTien = chuyendoi.getString(bmapViTien);
        String imageHangMuc = chuyendoi.getString(bmapHangMuc);
        String tenHangMuc = textHangMuc.getText().toString();
        String moTa = mMoTa.getText().toString();
        String ngayThang = mNgay.getText().toString();
        String tenViTien = textViTien.getText().toString();
        ThuChi thuChi = new ThuChi(soTien,imageHangMuc,tenHangMuc,moTa,ngayThang,imageViTien,tenViTien,1);
        mDatabase.child("ThuChi").push().setValue(thuChi);
    }

    public void them() {
        String soTien= mSoTien.getText().toString();
        imageViTien.getDrawingCache();
        imageHangMuc.getDrawingCache();
        Bitmap bmapViTien = imageViTien.getDrawingCache();
        Bitmap bmapHangMuc = imageHangMuc.getDrawingCache();
        String imageViTien = chuyendoi.getString(bmapViTien);
        String imageHangMuc = chuyendoi.getString(bmapHangMuc);
        String tenHangMuc = textHangMuc.getText().toString();
        String moTa = mMoTa.getText().toString();
        String ngayThang = mNgay.getText().toString();
        String tenViTien = textViTien.getText().toString();

        ThuChiHelper database = new ThuChiHelper(getContext());
        boolean trt = database.insertdata(soTien,imageHangMuc,
                tenHangMuc,moTa,
                ngayThang,imageViTien,
                tenViTien,0);
        Intent intent = new Intent(getContext(), TongQuanActivity.class);
        startActivity(intent);
    }
    public void anhXa(){
        mNgay = (TextView)view.findViewById(R.id.btn_Ngay);
        chonNgay=(TextView)view.findViewById(R.id.tv_ChonNgay);
        mChonHangMuc=(LinearLayout) view.findViewById(R.id.btn_HangMuc);
        imageViTien = (ImageView) view.findViewById(R.id.image_vitien);
        textViTien = (TextView) view.findViewById(R.id.text_vitien);
        btnLoaiTaiKhoan = (LinearLayout) view.findViewById(R.id.btn_chonvi);
        mSoTien=(EditText) view.findViewById(R.id.edt_SoTien);
        mMoTa=(EditText) view.findViewById(R.id.edt_MoTa);
        mLuu=(Button)view.findViewById(R.id.btn_Luu);
        mNgay.setText(simpleDateFormat.format(calendar.getTime()));
        imageHangMuc=(ImageView)view.findViewById(R.id.image_hangmuc);
        textHangMuc=(TextView)view.findViewById(R.id.text_hangmuc);
    }
}

