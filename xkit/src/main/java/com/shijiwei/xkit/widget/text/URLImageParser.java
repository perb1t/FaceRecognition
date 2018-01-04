//package com.shijiwei.xkit.widget.text;
//
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.text.Html;
//import android.widget.TextView;
//
///**
// * Created by shijiwei on 2017/10/16.
// *
// * @VERSION 1.0
// */
//
//public class URLImageParser implements Html.ImageGetter {
//    TextView mTextView;
//
//    public URLImageParser(TextView textView) {
//        this.mTextView = textView;
//    }
//
//    @Override
//    public Drawable getDrawable(String source) {
//        final URLDrawable urlDrawable = new URLDrawable();
//        ImageLoader.getInstance().loadImage( source, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                urlDrawable.bitmap = loadedImage;
//                urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
//                mTextView.invalidate();
//                mTextView.setText(mTextView.getText()); // 解决图文重叠
//            }
//        });
//
//
//        return urlDrawable;
//    }
//
//    public class URLDrawable extends BitmapDrawable {
//        protected Bitmap bitmap;
//
//        @Override
//        public void draw(Canvas canvas) {
//            if (bitmap != null) {
//                canvas.drawBitmap(bitmap, 0, 0, getPaint());
//            }
//        }
//    }
//
//}
