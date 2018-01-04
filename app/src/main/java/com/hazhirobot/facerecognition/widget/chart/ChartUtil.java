package com.hazhirobot.facerecognition.widget.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.hazhirobot.facerecognition.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiwei on 2016/6/22.
 */
public class ChartUtil {

    private static Context mContext;
    public static List<Integer> mColors = new ArrayList<>();

    static {

        for (int i = 0; i < 5; i++) {

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                mColors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                mColors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                mColors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                mColors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                mColors.add(c);
        }

        mColors.add(ColorTemplate.getHoloBlue());
    }


    public static void init(Context context) {
        mContext = context;
    }


    /**
     * @param mChart          图表控件
     * @param mPieData        数据源
     * @param isHoleEnabled   是否隐藏显示中心圆孔
     * @param spannableString 圆孔上的文字
     */
    public static void initPieChart(PieChart mChart, PieData mPieData, boolean isHoleEnabled, String spannableString, boolean isRotationEnabled, boolean isShowLegened) {
        if (mContext == null) {
            throw new NullPointerException("chartUtil uninitialized ,please call init() before !");
        }
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        mChart.setBackgroundColor(Color.TRANSPARENT);

//        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        if (spannableString == null) {
            mChart.setCenterText(null);
        } else {
            mChart.setCenterText(generateCenterSpannableText(spannableString));
        }
        mChart.setDrawCenterText(false);
        mChart.setDrawHoleEnabled(isHoleEnabled);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setRotationAngle(0);

        mChart.setRotationEnabled(isRotationEnabled);
        mChart.setHighlightPerTapEnabled(true);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

//        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
//        mChart.setMarkerView(mv);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setEnabled(isShowLegened);

