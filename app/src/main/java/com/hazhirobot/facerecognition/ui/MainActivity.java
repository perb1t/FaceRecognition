package com.hazhirobot.facerecognition.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.hazhirobot.facerecognition.R;
import com.hazhirobot.facerecognition.adapter.ActionSheetAdapter;
import com.hazhirobot.facerecognition.adapter.PagerAdapter;
import com.hazhirobot.facerecognition.ui.fragment.InputFragment;
import com.hazhirobot.facerecognition.ui.fragment.RecognitionFragment;
import com.hazhirobot.facerecognition.ui.fragment.SettingFragment;
import com.shijiwei.xkit.app.base.BaseActivity;
import com.shijiwei.xkit.utility.data.sp.SPUtility;
import com.shijiwei.xkit.utility.display.DisplayUtility;
import com.shijiwei.xkit.widget.camera.XFaceDetector;
import com.shijiwei.xkit.widget.dialog.ActionSheet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    private ActionSheet actionSheet;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mActionSheet;
    private ViewPager mPagerSheet;

    private ActionSheetAdapter actionSheetAdapter;
    private String[] menuSet = {"人脸识别", "人脸录入", "设置"};

    private List<Fragment> mPagerSet;
    private PagerAdapter mPagerAdapter;

    private SPUtility spUtility;

    private Dialog dialog;

    XFaceDetector xfd;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    public void initialData(Bundle savedInstanceState) {

        spUtility = new SPUtility(this, SPUtility.HZ_ROTOT_FACE_CONFIG);
        actionSheet = new ActionSheet(this);
        mPagerSet = new ArrayList<>();
        mPagerSet.add(new RecognitionFragment());
        mPagerSet.add(new InputFragment());
        mPagerSet.add(new SettingFragment());

        dialog = new Dialog(this, R.style.simple_dialog_x);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_dialog);
        ImageView iv = dialog.findViewById(R.id.iv_progress);
        LinearInterpolator lir = new LinearInterpolator();
        AnimationSet rotate = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.rotate_);
        rotate.setInterpolator(lir);
        iv.startAnimation(rotate);

        xfd = new XFaceDetector();


    }


    @Override
    public void initialView() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mActionSheet = (ListView) findViewById(R.id.lv_menu);
        if (Build.VERSION.SDK_INT >= 19) {
            mToolbar.setPadding(0, DisplayUtility.getStatusbarHeight(this), 0, 0);
        }
        mToolbar.setTitle("人脸识别");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };

        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        actionSheetAdapter = new ActionSheetAdapter(menuSet, this);
        mActionSheet.setAdapter(actionSheetAdapter);
        mActionSheet.setOnItemClickListener(this);

        mPagerSheet = (ViewPager) findViewById(R.id.view_pager);
        mPagerSheet.setOffscreenPageLimit(mPagerSet.size());
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mPagerSet);
        mPagerSheet.setAdapter(mPagerAdapter);


    }

    @Override
    public void initialEvn() {

    }

    @Override
    public void onBackKeyPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }

    }

    @Override
    public void onNetworkStateChanged(int type, boolean isAvailable) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mPagerSheet.getCurrentItem() != position) {
            mToolbar.setTitle(menuSet[position]);
            mPagerSheet.setCurrentItem(position, false);
        }
        mDrawerLayout.closeDrawers();
    }

    public SPUtility getSpUtility() {
        return spUtility;
    }

    public Dialog getDialog() {
        return dialog;
    }



}
