package com.joybar.dabaiui.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by joybar on 4/18/16.
 */
public class ToolbarActivity extends AppCompatActivity {

    protected void initToolbar(Toolbar toolbar, boolean showBack) {
        setSupportActionBar(toolbar);
        if (showBack && null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!handleNvgOnClickListener()) {
                    superBack();
                }

            }
        });
    }

    /**
     * 是否处理返回按钮点击事迹
     *
     * @return 是否处理
     */
    protected boolean handleNvgOnClickListener() {
        return false;
    }

    protected void superBack() {
        super.onBackPressed();
    }


}
