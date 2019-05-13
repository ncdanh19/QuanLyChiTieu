package com.sinhvien.quanlychitieu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.ViewHolder> {
    private List<TaiKhoan> listTaiKhoan = new ArrayList<TaiKhoan>();
    private Context context;
    private OnPagerItemSelected listener;
    ChuyenImage chuyendoi;



    public TaiKhoanAdapter(Context context, List<TaiKhoan> listTaiKhoan, OnPagerItemSelected listener) {
        this.context = context;
        this.listTaiKhoan = listTaiKhoan;
        this.listener = listener;
    }
    public TaiKhoanAdapter(Context context, List<TaiKhoan> listTaiKhoan) {
        this.context = context;
        this.listTaiKhoan = listTaiKhoan;
    }

    public TaiKhoanAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_taikhoan, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        final TaiKhoan taikhoan = listTaiKhoan.get(i);
        holder.imageicon.setImageBitmap(chuyendoi.getStringtoImage(taikhoan.getImgage()));
        holder.tv_tenTaiKhoan.setText(taikhoan.getTenTaiKhoan());
        holder.tv_SoTien.setText(formatCurrency(String.valueOf(taikhoan.getSoTien())));

        holder.buttonTuyChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "hihihi", Toast.LENGTH_SHORT).show();
            }
        });
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick)
                    Toast.makeText(context, "Long Click: " + listTaiKhoan.get(position), Toast.LENGTH_SHORT).show();
                else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent("taikhoan");
                    bundle.putInt("_id",taikhoan.get_id());
                    bundle.putString("text", taikhoan.getTenTaiKhoan());
                    bundle.putString("img", taikhoan.getImgage());
                    intent.putExtras(bundle);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    listener.pagerItemSelected();
                    Toast.makeText(context, ""+taikhoan.get_id(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public String formatCurrency(String string){
        String originalString = string;
        Long longval = Long.parseLong(originalString);

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
        formatter.applyPattern("#,###,###.###");
        String formattedString = formatter.format(longval);

        //setting text after format to EditText
        return formattedString;
    }
    @Override
    public int getItemCount() {
        return listTaiKhoan == null ? 0 : listTaiKhoan.size();
    }

    public void taoTaiKhoan(int position, TaiKhoan data) {
        listTaiKhoan.add(position, data);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ImageButton buttonTuyChon;
        public ImageView imageicon;
        public TextView tv_tenTaiKhoan;
        public TextView tv_SoTien;
        public TextView textCurrency;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imageicon = (ImageView) itemView.findViewById(R.id.image_vitien);
            tv_tenTaiKhoan = (TextView) itemView.findViewById(R.id.tv_tentaikhoan);
            tv_SoTien = (TextView) itemView.findViewById(R.id.tv_sotien);
            textCurrency=(TextView)itemView.findViewById(R.id.currency);
            buttonTuyChon=(ImageButton)itemView.findViewById(R.id.tuychon);
            textCurrency.setPaintFlags(textCurrency.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

}
