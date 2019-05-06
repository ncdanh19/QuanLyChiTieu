package com.sinhvien.quanlychitieu.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.ViewHolder> {
    private List<TaiKhoan> listTaiKhoan = new ArrayList<TaiKhoan>();
    private Context context;
    private OnPagerItemSelected listener;
    ChuyenImage chuyendoi;

//    public TaiKhoanAdapter(Context context, OnPagerItemSelected listener) {
//        this.context = context;
//        this.listener=listener;
//    }


    public TaiKhoanAdapter(Context context, List<TaiKhoan> listTaiKhoan, OnPagerItemSelected listener) {
        this.context = context;
        this.listTaiKhoan = listTaiKhoan;
        this.listener = listener;
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
        holder.tv_SoTien.setText(taikhoan.getSoTien());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick)
                    Toast.makeText(context, "Long Click: " + listTaiKhoan.get(position), Toast.LENGTH_SHORT).show();
                else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent("taikhoan");
                    bundle.putString("text", taikhoan.getTenTaiKhoan());
                    bundle.putString("img", taikhoan.getImgage());
                    intent.putExtras(bundle);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    listener.pagerItemSelected();
                }
            }
        });

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
        public ImageView imageicon;
        public TextView tv_tenTaiKhoan;
        public TextView tv_SoTien;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imageicon = (ImageView) itemView.findViewById(R.id.image_vitien);
            tv_tenTaiKhoan = (TextView) itemView.findViewById(R.id.tv_tentaikhoan);
            tv_SoTien = (TextView) itemView.findViewById(R.id.tv_sotien);
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
