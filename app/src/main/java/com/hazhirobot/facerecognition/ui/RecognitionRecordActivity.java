package com.hazhirobot.facerecognition.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.hazhirobot.facerecognition.R;
import com.hazhirobot.facerecognition.adapter.SignAdapter;
import com.hazhirobot.facerecognition.global.Constants;
import com.hazhirobot.facerecognition.global.URLManager;
import com.hazhirobot.facerecognition.modle.FacePerson;
import com.hazhirobot.facerecognition.widget.chart.ChartUtil;
import com.shijiwei.xkit.app.base.BaseActivity;
import com.shijiwei.xkit.nohttp.base.SimpleRequest;
import com.shijiwei.xkit.utility.TimeMannager;
import com.shijiwei.xkit.utility.log.LogUtil;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiwei on 2017/12/22.
 *
 * @VERSION 1.0
 */

public class RecognitionRecordActivity extends BaseActivity implements View.OnClickListener, TimeMannager.DateFormatCallBack {


    private static final String TAG = "RecognitionRecordActivi";

    private PieChart pieChart;

    private TextView tvWeek;
    private TextView tvTime;
    private TextView tvDate;
    private TextView tvTotal;
    private TextView tvSign;
    private TextView tvunSign;
    private View btBack;

    private RecyclerView lvSign;
    private RecyclerView lvunSign;

    private SignAdapter signAdapter;
    private SignAdapter unsignAdapter;

    private List<FacePerson> signDataSet;
    private List<FacePerson> unsignDataSet;

    private TimeMannager timeMannager;

    private int[] colors = {
            Color.rgb(60, 161, 243),//未签到
            Color.rgb(31, 213, 152)//已签到
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_record;
    }

    @Override
    public void initialData(Bundle savedInstanceState) {
        timeMannager = new TimeMannager();
        timeMannager.startFormat(this);
        signDataSet = new ArrayList<>();
        unsignDataSet = new ArrayList<>();
    }

    @Override
    public void initialView() {

        btBack = findViewById(R.id.bt_back);
        tvWeek = (TextView) findViewById(R.id.tv_week);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        tvSign = (TextView) findViewById(R.id.tv_sign);
        tvunSign = (TextView) findViewById(R.id.tv_unsign);

        lvSign = (RecyclerView) findViewById(R.id.lv_sign);
        lvunSign = (RecyclerView) findViewById(R.id.lv_unsign);

        lvSign.setLayoutManager(new LinearLayoutManager(this));
        lvunSign.setLayoutManager(new LinearLayoutManager(this));

        unsignAdapter = new SignAdapter(this, unsignDataSet, SignAdapter.Status.UNSIGN);
        signAdapter = new SignAdapter(this, signDataSet, SignAdapter.Status.SIGN);

        lvSign.setAdapter(signAdapter);
        lvunSign.setAdapter(unsignAdapter);


        FacePerson person = new FacePerson();
        person.setOrgId(Constants.ORG.getId());
        signInRecord(person);

    }

    @Override
    public void initialEvn() {
        btBack.setOnClickListener(this);
    }

    @Override
    public void onBackKeyPressed() {
        finish();

    }

    @Override
    public void onNetworkStateChanged(int type, boolean isAvailable) {

    }

    @Override
    public void onHttpStart(int what) {
        showLoadingDialog();
    }

    @Override
    public void onHttpSucceed(int what, Response response) {

        LogUtil.e(TAG, "onHttpSucceed  " + response.get().toString());
        JSONObject result = JSON.parseObject(response.get().toString());
        if (result.getInteger("result") == Constants.N_RESULT_CODE_SUCCESS) {
            List<FacePerson> totalRecords = result.getJSONArray("sign_in_record").toJavaList(FacePerson.class);

            signDataSet.clear();
            unsignDataSet.clear();
            for (int i = 0; i < totalRecords.size(); i++) {
                if (totalRecords.get(i).getSignInTime() == 0) {
                    unsignDataSet.add(totalRecords.get(i));
                } else {
                    signDataSet.add(totalRecords.get(i));
                }
            }

            updatePieChart(signDataSet.size(), unsignDataSet.size());

            signAdapter.notifyDataSetChanged();
            unsignAdapter.notifyDataSetChanged();

            LogUtil.e(TAG, "onHttpSucceed  " + totalRecords.size());

        } else {
            showMsg(getString(R.string.get_data_failed));
        }
    }

    @Override
    public void onHttpFailed(int what, Response response) {
        showMsg(getString(R.string.get_data_failed));
    }

    @Override
    public void onHttpFinish(int what) {
        hideLoadingDialog();
    }

    @Override
    public void onDateFormat(TimeMannager.IDate iDate) {
        tvWeek.setText(iDate.week);
        tvDate.setText(iDate.date);
        tvTime.setText(iDate.time);
    }

    @Override
    public void onClick(View v) {
        finish();
    }


    private int[] percentage(int total, int signPart) {
        int[] percentage = new int[2];
        if (total == 0) {
            percentage[0] = 0;
            percentage[1] = 0;
        } else {
            float fper = (float) signPart / total * 100;
            int iper = 0;
            if (fper > 0f && fper < 1f) {
                iper = 1;
            } else {
                iper = Math.round(fper);
            }
            percentage[0] = iper;
            percentage[1] = 100 - iper;
        }
        return percentage;

    }

    private void updatePieChart(int signIn, int unSignIn) {

        SpannableStringBuilder strTotal = new SpannableStringBuilder();
        SpannableStringBuilder strSign = new SpannableStringBuilder();
        SpannableStringBuilder strunSign = new SpannableStringBuilder();
        strTotal.append("总人数（" + (signIn + unSignIn) + "人）");
        strSign.append("已签到（" + signIn + "人）");
        strunSign.append("未签到（" + unSignIn + "人）");
        strSign.setSpan(new ForegroundColorSpan(colors[1]), 3, strSign.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        strunSign.setSpan(new ForegroundColorSpan(colors[0]), 3, strunSign.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvTotal.setText(strTotal);
        tvSign.setText(strSign);
        tvunSign.setText(strunSign);

        List<Entry> yVals = new ArrayList<>();
        List<String> xVals = new ArrayList<>();
        List<Integer> cs = new ArrayList<>();
        yVals.add(new Entry(signIn, 0));
        yVals.add(new Entry(unSignIn, 1));

        int[] percentage = percentage(signIn + unSignIn, signIn);

        xVals.add(percentage[0] == 0 ? "" : percentage[0] + "%");
        xVals.add(percentage[1] == 0 ? "" : percentage[1] + "%");

        cs.add(colors[1]);
        cs.add(colors[0]);

        ChartUtil.init(this);
        pieChart = (PieChart) findViewById(R.id.pie_chart);
        ChartUtil.initPieChart(
                pieChart,
                ChartUtil.generatePieData(yVals, xVals, cs, true),
                false,
                "",
                true,
                false
        );
    }


    private void signInRecord(FacePerson person) {
        SimpleRequest<String> request = new SimpleRequest<String>(
                URLManager.getRouteSignInRecordURL(),
                String.class);
        request.setRequestBodyAsJson(person);
        addTask2Queue(Constants.N_ACTION_HZ_SIGB_IN_RECORD, request);
    }


}
