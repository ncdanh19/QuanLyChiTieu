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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.quanlychitieu.Database.HanMuc;
import com.sinhvien.quanlychitieu.Database.HanMucHelper;
import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.Database.TaiKhoanHelper;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.AlertDialogAdapter;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;
import com.sinhvien.quanlychitieu.adapter.HanMucAdapter;
import com.sinhvien.quanlychitieu.adapter.OnPagerItemSelected;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CustomThuChi extends AppCompatActivity {

    TextView mNgay;
    LinearLayout mChonHangMuc;
    EditText mSoTien;
    EditText mMoTa;
    Button btnLuu;
    Button btnXoa;
    ImageButton btnTroLai;
    ImageView imageViTien;
    TextView textViTien;
    LinearLayout btnLoaiTaiKhoan;
    ImageView imageHangMuc;
    TextView textHangMuc;
    TextView textCurrency;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    final Calendar calendar = Calendar.getInstance();
    int idThuChi, idViTienMoi, trangThaiCu, trangThaiMoi;
    int idViTienCu, soTienCu, soTienMoi;
    String ngayCu, ngayMoi;
    String hangMucCu;
    String moTaCu;
    ThuChiHelper tc_database;
    TaiKhoanHelper tk_database;
    int pos, begin;
    List<TaiKhoan> listTaiKhoan;
    AlertDialogAdapter adapter;
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
        if (trangThaiMoi == 0) {
            mSoTien.setTextColor(Color.RED);
            //textCurrency.setTextColor(Color.RED);
        }
        if (trangThaiMoi == 1) {
            mSoTien.setTextColor(Color.parseColor("#33cc33"));
            //textCurrency.setTextColor(Color.parseColor("#33cc33"));
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
        try {
            boolean result = tc_database.deleteThuChi(idThuChi);
            if (result) {
                if (trangThaiCu == 0) // nếu là chi thì xóa sẽ + tiền trở lại cho tài khoản
                    tk_database.xuLy(idViTienCu, 1, soTienCu); //xóa dữ liệu cũ vì chưa thay đổi
                else
                    tk_database.xuLy(idViTienCu, 0, soTienCu);
                updateHanMuc();
                finish();
                Toast.makeText(getBaseContext(), "Xóa giao dịch thành công", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        } catch (Exception ignored) {
        }
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
        imageViTien.getDrawingCache();
        imageHangMuc.getDrawingCache();
        Bitmap bmapViTien = ((BitmapDrawable) imageViTien.getDrawable()).getBitmap();
        Bitmap bmapHangMuc = ((BitmapDrawable) imageHangMuc.getDrawable()).getBitmap();
        String imgViTien = ChuyenImage.getString(bmapViTien);
        String imgHangMuc = ChuyenImage.getString(bmapHangMuc);
        String tenHangMuc = textHangMuc.getText().toString();
        String moTaMoi = mMoTa.getText().toString();
        ngayMoi = mNgay.getText().toString();
        String tenViTien = textViTien.getText().toString();
        boolean flag = true;
        if (formatSoTien.equals("")) {
            Toast.makeText(thisContext, "Bạn chưa nhập số tiền", Toast.LENGTH_SHORT).show();
            flag = false;
        }
        if (flag) {
            soTienMoi = Integer.parseInt(formatSoTien);
            if (soTienCu == soTienMoi) {
                if (idViTienMoi == idViTienCu && hangMucCu.equals(tenHangMuc) && trangThaiCu == trangThaiMoi && ngayCu.equals(ngayMoi) && moTaCu.equals(moTaMoi)) {
                    Toast.makeText(getBaseContext(), "Không có thay đổi", Toast.LENGTH_SHORT).show();
                } else {
                    ThuChiHelper tc_database = new ThuChiHelper(thisContext);
                    boolean trt = tc_database.updateThuChi(idThuChi, idViTienMoi, soTienMoi, imgHangMuc, tenHangMuc,
                            moTaMoi, ngayMoi, imgViTien, tenViTien, trangThaiMoi);
                    Boolean xuly = tk_database.xuLyUpdate(idViTienMoi, soTienMoi, idViTienCu, soTienCu, trangThaiCu, trangThaiMoi);
                    if (trt) {
                        Toast.makeText(getBaseContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            } else {
                ThuChiHelper tc_database = new ThuChiHelper(thisContext);
                boolean trt = tc_database.updateThuChi(idThuChi, idViTienMoi, soTienMoi, imgHangMuc, tenHangMuc,
                        moTaMoi, ngayMoi, imgViTien, tenViTien, trangThaiMoi);
                Boolean xuly = tk_database.xuLyUpdate(idViTienMoi, soTienMoi, idViTienCu, soTienCu, trangThaiCu, trangThaiMoi);

                if (trt) {
                    updateHanMuc();
                    Toast.makeText(getBaseContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public void updateHanMuc() {
        HanMucHelper hm_database = new HanMucHelper(thisContext);
        List<HanMuc> listHanMuc = hm_database.getdata();
        //gắn list vào adapter
        HanMucAdapter adapter = new HanMucAdapter(thisContext, listHanMuc);
        adapter.updateData(listHanMuc);
    }

    public void goToHangMuc(View v) {
        Intent intent = new Intent(getBaseContext(), HangMucActivity.class);
        intent.putExtra("page", trangThaiCu);
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
                    idViTienMoi = i.getInt("_id");
                    textViTien.setText(text);
                    imageViTien.setImageBitmap(ChuyenImage.getStringtoImage(img));
                    imageViTien.setDrawingCacheEnabled(true);
                    textViTien.setTextColor(Color.BLACK);

                }
                if (action.equals("hangmucchi")) {
                    hangMucCu = i.getString("text");
                    final int img = i.getInt("img");
                    trangThaiMoi = i.getInt("page");
                    textHangMuc.setText(hangMucCu);
                    imageHangMuc.setImageResource(img);
                    imageHangMuc.setDrawingCacheEnabled(true);
                    textHangMuc.setTextColor(Color.BLACK);
                }
                if (action.equals("hangmucthu")) {
                    hangMucCu = i.getString("text");
                    final int img = i.getInt("img");
                    trangThaiMoi = i.getInt("page");
                    textHangMuc.setText(hangMucCu);
                    imageHangMuc.setImageResource(img);
                    imageHangMuc.setDrawingCacheEnabled(true);
                    textHangMuc.setTextColor(Color.BLACK);

                }
            }
        }
    };

    public void truyenDuLieu() {
        Intent extras = getIntent();
        idThuChi = extras.getIntExtra("idThuChi", -1);
        soTienCu = Integer.parseInt(extras.getStringExtra("soTien"));
        hangMucCu = extras.getStringExtra("tenHangMuc");
        String imageHangMuc = extras.getStringExtra("imageHangMuc");
        moTaCu = extras.getStringExtra("moTa");
        ngayCu = extras.getStringExtra("ngayThang");
        ngayMoi = ngayCu;
        String tenViTien = extras.getStringExtra("tenViTien");
        String imageViTien = extras.getStringExtra("imageViTien");
        idViTienCu = extras.getIntExtra("idViTien", -1);
        idViTienMoi = idViTienCu;
        trangThaiCu = extras.getIntExtra("trangThai", -1);
        trangThaiMoi = trangThaiCu;
        setDuLieu(soTienCu, hangMucCu, imageHangMuc, moTaCu, ngayCu, tenViTien, imageViTien, trangThaiCu);
    }

    public void setDuLieu(int soTien, String tenHangMuc, String imgHangMuc, String moTa,
                          String ngayThang, String tenViTien, String imgViTien, int trangThai) {
        mSoTien.setText(String.valueOf(soTien));
        textHangMuc.setText(tenHangMuc);
        imageHangMuc.setImageBitmap(ChuyenImage.getStringtoImage(imgHangMuc));
        mMoTa.setText(moTa);
        mNgay.setText(ngayThang);
        textViTien.setText(tenViTien);
        imageViTien.setImageBitmap(ChuyenImage.getStringtoImage(imgViTien));
        if (trangThai == 0) {
            mSoTien.setTextColor(Color.RED);
            //textCurrency.setTextColor(Color.RED);
        }
        if (trangThai == 1) {
            mSoTien.setTextColor(Color.parseColor("#33cc33"));
            //textCurrency.setTextColor(Color.parseColor("#33cc33"));
        }
    }

    private void anhXa() {
        mNgay = (TextView) findViewById(R.id.btn_Ngay);
        mChonHangMuc = (LinearLayout) findViewById(R.id.btn_HangMuc);
        mSoTien = (EditText) findViewById(R.id.edt_SoTien);
        mMoTa = (EditText) findViewById(R.id.edt_MoTa);
        btnLuu = (Button) findViewById(R.id.btn_Luu);
        btnXoa = (Button) findViewById(R.id.btn_Xoa);
        btnTroLai = (ImageButton) findViewById(R.id.trolai);
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
