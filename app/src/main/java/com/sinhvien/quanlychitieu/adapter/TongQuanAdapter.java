package com.sinhvien.quanlychitieu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinhvien.quanlychitieu.Database.ThuChi;
import com.sinhvien.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class TongQuanAdapter extends RecyclerView.Adapter<TongQuanAdapter.ViewHolder> {

    private List<ThuChi> listThuChi = new ArrayList<ThuChi>();
    private Context context;
    ChuyenImage chuyendoi;


    public TongQuanAdapter(Context context,List<ThuChi> listThuChi) {
        this.listThuChi = listThuChi;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_tongquan, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        final ThuChi thuChi = listThuChi.get(i);
        holder.imageHangMuc.setImageBitmap(chuyendoi.getStringtoImage(thuChi.getImageHangMuc()));
        holder.textHangMuc.setText(thuChi.getTenHangMuc());
        holder.textNgay.setText(thuChi.getNgaythang());
        holder.textSoTien.setText(thuChi.getSotien());
        holder.textViTien.setText(thuChi.getTenViTien());
    }

    @Override
    public int getItemCount() {
        return listThuChi == null ? 0 : listThuChi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageHangMuc;
        public TextView textHangMuc;
        public TextView textNgay;
        public TextView textSoTien;
        public TextView textViTien;

        public ViewHolder(View itemView) {
            super(itemView);
            imageHangMuc = (ImageView) itemView.findViewById(R.id.image_hangmuc);
            textHangMuc = (TextView) itemView.findViewById(R.id.tv_hangmuc);
            textNgay = (TextView) itemView.findViewById(R.id.tv_ngay);
            textSoTien = (TextView) itemView.findViewById(R.id.tv_soTien);
            textViTien = (TextView) itemView.findViewById(R.id.tv_vitien);

        }
    }
}