        mChart.setData(mPieData);
        mChart.invalidate();
    }

    /**
     * 获取Piedata数据源
     *
     * @param yVals      模块数据值
     * @param xVals      模块数据名
     * @param colors     模块颜色管理
     * @param isNeedLine 是否需要导出线引导
     * @return
     */
    public static PieData generatePieData(List<Entry> yVals, List<String> xVals, List<Integer> colors, boolean isNeedLine) {
        if (mContext == null) {
            throw new NullPointerException("uninitialized ,please call init() before !");
        }

        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(xVals.size() >= 10 ? 0f : 3f);
        dataSet.setSelectionShift(5f);
        if (colors == null) {
            dataSet.setColors(mColors);
        } else {
            dataSet.setColors(colors);
        }
        if (isNeedLine) {
            dataSet.setValueLinePart1OffsetPercentage(80.f);
            dataSet.setValueLinePart1Length(0.2f);
            dataSet.setValueLinePart2Length(0.4f);
            dataSet.setValueLineColor(Color.WHITE);
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        }

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.WHITE);
        return data;
    }

    public static void initBarChart(BarChart mChart, BarData data, boolean isShowLegend) {
        if (mContext == null) {
            throw new NullPointerException("uninitialized ,please call init() before !");
        }
        mChart.setDescription("");
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(0);
        xAxis.setDrawGridLines(false);

        mChart.getAxisLeft().setDrawGridLines(false);

        // add a nice and smooth animation
        mChart.animateY(2500);

        mChart.getLegend().setEnabled(isShowLegend);
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setData(data);
        mChart.invalidate();

    }

    public static BarData generateBarData(List<BarEntry> yVals, List<String> xVals, boolean isDrawValues) {
        if (mContext == null) {
            throw new NullPointerException("uninitialized ,please call init() before !");
        }
        BarDataSet dataSet = new BarDataSet(yVals, "");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setDrawValues(isDrawValues);
        if (yVals.size() > 10) {
            dataSet.setDrawValues(false);
        } else {
            dataSet.setValueTextSize(9f);
        }
        BarData data = new BarData(xVals, dataSet);
        return data;
    }

    public static void initRadarChart(RadarChart mChart, RadarData data, int markViewId, int lableCount) {

        if (mContext == null) {
            throw new NullPointerException("uninitialized ,please call init() before !");
        }
        mChart.setDescription("");
        mChart.setWebLineWidth(1.5f);
        mChart.setWebLineWidthInner(0.75f);
        mChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        // set the marker to the chart
        if (markViewId != 0) {
            MyMarkerView mv = new MyMarkerView(mContext, markViewId);
            mChart.setMarkerView(mv);
        }

        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(9f);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setLabelCount(lableCount, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinValue(0f);
//        yAxis.setAxisMinValue(minVal);
//        yAxis.setAxisMaxValue(maxVal);  自适应最大值

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);

        mChart.setData(data);
        mChart.invalidate();
    }

    public static RadarData generateRadarData(List<Entry> yVals1, List<Entry> yVals2, List<String> xVals, String set1Name, String set2Name, boolean isDataDrawFilled1, boolean isDataDrawFilled2) {
        if (mContext == null) {
            throw new NullPointerException("uninitialized ,please call init() before !");
        }
        RadarDataSet dataSet1 = new RadarDataSet(yVals1, set1Name);
        dataSet1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        dataSet1.setFillColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        dataSet1.setDrawFilled(isDataDrawFilled1);
        dataSet1.setLineWidth(2f);

        RadarDataSet dataSet2 = new RadarDataSet(yVals2, set2Name);
        dataSet2.setColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        dataSet2.setFillColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        dataSet2.setDrawFilled(isDataDrawFilled2);
        dataSet2.setLineWidth(2f);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(dataSet1);
        sets.add(dataSet2);

        RadarData data = new RadarData(xVals, sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);

        return data;
    }

    public static void initLineChart(LineChart mChart, LineData data, int maxVal, int minVal, boolean isDrawGridBackground, boolean isDoubleTapToZoomEnabled) {

        if (mContext == null) {
            throw new NullPointerException("uninitialized ,please call init() before !");
        }
        mChart.setDrawGridBackground(isDrawGridBackground);
        mChart.setDoubleTapToZoomEnabled(isDoubleTapToZoomEnabled);
        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        // set the marker to the chart
        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
        mChart.setMarkerView(mv);

        mChart.setGridBackgroundColor(Color.parseColor("#ffffff"));

        // X 轴上的分割线
//        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//        llXAxis.setLineWidth(4f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);
//
//        XAxis xAxis = mChart.getXAxis();
//
//        LimitLine upperLimitLine = new LimitLine(130f, "Upper Limit");
//        upperLimitLine.setLineWidth(4f);
//        upperLimitLine.enableDashedLine(10f, 10f, 0f);
//        upperLimitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        upperLimitLine.setTextSize(10f);
//
//        LimitLine lowerLimitLine = new LimitLine(-30f, "Lower Limit");
//        lowerLimitLine.setLineWidth(4f);
//        lowerLimitLine.enableDashedLine(10f, 10f, 0f);
//        lowerLimitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        lowerLimitLine.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(lowerLimitLine);
//        leftAxis.addLimitLine(upperLimitLine);
        leftAxis.setAxisMaxValue(maxVal);
        leftAxis.setAxisMinValue(minVal);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);
        mChart.getAxisRight().setEnabled(false);
        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);

        mChart.setData(data);
        mChart.invalidate();

    }

    public static LineData generateLineData(List<Entry> yVals, List<String> xVals, String lable, boolean isDrawFilled, LineDataSet.Mode mode) {

        if (mContext == null) {
            throw new NullPointerException("uninitialized ,please call init() before !");
        }
        LineDataSet dataSet = new LineDataSet(yVals, lable);

        dataSet.setFillAlpha(110);
        dataSet.setFillColor(Color.RED);
        dataSet.setDrawFilled(isDrawFilled);

        // set the line to be drawn like this "- - - - - -"
        dataSet.enableDashedLine(10f, 5f, 0f);
        dataSet.enableDashedHighlightLine(10f, 5f, 0f);
        dataSet.setColor(R.color.main_color);
        dataSet.setCircleColor(R.color.main_color);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(3f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(9f);
        // LineDataSet.Mode.CUBIC_BEZIER  圆弧线条
        // LineDataSet.Mode.LINEAR 折线
        // LineDataSet.Mode.STEPPED 矩形折线
        dataSet.setMode(mode);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_red);
            dataSet.setFillDrawable(drawable);
        } else {
            dataSet.setFillColor(Color.BLACK);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(dataSet); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        return data;
    }

    public static void initHorizontalBarChart(HorizontalBarChart mChart, BarData data, boolean isDrawValueAboveBar, boolean isDoubleTapToZoomEnabled) {

        if (mContext == null) {
            throw new NullPointerException("uninitialized ,please call init() before !");
        }
        // mChart.setHighlightEnabled(false);
        mChart.setDoubleTapToZoomEnabled(isDoubleTapToZoomEnabled);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(isDrawValueAboveBar);
        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
//        mChart.setMaxVisibleValueCount(maxVal);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart.setDrawGridBackground(false);

        // mChart.setDrawYLabels(false);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);

        YAxis yl = mChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.3f);
        yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = mChart.getAxisRight();

        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinValue(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);
        mChart.animateY(2500);


        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

        mChart.setData(data);
        mChart.invalidate();
    }

    public static BarData generateBarData(List<List<BarEntry>> yVals, List<String> xVals, List<String> lables) {
        if (mContext == null) {
            throw new NullPointerException("uninitialized ,please call init() before !");
        }
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        for (int i = 0; i < yVals.size(); i++) {
            BarDataSet dataSet = new BarDataSet(yVals.get(i), lables.get(i));
            dataSet.setColor(mColors.get(i));
            dataSets.add(dataSet);
        }
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        return data;
    }


    private static SpannableString generateCenterSpannableText(String spannableString) {

        SpannableString s = new SpannableString(spannableString);
//        s.setSpan(new RelativeSizeSpan(1.5f), 0, 14, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
}
