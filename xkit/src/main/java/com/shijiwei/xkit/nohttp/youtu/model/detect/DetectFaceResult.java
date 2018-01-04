package com.shijiwei.xkit.nohttp.youtu.model.detect;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by shijiwei on 2017/3/20.
 */
public class DetectFaceResult {

    /**
     * session_id :
     * image_height : 337
     * image_width : 449
     * face : [{"face_id":"1969346852447883267","x":149,"y":40,"height":95,"width":95,"pitch":-6,"roll":-9,"yaw":4,"age":43,"gender":92,"glass":false,"expression":4,"beauty":76,"face_shape":{"face_profile":[{"x":163,"y":81},{"x":162,"y":88},{"x":161,"y":95},{"x":160,"y":102},{"x":161,"y":110},{"x":162,"y":117},{"x":166,"y":123},{"x":171,"y":128},{"x":177,"y":132},{"x":184,"y":135},{"x":191,"y":137},{"x":198,"y":137},{"x":205,"y":136},{"x":212,"y":133},{"x":217,"y":129},{"x":221,"y":124},{"x":224,"y":118},{"x":226,"y":111},{"x":227,"y":104},{"x":228,"y":98},{"x":229,"y":92}],"left_eye":[{"x":176,"y":83},{"x":179,"y":85},{"x":182,"y":86},{"x":185,"y":86},{"x":189,"y":85},{"x":186,"y":83},{"x":183,"y":81},{"x":179,"y":82}],"right_eye":[{"x":218,"y":90},{"x":215,"y":91},{"x":212,"y":91},{"x":209,"y":90},{"x":206,"y":88},{"x":209,"y":86},{"x":212,"y":86},{"x":216,"y":88}],"left_eyebrow":[{"x":172,"y":74},{"x":177,"y":74},{"x":182,"y":75},{"x":187,"y":76},{"x":192,"y":77},{"x":188,"y":73},{"x":182,"y":71},{"x":177,"y":71}],"right_eyebrow":[{"x":224,"y":83},{"x":220,"y":82},{"x":216,"y":81},{"x":211,"y":80},{"x":207,"y":79},{"x":211,"y":77},{"x":217,"y":78},{"x":221,"y":79}],"mouth":[{"x":183,"y":113},{"x":186,"y":117},{"x":189,"y":119},{"x":193,"y":120},{"x":197,"y":120},{"x":201,"y":119},{"x":205,"y":117},{"x":202,"y":113},{"x":199,"y":111},{"x":195,"y":111},{"x":191,"y":109},{"x":187,"y":111},{"x":187,"y":114},{"x":190,"y":115},{"x":194,"y":116},{"x":198,"y":116},{"x":201,"y":117},{"x":201,"y":116},{"x":198,"y":115},{"x":194,"y":114},{"x":191,"y":114},{"x":187,"y":114}],"nose":[{"x":197,"y":98},{"x":198,"y":87},{"x":195,"y":90},{"x":193,"y":93},{"x":190,"y":96},{"x":186,"y":101},{"x":191,"y":104},{"x":196,"y":105},{"x":200,"y":105},{"x":205,"y":103},{"x":203,"y":98},{"x":201,"y":94},{"x":200,"y":91}]}}]
     * errorcode : 0
     * errormsg : OK
     */

    @JSONField(name = "session_id")
    private String sessionId;
    @JSONField(name = "image_height")
    private int imageHeight;
    @JSONField(name = "image_width")
    private int imageWidth;
    private int errorcode;
    private String errormsg;
    private List<FaceBean> face;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public List<FaceBean> getFace() {
        return face;
    }

    public void setFace(List<FaceBean> face) {
        this.face = face;
    }

