package com.example.mywallet.services;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Application service.
 */
public class ApplicationService extends Application {

    /* *** General variables *** */
    private static ApplicationService applicationServiceInstance;
    private static Context appContext = getAppContext();
    private static Thread refreshThread;

    /**
     * Gets wideget ids.
     *
     * @return the wideget ids
     */
    public static List<Integer> getWidegetIds() {
        return widegetIds;
    }

    /**
     * Sets wideget ids.
     *
     * @param widegetIds the wideget ids
     */
    public static void setWidegetIds(List<Integer> widegetIds) {
        ApplicationService.widegetIds = widegetIds;
    }

    private static List<Integer> widegetIds = new ArrayList<>();

    /**
     * Gets refresh thread.
     *
     * @return the refresh thread
     */
    public static Thread getRefreshThread() {
        return refreshThread;
    }

    /**
     * Sets refresh thread.
     *
     * @param refreshThread the refresh thread
     */
    public static void setRefreshThread(Thread refreshThread) {
        ApplicationService.refreshThread = refreshThread;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationServiceInstance = this;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ApplicationService getInstance() {
        return applicationServiceInstance;
    }

    /**
     * Gets app context.
     *
     * @return the app context
     */
    public static Context getAppContext() {
        return appContext;
    }

    /**
     * Sets app context.
     *
     * @param context the context
     */
    public static void setAppContext(Context context) {
        appContext = context;
    }

}
