package com.sinhvien.quanlychitieu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sinhvien.quanlychitieu.Database.HanMuc;
import com.sinhvien.quanlychitieu.Database.HanMucHelper;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class HanMucAdapter extends RecyclerView.Adapter<HanMucAdapter.HanMucViewHolder> {
    Context context;
    List<HanMuc> listHanMuc;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public HanMucAdapter() {
    }

    public HanMucAdapter(Context context, List<HanMuc> listHanMuc) {
        this.context = context;
        this.listHanMuc = listHanMuc;
    }

    public void updateData( List<HanMuc> listHanMuc) {
        this.listHanMuc = listHanMuc;
        HanMucHelper hm_database = new HanMucHelper(context);
        for(int i = 0 ; i < listHanMuc.size();i++)
        {
            final HanMuc hanMuc = listHanMuc.get(i);

            Calendar bd = Calendar.getInstance();
            Calendar kt = Calendar.getInstance();
            try {

                bd.setTime(simpleDateFormat.parse(hanMuc.getNgayBatDau()));
                kt.setTime(simpleDateFormat.parse(hanMuc.getNgayKetThuc()));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            ThuChiHelper tc_database = new ThuChiHelper(context);
            int tienDaDung = tc_database.getTienHanMuc(hanMuc.getTenHangMuc(), hanMuc.get_idViTien(), bd, kt);
            int reuslt = hanMuc.getSoTien() - tienDaDung;
            if (reuslt < 1) {
                hm_database.updateTrangThaiHanMuc(hanMuc.get_id(), 1);
            } else {
                hm_database.updateTrangThaiHanMuc(hanMuc.get_id(), 0);
            }
        }
    }
    @Override
    public HanMucViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_hanmuc, viewGroup, false);
        return new HanMucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HanMucViewHolder holder, int i) {
        final HanMuc hanMuc = listHanMuc.get(i);
        holder.imageHangMuc.setImageBitmap(ChuyenImage.getStringtoImage(hanMuc.getImageHangMuc()));
        holder.tenHanMuc.setText(hanMuc.getTenHanMuc());
        holder.ngayBatDau.setText(hanMuc.getNgayBatDau());
        holder.ngayKetThuc.setText(hanMuc.getNgayKetThuc());
        holder.soTienHanMuc.setText(formatCurrency(hanMuc.getSoTien()));
        Calendar bd = Calendar.getInstance();
        Calendar kt = Calendar.getInstance();
        try {

            bd.setTime(simpleDateFormat.parse(hanMuc.getNgayBatDau()));
            kt.setTime(simpleDateFormat.parse(hanMuc.getNgayKetThuc()));
            long diff = kt.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
            int days = (int) (diff / (1000 * 60 * 60 * 24));
            if (days > 30) {
            } else
                holder.ngayConLai.setText(String.format("Còn %s ngày", String.valueOf(days)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ThuChiHelper tc_database = new ThuChiHelper(context);
        int tienDaDung = tc_database.getTienHanMuc(hanMuc.getTenHangMuc(), hanMuc.get_idViTien(), bd, kt);
        int reuslt = hanMuc.getSoTien() - tienDaDung;
        double tyLe = (double) (tienDaDung / (double) hanMuc.getSoTien()) * 100;
        holder.processBar.setMax(100);
        holder.processBar.setProgress((int) tyLe);
        HanMucHelper hm_database = new HanMucHelper(context);
        if (reuslt < 1) {
            int tmp = reuslt * -1;
            holder.soTienConLai.setText("" + formatCurrency(tmp));
            holder.trangThai.setText("(Bội chi)");
            holder.trangThai.setTextColor(Color.RED);
            holder.processBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            hm_database.updateTrangThaiHanMuc(hanMuc.get_id(), 1);
        } else {
            holder.soTienConLai.setText("" + formatCurrency(reuslt));
            holder.processBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            hm_database.updateTrangThaiHanMuc(hanMuc.get_id(), 0);
        }
    }


    private String formatCurrency(int value) {
        Long longval = (long) value;

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
        formatter.applyPattern("#,###,###.###");

        //setting text after format to EditText
        return formatter.format(longval);
    }

    @Override
    public int getItemCount() {
        return listHanMuc == null ? 0 : listHanMuc.size();
    }

    public class HanMucViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageHangMuc;
        private final TextView tenHanMuc;
        private final TextView ngayBatDau;
        private final TextView ngayKetThuc;
        private final TextView soTienHanMuc;
        private final TextView soTienConLai;
        private final TextView currentcy1;
        private final TextView currentcy2;
        private final TextView ngayConLai;
        private final TextView trangThai;
        private final ProgressBar processBar;

        public HanMucViewHolder(View itemView) {
            super(itemView);
            imageHangMuc = (ImageView) itemView.findViewById(R.id.img_hangmuc);
            tenHanMuc = (TextView) itemView.findViewById(R.id.tv_hanmuc);
            ngayBatDau = (TextView) itemView.findViewById(R.id.tv_ngayBatDau);
            ngayKetThuc = (TextView) itemView.findViewById(R.id.tv_ngayKetThuc);
            soTienHanMuc = (TextView) itemView.findViewById(R.id.soTienHanMuc);
            soTienConLai = (TextView) itemView.findViewById(R.id.soTienConLai);
            ngayConLai = (TextView) itemView.findViewById(R.id.soNgayConLai);
            trangThai = (TextView) itemView.findViewById(R.id.label_trangthai);
            processBar = (ProgressBar) itemView.findViewById(R.id.progress_horizontal);
            currentcy1 = (TextView) itemView.findViewById(R.id.currency1);
            currentcy2 = (TextView) itemView.findViewById(R.id.currency2);
            currentcy1.setPaintFlags(currentcy1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            currentcy2.setPaintFlags(currentcy2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }

}
