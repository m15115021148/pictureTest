package com.geek.imagedemo;

import android.app.Application;

import org.xutils.*;

/**
 * Created by Administrator on 2016/12/29.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initXutils();
    }

    /**
     * 初始化xutils框架
     */
    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(org.xutils.BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
