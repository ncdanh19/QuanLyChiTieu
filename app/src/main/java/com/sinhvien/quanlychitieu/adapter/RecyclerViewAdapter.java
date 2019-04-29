package com.sinhvien.quanlychitieu.adapter;

import android.app.Activity;
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

import com.sinhvien.quanlychitieu.Database.LoaiTaiKhoan;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.activity.LoaiTaiKhoanActivity;
import com.sinhvien.quanlychitieu.activity.TaoTaiKhoanActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    private ArrayList<LoaiTaiKhoan> loaiTaiKhoans;
    private Context context;
    private OnPagerItemSelected listener;
    public RecyclerViewAdapter(Context context, ArrayList<LoaiTaiKhoan> loaiTaiKhoans,OnPagerItemSelected listener) {
        this.loaiTaiKhoans = loaiTaiKhoans;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycleview, parent, false);
        return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.textItem.setText(loaiTaiKhoans.get(position).getTenLoai());
        holder.imageItem.setImageResource(loaiTaiKhoans.get(position).getImg());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(context, "hihi", Toast.LENGTH_SHORT).show();
                else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent("data");
                    bundle.putString("text",loaiTaiKhoans.get(position).getTenLoai());
                    bundle.putInt("img",loaiTaiKhoans.get(position).getImg());
                    intent.putExtras(bundle);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//                    context.startActivity(intent,bundle);
//                    ((Activity)context).finishActivity(1);
                    listener.pagerItemSelected();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return loaiTaiKhoans.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView textItem;
        ImageView imageItem;
        private ItemClickListener itemClickListener;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageItem = (ImageView) itemView.findViewById(R.id.image_icon);
            textItem = (TextView) itemView.findViewById(R.id.text_item);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
            return false;
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }
    }
    public interface OnPagerItemSelected {
        void pagerItemSelected();
    }

}
