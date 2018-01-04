package com.hazhirobot.facerecognition.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hazhirobot.facerecognition.R;
import com.hazhirobot.facerecognition.adapter.OrgPersonListAdapter;
import com.hazhirobot.facerecognition.global.Constants;
import com.hazhirobot.facerecognition.global.URLManager;
import com.hazhirobot.facerecognition.modle.FacePerson;
import com.hazhirobot.facerecognition.modle.Org;
import com.hazhirobot.facerecognition.widget.spinner.ButtonDialog;
import com.hazhirobot.facerecognition.widget.spinner.Spinner;
import com.shijiwei.xkit.app.base.BaseActivity;
import com.shijiwei.xkit.nohttp.base.SimpleRequest;
import com.shijiwei.xkit.nohttp.youtu.model.delperson.DelPerson;
import com.shijiwei.xkit.nohttp.youtu.model.delperson.DelPersonResult;
import com.shijiwei.xkit.nohttp.youtu.sign.YouTuConfig;
import com.shijiwei.xkit.utility.data.sp.SPUtility;
import com.shijiwei.xkit.utility.log.LogUtil;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiwei on 2017/12/22.
 *
 * @VERSION 1.0
 */

public class RecognitionSettingActivity extends BaseActivity implements
        View.OnClickListener,
        Spinner.SpinnerCallback,
        OrgPersonListAdapter.ItemLongClickListener,
        ButtonDialog.OnClickListener {

    private static final String TAG = "RecognitionSettingActiv";

    private TextView tvWelcomeWord;
    private TextView tvOrg;
    private View btBack;
    private View btAddFace;


    private Spinner orgSpinner;
    private ButtonDialog btDialog;


    private RecyclerView lvOrgPersonList;
    private OrgPersonListAdapter orgPersonListAdapter;
    private List<FacePerson> personSet = new ArrayList<>();

    private FacePerson person = new FacePerson();


    @Override
    public int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initialData(Bundle savedInstanceState) {

    }

    @Override
    public void initialView() {

        btBack = findViewById(R.id.bt_back);
        tvOrg = (TextView) findViewById(R.id.tv_org);
        tvWelcomeWord = (TextView) findViewById(R.id.tv_welcome_word);
        btAddFace = findViewById(R.id.bt_add_face);

        lvOrgPersonList = (RecyclerView) findViewById(R.id.lv_face_person_list);
        lvOrgPersonList.setLayoutManager(new LinearLayoutManager(this));
        orgPersonListAdapter = new OrgPersonListAdapter(this, personSet);
        lvOrgPersonList.setAdapter(orgPersonListAdapter);

        btDialog = new ButtonDialog(this);

        tvOrg.setText(Constants.ORG.getName());
        tvOrg.setSelected(true);
        orgSpinner = new Spinner(this);
        orgSpinner.setSpinnerCallback(this);
        orgSpinner.updateData(Constants.ORG_SET);
        tvWelcomeWord.setText(Constants.WELCOME_WORD);

    }

    @Override
    public void initialEvn() {

        btBack.setOnClickListener(this);
        btAddFace.setOnClickListener(this);
        tvOrg.setOnClickListener(this);
        btDialog.setOnClickCallback(this);
        orgPersonListAdapter.setItemLongClickListener(this);

        person.setOrgId(Constants.ORG.getId());
        getUserList(person);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackKeyPressed() {
        exit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (resultCode == RESULT_OK) {
            FacePerson newPerson = data.getParcelableExtra(KEY_DATA);
            if (data.getIntExtra(KEY_OPTION, 0) != ACTION_OPTION_SUCCESS) return;
            switch (requestCode) {
                case ACTION_ADD:
                    getUserList(person);
                    break;
                case ACTION_EDIT:
                    person.setName(newPerson.getName());
                    person.setOccupation(newPerson.getOccupation());
                    person.setCompany(newPerson.getCompany());
                    person.setSex(newPerson.getSex());
                    person.setPhone(newPerson.getPhone());
                    orgPersonListAdapter.notifyDataSetChanged();
                    break;
            }
        }
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

        switch (what) {

            case Constants.N_ACTION_HZ_USER_LIST:
                JSONObject userListResult = JSON.parseObject(response.get().toString());
                if (userListResult.getInteger("result") == Constants.N_RESULT_CODE_SUCCESS) {
                    List<FacePerson> persons = userListResult.getJSONArray("persons").toJavaList(FacePerson.class);
                    personSet.clear();
                    personSet.addAll(persons);
                    orgPersonListAdapter.notifyDataSetChanged();
                } else {
                    LogUtil.e(TAG, "onHttpSucceed  hz server add face failed");

                }
                break;

            case Constants.ACTION_DEL_PERSON:
                DelPersonResult delPersonResult =
                        JSON.toJavaObject(JSON.parseObject(response.get().toString()), DelPersonResult.class);
                if (delPersonResult.getErrorcode() == Constants.N_RESULT_CODE_SUCCESS) {
                    delPersonHz(person);
                } else {
                    showMsg(getString(R.string.del_failed));
                }
                break;

            case Constants.N_ACTION_HZ_DELETE_USER:
                JSONObject delUserResult = JSON.parseObject(response.get().toString());
                if (delUserResult.getInteger("result") == Constants.N_RESULT_CODE_SUCCESS) {
                    showMsg(getString(R.string.del_success));
                    personSet.remove(person);
                    orgPersonListAdapter.notifyDataSetChanged();
                } else {
                    showMsg(getString(R.string.del_failed));
                }
                break;
        }


    }

    @Override
    public void onHttpFailed(int what, Response response) {
        switch (what) {
            case Constants.N_ACTION_HZ_USER_LIST:
                showMsg(getString(R.string.get_data_failed));
                break;
            case Constants.ACTION_DEL_PERSON:
            case Constants.N_ACTION_HZ_DELETE_USER:
                showMsg(getString(R.string.del_failed));
                break;
        }
    }

    @Override
    public void onHttpFinish(int what) {
        hideLoadingDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                exit();
                break;
            case R.id.bt_add_face:
                person.clear();
                person.setOrgId(Constants.ORG.getId());
                startActivityforResultWithAction(ACTION_ADD, person);
                break;
            case R.id.tv_org:
                orgSpinner.show();
                break;
        }
    }


    @Override
    public void onLongClick(int position) {
        person = personSet.get(position);
        btDialog.setTitle(person.getName());
        btDialog.show();
    }

    @Override
    public void onLeftButtonClick() {
        startActivityforResultWithAction(ACTION_EDIT, person);
    }

    @Override
    public void onRightButtonClick() {
        delPerson(new DelPerson(person.getPersonId(), person.getOrgId()));
    }


    @Override
    public void onSpinnerConfirm(Org org) {
        if (Constants.ORG.getId().equals(org.getId())) return;
        tvOrg.setText(org.getName());
        Constants.ORG = org;
        mSPUtility.putString(SPUtility.ORG_ID, org.getId());

        person.setOrgId(org.getId());
        getUserList(person);
    }

    public static final String KEY_ACTION = "action";
    public static final String KEY_DATA = "person";
    public static final String KEY_OPTION = "option";
    public static final int ACTION_ADD = 0;
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_OPTION_SUCCESS = 2;

    private void startActivityforResultWithAction(int action, FacePerson person) {
        Intent actionIntent = new Intent(this, RecognitionAddPersonActivity.class);
        actionIntent.putExtra(KEY_ACTION, action);
        actionIntent.putExtra(KEY_DATA, person);
        startActivityForResult(actionIntent, action);
    }

    private void exit() {
        String welcomeWord = tvWelcomeWord.getText().toString().trim();
        if (welcomeWord.length() == 0) {
            showMsg("请填写欢迎词");
            return;
        }
        Constants.WELCOME_WORD = welcomeWord;
        mSPUtility.putString(SPUtility.WELCOME_WORD, welcomeWord);
        finish();
    }


    /* 哈智 api */
    private void getUserList(FacePerson person) {
        SimpleRequest<String> request = new SimpleRequest<String>(
                URLManager.getUserList(),
                String.class);
        request.setRequestBodyAsJson(person);
        addTask2Queue(Constants.N_ACTION_HZ_USER_LIST, request);
    }

    private void delPersonHz(FacePerson person) {

        SimpleRequest<String> request = new SimpleRequest<String>(
                URLManager.getDeleteUserURL(),
                String.class);
        request.setYTRequestBodyAsJson(person);
        addTask2Queue(Constants.N_ACTION_HZ_DELETE_USER, request);
    }


    /* 优图 api */
    private void delPerson(DelPerson dperson) {
        SimpleRequest<String> request = new SimpleRequest<String>(
                YouTuConfig.getDelPersonURL(),
                String.class);
        request.setYTRequestBodyAsJson(dperson);
        addTask2Queue(Constants.ACTION_DEL_PERSON, request);
    }


}
