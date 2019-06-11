package com.sinhvien.quanlychitieu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.sinhvien.quanlychitieu.Database.ThuChi;
import com.sinhvien.quanlychitieu.R;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ThongKeViewHolder> {
    private Context context;
    private List<ThuChi> listThuChi;

    public ThongKeAdapter(Context context, List<ThuChi> listThuChi) {
        this.context = context;
        this.listThuChi = listThuChi;
    }

    @Override
    public ThongKeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_thong_ke, viewGroup, false);
        return new ThongKeViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ThongKeViewHolder holder, int i) {
        final ThuChi thuChi = listThuChi.get(i);
        ArrayList<Integer> colors = new ArrayList<>();

        final int[] My_COLORS = {
                Color.parseColor("#bcaaa4"),
                Color.parseColor("#7c4dff"),
                Color.parseColor("#ffea00"),
                Color.parseColor("#b39ddb"),
                Color.parseColor("#90caf9"),
                Color.parseColor("#fff59d"),
                Color.parseColor("#c6ff00"),
                Color.parseColor("#ffab91"),
                Color.parseColor("#ff5252"),
                Color.parseColor("#c5e1a5"),
                Color.parseColor("#e0e0e0"),
        };
        for (int c : My_COLORS)
            colors.add(c);


        colors.add(ColorTemplate.getHoloBlue());
        double tyLe = (double) (Integer.parseInt(thuChi.getSotien()) / (double) TongTien()) * 100;
        holder.imageHangMuc.setImageBitmap(ChuyenImage.getStringtoImage(thuChi.getImageHangMuc()));
        holder.tenHangMuc.setText(thuChi.getTenHangMuc());
        DecimalFormat df = new DecimalFormat("#.#");
        holder.percent.setText(MessageFormat.format("({0}%)", df.format(tyLe)));
        holder.soTien.setText(formatCurrency(thuChi.getSotien()));
        holder.processBar.setMax(100);
        holder.processBar.setProgress((int) tyLe);
        //holder.processBar.setBackgroundColor(Color.GRAY);
        holder.processBar.getIndeterminateDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        holder.processBar.getProgressDrawable().setColorFilter(colors.get(i), PorterDuff.Mode.SRC_IN);

    }


    private int TongTien() {
        int tong = 0;
        for (int i = 0; i < listThuChi.size(); i++) {
            ThuChi thuChi = listThuChi.get(i);
            tong += Integer.parseInt(thuChi.getSotien());
        }
        return tong;
    }

    @Override
    public int getItemCount() {
        return listThuChi == null ? 0 : listThuChi.size();
    }

    private String formatCurrency(String string) {
        Long longval = Long.parseLong(string);

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
        formatter.applyPattern("#,###,###.###");

        //setting text after format to EditText
        return formatter.format(longval);
    }

    class ThongKeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHangMuc;
        TextView tenHangMuc;
        TextView soTien;
        ProgressBar processBar;
        TextView textCurrency;
        TextView percent;

        ThongKeViewHolder(View itemView) {
            super(itemView);
            imageHangMuc = (ImageView) itemView.findViewById(R.id.img_hangmuc);
            tenHangMuc = (TextView) itemView.findViewById(R.id.ten_hangmuc);
            percent = (TextView) itemView.findViewById(R.id.percent);
            soTien = (TextView) itemView.findViewById(R.id.soTien);
            processBar = (ProgressBar) itemView.findViewById(R.id.progress_horizontal);
            textCurrency = (TextView) itemView.findViewById(R.id.currency);
            textCurrency.setPaintFlags(textCurrency.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}