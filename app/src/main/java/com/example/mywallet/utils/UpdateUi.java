package com.example.mywallet.utils;

import android.widget.ListView;
import android.widget.TextView;

import com.example.mywallet.adapters.CurrencyBalanceAdapter;
import com.example.mywallet.models.CurrencyBalance;
import com.example.mywallet.services.ApplicationService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * The type Update ui.
 */
public class UpdateUi {

    private TextView totalWallet;
    private TextView currencyUnit;
    private TextView updatedHour;
    private ListView balancesListView;
    private static String selectedConvertCurrency;
    private static List<CurrencyBalance> balances;

    /**
     * Gets balances.
     *
     * @return the balances
     */
    public static List<CurrencyBalance> getBalances() {
        return balances;
    }

    /**
     * Sets up currencies balance view.
     *
     * @param currenciesBalanceList the currencies balance list
     */
    public void setUpCurrenciesBalanceView (ListView currenciesBalanceList) {
        balancesListView = currenciesBalanceList;
    }

    /**
     * Sets balances list.
     *
     * @param balances the balances
     */
    public void setBalancesList(List<CurrencyBalance> balances) {
        UpdateUi.balances = balances;
        CurrencyBalanceAdapter currencyBalanceAdapter = new CurrencyBalanceAdapter(ApplicationService.getAppContext(), balances);
        balancesListView.setAdapter(currencyBalanceAdapter);
        currencyBalanceAdapter.notifyDataSetChanged();
    }

    /**
     * Update total wallet.
     *
     * @param wallet the wallet
     */
    public void updateTotalWallet (double wallet) {
        totalWallet.setText(String.valueOf(wallet));
        currencyUnit.setText(Currency.valueOf(selectedConvertCurrency).getSymbol());
        updatedHour.setText(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis()));
    }

    /**
     * Sets total wallet.
     *
     * @param totalWallet the total wallet
     */
    public void setTotalWallet(TextView totalWallet) {
        this.totalWallet = totalWallet;
    }

    /**
     * Sets unit text view.
     *
     * @param unit the unit
     */
    public void setUnitTextView (TextView unit) {
        currencyUnit = unit;
    }

    /**
     * Sets selected convert currency.
     *
     * @param selectedConvertCurrency the selected convert currency
     */
    public static void setSelectedConvertCurrency(String selectedConvertCurrency) {
        UpdateUi.selectedConvertCurrency = selectedConvertCurrency;
    }

    /**
     * Sets updated hour.
     *
     * @param updatedHour the updated hour
     */
    public void setUpdatedHour(TextView updatedHour) {
        this.updatedHour = updatedHour;
    }

    /**
     * Gets selected convert currency.
     *
     * @return the selected convert currency
     */
    public static String getSelectedConvertCurrency() {
        return selectedConvertCurrency;
    }

}
