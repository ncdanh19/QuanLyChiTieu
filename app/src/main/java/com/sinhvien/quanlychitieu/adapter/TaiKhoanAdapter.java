package com.sinhvien.quanlychitieu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinhvien.quanlychitieu.Database.LoaiTaiKhoan;
import com.sinhvien.quanlychitieu.Database.TaiKhoan;
import com.sinhvien.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.ViewHolder> {
    private List<TaiKhoan> listTaiKhoan = new ArrayList<TaiKhoan>();
    private Context context;

    public TaiKhoanAdapter(Context context) {
        this.context = context;
    }


    public TaiKhoanAdapter(Context context, List<TaiKhoan> listTaiKhoan) {
        this.context=context;
        this.listTaiKhoan=listTaiKhoan;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_taikhoan, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
            TaiKhoan taikhoan = listTaiKhoan.get(i);
            holder.imageicon.setImageBitmap(getImage(taikhoan.getImgage()));
            holder.tv_tenTaiKhoan.setText(taikhoan.getTenTaiKhoan());
            holder.tv_SoTien.setText(taikhoan.getSoTien());

    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public int getItemCount() {
        return listTaiKhoan==null?0:listTaiKhoan.size();
    }

    public void taoTaiKhoan(int position, TaiKhoan data) {
        listTaiKhoan.add(position, data);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageicon;
        TextView tv_tenTaiKhoan;
        TextView tv_SoTien;

        public ViewHolder(View itemView) {
            super(itemView);
            imageicon = (ImageView) itemView.findViewById(R.id.image_icon);
            tv_tenTaiKhoan = (TextView) itemView.findViewById(R.id.tv_tentaikhoan);
            tv_SoTien=(TextView)itemView.findViewById(R.id.tv_sotien);
        }
    }
}
