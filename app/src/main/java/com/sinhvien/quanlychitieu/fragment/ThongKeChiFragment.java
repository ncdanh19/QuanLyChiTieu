package com.sinhvien.quanlychitieu.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.sinhvien.quanlychitieu.Database.ThuChi;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;

import java.util.ArrayList;
import java.util.List;


public class ThongKeChiFragment extends Fragment implements OnChartValueSelectedListener {
    PieChart pieChart;
    ThuChiHelper tc_database;
    List<ThuChi> listThuChi;
    View view;
    public ThongKeChiFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_thong_ke_chi, container, false);
        createPieChart();
        return view;
    }
    public int TongTien(){
        tc_database = new ThuChiHelper(getActivity());
        listThuChi = tc_database.getTongTien(0);
        int tong=0;
        for (int i = 0; i < listThuChi.size(); i++) {
            ThuChi thuChi = listThuChi.get(i);
            tong+=Integer.parseInt(thuChi.getSotien());
        }
        return tong;
    }
    private void createPieChart() {
        pieChart = (PieChart) view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.setExtraOffsets(42f, 42f, 42f, 42f);


        tc_database = new ThuChiHelper(getActivity());
        listThuChi = tc_database.getTongTien(0);
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();

        for (int i = 0; i < listThuChi.size(); i++) {
            final ThuChi thuChi = listThuChi.get(i);
            //Drawable user_icon = getResources().getDrawable(R.mipmap.ic_anuong);
            Bitmap bitmap = ChuyenImage.getStringtoImage(thuChi.getImageHangMuc());
// Scale it to 50 x 50
            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 80, 80, true));
// Set your new, scaled drawable "d"
            float tyLe= (float) (Integer.parseInt(thuChi.getSotien())/ (float)TongTien());
            yvalues.add(new PieEntry(tyLe, "", d));
        }

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.

        PieDataSet dataSet = new PieDataSet(yvalues, "");

        PieData data = new PieData(dataSet);
        // In Percentage term
        //data.setValueFormatter(new PercentFormatter());
        // Default value
        pieChart.setData(data);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(20f);
        pieChart.setHoleRadius(50f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.getLegend().setEnabled(false);   // Hide the legend

        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        data.setValueFormatter(new PercentFormatter(pieChart));
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        dataSet.setDrawIcons(true);
        dataSet.setSliceSpace(2f);

        dataSet.setValueLinePart1OffsetPercentage(90.0f);
        dataSet.setValueLinePart1Length(1f);
        dataSet.setValueLinePart2Length(0f);
        dataSet.setValueTextColor(Color.BLACK);


        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setIconsOffset(new MPPointF(0, 60));
        dataSet.setSelectionShift(5f);


        pieChart.animateXY(1400, 1400);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


}
