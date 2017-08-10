package com.baway.matianye.recyclerview;

import android.app.Application;

import org.xutils.*;
import org.xutils.BuildConfig;

/**
 * Created by matianye .
 * on 2017/8/10:20.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
