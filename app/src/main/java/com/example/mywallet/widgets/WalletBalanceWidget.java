package com.example.mywallet.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.example.mywallet.R;
import com.example.mywallet.models.CurrencyBalance;
import com.example.mywallet.services.ApplicationService;
import com.example.mywallet.utils.AppWidgetAlarm;
import com.example.mywallet.utils.UpdateUi;

import java.text.DecimalFormat;

/**
 * Implementation of App Widget functionality.
 */
public class WalletBalanceWidget extends AppWidgetProvider {

    public static final String ACTION_AUTO_UPDATE = "AUTO_UPDATE";

    private static RemoteViews viewsMagic;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wallet_value);
        viewsMagic = views;

        updateTotalWallet(views);
        ApplicationService.getWidegetIds().add(appWidgetId);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wallet_value);
        updateTotalWallet(views);
    }

    @Override
    public void onEnabled(Context context) {
        // start alarm
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.startAlarm();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction().equals(ACTION_AUTO_UPDATE))
        {
            System.out.println("WORKS");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wallet_value);
            updateTotalWallet(views);
        }
    }

    @Override
    public void onDisabled(Context context) {
        // stop alarm only if all widgets have been disabled
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidgetComponentName = new ComponentName(context.getPackageName(),getClass().getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
        if (appWidgetIds.length == 0) {
            // stop alarm
            AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
            appWidgetAlarm.stopAlarm();
        }
    }

    public static void updateTotalWallet(RemoteViews views) {
        if (UpdateUi.getBalances() != null) {
            double totalWallet = 0;
            for (CurrencyBalance balance : UpdateUi.getBalances()) totalWallet += balance.getCurrentBalanceConverted();
            views.setTextViewText(R.id.appwidget_text, new DecimalFormat("#.##").format(totalWallet));
        }
    }

    public static RemoteViews getViewsMagic() {
        return viewsMagic;
    }

}