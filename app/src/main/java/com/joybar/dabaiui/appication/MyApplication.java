package com.joybar.dabaiui.appication;

import android.app.Application;
import android.content.Context;

/**
 * Created by joybar on 01/12/16.
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
