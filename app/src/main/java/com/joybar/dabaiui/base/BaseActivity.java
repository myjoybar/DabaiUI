package com.joybar.dabaiui.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Created by joybar on 4/19/16.
 */
public class BaseActivity extends ToolbarActivity {

    public static String TAG;
    public Context mContext;
    public static int TIME_DELAY = 200;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        mContext = this;



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
