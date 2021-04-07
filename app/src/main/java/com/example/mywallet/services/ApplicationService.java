package com.example.mywallet.services;

import android.app.Application;
import android.content.Context;

public class ApplicationService extends Application {

    /* *** General variables *** */
    private static ApplicationService applicationServiceInstance;
    private static Context appContext;
    private static Thread refreshThread;

    public static Thread getRefreshThread() {
        return refreshThread;
    }

    public static void setRefreshThread(Thread refreshThread) {
        ApplicationService.refreshThread = refreshThread;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationServiceInstance = this;
    }

    public static ApplicationService getInstance() {
        return applicationServiceInstance;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context context) {
        appContext = context;
    }

}
