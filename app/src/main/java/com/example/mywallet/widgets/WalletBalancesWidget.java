package com.example.mywallet.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.mywallet.R;
import com.example.mywallet.models.CurrencyBalance;
import com.example.mywallet.adapters.WidgetAdapter;
import com.example.mywallet.utils.Currency;
import com.example.mywallet.utils.UpdateUi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */
public class WalletBalancesWidget extends AppWidgetProvider {

    private static final String LIST_ITEM_CLICKED_ACTION = "LIST_ITEM_CLICKED_ACTION";
    private static final String REFRESH_WIDGET_ACTION = "REFRESH_WIDGET_ACTION";
    public static final String EXTRA_CLICKED_FILE = "EXTRA_CLICKED_FILE";

    public static final String ACTION_WIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wallet_balances_widget);
        // views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.wallet_balances_widget);

            // The empty view is displayed when the collection has no items. It should be a sibling
            // of the collection view:
            rv.setEmptyView(R.id.widget_list, R.id.empty_view);

            double totalBalance = 0;
            for (CurrencyBalance currencyBalance : UpdateUi.getBalances()) {
                totalBalance += currencyBalance.getCurrentBalanceConverted();
            }

            rv.setTextViewText(R.id.total_wallet, new DecimalFormat("#.##").format(totalBalance));
            rv.setTextViewText(R.id.unit_total_wallet, Currency.valueOf(UpdateUi.getSelectedConvertCurrency()).getSymbol());
            rv.setTextViewText(R.id.last_update_title, new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis()));

            // Setup intent which points to the WidgetService which will provide the views for this collection.
            Intent intent = new Intent(context, WidgetAdapter.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            // When intents are compared, the extras are ignored, so we need to embed the extras
            // into the data so that the extras will not be ignored.
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            rv.setRemoteAdapter(R.id.widget_list, intent);

            // Setup refresh button:
            Intent refreshIntent = new Intent(context, WalletBalancesWidget.class);
            refreshIntent.setAction(WalletBalancesWidget.REFRESH_WIDGET_ACTION);
            refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            refreshIntent.setData(Uri.parse(refreshIntent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.refresh_button, refreshPendingIntent);

            // Here we setup the a pending intent template. Individuals items of a collection
            // cannot setup their own pending intents, instead, the collection as a whole can
            // setup a pending intent template, and the individual items can set a fillInIntent
            // to create unique before on an item to item basis.
            Intent toastIntent = new Intent(context, WalletBalancesWidget.class);
            toastIntent.setAction(WalletBalancesWidget.LIST_ITEM_CLICKED_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.widget_list, toastPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        switch (intent.getAction()) {
            case LIST_ITEM_CLICKED_ACTION:
                String clickedFilePath = intent.getStringExtra(EXTRA_CLICKED_FILE);
                Toast toast = Toast.makeText(context, "Currency click : " + clickedFilePath, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            case REFRESH_WIDGET_ACTION:
                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);

                double totalBalance = 0;
                for (CurrencyBalance currencyBalance : UpdateUi.getBalances()) {
                    totalBalance += currencyBalance.getCurrentBalanceConverted();
                }

                RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.wallet_balances_widget);

                rv.setTextViewText(R.id.total_wallet, new DecimalFormat("#.##").format(totalBalance));
                rv.setTextViewText(R.id.unit_total_wallet, Currency.valueOf(UpdateUi.getSelectedConvertCurrency()).getSymbol());
                rv.setTextViewText(R.id.last_update_title, new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis()));

                ComponentName cn = new ComponentName(context, WalletBalancesWidget.class);
                AppWidgetManager.getInstance(context).updateAppWidget(cn, rv);

                Toast toast2 = Toast.makeText(context, "refresh balances ...", Toast.LENGTH_SHORT);
                toast2.setGravity(Gravity.CENTER, 0, 0);
                toast2.show();
                break;
            case ACTION_WIDGET_UPDATE:
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WalletBalancesWidget.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
                break;
        }
    }

}