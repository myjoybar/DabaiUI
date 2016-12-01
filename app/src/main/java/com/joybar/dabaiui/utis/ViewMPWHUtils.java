package com.joybar.dabaiui.utis;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;

/**
 * Created by joybar on 01/12/16.
 */
public class ViewMPWHUtils {
        public static String WIDTH = "width";
        public static String HEIGHT = "height";
        public static String LEFT = "left";
        public static String TOP = "top";
        public static String RIGHT = "right";
        public static String BOTTOM = "bottom";

        public static boolean hasMeasure = false;// 是否已经测量
        private static int mWidth;
        private static int mHeight;
        private static int mLeft;
        private static int mTop;
        private static int mRight;
        private static int mBottom;

        private static IMeasureResult iMeasureResult;

        public static boolean isHasMeasure() {
            return hasMeasure;
        }

        public static void setHasMeasure(boolean mHasMeasure) {
            hasMeasure = mHasMeasure;
        }

        public static void setMeasureResult(IMeasureResult imeasureResult) {
            iMeasureResult = imeasureResult;
        }

        // 动态设置Margin值
        public static void setMargins(View v, int l, int t, int r, int b) {
            if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v
                        .getLayoutParams();
                p.setMargins(l, t, r, b);

                v.requestLayout();
            }
        }

        /**
         * TODO<动态设置高宽值>
         *
         * @param v
         * @param h
         *            ,为0表示高度不变
         * @param w
         *            ,为0表示宽度不变
         * @throw
         * @return void
         */
        public static void setHeightWidth(View v, int h, int w) {
            if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v
                        .getLayoutParams();
                if (h != 0) {
                    lp.height = h;
                }

                if (w != 0) {
                    lp.width = w;
                }

                v.setLayoutParams(lp);

            }
        }
        /**
         *
         * TODO<针对ViewGroup>
         * @param v
         * @param h
         * @param w
         * @throw
         * @return void
         */
        public static void setHeightWidth(ViewGroup v, int h, int w) {
            if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v
                        .getLayoutParams();
                if (h != 0) {
                    lp.height = h;
                }

                if (w != 0) {
                    lp.width = w;
                }

                v.setLayoutParams(lp);

            }
        }



        public static void setHeightWidthWrap(ViewGroup v, int h, int w) {
            if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v
                        .getLayoutParams();
                if (h != 0) {
                    lp.height = lp.WRAP_CONTENT;
                }

                if (w != 0) {
                    lp.width = lp.WRAP_CONTENT;;
                }

                v.setLayoutParams(lp);

            }
        }


        // 使用前调用setHasMeasure
        public static void getMeasureNumber(final View v, final String type) {

            ViewTreeObserver vto = v.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @SuppressLint("NewApi")
                public boolean onPreDraw() {
                    if (hasMeasure == false) {
                        hasMeasure = true;

                        mWidth = v.getMeasuredWidth();
                        mHeight = v.getMeasuredHeight();

                        mLeft = v.getLeft();
                        mTop = v.getTop();

                        mRight = v.getRight();
                        mBottom = v.getBottom();

                        if (type.equals(WIDTH)) {
                            iMeasureResult.onMeasureResult(WIDTH, mWidth);

                        } else if (type.equals(HEIGHT)) {

                            iMeasureResult.onMeasureResult(HEIGHT, mHeight);
                        } else if (type.equals(LEFT)) {

                            iMeasureResult.onMeasureResult(LEFT, mLeft);
                        } else if (type.equals(TOP)) {

                            iMeasureResult.onMeasureResult(TOP, mTop);
                        } else if (type.equals(RIGHT)) {

                            iMeasureResult.onMeasureResult(RIGHT, mRight);
                        } else if (type.equals(BOTTOM)) {

                            iMeasureResult.onMeasureResult(BOTTOM, mBottom);
                        }

                    }

                    return true;
                }
            });

        }


/** 用于测量结果的回调 */
public interface IMeasureResult {

    public void onMeasureResult(String type, int value);

}


    /** 获取控件尺寸 */
    public static void getMeasureNumber1(final View v, final String type) {

        ViewTreeObserver vto = v.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                // 移除上一次监听，避免重复监听
                v.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                mWidth = v.getMeasuredWidth();
                mHeight = v.getMeasuredHeight();

                mLeft = v.getLeft();
                mTop = v.getTop();

                mRight = v.getRight();
                mBottom = v.getBottom();

                if (type.equals(WIDTH)) {
                    iMeasureResult.onMeasureResult(WIDTH, mWidth);

                } else if (type.equals(HEIGHT)) {

                    iMeasureResult.onMeasureResult(HEIGHT, mHeight);
                } else if (type.equals(LEFT)) {

                    iMeasureResult.onMeasureResult(LEFT, mLeft);
                } else if (type.equals(TOP)) {

                    iMeasureResult.onMeasureResult(TOP, mTop);
                } else if (type.equals(RIGHT)) {

                    iMeasureResult.onMeasureResult(RIGHT, mRight);
                } else if (type.equals(BOTTOM)) {

                    iMeasureResult.onMeasureResult(BOTTOM, mBottom);
                }

            }
        });

    }

    /** 把自身从父View中移除 */
    public static void removeSelfFromParent(View view) {
        // 先找到父类，再通过父类移除孩子
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }
}

