package com.hazhirobot.facerecognition.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shijiwei on 2017/12/20.
 *
 * @VERSION 1.0
 */

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "hz_face_recognition.db";
    public static final String IF_TABLE_NOT_EXISTS = "if not exists ";
    public static int DB_VERSION = 1;

    public static final String TABLE_NAME_FACE_RECOGNITION = "face_recognition";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_NAME = "name";
    public static final String KEY_SEX = "sex";
    public static final String KEY_OCCUPATION = "occupation";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_SIGNINTIME = "sign_in_time";
    public static final String KEY_ORG = "org";


    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FACE_RECOGNITION_TABLE_STATEMENT = "CREATE TABLE " + IF_TABLE_NOT_EXISTS + TABLE_NAME_FACE_RECOGNITION + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_COMPANY + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_SEX + " TEXT,"
                + KEY_OCCUPATION + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_SIGNINTIME + " TEXT,"
                + KEY_ORG + " TEXT)";

        db.execSQL(CREATE_FACE_RECOGNITION_TABLE_STATEMENT);

        Log.e("=========","db onCreate");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        Log.e("=========","db onOpen");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
