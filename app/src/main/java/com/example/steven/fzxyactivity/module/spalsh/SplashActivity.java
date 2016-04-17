package com.example.steven.fzxyactivity.module.spalsh;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.steven.fzxyactivity.App;
import com.example.steven.fzxyactivity.Constant.AppConstants;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.SpUtils;
import com.example.steven.fzxyactivity.module.BaseActivity;
import com.example.steven.fzxyactivity.module.main.View.Fragment.Activity.BottomMainActivity;
import com.example.steven.fzxyactivity.module.main.View.Fragment.Activity.MainActivity;
import com.example.steven.fzxyactivity.module.user.LoginActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断是否是第一次开启应用
        boolean isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);
    }

    private void enterHomeActivity() {
        //userId不为空进入主界面否则进入登录界面
        if (!TextUtils.isEmpty(SpUtils.getString(App.getApp(),"userId"))){
            Intent intent = new Intent(this, BottomMainActivity.class);
            startActivity(intent);
        }else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();

    }
}
