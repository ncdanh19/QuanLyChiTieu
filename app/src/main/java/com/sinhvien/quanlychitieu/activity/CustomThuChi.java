package com.sinhvien.quanlychitieu.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.Database.TaiKhoanHelper;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.AlertDialogAdapter;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;
import com.sinhvien.quanlychitieu.adapter.OnPagerItemSelected;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CustomThuChi extends AppCompatActivity {

    private TextView mNgay;
    private LinearLayout mChonHangMuc;
    private EditText mSoTien;
    private EditText mMoTa;
    private Button btnLuu;
    private ImageView imageViTien;
    private TextView textViTien;
    private LinearLayout btnLoaiTaiKhoan;
    private ImageView imageHangMuc;
    private TextView textHangMuc;
    private TextView textCurrency;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    final Calendar calendar = Calendar.getInstance();
    int idThuChi, idViTien, trangThai;
    String soTien;
    ThuChiHelper tc_database;
    TaiKhoanHelper tk_database;
    private Button btnXoa;
    int pos, begin;
    private ImageView btnTroLai;
    List<TaiKhoan> listTaiKhoan;
    AlertDialogAdapter adapter;
    int _idViTien = -1;
    Context thisContext;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(thisContext).registerReceiver(mReceiver, new IntentFilter("taikhoan"));
        LocalBroadcastManager.getInstance(thisContext).registerReceiver(mReceiver, new IntentFilter("hangmucchi"));
        LocalBroadcastManager.getInstance(thisContext).registerReceiver(mReceiver, new IntentFilter("hangmucthu"));
        if (trangThai == 0) {
            mSoTien.setTextColor(Color.RED);
            textCurrency.setTextColor(Color.RED);
        }
        if (trangThai == 1) {
            mSoTien.setTextColor(Color.parseColor("#33cc33"));
            textCurrency.setTextColor(Color.parseColor("#33cc33"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_thu_chi);
        anhXa();
        formatSoTien();
        truyenDuLieu();
        thisContext = CustomThuChi.this;
        //button chọn hạng mục
        mChonHangMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHangMuc(v);
            }
        });

        //Button Ngày
        mNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });

        //Button chọn ví
        btnLoaiTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDialog(v);
            }
        });


        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luuDuLieu();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaDuLieu();
            }
        });

        btnTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    //hiển thị popup danh sách tài khoản
    public void popUpDialog(View v) {

        final Dialog dialog = new Dialog(thisContext);

        tk_database = new TaiKhoanHelper(thisContext);
        listTaiKhoan = tk_database.getdata();
        adapter = new AlertDialogAdapter(thisContext, listTaiKhoan, new OnPagerItemSelected() {
            @Override
            public void pagerItemSelected() {
                dialog.dismiss();
            }
        });

        //set layout custom
        dialog.setContentView(R.layout.alerdialog_layout);
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);

        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        dialog.show();

    }

    //hiển thị popup chọn ngày
    private void chonNgay() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(thisContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                mNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void xoaDuLieu() {
        boolean result = tc_database.deleteThuChi(idThuChi);
        if (result) {
            tk_database.xuLy(idViTien, trangThai, Integer.parseInt(soTien));
            finish();
            Toast.makeText(getBaseContext(), "Xóa giao dịch thành công", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
    }

    private void formatSoTien() {
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

                    long longval;
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
    }

    private void luuDuLieu() {
        //update theo idThuChi
        String formatSoTien = mSoTien.getText().toString().replaceAll("\\.", "");
        int soTien = Integer.parseInt(formatSoTien);
        imageViTien.getDrawingCache();
        imageHangMuc.getDrawingCache();
        Bitmap bmapViTien = ((BitmapDrawable) imageViTien.getDrawable()).getBitmap();
        Bitmap bmapHangMuc = ((BitmapDrawable) imageHangMuc.getDrawable()).getBitmap();
        String imageViTien = ChuyenImage.getString(bmapViTien);
        String imageHangMuc = ChuyenImage.getString(bmapHangMuc);
        String tenHangMuc = textHangMuc.getText().toString();
        String moTa = mMoTa.getText().toString();
        String ngayThang = mNgay.getText().toString();
        String tenViTien = textViTien.getText().toString();
        tc_database.updateThuChi(idThuChi, idViTien, soTien, imageHangMuc, tenHangMuc,
                moTa, ngayThang, imageViTien, tenViTien, trangThai);
        boolean result = tk_database.xuLy(idViTien, trangThai, soTien);
        if (result) {
            finish();
            Toast.makeText(getBaseContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToHangMuc(View v) {
        Intent intent = new Intent(getBaseContext(), HangMucActivity.class);
        intent.putExtra("page", trangThai);
        startActivity(intent);
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
                if (action.equals("hangmucchi")) {
                    final String text = i.getString("text");
                    final int img = i.getInt("img");
                    trangThai=i.getInt("page");
                    textHangMuc.setText(text);
                    imageHangMuc.setImageResource(img);
                    imageHangMuc.setDrawingCacheEnabled(true);
                }
                if(action.equals("hangmucthu")) {
                    final String text = i.getString("text");
                    final int img = i.getInt("img");
                    trangThai=i.getInt("page");
                    textHangMuc.setText(text);
                    imageHangMuc.setImageResource(img);
                    imageHangMuc.setDrawingCacheEnabled(true);
                }
            }
        }
    };

    public void truyenDuLieu() {
        Intent extras = getIntent();
        idThuChi = extras.getIntExtra("idThuChi", -1);
        soTien = extras.getStringExtra("soTien");
        String tenHangMuc = extras.getStringExtra("tenHangMuc");
        String imageHangMuc = extras.getStringExtra("imageHangMuc");
        String moTa = extras.getStringExtra("moTa");
        String ngayThang = extras.getStringExtra("ngayThang");
        String tenViTien = extras.getStringExtra("tenViTien");
        String imageViTien = extras.getStringExtra("imageViTien");
        idViTien = extras.getIntExtra("idViTien", -1);
        trangThai = extras.getIntExtra("trangThai", -1);
        setDuLieu(soTien, tenHangMuc, imageHangMuc, moTa, ngayThang, tenViTien, imageViTien, trangThai);
    }

    public void setDuLieu(String soTien, String tenHangMuc, String imgHangMuc, String moTa,
                          String ngayThang, String tenViTien, String imgViTien, int trangThai) {
        mSoTien.setText(soTien);
        textHangMuc.setText(tenHangMuc);
        imageHangMuc.setImageBitmap(ChuyenImage.getStringtoImage(imgHangMuc));
        mMoTa.setText(moTa);
        mNgay.setText(ngayThang);
        textViTien.setText(tenViTien);
        imageViTien.setImageBitmap(ChuyenImage.getStringtoImage(imgViTien));
        if (trangThai == 0) {
            mSoTien.setTextColor(Color.RED);
            textCurrency.setTextColor(Color.RED);
        }
        if (trangThai == 1) {
            mSoTien.setTextColor(Color.parseColor("#33cc33"));
            textCurrency.setTextColor(Color.parseColor("#33cc33"));
        }
    }

    private void anhXa() {
        mNgay = (TextView) findViewById(R.id.btn_Ngay);
        mChonHangMuc = (LinearLayout) findViewById(R.id.btn_HangMuc);
        mSoTien = (EditText) findViewById(R.id.edt_SoTien);
        mMoTa = (EditText) findViewById(R.id.edt_MoTa);
        btnLuu = (Button) findViewById(R.id.btn_Luu);
        btnXoa = (Button) findViewById(R.id.btn_Xoa);
        btnTroLai = (ImageView) findViewById(R.id.trolai);
        imageViTien = (ImageView) findViewById(R.id.image_vitien);
        textViTien = (TextView) findViewById(R.id.text_vitien);
        btnLoaiTaiKhoan = (LinearLayout) findViewById(R.id.btn_chonvi);
        imageHangMuc = (ImageView) findViewById(R.id.image_hangmuc);
        textHangMuc = (TextView) findViewById(R.id.text_hangmuc);
        textCurrency = (TextView) findViewById(R.id.currency);
        textCurrency.setPaintFlags(textCurrency.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tc_database = new ThuChiHelper(getApplicationContext());
        tk_database = new TaiKhoanHelper(getApplicationContext());
    }
}
