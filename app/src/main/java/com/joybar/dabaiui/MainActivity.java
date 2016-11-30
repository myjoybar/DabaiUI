package com.joybar.dabaiui;

import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.joybar.dabaiui.view.WaveBallView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static String TAG = "MainActivity";
    private FrameLayout frameWaveBallView;
    private WaveBallView waveBallView;
    private DrawerLayout drawerLayout;
    private boolean isOpen = false;

    private int initialBallLeftMargin = 0;
    private int waveBallWidth = 0;

    // 定义Sensor管理器
    private SensorManager sensorManager;
    private int SENSITIVITY = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSensor();
        initPosition();
        initListener();

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

    private void initView() {
        waveBallView = (WaveBallView) this.findViewById(R.id.wave_ball_view);
        frameWaveBallView = (FrameLayout) findViewById(R.id.frame_wave_ball_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

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
                setWaveZOffset(waveBallView, (float) -angle, 0.10f, 0.60f);
                break;
        }


    }


    private void setWaveZOffset(WaveBallView waveBallView, float orientationOffset,float heightPercent,float yPercent){
        waveBallView.setOrientationOffset(orientationOffset);
        waveBallView.setWaveYPercent(yPercent);
        waveBallView.setWaveHeightPercent(heightPercent);
        waveBallView.postInvalidate();
    }
}
