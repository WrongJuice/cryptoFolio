package com.example.mywallet.utils;

import android.widget.ListView;
import android.widget.TextView;

import com.example.mywallet.adapters.CurrencyBalanceAdapter;
import com.example.mywallet.models.CurrencyBalance;
import com.example.mywallet.services.ApplicationService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UpdateUi {

    private TextView totalWallet;
    private TextView currencyUnit;
    private TextView updatedHour;
    private ListView balancesListView;
    private static String selectedConvertCurrency;
    private static List<CurrencyBalance> balances;

    public static List<CurrencyBalance> getBalances() {
        return balances;
    }

    public void setUpCurrenciesBalanceView (ListView currenciesBalanceList) {
        balancesListView = currenciesBalanceList;
    }

    public void setBalancesList(List<CurrencyBalance> balances) {
        UpdateUi.balances = balances;
        CurrencyBalanceAdapter currencyBalanceAdapter = new CurrencyBalanceAdapter(ApplicationService.getAppContext(), balances);
        balancesListView.setAdapter(currencyBalanceAdapter);
        currencyBalanceAdapter.notifyDataSetChanged();
    }

    public void updateTotalWallet (double wallet) {
        totalWallet.setText(String.valueOf(wallet));
        currencyUnit.setText(Currency.valueOf(selectedConvertCurrency).getSymbol());
        updatedHour.setText(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis()));
    }

    public void setTotalWallet(TextView totalWallet) {
        this.totalWallet = totalWallet;
    }

    public void setUnitTextView (TextView unit) {
        currencyUnit = unit;
    }

    public static void setSelectedConvertCurrency(String selectedConvertCurrency) {
        UpdateUi.selectedConvertCurrency = selectedConvertCurrency;
    }

    public void setUpdatedHour(TextView updatedHour) {
        this.updatedHour = updatedHour;
    }

    public static String getSelectedConvertCurrency() {
        return selectedConvertCurrency;
    }

}
