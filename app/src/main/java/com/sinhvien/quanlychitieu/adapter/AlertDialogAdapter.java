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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class AlertDialogAdapter extends RecyclerView.Adapter<AlertDialogAdapter.AlertDialogViewHolder> {
    private List<TaiKhoan> listTaiKhoan = new ArrayList<>();
    private Context context;
    private OnPagerItemSelected listener;


    public AlertDialogAdapter(Context context, List<TaiKhoan> listTaiKhoan, OnPagerItemSelected listener) {
        this.context = context;
        this.listTaiKhoan = listTaiKhoan;
        this.listener = listener;
    }

    public AlertDialogAdapter(Context context, List<TaiKhoan> listTaiKhoan) {
        this.context = context;
        this.listTaiKhoan = listTaiKhoan;
    }

    public AlertDialogAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AlertDialogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_alertdialog, viewGroup, false);
        return new AlertDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlertDialogViewHolder holder, int i) {
        final TaiKhoan taikhoan = listTaiKhoan.get(i);
        holder.imageicon.setImageBitmap(ChuyenImage.getStringtoImage(taikhoan.getImgage()));
        holder.tv_tenTaiKhoan.setText(taikhoan.getTenTaiKhoan());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
//                    Toast.makeText(context, "Long Click: " + listTaiKhoan.get(position), Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent("taikhoan");
                    bundle.putInt("_id", taikhoan.get_id());
                    bundle.putString("text", taikhoan.getTenTaiKhoan());
                    bundle.putString("img", taikhoan.getImgage());
                    intent.putExtras(bundle);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    listener.pagerItemSelected();
                    Toast.makeText(context, "" + taikhoan.get_id(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return listTaiKhoan == null ? 0 : listTaiKhoan.size();
    }


    public class AlertDialogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imageicon;
        TextView tv_tenTaiKhoan;

        private ItemClickListener itemClickListener;

        AlertDialogViewHolder(View itemView) {
            super(itemView);
            imageicon = (ImageView) itemView.findViewById(R.id.image_vitien);
            tv_tenTaiKhoan = (TextView) itemView.findViewById(R.id.tv_tentaikhoan);

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
    }

}
