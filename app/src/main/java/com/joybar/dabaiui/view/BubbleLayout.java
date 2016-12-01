package com.joybar.dabaiui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by joybar on 01/12/16.
 */
public class BubbleLayout extends View {

    public static List<Bubble> bubbles = new ArrayList<Bubble>();
    private Random random = new Random();// 生成随机数
    private int width, height;
    private boolean starting = false;
    public static boolean stopAddBubble = false;
    private PorterDuffXfermode porterDuffXfermode;// Xfermode
    private Canvas mCanvas;// 在该画布上绘制目标图片//
    private Bitmap bg;// 目标图片

    public BubbleLayout(Context context) {
        this(context,null);
    }

    public BubbleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        stopAddBubble = false;
        new MyThread().start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        Paint paint = new Paint();
     // 绘制气泡
        paint.reset();
        paint.setColor(Color.parseColor("#CD302F"));// 灰白色
        paint.setAlpha(200);// 设置不透明度：透明为0，完全不透明为255
        List<Bubble> list = new ArrayList<Bubble>(bubbles);
        // 依次绘制气泡
        for (Bubble bubble : list) {
            // 碰到上边界从数组中移除
           // if (bubble.getY() - MyApplication.getWaveBallHeight() / 2 <= 0) {
            if (bubble.getY() - 118 <= 0) {
                bubbles.remove(bubble);
            }
            // 碰到左边界从数组中移除
            else if (bubble.getX() - bubble.getRadius() <= 0) {
                bubbles.remove(bubble);
            }
            // 碰到右边界从数组中移除
            else if (bubble.getX() + bubble.getRadius() >= width) {
                bubbles.remove(bubble);
            } else {
                int i = bubbles.indexOf(bubble);
                if (bubble.getX() + bubble.getSpeedX() <= bubble.getRadius()) {
                    bubble.setX(bubble.getRadius());
                } else if (bubble.getX() + bubble.getSpeedX() >= width
                        - bubble.getRadius()) {
                    bubble.setX(width - bubble.getRadius());
                } else {
                    bubble.setX(bubble.getX() + bubble.getSpeedX());
                }
                bubble.setY(bubble.getY() - bubble.getSpeedY());

                bubbles.set(i, bubble);

                //连续画两个圆也是可以的
                paint.setColor(Color.parseColor("#FFFFFF"));// 白色
                canvas.drawCircle(bubble.getX(), bubble.getY(),
                        bubble.getRadius()+2, paint);
                //连续画两个圆也是可以的
                paint.setColor(Color.parseColor("#CD302F"));//红色
                canvas.drawCircle(bubble.getX(), bubble.getY(),
                        bubble.getRadius(), paint);
            }
        }
        // 刷新屏幕
        postInvalidate();
    }



    class MyThread extends Thread {

        private boolean stop = false;
        public void setStop() {
            this.stop = true;
        }
        public void run() {
            while (!stopAddBubble) {
                try {
                    Thread.sleep(300);// 0,1,2
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Bubble bubble = new Bubble();
                int radius = random.nextInt(5) + 15;
                while (radius == 0) {
                    radius = random.nextInt(15);
                }
                float speedY = 0.6f;
                while (speedY < 1) {
                    speedY = random.nextFloat() * 5;
                }
                bubble.setRadius(radius);
                bubble.setSpeedY(29.8f);
                bubble.setX(width / 2);
                bubble.setY(height);
                float speedX = random.nextFloat() - 0.5f;
                while (speedX == 0) {
                    speedX = random.nextFloat() - 0.5f;
                }
                bubble.setSpeedX(-11.8f);

                bubbles.add(bubble);
            }
        };

    }

    private class Bubble {

        private float radius;

        private float speedY;

        private float speedX;

        private float x;

        private float y;


        public float getRadius() {
            radius = radius-0.05f; //实现上升逐渐减小效果
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getSpeedY() {
            return speedY;
        }

        public void setSpeedY(float speedY) {
            this.speedY = speedY;
        }

        public float getSpeedX() {
            return speedX;
        }

        public void setSpeedX(float speedX) {
            this.speedX = speedX;
        }

    }


}
