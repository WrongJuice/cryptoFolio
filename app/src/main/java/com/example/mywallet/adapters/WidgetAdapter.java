package com.example.mywallet.adapters;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.mywallet.R;
import com.example.mywallet.models.CurrencyBalance;
import com.example.mywallet.utils.Currency;
import com.example.mywallet.utils.UpdateUi;
import com.example.mywallet.widgets.WalletBalancesWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Widget adapter.
 */
public class WidgetAdapter extends RemoteViewsService {

    /**
     * The type Widget item.
     */
    public static final class WidgetItem {

        /**
         * Label to display in the list.
         */
        public String currencyName;
        /**
         * The Id.
         */
        public String id;
        /**
         * The Convert unit.
         */
        public String convertUnit;
        /**
         * The Value.
         */
        public double value;
        /**
         * The Currency balance.
         */
        public double currencyBalance;
        /**
         * The Converted balance.
         */
        public double convertedBalance;

        /**
         * Instantiates a new Widget item.
         *
         * @param currencyName    the currency name
         * @param id              the id
         * @param convertUnit     the convert unit
         * @param value           the value
         * @param currencyBalance the currency balance
         */
        public WidgetItem(String currencyName, String id, String convertUnit, double value, double currencyBalance) {
            this.currencyName = currencyName;
            this.id = id;
            this.convertUnit = convertUnit;
            this.value = value;
            this.currencyBalance = currencyBalance;
            convertedBalance = currencyBalance * value;
        }

    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext());
    }

    /**
     * The type List remote views factory.
     */
    public static class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private final List<WidgetItem> mWidgetItems = new ArrayList<>();
        private final Context mContext;

        /**
         * Instantiates a new List remote views factory.
         *
         * @param context the context
         */
        public ListRemoteViewsFactory(Context context) {
            mContext = context;
        }

        @Override
        public void onCreate() {
            // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
            // for example downloading or creating content etc, should be deferred to onDataSetChanged()
            // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.


        }

        @Override
        public void onDestroy() {
            mWidgetItems.clear();
        }

        @Override
        public int getCount() {
            return mWidgetItems.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            // Position will always range from 0 to getCount() - 1.
            WidgetItem widgetItem = mWidgetItems.get(position);

            // Construct remote views item based on the item xml file and set text based on position.
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.wallet_balances_widget_item);
            rv.setTextViewText(R.id.currency_name, widgetItem.currencyName);
            rv.setTextViewText(R.id.currency_id, widgetItem.id);
            rv.setTextViewText(R.id.value_unit, widgetItem.convertUnit);
            rv.setTextViewText(R.id.converted_balance_unit, widgetItem.convertUnit);
            rv.setTextViewText(R.id.currency_value, String.valueOf(widgetItem.value));
            rv.setTextViewText(R.id.currency_balance, String.valueOf(widgetItem.currencyBalance));
            rv.setTextViewText(R.id.currency_unit, widgetItem.id);
            rv.setTextViewText(R.id.currency_balance_converted, String.valueOf(widgetItem.convertedBalance));

            // Next, we set a fill-intent which will be used to fill-in the pending intent template
            // which is set on the collection view in AppWidgetProvider.
            Intent fillInIntent = new Intent().putExtra(WalletBalancesWidget.EXTRA_CLICKED_FILE, widgetItem.id);
            rv.setOnClickFillInIntent(R.id.widget_item_layout, fillInIntent);

            // You can do heaving lifting in here, synchronously. For example, if you need to
            // process an image, fetch something from the network, etc., it is ok to do it here,
            // synchronously. A loading view will show up in lieu of the actual contents in the
            // interim.

            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            // You can create a custom loading view (for instance when getViewAt() is slow.) If you
            // return null here, you will get the default loading view.
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public void onDataSetChanged() {
            // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
            // on the collection view corresponding to this factory. You can do heaving lifting in
            // here, synchronously. For example, if you need to process an image, fetch something
            // from the network, etc., it is ok to do it here, synchronously. The widget will remain
            // in its current state while work is being done here, so you don't need to worry about
            // locking up the widget.
            mWidgetItems.clear();


            for (CurrencyBalance currencyBalance : UpdateUi.getBalances()) {
                WidgetItem item = new WidgetItem(currencyBalance.getCurrencyName(),
                        currencyBalance.getCurrencyId(),
                        Currency.valueOf(currencyBalance.getConvertUnit()).getSymbol(),
                        currencyBalance.getCurrentPrice(),
                        currencyBalance.getCurrentBalance());
                mWidgetItems.add(item);
            }
        }
    }
}
