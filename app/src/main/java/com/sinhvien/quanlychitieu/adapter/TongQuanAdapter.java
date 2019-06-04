package com.sinhvien.quanlychitieu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.quanlychitieu.Database.ThuChi;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.activity.CustomThuChi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TongQuanAdapter extends RecyclerView.Adapter<TongQuanAdapter.TongQuanViewHolder> {

    private List<ThuChi> listThuChi;
    private Context context;


    public TongQuanAdapter(Context context, List<ThuChi> listThuChi) {
        this.listThuChi = listThuChi;
        this.context = context;
    }


    @Override
    public TongQuanViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_tongquan, viewGroup, false);
        return new TongQuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TongQuanViewHolder holder, int i) {
        final ThuChi thuChi = listThuChi.get(i);
        holder.imageHangMuc.setImageBitmap(ChuyenImage.getStringtoImage(thuChi.getImageHangMuc()));
        holder.textHangMuc.setText(thuChi.getTenHangMuc());
        holder.textNgay.setText(thuChi.getNgaythang());
        holder.textSoTien.setText(formatCurrency(thuChi.getSotien()));
        holder.textViTien.setText(thuChi.getTenViTien());
        int id = thuChi.getTrangThai();
        if (id == 0) {
            holder.textSoTien.setTextColor(Color.RED);
            holder.textCurrency.setTextColor(Color.RED);
        }
        if (id == 1) {
            holder.textSoTien.setTextColor(Color.parseColor("#33cc33"));
            holder.textCurrency.setTextColor(Color.parseColor("#33cc33"));
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
//                    Toast.makeText(context, "Long Click: ", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, CustomThuChi.class);
                    intent.putExtra("idThuChi", thuChi.get_id());
                    intent.putExtra("soTien", thuChi.getSotien());
                    intent.putExtra("tenHangMuc", thuChi.getTenHangMuc());
                    intent.putExtra("imageHangMuc", thuChi.getImageHangMuc());
                    intent.putExtra("moTa", thuChi.getMota());
                    intent.putExtra("ngayThang", thuChi.getNgaythang());
                    intent.putExtra("tenViTien", thuChi.getTenViTien());
                    intent.putExtra("imageViTien", thuChi.getImageViTien());
                    intent.putExtra("trangThai", thuChi.getTrangThai());
                    intent.putExtra("idViTien", thuChi.get_idViTien());
                    context.startActivity(intent);
                }
            }
        });

    }

    private String formatCurrency(String string) {
        Long longval = Long.parseLong(string);

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
        formatter.applyPattern("#,###,###.###");

        //setting text after format to EditText
        return formatter.format(longval);
    }

    @Override
    public int getItemCount() {
        return listThuChi == null ? 0 : listThuChi.size();
    }

    public class TongQuanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView textCurrency;
        ImageView imageHangMuc;
        TextView textHangMuc;
        TextView textNgay;
        TextView textSoTien;
        TextView textViTien;
        private ItemClickListener itemClickListener;


        TongQuanViewHolder(View itemView) {
            super(itemView);
            anhXa();
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getLayoutPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getLayoutPosition(), true);
            return true;
        }

        void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        private void anhXa() {
            imageHangMuc = (ImageView) itemView.findViewById(R.id.image_hangmuc);
            textHangMuc = (TextView) itemView.findViewById(R.id.tv_hangmuc);
            textNgay = (TextView) itemView.findViewById(R.id.tv_ngay);
            textSoTien = (TextView) itemView.findViewById(R.id.tv_soTien);
            textViTien = (TextView) itemView.findViewById(R.id.tv_vitien);
            textCurrency = (TextView) itemView.findViewById(R.id.currency);
            textCurrency.setPaintFlags(textCurrency.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}
