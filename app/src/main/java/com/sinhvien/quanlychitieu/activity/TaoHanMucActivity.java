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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.quanlychitieu.Database.HanMucHelper;
import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.Database.TaiKhoanHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.AlertDialogAdapter;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;
import com.sinhvien.quanlychitieu.adapter.HanMucAdapter;
import com.sinhvien.quanlychitieu.adapter.OnPagerItemSelected;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaoHanMucActivity extends AppCompatActivity {

    ImageButton btnTroLai;
    Button btnLuu;
    private EditText edtTenHanMuc;
    private LinearLayout btnHangMuc;
    private LinearLayout btnTaiKhoan;
    private EditText edtSoTien;
    TaiKhoanHelper tk_database;
    AlertDialogAdapter adapter;
    List<TaiKhoan> listTaiKhoan;
    int pos, begin;
    int _idViTien = -1;
    Context thisContext;
    ImageView imageHangMuc;
    TextView textHangMuc;
    TextView textViTien;
    ImageView imageViTien;
    TextView mNgayBatDau;
    TextView mNgayKetThuc;
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    RelativeLayout btnNgayBatDau;
    RelativeLayout btnNgayKetThuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_han_muc);
        anhXa();
        formatSoTien();
        thisContext = TaoHanMucActivity.this;

        //Button Ngày bắt đầu
        btnNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay(mNgayBatDau);
            }
        });

        btnNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay(mNgayKetThuc);
            }
        });

        //Button hạng mục
        btnHangMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_hangMuc = new Intent(thisContext, HangMucChiActivity.class);
                startActivity(i_hangMuc);
            }
        });

        //Button tài khoản
        btnTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDialog(view);
            }
        });

        //Button thêm
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                them();
            }
        });

        //Button trở lại
        btnTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //lấy dữ liệu từ broadcast
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(thisContext).registerReceiver(mReceiver, new IntentFilter("taikhoan"));
        LocalBroadcastManager.getInstance(thisContext).registerReceiver(mReceiver, new IntentFilter("hangmucchi"));
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
                    textViTien.setTextColor(Color.BLACK);

                }
                if (action.equals("hangmucchi")) {
                    final String text = i.getString("text");
                    final int img = i.getInt("img");
                    textHangMuc.setText(text);
                    imageHangMuc.setImageResource(img);
                    imageHangMuc.setDrawingCacheEnabled(true);
                    textHangMuc.setTextColor(Color.BLACK);
                }
            }
        }
    };

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

    private void formatSoTien() {
        edtSoTien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //mSoTien.setSelection(mSoTien.getText().length());
                pos = edtSoTien.getText().length();
                begin = edtSoTien.getSelectionStart();
            }

            @Override
            public void afterTextChanged(Editable s) {

                edtSoTien.removeTextChangedListener(this);
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
                    edtSoTien.setText(formattedString);

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                if (begin == pos) {
                    edtSoTien.setSelection(edtSoTien.getText().length());

                }
                if (begin != pos) {
                    edtSoTien.setSelection(begin);
                }
                edtSoTien.addTextChangedListener(this);
            }
        });
    }

    private void chonNgay(final TextView textview) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(thisContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                textview.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void them() {
        String formatSoTien = edtSoTien.getText().toString().replaceAll("\\.", "");
        imageViTien.getDrawingCache();
        imageHangMuc.getDrawingCache();
        String tenHanMuc = edtTenHanMuc.getText().toString();
        Bitmap bmapViTien = ((BitmapDrawable) imageViTien.getDrawable()).getBitmap();
        Bitmap bmapHangMuc = ((BitmapDrawable) imageHangMuc.getDrawable()).getBitmap();
        String imgViTien = ChuyenImage.getString(bmapViTien);
        String imgHangMuc = ChuyenImage.getString(bmapHangMuc);
        String ngayBatDau = mNgayBatDau.getText().toString();
        String ngayKetThuc = mNgayKetThuc.getText().toString();

        String tenHangMuc = textHangMuc.getText().toString();
        String tenViTien = textViTien.getText().toString();

        boolean flag = true;

        //Nếu chưa xác định ngày kết thúc thì gán = ngày bd + 7
        if (ngayKetThuc.equals("Không xác định")) {
            try {
                Calendar c = Calendar.getInstance();
                c.setTime(simpleDateFormat.parse(ngayBatDau));
                c.add(Calendar.DATE, 7);
                ngayKetThuc = "" + simpleDateFormat.format(c.getTime());
                System.out.println(c);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //Ngày kết thúc phải lớn hơn bd 7 ngày
        if (!ngayKetThuc.equals("Không xác định")) {
            try {
                Calendar bd = Calendar.getInstance();
                Calendar kt = Calendar.getInstance();
                bd.setTime(simpleDateFormat.parse(ngayBatDau));
                kt.setTime(simpleDateFormat.parse(ngayKetThuc));
                long diff = kt.getTimeInMillis() - bd.getTimeInMillis();
                int days = (int) (diff / (1000 * 60 * 60 * 24));
                if (days < 7) {
                    bd.add(Calendar.DATE, 7);
                    Toast.makeText(thisContext, "Ngày kết thúc phải lớn hơn ngày " + simpleDateFormat.format(bd.getTime()), Toast.LENGTH_SHORT).show();
                    flag = false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if (formatSoTien.equals("")) {
            Toast.makeText(thisContext, "Bạn chưa nhập số tiền", Toast.LENGTH_SHORT).show();
            flag = false;
        }
        if (tenHanMuc.equals("")) {
            edtTenHanMuc.setHintTextColor(Color.RED);
            flag = false;
        }
        if (tenViTien.equals("Chọn tài khoản")) {
            textViTien.setTextColor(Color.RED);
            flag = false;
        }
        if (tenHangMuc.equals("Chọn hạng mục")) {
            textHangMuc.setTextColor(Color.RED);
            flag = false;
        }
        if (flag) {
            int soTien = Integer.parseInt(formatSoTien);

            HanMucHelper hm_database = new HanMucHelper(thisContext);
            boolean insert = hm_database.insertHanMuc(soTien, tenHanMuc, imgHangMuc, tenHangMuc, imgViTien, tenViTien,
                    ngayBatDau, ngayKetThuc, 0, _idViTien);
            if (insert) {
                Toast.makeText(thisContext, "Thêm hạn mức thành công", Toast.LENGTH_SHORT).show();
                edtSoTien.setText("0");
                textViTien.setText("Chọn tài khoản");
                imageViTien.setImageResource(R.mipmap.ic_vi);
                textHangMuc.setText("Chọn hạng mục");
                imageHangMuc.setImageResource(R.mipmap.ic_chamhoi);
                edtTenHanMuc.setText("");
                edtTenHanMuc.setHintTextColor(Color.GRAY);
                mNgayBatDau.setText(simpleDateFormat.format(calendar.getTime()));
                mNgayKetThuc.setText("Không xác định");
            }
        }
    }

    private void anhXa() {
        btnTroLai = (ImageButton) findViewById(R.id.trolai);
        btnLuu = (Button) findViewById(R.id.btn_Luu);
        edtSoTien = (EditText) findViewById(R.id.edt_SoTien);
        edtTenHanMuc = (EditText) findViewById(R.id.edt_tenhanmuc);
        btnHangMuc = (LinearLayout) findViewById(R.id.btn_hangmuc);
        btnTaiKhoan = (LinearLayout) findViewById(R.id.btn_taikhoan);
        imageHangMuc = (ImageView) findViewById(R.id.image_hangmuc);
        textHangMuc = (TextView) findViewById(R.id.text_hangmuc);
        imageViTien = (ImageView) findViewById(R.id.image_vitien);
        textViTien = (TextView) findViewById(R.id.text_vitien);
        btnNgayBatDau = (RelativeLayout) findViewById(R.id.line_ngayBatDau);
        btnNgayKetThuc = (RelativeLayout) findViewById(R.id.line_ngayKetThuc);
        mNgayBatDau = (TextView) findViewById(R.id.text_NgayBatDau);
        mNgayKetThuc = (TextView) findViewById(R.id.text_NgayKetThuc);
        mNgayBatDau.setText(simpleDateFormat.format(calendar.getTime()));
        //calendar.add(Calendar.DATE,29);
        //mNgayKetThuc.setText(simpleDateFormat.format(calendar.getTime()));
        mNgayKetThuc.setText("Không xác định");
        TextView textCurrency = (TextView) findViewById(R.id.currency);
        textCurrency.setPaintFlags(textCurrency.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}
