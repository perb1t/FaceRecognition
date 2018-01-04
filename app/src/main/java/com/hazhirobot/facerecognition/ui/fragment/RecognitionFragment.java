package com.hazhirobot.facerecognition.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hazhirobot.facerecognition.R;
import com.hazhirobot.facerecognition.global.Constants;
import com.hazhirobot.facerecognition.ui.MainActivity;
import com.shijiwei.xkit.app.base.BaseFragment;
import com.shijiwei.xkit.nohttp.base.SimpleRequest;
import com.shijiwei.xkit.nohttp.youtu.model.identify.IdentifyFace;
import com.shijiwei.xkit.nohttp.youtu.model.identify.IdentifyFaceResult;
import com.shijiwei.xkit.nohttp.youtu.sign.YouTuConfig;
import com.shijiwei.xkit.utility.log.LogUtil;
import com.shijiwei.xkit.widget.dialog.ActionSheet;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shijiwei on 2017/11/6.
 *
 * @VERSION 1.0
 */

public class RecognitionFragment extends BaseFragment implements View.OnClickListener, ActionSheet.ActionSheetCallback {

    private static final String TAG = "RecognitionFragment";
    private final int REQUEST_TAKE_PHOTO = 100;
    private final int REQUEST_SELECT_PHOTO = 101;

    private ImageView mPreviewPhoto;
    private TextView mTVPersonTags;
    private TextView mButtonRecognition;
    private CardView mPhotoFrame;

    private ActionSheet actionSheet;

    private Uri mSaveUri;
    private Bitmap mSelectedBitmap;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_recognition;
    }

    @Override
    public void initialData(Bundle savedInstanceState) {
        actionSheet = new ActionSheet(getActivity());
    }

    @Override
    public void initialView(View view) {
        mPreviewPhoto = view.findViewById(R.id.image);
        mTVPersonTags = view.findViewById(R.id.tv_person_tags);
        mButtonRecognition = view.findViewById(R.id.btn_recognition);
        mPhotoFrame = view.findViewById(R.id.photo_frame);

    }

    @Override
    public void initialEvn() {
        actionSheet.setActionSheetCallback(this);
        mPreviewPhoto.setOnClickListener(this);
        mButtonRecognition.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                actionSheet.show();
                break;
            case R.id.btn_recognition:
                if (mSelectedBitmap == null) {
                    Toast.makeText(getActivity(), "请选择识别人脸照片!", Toast.LENGTH_LONG).show();
                    return;
                }
                identifyFace(mSelectedBitmap);
                break;
        }
    }

    @Override
    public void onTakePhoto(View view) {


        if (checkSelfPermission(REQUEST_TAKE_PHOTO)) {
            takePhoto();
            actionSheet.dismiss();
        }
    }

    @Override
    public void onSelectPhoto(View view) {

        if (checkSelfPermission(REQUEST_SELECT_PHOTO)) {
            selectPhoto();
            actionSheet.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            mTVPersonTags.setText("");
            mPhotoFrame.setAlpha(1);
            mPreviewPhoto.setImageBitmap(mSelectedBitmap);
        }
    }

    @Override
    public void onHttpStart(int what) {
        ((MainActivity) getActivity()).getDialog().show();
    }

    @Override
    public void onHttpSucceed(int what, Response response) {
        LogUtil.e("onHttpSucceed : " + response.get());
        IdentifyFaceResult identifyFaceResult =
                JSON.toJavaObject(JSON.parseObject(response.get().toString()), IdentifyFaceResult.class);
        if (identifyFaceResult.getErrorcode() == 0) {
            if (identifyFaceResult == null || identifyFaceResult.getPersons() == null || identifyFaceResult.getPersons().size() == 0) {
                Toast.makeText(getActivity(), "数据为空", Toast.LENGTH_LONG).show();
                return;
            }
            mTVPersonTags.setText(identifyFaceResult.getPersons().get(0).getTag());
        } else {
            Toast.makeText(getActivity(), "人脸识别失败！", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onHttpFinish(int what) {
        ((MainActivity) getActivity()).getDialog().hide();
    }


    /**
     * 启动拍照
     */
    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mSaveUri = createImageUri();
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Continue only if the File was successfully created
            if (mSaveUri != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        mSaveUri);//设置文件保存的URI
                takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * 从图库选择照片
     */
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_SELECT_PHOTO);
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
        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        //获取照片路径
        String path = cursor.getString(columnIndex);
        cursor.close();
        Bitmap bitmap = getBitmap(path, 256, 256);


        LogUtil.e(" getBitmapFromIntent  ", "  " + bitmap.getWidth() + "   ,  " + bitmap.getHeight());
        return bitmap;
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
        Uri imageUri = getActivity().getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return imageUri;
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
            ops.inJustDecodeBounds = true;
            ops.inSampleSize = 1;
            BitmapFactory.decodeFile(path, ops);
            int oHeight = ops.outHeight;
            int oWidth = ops.outWidth;

            if (!(oHeight <= maxHeight && oWidth <= maxWidth)) {
                //控制压缩比
                int contentHeight = maxWidth;
                int contentWidth = maxHeight;
                if (((float) oHeight / contentHeight) < ((float) oWidth / contentWidth)) {
                    ops.inSampleSize = (int) Math.ceil((float) oWidth / contentWidth);
                } else {
                    ops.inSampleSize = (int) Math.ceil((float) oHeight / contentHeight);
                }
            }
            ops.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(path, ops);

            if (rotate > 0) {
                Matrix matrix = new Matrix();
                matrix.setRotate(rotate, (float) ops.outWidth / 2, (float) ops.outHeight / 2);
                Bitmap rotateBm = Bitmap.createBitmap(bm, 0, 0, ops.outWidth, ops.outHeight, matrix, true);
                if (rotateBm != null) {
                    bm.recycle();
                    bm = rotateBm;
                }
            }

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


    private boolean checkSelfPermission(int requstCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                ) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, requstCode);

            return false;

        }
        return true;
    }

    /**
     * 人脸查找
     *
     * @param face
     */
    private void identifyFace(Bitmap face) {
        SimpleRequest<String> request = new SimpleRequest<String>(
                YouTuConfig.getIdentifyFaceURL(),
                String.class);
        request.setYTRequestBodyAsJson(new IdentifyFace(bitmapToBase64(face), Constants.FACE_GROUP_ID));
        addTask2Queue(Constants.ACTION_IDENTIFY_FACE, request);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                takePhoto();
                actionSheet.dismiss();
            } else if (requestCode == REQUEST_SELECT_PHOTO) {
                selectPhoto();
                actionSheet.dismiss();
            }

        } else {
            Toast.makeText(getActivity(), "请选择允许访问权限", Toast.LENGTH_SHORT).show();
        }

    }
}
