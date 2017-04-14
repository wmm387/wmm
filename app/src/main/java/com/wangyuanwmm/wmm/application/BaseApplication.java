package com.wangyuanwmm.wmm.application;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.wangyuanwmm.wmm.utils.StaticClass;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class BaseApplication extends Application {

    //创建
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);

        // the 'theme' has two values, 0 and 1
        // 0 --> day theme, 1 --> night theme
        if (getSharedPreferences("user_settings",MODE_PRIVATE).getInt("theme", 0) == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
