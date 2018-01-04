package com.shijiwei.xkit.utility;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.widget.Toast;

import com.shijiwei.xkit.app.XKitApplication;
import com.shijiwei.xkit.utility.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

//跟App相关的辅助类
public class AppUtils {

    private static final String TAG = "AppUtils";

    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * 只知道包名-跳转第三方应用
     * @param packagename
     */
    public static void doStartAppWithPackageName(Context context, String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        LogUtil.d(TAG, "doStartAppWithPackageName: packageinfo===" + packageinfo);

        if (packageinfo == null) {
            Toast.makeText(context, "没有安装该应用", Toast.LENGTH_LONG).show();
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        LogUtil.d(TAG, "doStartAppWithPackageName: startActivity resolveinfoList===" + resolveinfoList);

        if (resolveinfoList != null && resolveinfoList.size() > 0) {
            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
            LogUtil.d(TAG, "doStartAppWithPackageName: startActivity packageName===" + packageName + " &className===" + className);
        } else {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packagename);
            if (null != intent) {
                context.startActivity(intent);
            }
            LogUtil.d(TAG, "doStartAppWithPackageName: startActivity intent===" + intent);
        }
    }


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /** 重启app. */
    public static void restart() {
        Intent intent = XKitApplication.getApplication().getBaseContext().getPackageManager().getLaunchIntentForPackage(
                XKitApplication.getApplication().getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        XKitApplication.getApplication().startActivity(intent);
    }

    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null)
            return runningTaskInfos.get(0).topActivity.getClassName();
        else
            return "";
    }

    /**
     * 判断手机是否安装某个应用
     * @param context
     * @param packageName 应用包名
     * @return true：安装，false：未安装
     */
    public static boolean isAppInstalledFromList(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 判断手机是否安装某个应用
     * @param context
     * @param packageName 应用包名
     * @return true：安装，false：未安装
     * 该方法容易报错：java.lang.RuntimeException: Package manager has died
     */
    public static boolean isAppInstalledFromPkg(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            installed = false;
        }
        return installed;
    }

}
