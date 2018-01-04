package com.shijiwei.xkit.utility.data.sp;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

    public static void writeBool(Context context, String fileName, String key,
                                 Boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void writeInt(Context context, String fileName, String key,
                                int value) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void writeLong(Context context, String fileName, String key,
                                 long value) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void writeStr(Context context, String fileName, String key,
                                String value) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean readBool(Context context, String fileName, String key) {
        boolean index = false;
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        index = preferences.getBoolean(key, index);
        return index;
    }

    public static int readInt(Context context, String fileName, String key) {
        int index = -1;
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        index = preferences.getInt(key, index);
        return index;
    }

    public static long readLong(Context context, String fileName, String key) {
        long index = 0l;
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        index = (long) preferences.getFloat(key, index);
        return index;
    }

    public static String readStr(Context context, String fileName, String key) {
        String string = null;
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        string = preferences.getString(key, null);
        return string;
    }

    public static void remove(Context context, String fileName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
