package com.hazhirobot.facerecognition.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cityeasy.tts.SpeechTts;
import com.hazhirobot.facerecognition.R;
import com.hazhirobot.facerecognition.global.Constants;
import com.hazhirobot.facerecognition.global.URLManager;
import com.hazhirobot.facerecognition.modle.FacePerson;
import com.shijiwei.xkit.app.base.BaseActivity;
import com.shijiwei.xkit.nohttp.base.SimpleRequest;
import com.shijiwei.xkit.nohttp.youtu.model.identify.IdentifyFace;
import com.shijiwei.xkit.nohttp.youtu.model.identify.IdentifyFaceResult;
import com.shijiwei.xkit.nohttp.youtu.sign.YouTuConfig;
import com.shijiwei.xkit.utility.FaceTracker;
import com.shijiwei.xkit.utility.log.LogUtil;
import com.shijiwei.xkit.widget.camera.CameraPreview;
import com.shijiwei.xkit.widget.camera.XFaceDetector;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by shijiwei on 2017/12/18.
 *
 * @VERSION 1.0
 */

public class RecognitionActivity extends BaseActivity implements XFaceDetector.FaceDetectCallback,
        CameraPreview.CameraPreviewCallBack,
        View.OnClickListener {

    private static final String TAG = "RecognitionActivity";

    public static long DETECTION_INTERVAL_ERROR = 3 * 1000;
    public static long DETECTION_INTERVAL_SUCCESS = 6 * 1000;

    private CameraPreview cameraPreview;
    private XFaceDetector xfd;
    private Animation mScaleAnim;

    private ImageView ivVerifyStatus;
    private ImageView buttonBack;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvCompany;
    private TextView tvOcc;
    private TextView tvPhone;
    private TextView tvSignInTime;
    private FaceTracker faceTracker;

    private ImageView ivPreView;

    private boolean CAMEAR_ENABLE = true;
    private boolean isDetecting = false;
    private boolean isIdentifing = false;
    private FacePerson facePerson = new FacePerson();

    private SpeechTts mTTs;

    private Handler mDetectorHandler = new Handler();
    private Runnable mDetectorRunnable = new Runnable() {
        @Override
        public void run() {
            if (CAMEAR_ENABLE) {
                isDetecting = false;
                isIdentifing = false;
                ivVerifyStatus.setVisibility(View.GONE);
                updatePersonPrefile(facePerson.clear());
            }
        }
    };

    boolean oneshot = false;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_recognition;
    }

    @Override
    public void initialData(Bundle savedInstanceState) {

        if (Constants.inInitialVoiceService)
            mTTs = SpeechTts.createSpeechTts(this);
        oneshot = getIntent().getBooleanExtra("oneshot", false);
        xfd = new XFaceDetector();
        xfd.setFaceDetectCallback(this);
        mScaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale_verify_status);
        mScaleAnim.setInterpolator(new AnticipateInterpolator());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void initialView() {
        cameraPreview = (CameraPreview) findViewById(R.id.camera_pre_view);
        buttonBack = (ImageView) findViewById(R.id.bt_back);
        ivVerifyStatus = (ImageView) findViewById(R.id.iv_verify_status);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvCompany = (TextView) findViewById(R.id.tv_company);
        tvOcc = (TextView) findViewById(R.id.tv_occ);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvSignInTime = (TextView) findViewById(R.id.tv_sign_in_time);
        faceTracker = (FaceTracker) findViewById(R.id.iv_face_tracker);

        ivPreView = (ImageView) findViewById(R.id.iv_pre_face);
    }

    @Override
    public void initialEvn() {
        cameraPreview.setCameraPreviewCallBack(this);
        buttonBack.setOnClickListener(this);

        mDetectThread.start();

    }

    @Override
    public void onBackKeyPressed() {
        exit();
    }

    @Override
    public void onClick(View v) {
        exit();
    }

    @Override
    public void onNetworkStateChanged(int type, boolean isAvailable) {

    }

    @Override
    public void onPictureTaken(Bitmap picture) {
        if (xfd != null && picture != null) {
            Bitmap bm = picture.copy(Bitmap.Config.ARGB_8888, true);
            xfd.start(bm);
        }
    }


    @Override
    public void onCameraPreviewError(int code) {
        LogUtil.e(TAG, "onCameraPreviewError : " + code);
    }

    @Override
    public void detectCallback(int faceNumber, Bitmap face, FaceDetector.Face[] faceSet) {


        boolean centre = false;
        PointF p = new PointF();
        if (CAMEAR_ENABLE) {
            isDetecting = false;
            if (faceNumber == 0) {
                faceTracker.trackFaceSet(null, 0, 0);
            } else {
//                LogUtil.e(TAG, " face imasge  width = " + face.getWidth() + "   height = " + face.getHeight());
                faceTracker.trackFaceSet(faceSet, face.getWidth(), face.getHeight());
                if (faceSet != null) {
                    for (int i = 0; i < faceSet.length; i++) {
                        faceSet[i].getMidPoint(p);
                        if (p.x > 50 && p.x < face.getWidth() - 50 && p.y > 50 && p.y < face.getHeight() - 50)
                            centre = true;
                        break;
                    }
                }

//                LogUtil.e("====== centre ", centre + "");
                if (!isIdentifing && centre) {
                    identifyFace(face);
                }

            }
        }

    }

    private Thread mDetectThread = new Thread(new Runnable() {
        @Override
        public void run() {

            while (CAMEAR_ENABLE) {
                while (!isDetecting && previewSize != null && data != null) {
                    isDetecting = true;
                    YuvImage yuvimage = new YuvImage(
                            data,
                            ImageFormat.NV21,
                            previewSize.width,
                            previewSize.height,
                            null);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    yuvimage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 100, baos);// 80--JPG图片的质量[0-100],100最高
                    byte[] rawImage = baos.toByteArray();
                    //将rawImage转换成bitmap
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inPreferredConfig = Bitmap.Config.RGB_565;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, opts);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    preBitmap = Bitmap.createBitmap(
                            bitmap,
                            0, 0, bitmap.getWidth(), bitmap.getHeight(),
                            matrix,
                            true);
//                    handler.sendEmptyMessage(0);
//                    isDetecting = false;
                    xfd.start(preBitmap);
                }
            }
        }
    });

    Bitmap preBitmap;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ivPreView.setImageBitmap(preBitmap);
        }
    };

    Camera.Size previewSize;
    Camera camera;
    byte[] data;

    @Override
    public void onPreviewFrame(final byte[] data, final Camera camera) {

        this.camera = camera;
        this.data = data;
        if (previewSize == null) {
            previewSize = camera.getParameters().getPreviewSize();//获取尺寸,格式转换的时候要用到
            LogUtil.e(TAG, "previewSize   width : " + previewSize.width + "  ,  height : " + previewSize.height);
        }

    }

    @Override
    public void onHttpStart(int what) {

    }

    @Override
    public void onHttpSucceed(int what, Response response) {
        LogUtil.e("onHttpSucceed : " + response.get());

        switch (what) {
            case Constants.ACTION_IDENTIFY_FACE:

                IdentifyFaceResult identifyFaceResult =
                        JSON.toJavaObject(JSON.parseObject(response.get().toString()), IdentifyFaceResult.class);
                if (identifyFaceResult.getErrorcode() == 0) {
                    if (identifyFaceResult == null || identifyFaceResult.getPersons() == null || identifyFaceResult.getPersons().size() == 0) {
                        // 数据为空
                        updateStatus(R.mipmap.face_verify_error);
                        return;
                    } else {
                        int confidence = identifyFaceResult.getPersons().get(0).getConfidence();
                        if (confidence > 85) {
                            facePerson = JSON.toJavaObject(JSON.parseObject(identifyFaceResult.getPersons().get(0).getTag()), FacePerson.class);
                            JSONObject userListParams = new JSONObject();
                            userListParams.put("person_ids", facePerson.getPersonId());
                            getUserListByIds(userListParams);
                        } else {
                            updateStatus(R.mipmap.face_verify_error_other);
                        }
                    }
                } else {
                    //验证失败
                    if (identifyFaceResult.getErrorcode() == -1101) {
                        // 图片不清晰
                        updateStatus(R.mipmap.face_verify_error);
                    } else {
                        updateStatus(R.mipmap.face_net_error);
                    }

                }

                break;

            case Constants.N_ACTION_HZ_USER_LIST_BY_IDS:
                JSONObject userLisResult = JSON.parseObject(response.get().toString());
                if (userLisResult.getInteger("result") == Constants.N_RESULT_CODE_SUCCESS) {
                    List<FacePerson> persons = userLisResult.getJSONArray("persons").toJavaList(FacePerson.class);
                    if (persons == null || persons.size() == 0) {
                        LogUtil.e("=====", " size = 0");
                        updateStatus(R.mipmap.face_verify_error);
                    } else {
                        facePerson = persons.get(0);
                        signIn2HzServer(facePerson);
                    }
                } else {
                    updateStatus(R.mipmap.face_verify_error);
                }
                break;
            case Constants.N_ACTION_HZ_SIGB_IN:
                JSONObject result = JSON.parseObject(response.get().toString());
                if (result.getInteger("result") == Constants.N_RESULT_CODE_SUCCESS) {
                    facePerson.setSignInTime(System.currentTimeMillis());
                    updateStatus(R.mipmap.face_verify_ok);
                } else {
                    updateStatus(R.mipmap.face_verify_error);
                }
                break;

        }
    }

    @Override
    public void onHttpFailed(int what, Response response) {
//        super.onHttpFailed(what, response);

        LogUtil.e(TAG, "onHttpFailed action = " + what + " Response : " + response.getException());
        isDetecting = false;
        isIdentifing = false;
        ivVerifyStatus.setVisibility(View.GONE);
        updatePersonPrefile(facePerson.clear());
    }

    @Override
    public void onHttpFinish(int what) {
        mDetectorHandler.removeCallbacks(mDetectorRunnable);
        mDetectorHandler.postDelayed(mDetectorRunnable, DETECTION_INTERVAL_ERROR);
    }

    /**
     * 更新验证的状态图标
     *
     * @param res
     */
    private void updateStatus(int res) {
        if (res == R.mipmap.face_verify_ok) {
            updatePersonPrefile(facePerson);
        } else {
            updatePersonPrefile(facePerson.clear());
        }
        ivVerifyStatus.setVisibility(View.VISIBLE);
        ivVerifyStatus.setImageResource(res);
        ivVerifyStatus.startAnimation(mScaleAnim);
    }

    private void updatePersonPrefile(FacePerson facePerson) {
        tvName.setText(facePerson.getName());
        tvSex.setText(facePerson.getSex());
        tvCompany.setText(facePerson.getCompany());
        tvOcc.setText(facePerson.getOccupation());
        tvPhone.setText(facePerson.getPhone());
        tvSignInTime.setText(facePerson.getSignInTime() == 0 ? "" : Constants.SIMPLE_DATE_FORMAT.format(facePerson.getSignInTime()));


        if (Constants.inInitialVoiceService && !facePerson.getName().equals("")) {
            String welcomeWord = Constants.WELCOME_WORD.toUpperCase().replace("XXX", facePerson.getName());
            mTTs.startSpeaking(welcomeWord, null);
        }

        if (oneshot && !facePerson.getName().equals("")) {
            exit();
        }
    }

    /* 优图 api*/
    private void identifyFace(Bitmap face) {
        isIdentifing = true;
        SimpleRequest<String> request = new SimpleRequest<String>(
                YouTuConfig.getIdentifyFaceURL(),
                String.class);
        request.setYTRequestBodyAsJson(new IdentifyFace(bitmapToBase64(face), Constants.ORG.getId()));
        addTask2Queue(Constants.ACTION_IDENTIFY_FACE, request);

        LogUtil.e(TAG, "identifyFace params = " + request.toString());

    }


    /* 哈智 api */
    private void signIn2HzServer(FacePerson person) {
        SimpleRequest<String> request = new SimpleRequest<String>(
                URLManager.getSignInURL(),
                String.class);
        request.setRequestBodyAsJson(person);
        addTask2Queue(Constants.N_ACTION_HZ_SIGB_IN, request);
    }

    private void getUserListByIds(JSONObject params) {
        SimpleRequest<String> request = new SimpleRequest<String>(
                URLManager.getUserListByIdsURL(),
                String.class);
        request.setRequestBodyAsJson(params);
        addTask2Queue(Constants.N_ACTION_HZ_USER_LIST_BY_IDS, request);

        LogUtil.e(TAG, "getUserListByIds params = " + params.toString());
    }


    /**
     * 将Bitmap转换成base64数据
     *
     * @param bitmap
     * @return
     */
    public String bitmapToBase64(Bitmap bitmap) {

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


    private void exit() {
        mDetectorHandler.removeCallbacks(mDetectorRunnable);
        CAMEAR_ENABLE = false;
        cameraPreview.onRelease();
        isDetecting = true;
        mDetectThread = null;
        finish();
    }


}
