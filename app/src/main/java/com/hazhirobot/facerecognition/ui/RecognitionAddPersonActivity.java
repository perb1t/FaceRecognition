package com.hazhirobot.facerecognition.ui;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hazhirobot.facerecognition.R;
import com.hazhirobot.facerecognition.global.Constants;
import com.hazhirobot.facerecognition.global.URLManager;
import com.hazhirobot.facerecognition.modle.FacePerson;
import com.shijiwei.xkit.app.base.BaseActivity;
import com.shijiwei.xkit.nohttp.base.SimpleRequest;
import com.shijiwei.xkit.nohttp.youtu.model.add.AddPerson;
import com.shijiwei.xkit.nohttp.youtu.model.add.AddPersonResult;
import com.shijiwei.xkit.nohttp.youtu.sign.YouTuConfig;
import com.shijiwei.xkit.utility.log.LogUtil;
import com.shijiwei.xkit.widget.dialog.ActionSheet;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by shijiwei on 2018/1/2.
 *
 * @VERSION 1.0
 */

public class RecognitionAddPersonActivity extends BaseActivity implements
        RadioGroup.OnCheckedChangeListener,
        ActionSheet.ActionSheetCallback,
        PermissionListener,
        View.OnClickListener {

    private static final String TAG = "RecognitionAddPersonAct";

    private final int REQUEST_TAKE_PHOTO = 100;
    private final int REQUEST_SELECT_PHOTO = 101;

    private RadioGroup rgSex;
    private RadioButton rbSexWoman;
    private RadioButton rbSexMan;
    private EditText etName;
    private EditText etCompany;
    private EditText etOcc;
    private EditText etPhone;
    private ImageView ivFacePicture;
    private TextView tvTitel;
    private View btAddFace;
    private View btBack;

    private ActionSheet actionSheet;
    private Uri mSaveUri;

    private Bitmap mSelectedBitmap;

    private FacePerson mFacePerson;

    private int action = 0;
    private boolean isDataChanged = false;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_add_person;
    }

    @Override
    public void initialData(Bundle savedInstanceState) {

        actionSheet = new ActionSheet(this);
        Intent intent = getIntent();
        action = intent.getIntExtra(RecognitionSettingActivity.KEY_ACTION, 0);
        mFacePerson = intent.getParcelableExtra(RecognitionSettingActivity.KEY_DATA);
    }

    @Override
    public void initialView() {

        ivFacePicture = (ImageView) findViewById(R.id.iv_face_picture);
        btAddFace = findViewById(R.id.bt_add_face);
        etName = (EditText) findViewById(R.id.et_name);
        etCompany = (EditText) findViewById(R.id.et_company);
        etOcc = (EditText) findViewById(R.id.et_occ);
        etPhone = (EditText) findViewById(R.id.et_phone);
        rgSex = (RadioGroup) findViewById(R.id.rg_sex);
        rbSexMan = (RadioButton) findViewById(R.id.rb_sex_man);
        rbSexWoman = (RadioButton) findViewById(R.id.rb_sex_woman);
        tvTitel = (TextView) findViewById(R.id.tv_title);
        btBack = findViewById(R.id.bt_back);

        Drawable draMan = getResources().getDrawable(R.drawable.selector_check);
        Drawable draWoman = getResources().getDrawable(R.drawable.selector_check);
        draMan.setBounds(0, 0, 26, 26);
        draWoman.setBounds(0, 0, 26, 26);
        rbSexMan.setCompoundDrawables(draMan, null, null, null);
        rbSexWoman.setCompoundDrawables(draWoman, null, null, null);

    }

    @Override
    public void initialEvn() {

        rgSex.setOnCheckedChangeListener(this);
        actionSheet.setActionSheetCallback(this);
        ivFacePicture.setOnClickListener(this);
        btBack.setOnClickListener(this);
        btAddFace.setOnClickListener(this);

        if (action == RecognitionSettingActivity.ACTION_EDIT) {
            tvTitel.setText("编辑信息");
            etCompany.setText(mFacePerson.getCompany());
            etName.setText(mFacePerson.getName());
            etOcc.setText(mFacePerson.getOccupation());
            etPhone.setText(mFacePerson.getPhone());
        } else {
            tvTitel.setText("添加人员");
        }

        if (mFacePerson.getSex() == null || mFacePerson.getSex().equals("") || mFacePerson.getSex().equals("男")) {
            rbSexMan.performClick();
        } else {
            rbSexWoman.performClick();
        }


    }

    @Override
    public void onBackKeyPressed() {
        finisAndResultActivity(isDataChanged ? 2 : 0);
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
            case Constants.ACTION_ADD_PERSON:
                AddPersonResult addPersonResult =
                        JSON.toJavaObject(JSON.parseObject(response.get().toString()), AddPersonResult.class);
                if (addPersonResult.getErrorcode() == 0) {
                    mFacePerson.setFaceId(addPersonResult.getFaceId());
                    LogUtil.e(TAG, "====" + JSON.toJSON(mFacePerson).toString());
                    addPerson2HzServer(mFacePerson);
                } else {
                    showMsg(getString(R.string.upload_failed));
                }
                break;
            case Constants.N_ACTION_HZ_ADD_FACE:
                JSONObject result = JSON.parseObject(response.get().toString());
                if (result.getInteger("result") == Constants.N_RESULT_CODE_SUCCESS) {
                    LogUtil.e(TAG, "onHttpSucceed  hz server add face success");
                    showMsg(getString(R.string.upload_success));
                    isDataChanged = true;
                    reset();
                } else {
                    LogUtil.e(TAG, "onHttpSucceed  hz server add face failed");
                    showMsg(getString(R.string.upload_failed));
                    addPerson2HzServer(mFacePerson);
                }
                break;

            case Constants.N_ACTION_HZ_UPDATE_USER:

                JSONObject updateUserResult = (JSONObject) JSON.parse(response.get().toString());
                if (updateUserResult.getInteger("result") == Constants.N_RESULT_CODE_SUCCESS) {
                    showMsg(getString(R.string.update_success));
                    isDataChanged = true;
                } else {
                    showMsg(getString(R.string.update_failed));
                }
                break;
        }
    }

    @Override
    public void onHttpFailed(int what, Response response) {
        LogUtil.e(TAG,"onHttpFailed  action " + what + "  Response : " + response.getException() );
        switch (what) {
            case Constants.ACTION_ADD_PERSON:
            case Constants.N_ACTION_HZ_ADD_FACE:
                showMsg(getString(R.string.upload_failed));
                break;
            case Constants.N_ACTION_HZ_UPDATE_USER:
                showMsg(getString(R.string.update_failed));
                break;
        }
    }

    @Override
    public void onHttpFinish(int what) {
        hideLoadingDialog();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_sex_man:
                mFacePerson.setSex("男");
                break;
            case R.id.rb_sex_woman:
                mFacePerson.setSex("女");
                break;
        }
    }

    @Override
    public void onTakePhoto(View view) {
        checkSelfPermission(REQUEST_TAKE_PHOTO);
    }

    @Override
    public void onSelectPhoto(View view) {
        checkSelfPermission(REQUEST_SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogUtil.e(" ==== ", "onActivityResult : " + requestCode);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:  //拍照
                    //注意，如果拍照的时候设置了MediaStore.EXTRA_OUTPUT，data.getData=null
                    mSelectedBitmap = getBitmapFromIntent(mSaveUri);
                    break;
                case REQUEST_SELECT_PHOTO://选择图片
                    mSelectedBitmap = getBitmapFromIntent(data);
                    break;
            }
            ivFacePicture.setAlpha(1f);
            ivFacePicture.setImageBitmap(mSelectedBitmap);
        }
    }

    @Override
    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                takePhoto();
                break;
            case REQUEST_SELECT_PHOTO:
                selectPhoto();
                break;
        }
        actionSheet.dismiss();
    }

    @Override
    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                break;
            case REQUEST_SELECT_PHOTO:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                finisAndResultActivity(isDataChanged ? 2 : 0);
                break;
            case R.id.bt_add_face:
                if (action == RecognitionSettingActivity.ACTION_ADD) {
                    add();
                } else {
                    update();
                }
                break;
            case R.id.iv_face_picture:
                actionSheet.show();
                break;
        }
    }


    /* Function **
    */

    public boolean isValidData(EditText v) {

        Editable editable = v.getText();
        if (editable == null) {
            return false;
        }
        String data = editable.toString();
        if (data.trim().length() == 0) {
            return false;
        }
        return true;
    }

    private void update() {
        if (!isValidData(etName)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        mFacePerson.setName(etName.getText().toString());
        mFacePerson.setCompany(etCompany.getText().toString());
        mFacePerson.setOccupation(etOcc.getText().toString());
        mFacePerson.setPhone(etPhone.getText().toString());
        mFacePerson.setOrg(Constants.ORG.getName());
        mFacePerson.setOrgId(Constants.ORG.getId());

        JSONObject tag = (JSONObject) JSON.toJSON(mFacePerson);
        setPersonInfo(mFacePerson);

    }

    private void add() {

        if (mSelectedBitmap == null) {
            Toast.makeText(this, "请选择人脸图片", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidData(etName)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        mFacePerson.setName(etName.getText().toString());
        mFacePerson.setCompany(etCompany.getText().toString());
        mFacePerson.setOccupation(etOcc.getText().toString());
        mFacePerson.setPhone(etPhone.getText().toString());
        mFacePerson.setOrg(Constants.ORG.getName());

        mFacePerson.setOrgId(Constants.ORG.getId());
        mFacePerson.setPersonId("hz_" + generateUUID());

        JSONObject tag = (JSONObject) JSON.toJSON(mFacePerson);
        Log.e("=====", tag.toJSONString() + "   ,  uuid " + mFacePerson.getPersonId());

        addPerson(mSelectedBitmap, mFacePerson.getName(), tag.toString());
    }

    private void reset() {
        mFacePerson.clear();
        mSelectedBitmap = null;
        ivFacePicture.setImageResource(R.mipmap.icon_face_picture);
        ivFacePicture.setAlpha(0.4f);
        etName.setText("");
        rbSexMan.setChecked(true);
        etCompany.setText("");
        etOcc.setText("");
        etPhone.setText("");
    }

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }

    /**
     * 启动拍照
     */
    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mSaveUri = createImageUri();
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Continue only if the File was successfully created
            if (mSaveUri != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        mSaveUri);//设置文件保存的URI
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * 从图库选择照片
     */
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_SELECT_PHOTO);
    }


    /**
     * 创建保存得到的图片的uri
     *
     * @return
     */
    private Uri createImageUri() {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String filename = "JPEG_" + timeStampFormat.format(new Date());
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, filename);
        Uri imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return imageUri;
    }

    /**
     * 根据intent返回bitmap
     *
     * @param data
     * @return
     */

    private Bitmap getBitmapFromIntent(Intent data) {
        return getBitmapFromIntent(data.getData());
    }

    private Bitmap getBitmapFromIntent(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        //从系统表中查询指定Uri对应的照片
        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        //获取照片路径
        String path = cursor.getString(columnIndex);
        cursor.close();
        Bitmap bitmap = getBitmap(path, 256, 256);
        LogUtil.e(" getBitmapFromIntent  ", " " + bitmap.getWidth() + "   ,  " + bitmap.getHeight());
        return bitmap;
    }

    /**
     * 压缩图片
     *
     * @param path
     * @param maxWidth
     * @param maxHeight
     * @return
     */

    private Bitmap getBitmap(String path, int maxWidth, int maxHeight) {

        int rotate = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int result = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch (result) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //先解析图片边框的大小
        Bitmap bm = null;
        File file = new File(path);
        if (file.exists()) {
            BitmapFactory.Options ops = new BitmapFactory.Options();
            ops.inJustDecodeBounds = false;
            ops.inSampleSize = 1;
            Bitmap source = BitmapFactory.decodeFile(path, ops);
            int height = ops.outHeight;
            int width = ops.outWidth;

            if (rotate != 0) {
                Matrix matrix = new Matrix();
                matrix.setRotate(rotate, (float) ops.outWidth / 2, (float) ops.outHeight / 2);
                Bitmap rotateBm = Bitmap.createBitmap(source, 0, 0, ops.outWidth, ops.outHeight, matrix, true);
                if (rotateBm != null) {
                    source.recycle();
                    source = rotateBm;
                }
            }

            float scale = Math.max((float) maxWidth / width, (float) maxHeight / height);
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);

            return Bitmap
                    .createBitmap(source, 0, 0, width, height, matrix, true)
                    .copy(Bitmap.Config.RGB_565, true)
                    ;
        }
        return bm;
    }

    /**
     * 将Bitmap转换成base64数据
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
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

    private void finisAndResultActivity(int optionStatus) {
        Intent resultIntent = getIntent();
        resultIntent.putExtra(RecognitionSettingActivity.KEY_DATA, mFacePerson);
        resultIntent.putExtra(RecognitionSettingActivity.KEY_OPTION, optionStatus);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    /**
     * 添加人脸
     *
     * @param face
     */

    /* 优图 api*/
    private void addPerson(Bitmap face, String name, String tags) {
        SimpleRequest<String> request = new SimpleRequest<String>(
                YouTuConfig.getAddPersonURL(),
                String.class);
        request.setYTRequestBodyAsJson(
                new AddPerson(
                        new String[]{mFacePerson.getOrgId()},
                        mFacePerson.getPersonId(),
                        bitmapToBase64(face),
                        name,
                        tags));
        addTask2Queue(Constants.ACTION_ADD_PERSON, request);
    }

    private void setPersonInfo(FacePerson person) {
        SimpleRequest<String> request = new SimpleRequest<String>(
                URLManager.getUpdateUserURL(),
                String.class);
        request.setRequestBodyAsJson(person);
        LogUtil.e(TAG, "setPersonInfo  params : " + JSON.toJSONString(person));
        addTask2Queue(Constants.N_ACTION_HZ_UPDATE_USER, request);
    }


    /* 哈智 api*/
    private void addPerson2HzServer(FacePerson person) {
        SimpleRequest<String> request = new SimpleRequest<String>(
                URLManager.getAddFaceURL(),
                String.class);
        request.setRequestBodyAsJson(person);
        addTask2Queue(Constants.N_ACTION_HZ_ADD_FACE, request);
    }
}
