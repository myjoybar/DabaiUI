package com.joybar.dabaiui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.AttributeSet;
import android.view.View;

import com.joybar.dabaiui.R;

/**
 * Created by joybar on 28/11/16.
 */
public class WaveBallView extends View {


    private PorterDuffXfermode porterDuffXfermode;// Xfermode
    private Paint paint;// 画笔
    private Bitmap bitmap;// 源图片

    private float width, height;// 控件宽高
    private Canvas mCanvas;// 在该画布上绘制目标图片
    private Path path;// 画贝塞尔曲线需要用到
    private Bitmap bg;// 目标图片

    private float waveY;// 上升的高度
    private float waveYPercent = 0.5f;//波浪高度所占背景的百分比；0.0-1.0之间
    private float waveHeight = 10;// 默认波幅
    private float waveHeightPercent = 0.1f;//波幅所占整个高度的百分比；0.0-0.12之间,最大为0.12，否则会失真
    private float maxWaveHeightPercent = 0.12f;

    private float centerX = 0;//球心x坐标
    private float centerY = 0;//球心y坐标

    private float orientationOffset = 1;// 方向角度偏移量
    private float rightOffset = 0;//控制点在X上的偏移量，开启线程，起到荡漾效果
    private int SENSITIVITY = 90;

    Float[] aArray = null;


    Float p0H = 0f;
    Float p1H = 0f;
    Float p2H = 0f;
    Float p3H = 0f;

    Float p0R = 0f;
    Float p1R = 0f;
    Float p2R = 0f;
    Float p3R = 0f;

    Float p0x = 0f;
    Float p1x = 0f;
    Float p2x = 0f;
    Float p3x = 0f;

    Float p0y = 0f;
    Float p1y = 0f;
    Float p2y = 0f;
    Float p3y = 0f;
    //
    Float arc0Initial = 0f;
    Float arc0 = 0f;
    Float arc1Initial = 0f;
    Float arc1 = 0f;
    Float arc2Initial = 0f;
    Float arc2 = 0f;
    Float arc3Initial = 0f;
    Float arc3 = 0f;

    Float closeR = 0f;

    Float arcClose = 0f;

    Float pClose1x = 0f;
    Float pClose1y = 0f;

    Float pClose2x = 0f;
    Float pClose2y = 0f;

    public WaveBallView(Context context) {
        this(context,null);
    }

