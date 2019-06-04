package com.sinhvien.quanlychitieu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.quanlychitieu.Database.HangMuc;
import com.sinhvien.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;


public class MyHangMucChiRecyclerViewAdapter extends RecyclerView.Adapter<MyHangMucChiRecyclerViewAdapter.HangMucChiViewHolder> {

    private Context context;
    private final List<HangMuc> listHangMuc;
    private OnPagerItemSelected listener;

    public MyHangMucChiRecyclerViewAdapter(Context context, ArrayList<HangMuc> items, OnPagerItemSelected listener) {
        this.context = context;
        this.listHangMuc = items;
        this.listener = listener;
    }

    @Override
    public HangMucChiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hangmucchi, parent, false);
        return new HangMucChiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HangMucChiViewHolder holder, int position) {
        final HangMuc hangMuc = listHangMuc.get(position);
        holder.imageItem.setImageResource(hangMuc.getImage());
        holder.textItem.setText(hangMuc.getTenHangMuc());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
//                    Toast.makeText(context, "hihi", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent("hangmucchi");
                    bundle.putString("text", hangMuc.getTenHangMuc());
                    bundle.putInt("img", hangMuc.getImage());
                    bundle.putInt("page", 0); //chuyển về mục chi khi chọn hạng mục của chi tiền
                    intent.putExtras(bundle);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    listener.pagerItemSelected();

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return listHangMuc.size();
    }

    public class HangMucChiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView textItem;
        ImageView imageItem;
        private ItemClickListener itemClickListener;

        HangMucChiViewHolder(View view) {
            super(view);
            imageItem = (ImageView) view.findViewById(R.id.image_vitien);
            textItem = (TextView) view.findViewById(R.id.text_vitien);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }

        void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
