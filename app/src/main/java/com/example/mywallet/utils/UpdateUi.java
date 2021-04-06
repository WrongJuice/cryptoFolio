package com.example.mywallet.utils;

import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mywallet.R;
import com.example.mywallet.adapters.CurrencyBalanceAdapter;
import com.example.mywallet.models.CurrencyBalance;
import com.example.mywallet.services.ApplicationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateUi {

    private TextView totalWallet;
    private TextView currencyUnit;
    private String selectedConvertCurrency;
    private List<CurrencyBalance> balances;
    private CurrencyBalanceAdapter currencyBalanceAdapter;

    public void setUpCurrenciesBalanceView (ListView currenciesBalanceList) {
        balances = new ArrayList<>();
        currencyBalanceAdapter = new CurrencyBalanceAdapter(ApplicationService.getAppContext(), balances);
        currenciesBalanceList.setAdapter(currencyBalanceAdapter);
    }

    public void setBalancesList(List<CurrencyBalance> balances) {
        this.balances = balances;
    }

    public void updateTotalWallet (double wallet) {
        totalWallet.setText(String.valueOf(wallet));
        switch (selectedConvertCurrency) {
            case "EUR" : currencyUnit.setText(totalWallet.getContext().getString(R.string.EUR)); break;
            case "USD" : currencyUnit.setText(totalWallet.getContext().getString(R.string.USD)); break;
            case "JPY" : currencyUnit.setText(totalWallet.getContext().getString(R.string.JPY)); break;
            case "GBP" : currencyUnit.setText(totalWallet.getContext().getString(R.string.GBP)); break;
            case "CHF" : currencyUnit.setText(totalWallet.getContext().getString(R.string.CHF)); break;
            case "CAD" : currencyUnit.setText(totalWallet.getContext().getString(R.string.CAD)); break;
            case "RUB" : currencyUnit.setText(totalWallet.getContext().getString(R.string.RUB)); break;
            case "BTC" : currencyUnit.setText(totalWallet.getContext().getString(R.string.BTC)); break;
        }
    }

    public void setTotalWallet(TextView totalWallet) {
        this.totalWallet = totalWallet;
    }

    public void updateOriginalBalances(Map<String, Double> balances) {
    }

    public void setUnitTextView (TextView unit) {
        currencyUnit = unit;
    }

    public void setSelectedConvertCurrency (String selectedConvertCurrency) {
        this.selectedConvertCurrency = selectedConvertCurrency;
    }

    public List<CurrencyBalance> getBalancesList() {
        return balances;
    }

    public CurrencyBalanceAdapter getCurrencyBalanceAdapter() {
        return currencyBalanceAdapter;
    }

}
