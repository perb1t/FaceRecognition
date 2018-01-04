package com.shijiwei.xkit.nohttp.youtu.model.detect;

import com.shijiwei.xkit.nohttp.youtu.model.Face;

/**
 * Created by shijiwei on 2017/3/20.
 */
public class DetectFace extends Face {

    /* 图片的base64格式 */
    private String image;
    /* 检测模式 0/1 正常/大脸模式 */
    private int mode = 1;

    public DetectFace() {
    }


    public DetectFace(String image) {
        this(image,1);
    }

    public DetectFace(String image, int mode) {
        this.image = image;
        this.mode = mode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
