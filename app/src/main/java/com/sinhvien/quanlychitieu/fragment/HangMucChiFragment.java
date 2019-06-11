package com.sinhvien.quanlychitieu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinhvien.quanlychitieu.Database.HangMuc;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.activity.HangMucActivity;
import com.sinhvien.quanlychitieu.activity.ThuChiActivity;
import com.sinhvien.quanlychitieu.adapter.MyHangMucChiRecyclerViewAdapter;
import com.sinhvien.quanlychitieu.adapter.OnPagerItemSelected;

import java.util.ArrayList;

public class HangMucChiFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private final String[] tenloai = {
            "Ăn uống", "Mua sắm", "Điện thoại", "Internet", "Điện", "Nước", "Đi lại", "Gửi xe",
            "Bảo dưỡng", "Xăng xe","Khoản chi khác"
    };

    private final int[] image = {
            R.mipmap.ic_anuong, R.mipmap.ic_shopping, R.mipmap.ic_dienthoai,
            R.mipmap.ic_internet, R.mipmap.ic_electric, R.mipmap.ic_water,
            R.mipmap.ic_car, R.mipmap.ic_ticket, R.mipmap.ic_tool,
            R.mipmap.ic_fuel,R.mipmap.ic_box

    };


    public HangMucChiFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hangmucchi_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            ArrayList<HangMuc> loaiHangMuc = prepareData();
            DividerItemDecoration dividerHorizontal =
                    new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerHorizontal);

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyHangMucChiRecyclerViewAdapter(getContext(), loaiHangMuc, new OnPagerItemSelected() {
                @Override
                public void pagerItemSelected() {
                    getActivity().finish();
                }
            }));
        }
        return view;
    }

    private ArrayList<HangMuc> prepareData() {

        ArrayList<HangMuc> list = new ArrayList<>();
        for (int i = 0; i < tenloai.length; i++) {
            HangMuc loaiHangMuc = new HangMuc();
            loaiHangMuc.setTenHangMuc(tenloai[i]);
            loaiHangMuc.setImage(image[i]);
            list.add(loaiHangMuc);
        }
        return list;
    }
}
