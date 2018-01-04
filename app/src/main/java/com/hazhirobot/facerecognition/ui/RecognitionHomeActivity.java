package com.hazhirobot.facerecognition.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hazhirobot.facerecognition.R;
import com.hazhirobot.facerecognition.global.Constants;
import com.hazhirobot.facerecognition.global.URLManager;
import com.hazhirobot.facerecognition.modle.Org;
import com.shijiwei.xkit.app.base.BaseActivity;
import com.shijiwei.xkit.nohttp.base.SimpleRequest;
import com.shijiwei.xkit.utility.data.sp.SPUtility;
import com.shijiwei.xkit.utility.log.LogUtil;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

/**
 * Created by shijiwei on 2017/12/19.
 *
 * @VERSION 1.0
 */

public class RecognitionHomeActivity extends BaseActivity implements View.OnClickListener, PermissionListener {

    private static final String TAG = "RecognitionHomeActivity";

    private FrameLayout btStartVerify;
    private ImageView ivOuterRing;
    private ImageView ivInnerRing;

    private Button btRecordSearch;
    private Button btSetting;

    private Animation innerRotareAnim;
    private Animation outerRotareAnim;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_face_recognition_home;
    }

    @Override
    public void initialData(Bundle savedInstanceState) {

        innerRotareAnim = AnimationUtils.loadAnimation(this, R.anim.inner_ring_rotate);
        outerRotareAnim = AnimationUtils.loadAnimation(this, R.anim.outer_ring_rotate);
//        innerRotareAnim.setInterpolator(new BounceInterpolator());
//        outerRotareAnim.setInterpolator(new BounceInterpolator());
        innerRotareAnim.setInterpolator(new LinearInterpolator());
        outerRotareAnim.setInterpolator(new LinearInterpolator());

        checkSelfPermission(200);

        getOrgList();
    }


    @Override
    public void initialView() {

        ivInnerRing = (ImageView) findViewById(R.id.iv_inner_ring);
        ivOuterRing = (ImageView) findViewById(R.id.iv_outer_ring);
        btStartVerify = (FrameLayout) findViewById(R.id.btn_face_verify);

        btRecordSearch = (Button) findViewById(R.id.bt_record_search);
        btSetting = (Button) findViewById(R.id.bt_setting);


        ivInnerRing.startAnimation(innerRotareAnim);
        ivOuterRing.startAnimation(outerRotareAnim);


    }

    @Override
    public void initialEvn() {
        ivInnerRing.setOnClickListener(this);
        btRecordSearch.setOnClickListener(this);
        btSetting.setOnClickListener(this);
    }

    long lastTapTime;

    @Override
    public void onBackKeyPressed() {
        long l = System.currentTimeMillis();
        if (l - lastTapTime < 1000) {
            finish();
        } else {
            showMsg(getString(R.string.double_tap_exit));
        }
        lastTapTime = l;

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

        LogUtil.e("onHttpSucceed", response.get().toString());

        switch (what) {
            case Constants.N_ACTION_HZ_GET_ORG_LIST:

                JSONObject result = JSON.parseObject(response.get().toString());

                if (result.getInteger("result") == Constants.N_RESULT_CODE_SUCCESS) {
                    Constants.ORG_SET = result.getJSONArray("orgs").toJavaList(Org.class);
                    String orgId = mSPUtility.getString(SPUtility.ORG_ID, "-1");

                    if (Constants.ORG_SET == null || Constants.ORG_SET.size() == 0) {
                        showMsg(getString(R.string.system_initialize_failed));
                        return;
                    }

                    Constants.isInitialSuccess = true;
                    for (int i = 0; i < Constants.ORG_SET.size(); i++) {
                        Constants.ORG_SET.get(i).setChecked(false);
                        if (i == 0 || Constants.ORG_SET.get(i).getId().equals(orgId)) {
                            Constants.ORG_SET.get(i).setChecked(true);
                            Constants.ORG = Constants.ORG_SET.get(i);
                            mSPUtility.putString(SPUtility.ORG_ID, Constants.ORG.getId());
                            break;
                        }
                    }
                } else {
                    showMsg(getString(R.string.system_initialize_failed));
                }

                break;
        }

    }

    @Override
    public void onHttpFailed(int what, Response response) {
        showMsg(getString(R.string.system_initialize_failed));
    }

    @Override
    public void onHttpFinish(int what) {

        hideLoadingDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_inner_ring:
                if (!Constants.isInitialSuccess) {
                    showMsg(getString(R.string.system_initialize_failed));
                    getOrgList();
                    return;
                }
                startActivityWithPermission(new Intent(this, RecognitionActivity.class), 200);
                break;
            case R.id.bt_record_search:
                if (!Constants.isInitialSuccess) {
                    showMsg(getString(R.string.system_initialize_failed));
                    getOrgList();
                    return;
                }
                startActivityWithPermission(new Intent(this, RecognitionRecordActivity.class), 201);
                break;
            case R.id.bt_setting:
                if (!Constants.isInitialSuccess) {
                    showMsg(getString(R.string.system_initialize_failed));
                    getOrgList();
                    return;
                }
                startActivityWithPermission(new Intent(this, RecognitionSettingActivity.class), 202);
                break;
        }
    }

    private void startActivityWithPermission(Intent startActivityIntent, int requestCode) {
        if (Constants.isRequstedPermission) {
            startActivity(startActivityIntent);
        } else {
            checkSelfPermission(requestCode);
        }
    }


    private void checkSelfPermission(int code) {

        AndPermission
                .with(this)
                .requestCode(code)
                .permission(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA})
                .callback(this)
                .start();
    }

    @Override
    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
        for (int i = 0; i < grantPermissions.size(); i++) {
            if (grantPermissions.get(i).equals(Manifest.permission.CAMERA)) {
                Constants.isRequstedPermission = true;
            }
        }


    }

    @Override
    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
        for (int i = 0; i < deniedPermissions.size(); i++) {
            if (deniedPermissions.get(i).equals(Manifest.permission.CAMERA)) {
                Constants.isRequstedPermission = false;
            }
        }
    }


    private void getOrgList() {
        SimpleRequest<String> request = new SimpleRequest<String>(
                URLManager.getOrgListURL(),
                String.class);
        request.setRequestBodyAsJson(null);
        addTask2Queue(Constants.N_ACTION_HZ_GET_ORG_LIST, request);
    }
}
