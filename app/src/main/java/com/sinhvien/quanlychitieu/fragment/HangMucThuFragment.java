package com.sinhvien.quanlychitieu.fragment;

import android.content.Context;
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
import com.sinhvien.quanlychitieu.adapter.MyHangMucThuRecyclerViewAdapter;
import com.sinhvien.quanlychitieu.adapter.OnPagerItemSelected;

import java.util.ArrayList;


public class HangMucThuFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private final String tenloai[] = {
            "Lương","Tiền lãi"
    };

    private final int image[] = {
            R.mipmap.ic_luong,R.mipmap.ic_tienlai

    };

    public HangMucThuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hangmucthu_list, container, false);

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
            recyclerView.setAdapter(new MyHangMucThuRecyclerViewAdapter(getContext(),loaiHangMuc, new OnPagerItemSelected() {
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