    public WaveBallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveBallView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }



    public void setSensorEvent(SensorEvent sensorEvent) {

            float[] values = sensorEvent.values;
            // 获取手机触发event的传感器的类型
            int sensorType = sensorEvent.sensor.getType();
            //定义气泡当前位置X Y坐标值
            int x = 0;
            int y = -0;
            int cx = 90;
            int cy = 90;


            switch (sensorType) {
                case Sensor.TYPE_ORIENTATION:
                    int zAngle = (int) values[0];
                    int yAngle = (int) values[1];
                    int xAngle = (int) values[2];

                    if (Math.abs(xAngle) <= SENSITIVITY) {
                        int deltaX = (int) (cx * xAngle / SENSITIVITY);
                        x += deltaX;
                    } else if (xAngle > SENSITIVITY) {
                        x = 0;
                    } else {
                        x = cx * 2;
                    }
                    if (Math.abs(yAngle) <= SENSITIVITY) {
                        int deltaY = (int) (cy * yAngle / SENSITIVITY);
                        y += deltaY;
                    } else if (yAngle > SENSITIVITY) {
                        y = cy * 2;
                    } else {
                        y = 0;
                    }


                    double angle = 0;
                    if (y == 0) {
                        if (x < 0) {
                            angle = 90;
                        } else if (x == 0) {
                            angle = 0;
                        } else {
                            angle = -90;
                        }
                    } else if (y < 0) {
                        if (x < 0) {
                            float v = ((float) (float) x / (float) y);
                            angle = Math.toDegrees(Math.atan(Math.abs(v)));
                        } else if (x == 0) {
                            //angle = 0;
                        } else {
                            float v = ((float) (float) x / (float) y);
                            angle = -Math.toDegrees(Math.atan(Math.abs(v)));
                        }
                    } else {
                        if (x < 0) {
                            float v = ((float) (float) y / (float) x);
                            angle = Math.toDegrees(Math.atan(Math.abs(v)));
                            angle = angle + 90.0;

                        } else if (x == 0) {
                            //angle = -180;
                        } else {
                            float v = ((float) (float) y / (float) x);
                            angle = -Math.toDegrees(Math.atan(Math.abs(v)));
                            angle = angle - 90.0;
                        }
                    }
                    this.setOrientationOffset((float)-angle);

                    this.postInvalidate();
                    break;
            }


        }

    public void setOrientationOffset(float orientationOffset) {
        this.orientationOffset = orientationOffset;
    }

    public float getWaveYPercent() {
        return waveYPercent;
    }

    public void setWaveYPercent(float waveYPercent) {
        if(waveYPercent<0f){
            this.waveYPercent = 0;
        }else if(waveYPercent>1.0f){
            this.waveYPercent = 1.0f;
        }else{
            this.waveYPercent = waveYPercent;
        }

    }
    public void setWaveHeightPercent(float waveHeightPercent) {

        this.waveHeightPercent = waveHeightPercent;
        if(waveHeightPercent>maxWaveHeightPercent){
            this.waveHeightPercent = maxWaveHeightPercent;
        }
        this.waveHeight = height*waveHeightPercent;
        // intRightOffset();
        //顶部或者底部，波幅为0,高度越接近一半则波幅越大
        this.waveHeight = (float) (waveHeight*-Math.sin(((waveYPercent*180)+180)* Math.PI / 180));

    }




    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#CD302F"));
        // 获得资源文件
        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.icon_log_transparent);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        centerX = bitmap.getWidth() / 2f;
        centerY = bitmap.getHeight() / 2f;
        // 初始化Xfermode
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        // 初始化path
        path = new Path();
        // 初始化画布
        mCanvas = new Canvas();
        // 创建bitmap
        bg = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);
        // 将新建的bitmap注入画布
        mCanvas.setBitmap(bg);
        intRightOffset();
        //开启波浪滚动效果
        new WaveThread().start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画目标图，存在bg上
        drawTargetBitmap();
        canvas.drawBitmap(bg, getPaddingLeft(), getPaddingTop(), null);

    }

    private void drawTargetBitmap() {
        // 重置path
        path.reset();
        // 擦除像素，这个必须要有
        bg.eraseColor(Color.TRANSPARENT);
        calculate();// 计算值
        path.moveTo(p0x, p0y);
        path.cubicTo(p1x, p1y,
                p2x, p2y, p3x, p3y);
        path.lineTo(pClose1x, pClose1y);
        path.lineTo(pClose2x, pClose2y);
        // // 进行闭合
        path.close();

        mCanvas.drawBitmap(bitmap, 0, 0, paint);//
        paint.setXfermode(porterDuffXfermode);// 设置Xfermode

        mCanvas.drawPath(path, paint);// 画三阶贝塞尔曲线
        paint.setXfermode(null);// 重置Xfermode
    }

    public void calculate() {
        waveY =height*(1f-waveYPercent);
        //以bg中心为圆心建立直角坐标系，计算4个点距离X轴的高度
        p0H = Math.abs(waveY - 1 / 2f * height);
        p1H = 0f;
        if ((waveY - 1 / 2f * height) >= 0) {
            p1H = Math.abs(waveY - 1 / 2f * height - waveHeight);
        } else {
            p1H = Math.abs(1 / 2f * height - waveY + waveHeight);
        }

        p2H = 0f;

        if ((waveY - 1 / 2f * height) >= 0) {
            p2H = Math.abs(waveY - 1 / 2f * height + waveHeight);
        } else {
            p2H = Math.abs(1 / 2f * height - waveY - waveHeight);
        }

        p3H = Math.abs(waveY - 1 / 2f * height);


        //以bg中心为圆心建立直角坐标系，计算4个点距离圆心的半径
        p0R = (float) Math.sqrt((1 / 2f) * height * (1 / 2f) * height + p0H
                * p0H);

        p1R = (float) Math.sqrt(1 / 4f * height * 1 / 4f * height + p1H * p1H);

        p2R = (float) Math.sqrt(1 / 4f * height * 1 / 4f * height + p2H * p2H);

        p3R = (float) Math.sqrt((1 / 2f) * height * (1 / 2f) * height + p3H
                * p3H);


        //计算圆心角
        arc0Initial = (float) Math.toDegrees(Math.acos(p0H / p0R));
        arc0 = arc0Initial + orientationOffset;

        //计算p0坐标
        if ((waveY - 1 / 2f * height) >= 0) {
            p0x = centerX - p0R * (float) Math.sin(arc0 * Math.PI / 180);
            p0y = centerY + p0R * (float) Math.cos(arc0 * Math.PI / 180);
        } else {
            arc0Initial = (float) Math.toDegrees(Math.acos(Math.sqrt(p0R * p0R - p0H * p0H)/ p0R));
            arc0 = arc0Initial + orientationOffset;
            p0x = centerX - p0R * (float) Math.cos(arc0 * Math.PI / 180);
            p0y = centerY - p0R * (float) Math.sin(arc0 * Math.PI / 180);
        }

        //计算p1坐标
        arc1Initial = (float)Math.toDegrees(Math.acos(p1H / p1R));
        arc1 = arc1Initial + orientationOffset;

        if ((waveY - 1 / 2f * height) >= 0) {
            p1x = (float) (centerX - p1R
                    * (float) Math.sin(arc1 * Math.PI / 180) + (float) rightOffset
                    * Math.sin((orientationOffset + 90) * Math.PI / 180) / 1.5f);
            p1y = (float) (centerY + p1R
                    * (float) Math.cos(arc1 * Math.PI / 180) + (float) rightOffset
                    * Math.sin((orientationOffset) * Math.PI / 180) / 1.5f);

        } else {

            arc1Initial = (float) Math.toDegrees(Math.acos(Math.sqrt(p1R * p1R - p1H * p1H)/ p1R));
            arc1 = arc1Initial + orientationOffset;
            p1x = (float) (centerX - p1R
                    * (float) Math.cos(arc1 * Math.PI / 180) + (float) rightOffset
                    * Math.sin((orientationOffset + 90) * Math.PI / 180) / 1.5f);
            p1y = (float) (centerY - p1R
                    * (float) Math.sin(arc1 * Math.PI / 180) + (float) rightOffset
                    * Math.sin((orientationOffset) * Math.PI / 180) / 1.5f);

        }

        //计算p2坐标

        arc2Initial = (float) Math.toDegrees(Math.acos(Math.sqrt(p2R * p2R - p2H * p2H) / p2R));

        arc2 = arc2Initial + orientationOffset;

        //注意p2点的坐标
        if ((waveY - 1 / 2f * height) > 0) {

            p2x = (float) (centerX + p2R
                    * (float) Math.cos(arc2 * Math.PI / 180) + (float) rightOffset
                    * Math.sin((orientationOffset + 90) * Math.PI / 180) / 1.5f);

            p2y = (float) (centerY + p2R
                    * (float) Math.sin(arc2 * Math.PI / 180) + (float) rightOffset
                    * Math.sin((orientationOffset) * Math.PI / 180) / 1.5f);
        } else {

            arc2Initial = (float) Math.toDegrees(Math.acos(p2H / p2R));

            arc2 = arc2Initial + orientationOffset;

            p2x = (float) (centerX + p2R
                    * (float) Math.sin(arc2 * Math.PI / 180) + (float) rightOffset
                    * Math.sin((orientationOffset + 90) * Math.PI / 180) / 1.5f);

            p2y = (float) (centerY - p2R
                    * (float) Math.cos(arc2 * Math.PI / 180) + (float) rightOffset
                    * Math.sin((orientationOffset) * Math.PI / 180) / 1.5f);

        }

        //计算p3坐标
        arc3Initial = (float) Math.toDegrees(Math.acos(Math.sqrt(p3R * p3R - p3H * p3H) / p3R));

        arc3 = arc3Initial + orientationOffset;

        if ((waveY - 1 / 2f * height) >= 0) {
            p3x = centerX + p3R * (float) Math.cos(arc3 * Math.PI / 180);
            p3y = centerY + p3R * (float) Math.sin(arc3 * Math.PI / 180);
        } else {
            arc3Initial = (float) Math.toDegrees(Math.acos(p3H / p3R));
            arc3 = arc3Initial + orientationOffset;
            p3x = centerX + p3R * (float) Math.sin(arc3 * Math.PI / 180);
            p3y = centerY - p3R * (float) Math.cos(arc3 * Math.PI / 180);
        }


        //计算闭合坐标
        closeR = (float) Math.sqrt(1 / 2f * width * 1 / 2f * width + 1 / 2f* height * 1 / 2f * height);

        arcClose = 45f + orientationOffset;

        pClose1x = centerX + closeR
                * (float) Math.cos(arcClose * Math.PI / 180);
        pClose1y = centerY + closeR
                * (float) Math.sin(arcClose * Math.PI / 180);

        pClose2x = centerX - closeR
                * (float) Math.sin(arcClose * Math.PI / 180);
        pClose2y = centerY + closeR
                * (float) Math.cos(arcClose * Math.PI / 180);


    }
    int maxWidthNum;
    public void intRightOffset() {
        //   int maxWidthNum = (int)width;
        maxWidthNum =(int) (width *Math.sin((waveYPercent*180)* Math.PI / 180));
        aArray = new Float[maxWidthNum];
        Float min = -maxWidthNum/4f;
        Float max = maxWidthNum/4f;
        int halfWidth = (int)(maxWidthNum/2);
        for (int i = 0; i < halfWidth; i++) {
            aArray[i] = min;
            min = min + 1f;
        }

        for (int i = halfWidth; i < maxWidthNum; i++) {
            aArray[i] = max;
            max = max - 1f;

        }
    }


    boolean isRun = true;
    int i = 0;
    int sleepTime = 5;

    class WaveThread extends Thread {

        @Override
        public void run() {
            while (isRun) {
                if(null==aArray){
                    return;
                }
                if (i >= maxWidthNum) {
                    i = 0;
                }
                rightOffset = aArray[i];
                i++;


                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = (int) this.width;
        int height = (int) this.height;
        if( widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST ){
            setMeasuredDimension(width, height);
        }else if( widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(width, heightSize);
        }else if( heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize, height);
        }else{
            setMeasuredDimension(widthSize, heightSize);
        }
    }


}
