package com.joybar.dabaiui;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joybar.dabaiui.appication.MyApplication;
import com.joybar.dabaiui.base.BaseActivity;
import com.joybar.dabaiui.utis.ScreenUtils;
import com.joybar.dabaiui.utis.ViewMPWHUtils;
import com.joybar.dabaiui.view.BubbleLayout;
import com.joybar.dabaiui.view.WaveBallView;

/**
 * Created by joybar on 01/12/16.
 */
public class DialogActivity extends BaseActivity implements SensorEventListener, View.OnClickListener {

    private static String TAG = "DialogActivity";
    private static String INTENT_EXTRA_HEIGHT_PERCENT = "heightPercent";
    private static String INTENT_EXTRA_Y_PERCENT = "yPercent";

    private float waveYPercent = 0.5f;//波浪高度所占背景的百分比；0.0-1.0之间
    private float waveHeightPercent = 0.1f;//波幅所占整个高度的百分比；0.0-0.12之间,最大为0.12，否则会失真


    private SensorManager sensorManager;
    private Sensor sensor_orientation;// 方向
    private TextView tv_lv, tv_lv_points_need, tv_lv_points_gain;
    private LinearLayout lin_click, layout_tips;
    private BubbleLayout bubbleLayout;
    private WaveBallView waveBallView;


    public static void launch(Context context, float heightPercent, float yPercent) {
        Intent intent = new Intent(context, DialogActivity.class);
        intent.putExtra(INTENT_EXTRA_HEIGHT_PERCENT, heightPercent);
        intent.putExtra(INTENT_EXTRA_Y_PERCENT, yPercent);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_custom_game_points);
        initView();
        initData();
        setListener();
        fillData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, sensor_orientation,
                SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacksAndMessages(null);
        sensorManager.unregisterListener(this, sensor_orientation);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.finish();
            // 结束动画
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }
        return false;
    }

    // 初始化部分————————————————————————————————————————————————————————————————————————————————————————————————————————————————

    protected void initView() {
        tv_lv = (TextView) findViewById(R.id.tv_lv);
        tv_lv_points_need = (TextView) findViewById(R.id.tv_lv_points_need);
        tv_lv_points_gain = (TextView) findViewById(R.id.tv_lv_points_gain);


        lin_click = (LinearLayout) findViewById(R.id.lin_click);
        layout_tips = (LinearLayout) findViewById(R.id.layout_tips);
        bubbleLayout = (BubbleLayout) findViewById(R.id.bubble_view);
        waveBallView = (WaveBallView) findViewById(R.id.wave_ball_view);
        initToolbar((Toolbar) findViewById(R.id.toolbar), false);
    }

    protected void initData() {
        Intent intent = getIntent();
        waveHeightPercent = intent.getFloatExtra(INTENT_EXTRA_HEIGHT_PERCENT, 0.10f);
        waveYPercent = intent.getFloatExtra(INTENT_EXTRA_Y_PERCENT, 0.50f);

        tv_lv.setText("LV6");
        tv_lv_points_need.setText("升级还需50" + "大白点数");
        tv_lv_points_gain.setText("+" + 80);


        measureBottom();
        bubbleLayout.setVisibility(View.INVISIBLE);
        waveBallView.setWaveYPercent(waveYPercent);
        waveBallView.setWaveHeightPercent(waveHeightPercent);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor_orientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);


    }

    protected void setListener() {
        lin_click.setOnClickListener(this);

    }

    protected void fillData() {

    }


    // 测量
    public void measureBottom() {

        tv_lv_points_gain.post(new Runnable() {
            @Override
            public void run() {
                int bottomDis = (ScreenUtils.getScreenHeight(MyApplication.getContext()) - layout_tips.getHeight() - findViewById(R.id.toolbar).getHeight() - ScreenUtils.getStatusHeight(MyApplication.getContext())) / 2;
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) waveBallView.getLayoutParams();
                int waveMarginTop = lp.topMargin;
                int waveMarginLeft = lp.leftMargin;

                int bubbleLayoutWith = ScreenUtils.getScreenWidth(MyApplication.getContext()) - waveMarginLeft * 2 - waveBallView.getWidth();
                int bubbleLayoutHeight = ScreenUtils.getScreenHeight(MyApplication.getContext()) - ScreenUtils.getStatusHeight(MyApplication.getContext()) - findViewById(R.id.toolbar).getHeight() - bottomDis - waveBallView.getHeight() / 2 - waveMarginTop;

                int bubbleLayoutMarginTop = waveMarginTop + waveBallView.getHeight() / 2;
                int bubbleLayoutMarginBottom = bottomDis;
                int bubbleLayoutMarginLeft = waveMarginLeft + waveBallView.getWidth() / 2;
                int bubbleLayoutMarginRight = waveMarginLeft + waveBallView.getWidth() / 2;

                Log.d(TAG, "bubbleLayoutMarginLeft=" + bubbleLayoutMarginLeft);
                ViewMPWHUtils.setHeightWidth(bubbleLayout,
                        bubbleLayoutHeight,
                        ScreenUtils.getScreenWidth(mContext) - bubbleLayoutMarginLeft * 2);
                ViewMPWHUtils.setMargins(bubbleLayout, bubbleLayoutMarginLeft,
                        bubbleLayoutMarginTop, bubbleLayoutMarginRight, bubbleLayoutMarginBottom);

            }
        });

    }


    private static Handler handler = new Handler();


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub

            if (waveBallView.getWaveYPercent() > 0.85f) {
                handler.removeCallbacksAndMessages(null);
                handler.removeCallbacks(this);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            } else {
                waveBallView.setWaveYPercent(waveBallView.getWaveYPercent() + 0.004f);
                waveBallView.setWaveHeightPercent(waveHeightPercent);
                waveBallView.postInvalidate();
                handler.postDelayed(this, 100);
            }

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_click:
                lin_click.setClickable(false);
                bubbleLayout.setVisibility(View.VISIBLE);
                bubbleLayout.run();
                bubbleLayout.setFinishListener(new BubbleLayout.FinishListener() {
                    @Override
                    public void bubbleFinish() {
                        handler.removeCallbacksAndMessages(null);
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                });
                handler.postDelayed(runnable, 3500);
                break;

            default:
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        waveBallView.setSensorEvent(sensorEvent);
    }

}
