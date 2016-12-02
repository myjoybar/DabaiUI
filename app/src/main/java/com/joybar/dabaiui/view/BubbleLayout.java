package com.joybar.dabaiui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by joybar on 01/12/16.
 */
public class BubbleLayout extends View {

    public List<Bubble> bubbles = new ArrayList<Bubble>();
    private Random random = new Random();
    private int width, height;
    private boolean start = false;
    private float valueAnimatorTime;
    private  FinishListener finishListener;

    public BubbleLayout(Context context) {
        this(context, null);
    }

    public BubbleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {

    }


    public float getValueAnimatorTime() {
        return valueAnimatorTime;
    }

    public void setFinishListener(FinishListener finishListener) {
        this.finishListener = finishListener;
    }

    public void run() {
        if (!start) {
            createBubbles();
            start = true;
        }
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

        for (Bubble bubble : bubbles) {
            //左边上边的边界
            if (bubble.getY() - bubble.getRadius() >= 6&& bubble.getX() - bubble.getRadius() >= 6 ) {
                //底部边界
                if(bubble.getY()+bubble.getRadius()+6<=height){
                    //连续画两个圆
                    paint.setColor(Color.parseColor("#FFFFFF"));// 白色
                    canvas.drawCircle(bubble.getX(), bubble.getY(),
                            bubble.getRadius() + 6, paint);
                    paint.setColor(Color.parseColor("#CD302F"));//红色
                    canvas.drawCircle(bubble.getX(), bubble.getY(),
                            bubble.getRadius(), paint);
                }

            }
        }
        postInvalidate();
    }


    private void createBubbles() {
        for (int i = 0; i < 30; i++) {
            Bubble bubble = new Bubble();
            int radius = random.nextInt(5) + 15;
            bubble.setRadius(radius);
            bubble.setX(width / 2);
            bubble.setY(height);
            bubble.setDelays(100 + 200 * i);
            //生产小球
            bubbles.add(bubble);
            //生产动画
            createAnimation(bubble, i);
        }
    }


    private void createAnimation(Bubble bubble, int i) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(4 * 1000);
        valueAnimator.setRepeatCount(0);
        valueAnimator.setStartDelay(bubble.getDelays());
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new BubbleAnimatorUpdateListener(bubble, i));
        valueAnimator.start();
    }

    class BubbleAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {


        private Bubble bubble;
        private int index;

        public BubbleAnimatorUpdateListener(Bubble bubble, int i) {
            this.bubble = bubble;
            this.index = i;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {

            float t = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            float px1 = width / 2 / 2;
            float px2 = width / 2 / 3;
            float px3 = width / 2 / 4;
            float px4 = width / 2 / 5;

            float py1 = height / 2;
            float py2 = height / 3;
            float py3 = height / 4;
            float py4 = height / 3;
//            int index_X = random.nextInt(3) + 2;//2-4
//            int index_Y = random.nextInt(3) + 2;//2-4
            int index_X = 1;
            int index_Y = 2;

            float moveX = (float) (px1 * Math.pow((t), index_X + 1))
                    + (float) (px2 * Math.pow((t), index_X + 2)) +
                    +(float) (px3 * Math.pow((t), index_X + 3))
                    + (float) (px4 * Math.pow((t), index_X + 4));

            float moveY = (float) (py1 * Math.pow((t), index_Y + 1))
                    + (float) (py2 * Math.pow((t), index_Y + 2)) +
                    +(float) (py3 * Math.pow((t), index_Y + 3))
                    + (float) (py4 * Math.pow((t), index_Y + 4));

            float x = width / 2 - moveX;
            float y = height - moveY ;

            bubble.setX(x);
            bubble.setY(y);

            bubble.setRadius(bubble.getRadius() - 0.05f);//实现上升逐渐减小效果

            if (1f == ((Float) valueAnimator.getAnimatedValue()).floatValue()) {
                bubbles.remove(bubble);
            }
            if(bubbles.size()==0){
                if(null!=finishListener){
                    finishListener.bubbleFinish();
                }
            }
            invalidate();
        }
    }


    private class Bubble {

        private float radius;

        private float x;

        private float y;

        int delays;//启动延迟时间


        public float getRadius() {
//            radius = radius - 0.05f; //实现上升逐渐减小效果
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


        public int getDelays() {
            return delays;
        }

        public void setDelays(int delays) {
            this.delays = delays;
        }
    }

   public interface FinishListener{
       void bubbleFinish();
   }


}
