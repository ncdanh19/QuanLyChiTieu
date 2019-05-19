package com.sinhvien.quanlychitieu.fragment;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.sinhvien.quanlychitieu.Database.ThuChi;
import com.sinhvien.quanlychitieu.Database.ThuChiHelper;
import com.sinhvien.quanlychitieu.R;
import com.sinhvien.quanlychitieu.adapter.ChuyenImage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongKeThuFragment extends Fragment implements OnChartValueSelectedListener {
    PieChart pieChart;
    ThuChiHelper tc_database;
    List<ThuChi> listThuChi;
    View view;
    private TextView noItem;
    private LinearLayout haveItem;

    public ThongKeThuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_thong_ke_thu, container, false);

        noItem=(TextView) view.findViewById(R.id.noItem);
        haveItem=(LinearLayout) view.findViewById(R.id.haveItem);
        pieChart = (PieChart) view.findViewById(R.id.piechart);

        tc_database = new ThuChiHelper(getActivity());
        listThuChi = tc_database.getTongTien(1);

        if(listThuChi.size()<1)
        {
            noItem.setVisibility(View.VISIBLE);
            haveItem.setVisibility(View.GONE);
        }
        else {
            noItem.setVisibility(View.GONE);
            haveItem.setVisibility(View.VISIBLE);
            createPieChart();
        }
        return view;
    }

    public int TongTien(){
        int tong=0;
        for (int i = 0; i < listThuChi.size(); i++) {
            ThuChi thuChi = listThuChi.get(i);
            tong+=Integer.parseInt(thuChi.getSotien());
        }
        return tong;
    }

    public boolean checkZeroList() {
        for (int i = 0; i < listThuChi.size(); i++)
            if (Integer.parseInt(listThuChi.get(i).getSotien()) != 0)
                return false;
        return true;
    }

    private void createPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.setExtraOffsets(42f, 42f, 42f, 42f);


        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();

        if (checkZeroList()) { // nếu tất cả phần tử trong list đều là 0
            Bitmap bitmap = ChuyenImage.getStringtoImage(listThuChi.get(0).getImageHangMuc());
            // Scale it to 50 x 50
            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 80, 80, true));
            // Set your new, scaled drawable "d"
            yvalues.add(new PieEntry(1, "", d)); //thêm phần tử cuối vào chart
        } else {
            for (int i = 0; i < listThuChi.size(); i++) {
                final ThuChi thuChi = listThuChi.get(i);
                //Drawable user_icon = getResources().getDrawable(R.mipmap.ic_anuong);
                Bitmap bitmap = ChuyenImage.getStringtoImage(thuChi.getImageHangMuc());
                // Scale it to 50 x 50
                Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 80, 80, true));
                // Set your new, scaled drawable "d"
                float tyLe = (float) (Integer.parseInt(thuChi.getSotien()) / (float) TongTien());
                yvalues.add(new PieEntry(tyLe, "", d));
            }
        }

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.

        PieDataSet dataSet = new PieDataSet(yvalues, "");
        PieData data = new PieData(dataSet);

        // Default value
        pieChart.setData(data);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(20f);
        pieChart.setHoleRadius(50f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.getLegend().setEnabled(false);   // Hide the legend

        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        //data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueFormatter(new ThongKeThuFragment.MyPercentFormatter(pieChart));
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

    public class MyPercentFormatter extends ValueFormatter
    {

        public DecimalFormat mFormat;
        private PieChart pieChart;
        private boolean percentSignSeparated;

        public MyPercentFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0");
            percentSignSeparated = true;
        }

        // Can be used to remove percent signs if the chart isn't in percent mode
        public MyPercentFormatter(PieChart pieChart) {
            this();
            this.pieChart = pieChart;
        }

        // Can be used to remove percent signs if the chart isn't in percent mode
        public MyPercentFormatter(PieChart pieChart, boolean percentSignSeparated) {
            this(pieChart);
            this.percentSignSeparated = percentSignSeparated;
        }

        @Override
        public String getFormattedValue(float value) {
            if(value<10)
                return "";
            if(checkZeroList())
                return "";
            return mFormat.format(value) + (percentSignSeparated ? " %" : "%");
        }

        @Override
        public String getPieLabel(float value, PieEntry pieEntry) {
            if (pieChart != null && pieChart.isUsePercentValuesEnabled()) {
                // Converted to percent
                return getFormattedValue(value);
            } else {
                // raw value, skip percent sign
                return mFormat.format(value);
            }
        }

    }
}
