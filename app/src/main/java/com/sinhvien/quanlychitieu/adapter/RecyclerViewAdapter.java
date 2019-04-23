package com.sinhvien.quanlychitieu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinhvien.quanlychitieu.Database.LoaiTaiKhoan;
import com.sinhvien.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<LoaiTaiKhoan> loaiTaiKhoans;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<LoaiTaiKhoan> loaiTaiKhoans) {
        this.loaiTaiKhoans = loaiTaiKhoans;
        this.context = context;
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
        holder.imageItem.setImageResource(loaiTaiKhoans.get(position).getImg_Url());
    }

    @Override
    public int getItemCount() {
        return loaiTaiKhoans.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textItem;
        ImageView imageItem;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageItem=(ImageView)itemView.findViewById(R.id.image_icon);
            textItem = (TextView) itemView.findViewById(R.id.text_item);
        }
    }
}