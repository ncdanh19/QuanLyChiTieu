package com.sinhvien.quanlychitieu.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.Database.TaiKhoanHelper;
import com.sinhvien.quanlychitieu.Database.ThuChi;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.activity.HangMucActivity;
import com.sinhvien.quanlychitieu.activity.TongQuanActivity;
import com.sinhvien.quanlychitieu.adapter.AlertDialogAdapter;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;
import com.sinhvien.quanlychitieu.adapter.OnPagerItemSelected;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ThuTienFragment extends Fragment {
    View view;
    TextView mNgay;
    TextView textCurrency;
    LinearLayout mChonHangMuc;
    Button mLuu;
    EditText mSoTien;
    EditText mMoTa;
    ImageView imageViTien;
    ImageView imageHangMuc;
    TextView textHangMuc;
    TextView textViTien;
    LinearLayout btnLoaiTaiKhoan;
    ChuyenImage chuyendoi;
    int _idViTien = -1;
    TaiKhoanHelper database;
    AlertDialogAdapter adapter;
    List<TaiKhoan> listTaiKhoan;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mDatabase;
    final Calendar calendar = Calendar.getInstance();
    int pos,begin;
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
                popUpDialog(v);
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



        mSoTien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //mSoTien.setSelection(mSoTien.getText().length());
                pos = mSoTien.getText().length();
                begin = mSoTien.getSelectionStart();
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mSoTien.getText().toString();

                mSoTien.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(".")) {
                        originalString = originalString.replaceAll("\\.", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
                    formatter.applyPattern("#,###,###.###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    mSoTien.setText(formattedString);

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                if (begin == pos) {
                    mSoTien.setSelection(mSoTien.getText().length());

                }
                if (begin != pos) {
                    mSoTien.setSelection(begin);
                }
                mSoTien.addTextChangedListener(this);


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
                    _idViTien = i.getInt("_id");
                    textViTien.setText(text);
                    imageViTien.setImageBitmap(ChuyenImage.getStringtoImage(img));
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
    public void popUpDialog(View v) {


        final Dialog dialog = new Dialog(getActivity());

        database = new TaiKhoanHelper(getActivity());
        listTaiKhoan = database.getdata();
        adapter =new AlertDialogAdapter(getActivity(), listTaiKhoan, new OnPagerItemSelected() {
            @Override
            public void pagerItemSelected() {
                dialog.dismiss();
            }
        });

        //set layout custom
        dialog.setContentView(R.layout.alerdialog_layout);
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);

        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        dialog.show();

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
        Bitmap bmapViTien = ((BitmapDrawable) imageViTien.getDrawable()).getBitmap();
        Bitmap bmapHangMuc =((BitmapDrawable) imageHangMuc.getDrawable()).getBitmap();
        String imageViTien = ChuyenImage.getString(bmapViTien);
        String imageHangMuc = ChuyenImage.getString(bmapHangMuc);
        String tenHangMuc = textHangMuc.getText().toString();
        String moTa = mMoTa.getText().toString();
        String ngayThang = mNgay.getText().toString();
        String tenViTien = textViTien.getText().toString();
        ThuChi thuChi = new ThuChi(_idViTien, soTien, imageHangMuc, tenHangMuc,
                moTa, ngayThang, imageViTien, tenViTien, 1);
        mDatabase.child("ThuChi").push().setValue(thuChi);
    }

    public void them() {
        String formatSoTien = mSoTien.getText().toString().replaceAll("\\.", "");
        int soTien = Integer.parseInt(formatSoTien);
        imageViTien.getDrawingCache();
        imageHangMuc.getDrawingCache();
        Bitmap bmapViTien = ((BitmapDrawable) imageViTien.getDrawable()).getBitmap();
        Bitmap bmapHangMuc =((BitmapDrawable) imageHangMuc.getDrawable()).getBitmap();
        String imageViTien = ChuyenImage.getString(bmapViTien);
        String imageHangMuc = ChuyenImage.getString(bmapHangMuc);
        String tenHangMuc = textHangMuc.getText().toString();
        String moTa = mMoTa.getText().toString();
        String ngayThang = mNgay.getText().toString();
        String tenViTien = textViTien.getText().toString();

        ThuChiHelper tc_database = new ThuChiHelper(getContext());
        boolean trt = tc_database.insertThuChi(_idViTien, soTien, imageHangMuc,
                tenHangMuc, moTa,
                ngayThang, imageViTien,
                tenViTien, 1);

        TaiKhoanHelper tk_database = new TaiKhoanHelper(getContext());
        Boolean xuly = tk_database.xuLy(_idViTien, 1,soTien);
        Intent intent = new Intent(getContext(), TongQuanActivity.class);
        startActivity(intent);
    }
    public void anhXa(){
        mNgay = (TextView) view.findViewById(R.id.btn_Ngay);
        mChonHangMuc = (LinearLayout) view.findViewById(R.id.btn_HangMuc);
        mSoTien = (EditText) view.findViewById(R.id.edt_SoTien);
        mMoTa = (EditText) view.findViewById(R.id.edt_MoTa);
        mLuu = (Button) view.findViewById(R.id.btn_Luu);
        mNgay.setText(simpleDateFormat.format(calendar.getTime()));
        imageViTien = (ImageView) view.findViewById(R.id.image_vitien);
        textViTien = (TextView) view.findViewById(R.id.text_vitien);
        btnLoaiTaiKhoan = (LinearLayout) view.findViewById(R.id.btn_chonvi);
        imageHangMuc = (ImageView) view.findViewById(R.id.image_hangmuc);
        textHangMuc = (TextView) view.findViewById(R.id.text_hangmuc);
        textCurrency = (TextView) view.findViewById(R.id.currency);
        textCurrency.setPaintFlags(textCurrency.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}

