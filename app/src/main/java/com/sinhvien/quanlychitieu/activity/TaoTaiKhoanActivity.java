package com.sinhvien.quanlychitieu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.sinhvien.quanlychitieu.Database.LoaiTaiKhoan;
import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.Database.TaiKhoanHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.RecyclerViewAdapter;
import com.sinhvien.quanlychitieu.adapter.TaiKhoanAdapter;
import com.sinhvien.quanlychitieu.adapter.TaiKhoanDao;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TaoTaiKhoanActivity extends AppCompatActivity {

    ImageButton mTroLai;
    ImageView mIconItem;
    TextView mTextItem;
    LinearLayout btnLoaiTaiKhoan;
    Button btnLuu;
    EditText edtSoTien;
    EditText edtTenTaiKhoan;
    EditText edtChuThich;
    ArrayList<TaiKhoan> listTaiKhoan;
    private static long id = -1;

    private DatabaseReference mDatabase;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_tai_khoan);
        anhXa();
        btnLoaiTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaoTaiKhoanActivity.this, LoaiTaiKhoanActivity.class);
                // startActivityForResult(i,1);
                startActivity(i);
            }
        });

        mTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewTaiKhoan();
                //taoTaiKhoan();
                them();
                finish();
            }
        });
//        Bundle i = getIntent().getExtras();
//        if (i!=null) {
//            final String text = i.getString("text");
//            final int img = i.getInt("img");
//            mTextItem.setText(text);
//            mIconItem.setImageResource(img);
//        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("data"));

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("data"));
        Toast.makeText(this, "resume", Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle i = intent.getExtras();
            if (i != null) {
                final String text = i.getString("text");
                final int img = i.getInt("img");
                mTextItem.setText(text);
                mIconItem.setImageResource(img);
            }
        }
    };

    private void taoTaiKhoan() {
        Intent intent = new Intent(this, TaiKhoanActivity.class);
        String soTien = edtSoTien.getText().toString();
        String tenTaiKhoan = edtTenTaiKhoan.getText().toString();
        mIconItem.buildDrawingCache();
        Bitmap bmap = mIconItem.getDrawingCache();
        String loaiTaiKhoan = mTextItem.getText().toString();
        String chuThich = edtChuThich.getText().toString();
        // bundle data.
        intent.putExtra("bmap", bmap);
        intent.putExtra("tentaikhoan", tenTaiKhoan);
        intent.putExtra("sotien", soTien);
        intent.putExtra("loaitaikhoan", loaiTaiKhoan);
        intent.putExtra("chuthich", chuThich);
        startActivity(intent);
    }

    private void writeNewTaiKhoan() {
        String soTien = edtSoTien.getText().toString();
        String tenTaiKhoan = edtTenTaiKhoan.getText().toString();
        mIconItem.buildDrawingCache();
        Bitmap bmap = mIconItem.getDrawingCache();
        String loaiTaiKhoan = mTextItem.getText().toString();
        String chuThich = edtChuThich.getText().toString();
        TaiKhoan taiKhoan = new TaiKhoan(soTien, tenTaiKhoan, getBytes(bmap), loaiTaiKhoan, chuThich);
        mDatabase.child("TaiKhoan").push().setValue(taiKhoan);
    }

    private void anhXa() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        mTroLai = (ImageButton) findViewById(R.id.trolai);
        mIconItem = (ImageView) findViewById(R.id.image_icon);
        mTextItem = (TextView) findViewById(R.id.text_item);
        btnLoaiTaiKhoan = (LinearLayout) findViewById(R.id.btn_loaitk);
        edtSoTien = (EditText) findViewById(R.id.edt_SoTien);
        edtTenTaiKhoan = (EditText) findViewById(R.id.edt_tentk);
        edtChuThich = (EditText) findViewById(R.id.edt_chuthich);
        btnLuu = (Button) findViewById(R.id.btn_Luu);
    }

    //    public void capNhatDuLieu() {
//        if (listTaiKhoan == null) {
//            listTaiKhoan = new ArrayList<TaiKhoan>();
//        } else {
//            listTaiKhoan.removeAll(listTaiKhoan);
//        }
//
//        // Lấy dữ liệu, dùng Cursor nhận lại
//        Cursor cursor = database.layTatCaDuLieu();
//        if (cursor != null) {
//            /*
//             * Di chuyển đến từng dòng dữ liệu
//             *  thông qua phương thức moveToNext
//             */
//            while (cursor.moveToNext()) {
//                TaiKhoan taiKhoan = new TaiKhoan();
//
//                /*
//                 * Mỗi dòng dữ liệu chúng ra sẽ lấy
//                 *  theo cột và gán vào đối tượng
//                 *  SinhVien
//                 */
//                taiKhoan.set_id(Integer.parseInt
//                        (cursor.getString(cursor
//                                .getColumnIndex
//                                        (TaiKhoanHelper.COT_ID))));
//                taiKhoan.setTenTaiKhoan(cursor.getString
//                        (cursor
//                                .getColumnIndex
//                                        (TaiKhoanHelper.TEN_BANG_TAIKHOAN)));
//                taiKhoan.setLoaiTaiKhoan(cursor.getString
//                        (cursor
//                                .getColumnIndex
//                                        (TaiKhoanHelper.COT_LOAI_TAI_KHOAN)));
//                taiKhoan.setSoTien(cursor.getString
//                        (cursor
//                                .getColumnIndex
//                                        (TaiKhoanHelper.COT_SO_TIEN)));
//                taiKhoan.setChuThich(cursor.getString
//                        (cursor
//                                .getColumnIndex
//                                        (TaiKhoanHelper.COT_CHU_THICH)));
//                // thêm vào danh sách SinhVien
//                listTaiKhoan.add(taiKhoan);
//            }
//        }
//    }
//
    public void them() {
        String soTien = edtSoTien.getText().toString();
        String tenTaiKhoan = edtTenTaiKhoan.getText().toString();
        mIconItem.buildDrawingCache();
        Bitmap bmap = mIconItem.getDrawingCache();
        byte[] byteArray = getBytes(bmap);
        String loaiTaiKhoan = mTextItem.getText().toString();
        String chuThich = edtChuThich.getText().toString();

        TaiKhoanHelper database = new TaiKhoanHelper(getApplicationContext());
        boolean trt = database.insertdata(soTien, tenTaiKhoan, loaiTaiKhoan, chuThich, byteArray);
        Intent intent = new Intent(this, TaiKhoanActivity.class);
        startActivity(intent);
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
//
//    public void them() {
//        TaiKhoan sinhVien1 = layDuLieuNguoiDung();
//        if (sinhVien1 != null) {
//            if (database.them(sinhVien1) != -1) {
//                listTaiKhoan.add(sinhVien1);
//                capNhatDuLieu();
//                id = -1;
//            }
//        }
//    }


}
