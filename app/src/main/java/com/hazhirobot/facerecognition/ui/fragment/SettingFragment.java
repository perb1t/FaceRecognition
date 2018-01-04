package com.hazhirobot.facerecognition.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hazhirobot.facerecognition.R;
import com.hazhirobot.facerecognition.global.Constants;
import com.hazhirobot.facerecognition.ui.MainActivity;
import com.shijiwei.xkit.app.base.BaseFragment;
import com.shijiwei.xkit.utility.data.sp.SPUtility;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by shijiwei on 2017/11/7.
 *
 * @VERSION 1.0
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private SPUtility spUtility;

    private EditText mEditPersonHeader;
    private EditText mEditPersonCurrentId;
    private TextView mButtonSave;

    private boolean isDisplay = false;

    @Override
    public int getLayoutResId() {
        return R.layout.frament_setting;
    }

    @Override
    public void initialData(Bundle savedInstanceState) {

    }

    @Override
    public void initialView(View view) {
        mEditPersonCurrentId = view.findViewById(R.id.et_person_current_id);
        mEditPersonHeader = view.findViewById(R.id.et_person_header);
        mButtonSave = view.findViewById(R.id.btn_save);
    }

    @Override
    public void initialEvn() {
        mButtonSave.setOnClickListener(this);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isDisplay) {
            if (!isVisibleToUser) {
                isDisplay = false;
                /* hide pager*/
            }
        } else {
            if (isVisibleToUser) {
                isDisplay = true;
                /* show pager*/
                spUtility = ((MainActivity) getActivity()).getSpUtility();
                int id = spUtility.getInt(SPUtility.PERSON_ID, -1);
                String header = spUtility.getString(SPUtility.PERSON_HEADER, Constants.PERSON_FACE_HEADER);
                mEditPersonCurrentId.setText("" + id);
                mEditPersonHeader.setText(header);

            }
        }


    }

    @Override
    public void onHttpStart(int what) {

    }

    @Override
    public void onHttpSucceed(int what, Response response) {

    }

    @Override
    public void onHttpFinish(int what) {

    }

    @Override
    public void onClick(View v) {
        if (mEditPersonCurrentId.getText() == null
                || mEditPersonCurrentId.getText().length() == 0
                || mEditPersonCurrentId.getText().toString().trim().length() == 0
                ) {
            Toast.makeText(getActivity(), "输填写 person id !", Toast.LENGTH_LONG).show();
            return;
        }
        if (mEditPersonHeader.getText() == null || mEditPersonHeader.getText().length() == 0 || mEditPersonHeader.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "输填写 person header !", Toast.LENGTH_LONG).show();
            return;
        }
        spUtility.putInt(SPUtility.PERSON_ID, Integer.valueOf(mEditPersonCurrentId.getText().toString().trim()));
        spUtility.putString(SPUtility.PERSON_HEADER, mEditPersonHeader.getText().toString());
    }
}
