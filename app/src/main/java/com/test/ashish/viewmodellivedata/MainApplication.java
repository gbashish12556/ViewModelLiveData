package com.test.ashish.viewmodellivedata;

import android.app.Application;
import android.content.Context;

//import com.squareup.leakcanary.AndroidDebuggerControl;
//import com.squareup.leakcanary.AndroidHeapDumper;
//import com.squareup.leakcanary.AndroidWatchExecutor;
//import com.squareup.leakcanary.GcTrigger;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
    }
}