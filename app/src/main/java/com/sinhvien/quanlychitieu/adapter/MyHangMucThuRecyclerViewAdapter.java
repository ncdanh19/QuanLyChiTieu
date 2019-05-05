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

import com.sinhvien.quanlychitieu.Database.HangMuc;
import com.sinhvien.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;


public class MyHangMucThuRecyclerViewAdapter extends RecyclerView.Adapter<MyHangMucThuRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private final List<HangMuc> listHangMuc;
    private OnPagerItemSelected listener;

    public MyHangMucThuRecyclerViewAdapter(Context context, ArrayList<HangMuc> items,OnPagerItemSelected listener) {
        this.context=context;
        this.listHangMuc = items;
        this.listener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hangmucthu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final HangMuc hangMuc = listHangMuc.get(position);
        holder.imageItem.setImageResource(hangMuc.getImage());
        holder.textItem.setText(hangMuc.getTenHangMuc());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(context, "hihi", Toast.LENGTH_SHORT).show();
                else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent("hangmucthu");
                    bundle.putString("text", hangMuc.getTenHangMuc());
                    bundle.putInt("img", hangMuc.getImage());
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView textItem;
        ImageView imageItem;
        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);
            imageItem = (ImageView) view.findViewById(R.id.image_vitien);
            textItem = (TextView) view.findViewById(R.id.text_vitien);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }
    }
}