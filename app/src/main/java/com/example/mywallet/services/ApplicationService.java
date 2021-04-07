package com.example.mywallet.services;

import android.app.Application;
import android.content.Context;

import com.example.mywallet.utils.UpdateUi;

import java.util.ArrayList;
import java.util.List;

public class ApplicationService extends Application {

    /* *** General variables *** */
    private static ApplicationService applicationServiceInstance;
    private static Context appContext = getAppContext();
    private static Thread refreshThread;

    public static List<Integer> getWidegetIds() {
        return widegetIds;
    }

    public static void setWidegetIds(List<Integer> widegetIds) {
        ApplicationService.widegetIds = widegetIds;
    }

    private static List<Integer> widegetIds = new ArrayList<>();

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