    public static class FaceBean {
        /**
         * face_id : 1969346852447883267
         * x : 149
         * y : 40
         * height : 95.0
         * width : 95.0
         * pitch : -6
         * roll : -9
         * yaw : 4
         * age : 43
         * gender : 92
         * glass : false
         * expression : 4
         * beauty : 76
         * face_shape : {"face_profile":[{"x":163,"y":81},{"x":162,"y":88},{"x":161,"y":95},{"x":160,"y":102},{"x":161,"y":110},{"x":162,"y":117},{"x":166,"y":123},{"x":171,"y":128},{"x":177,"y":132},{"x":184,"y":135},{"x":191,"y":137},{"x":198,"y":137},{"x":205,"y":136},{"x":212,"y":133},{"x":217,"y":129},{"x":221,"y":124},{"x":224,"y":118},{"x":226,"y":111},{"x":227,"y":104},{"x":228,"y":98},{"x":229,"y":92}],"left_eye":[{"x":176,"y":83},{"x":179,"y":85},{"x":182,"y":86},{"x":185,"y":86},{"x":189,"y":85},{"x":186,"y":83},{"x":183,"y":81},{"x":179,"y":82}],"right_eye":[{"x":218,"y":90},{"x":215,"y":91},{"x":212,"y":91},{"x":209,"y":90},{"x":206,"y":88},{"x":209,"y":86},{"x":212,"y":86},{"x":216,"y":88}],"left_eyebrow":[{"x":172,"y":74},{"x":177,"y":74},{"x":182,"y":75},{"x":187,"y":76},{"x":192,"y":77},{"x":188,"y":73},{"x":182,"y":71},{"x":177,"y":71}],"right_eyebrow":[{"x":224,"y":83},{"x":220,"y":82},{"x":216,"y":81},{"x":211,"y":80},{"x":207,"y":79},{"x":211,"y":77},{"x":217,"y":78},{"x":221,"y":79}],"mouth":[{"x":183,"y":113},{"x":186,"y":117},{"x":189,"y":119},{"x":193,"y":120},{"x":197,"y":120},{"x":201,"y":119},{"x":205,"y":117},{"x":202,"y":113},{"x":199,"y":111},{"x":195,"y":111},{"x":191,"y":109},{"x":187,"y":111},{"x":187,"y":114},{"x":190,"y":115},{"x":194,"y":116},{"x":198,"y":116},{"x":201,"y":117},{"x":201,"y":116},{"x":198,"y":115},{"x":194,"y":114},{"x":191,"y":114},{"x":187,"y":114}],"nose":[{"x":197,"y":98},{"x":198,"y":87},{"x":195,"y":90},{"x":193,"y":93},{"x":190,"y":96},{"x":186,"y":101},{"x":191,"y":104},{"x":196,"y":105},{"x":200,"y":105},{"x":205,"y":103},{"x":203,"y":98},{"x":201,"y":94},{"x":200,"y":91}]}
         */

        @JSONField(name = "face_id")
        private String faceId;
        private int x;
        private int y;
        private double height;
        private double width;
        private int pitch;
        private int roll;
        private int yaw;
        private int age;
        private int gender;
        private boolean glass;
        private int expression;
        private int beauty;
        @JSONField(name = "face_shape")
        private FaceShapeBean faceShape;

        public String getFaceId() {
            return faceId;
        }

        public void setFaceId(String faceId) {
            this.faceId = faceId;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public int getPitch() {
            return pitch;
        }

        public void setPitch(int pitch) {
            this.pitch = pitch;
        }

        public int getRoll() {
            return roll;
        }

        public void setRoll(int roll) {
            this.roll = roll;
        }

        public int getYaw() {
            return yaw;
        }

        public void setYaw(int yaw) {
            this.yaw = yaw;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public boolean isGlass() {
            return glass;
        }

        public void setGlass(boolean glass) {
            this.glass = glass;
        }

        public int getExpression() {
            return expression;
        }

        public void setExpression(int expression) {
            this.expression = expression;
        }

        public int getBeauty() {
            return beauty;
        }

        public void setBeauty(int beauty) {
            this.beauty = beauty;
        }

        public FaceShapeBean getFaceShape() {
            return faceShape;
        }

        public void setFaceShape(FaceShapeBean faceShape) {
            this.faceShape = faceShape;
        }

        public static class FaceShapeBean {
            @JSONField(name = "face_profile")
            private List<Point> faceProfile;
            @JSONField(name = "left_eye")
            private List<Point> leftEye;
            @JSONField(name = "right_eye")
            private List<Point> rightEye;
            @JSONField(name = "left_eyebrow")
            private List<Point> leftEyebrow;
            @JSONField(name = "right_eyebrow")
            private List<Point> rightEyebrow;
            private List<Point> mouth;
            private List<Point> nose;

            public List<Point> getFaceProfile() {
                return faceProfile;
            }

            public void setFaceProfile(List<Point> faceProfile) {
                this.faceProfile = faceProfile;
            }

            public List<Point> getLeftEye() {
                return leftEye;
            }

            public void setLeftEye(List<Point> leftEye) {
                this.leftEye = leftEye;
            }

            public List<Point> getRightEye() {
                return rightEye;
            }

            public void setRightEye(List<Point> rightEye) {
                this.rightEye = rightEye;
            }

            public List<Point> getLeftEyebrow() {
                return leftEyebrow;
            }

            public void setLeftEyebrow(List<Point> leftEyebrow) {
                this.leftEyebrow = leftEyebrow;
            }

            public List<Point> getRightEyebrow() {
                return rightEyebrow;
            }

            public void setRightEyebrow(List<Point> rightEyebrow) {
                this.rightEyebrow = rightEyebrow;
            }

            public List<Point> getMouth() {
                return mouth;
            }

            public void setMouth(List<Point> mouth) {
                this.mouth = mouth;
            }

            public List<Point> getNose() {
                return nose;
            }

            public void setNose(List<Point> nose) {
                this.nose = nose;
            }

            public static class Point {
                /**
                 * x : 163
                 * y : 81
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

        }
    }
}
