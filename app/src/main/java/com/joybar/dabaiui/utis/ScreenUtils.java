package com.joybar.dabaiui.utis;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.joybar.dabaiui.appication.MyApplication;

/**
 * Created by joybar on 01/12/16.
 */
public class ScreenUtils {

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    // 转换dip为px
    public static int convertDipOrPx(int dip) {
        float scale = MyApplication.getContext().getResources()
                .getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    // 转换px为dip
    public static int convertPxOrDip(int px) {
        float scale = MyApplication.getContext().getResources()
                .getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }
}
