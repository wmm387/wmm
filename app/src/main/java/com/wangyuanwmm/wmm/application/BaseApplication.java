package com.wangyuanwmm.wmm.application;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.wangyuanwmm.wmm.utils.StaticClass;

import cn.bmob.v3.Bmob;

//Appliacation基类

public class BaseApplication extends Application {

    //创建
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);

        // 切换主题，theme=0是默认主题，theme不等于0是暗色主题
        if (getSharedPreferences("user_settings",MODE_PRIVATE).getInt("theme", 0) == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
