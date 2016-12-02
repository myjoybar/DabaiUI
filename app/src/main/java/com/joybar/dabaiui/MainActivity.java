package com.joybar.dabaiui;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.joybar.dabaiui.base.BaseActivity;
import com.joybar.dabaiui.view.WaveBallView;

public class MainActivity extends BaseActivity implements SensorEventListener {

    private static String TAG = "MainActivity";
    private static int REQUEST_BUBBLE_CODE = 10;
    private FrameLayout frameWaveBallView;
    private WaveBallView waveBallView;
    private DrawerLayout drawerLayout;
    private boolean isOpen = false;

    private int initialBallLeftMargin = 0;
    private int waveBallWidth = 0;

    // 定义Sensor管理器
    private SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSensor();
        initPosition();
        initListener();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogActivity.launch(MainActivity.this,0.10f,0.60f,REQUEST_BUBBLE_CODE);

            }
        },1*1000);

    }
    @Override
    public void onResume() {
        super.onResume();
        // 注册/监听方向传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        // 取消方向传感器的监听
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        // 取消方向传感器的监听
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_BUBBLE_CODE){
            Bundle b=data.getExtras();
            float yPercent=b.getFloat(DialogActivity.INTENT_EXTRA_Y_PERCENT);
            waveBallView.setWaveYPercent(yPercent);
            waveBallView.setWaveHeightPercent(0.10f);
            waveBallView.postInvalidate();
        }
    }

    private void initView() {
        waveBallView = (WaveBallView) this.findViewById(R.id.wave_ball_view);
        frameWaveBallView = (FrameLayout) findViewById(R.id.frame_wave_ball_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initToolbar((Toolbar) findViewById(R.id.toolbar), false);
    }
    private void initSensor() {
        // 获取手机传感器管理服务
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    private void initPosition() {

        waveBallView.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) frameWaveBallView.getLayoutParams();
                initialBallLeftMargin = lp.leftMargin;
                waveBallWidth = waveBallView.getWidth();

            }
        });
    }

    private void initListener() {

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                ObjectAnimator animator = null;
                int drawerViewWidth = drawerView.getWidth();
                if (!isOpen) {
                  if(drawerView.getX()+drawerViewWidth>initialBallLeftMargin+waveBallWidth/2){
                      animator = ObjectAnimator.ofFloat(frameWaveBallView,
                              "translationX", frameWaveBallView.getX(),drawerView.getX()+drawerViewWidth-waveBallWidth/2-initialBallLeftMargin
                      );
                      animator.setDuration(0);
                      animator.start();
                  }
                }else{
                    if(drawerView.getX()+drawerViewWidth>initialBallLeftMargin+waveBallWidth/2){
                        animator = ObjectAnimator.ofFloat(frameWaveBallView,
                                "translationX", frameWaveBallView.getX(),drawerView.getX()+drawerViewWidth-waveBallWidth/2-initialBallLeftMargin);
                        animator.setDuration(0);
                        animator.start();
                    }
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        waveBallView.setWaveYPercent(0.60f);
        waveBallView.setWaveHeightPercent(0.10f);
        waveBallView.setSensorEvent(sensorEvent);

    }


}
