package com.example.mywallet.models;

public class CurrencyBalance {

    private final String currencyId;
    private final String convertUnit;
    private final String currencyName;
    private Double currentPrice;
    private final Double currentBalance;
    private Double currentBalanceConverted;

    public String getCurrencyId() {
        return currencyId;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public Double getCurrentBalanceConverted() {
        return currentBalanceConverted;
    }

    public String getConvertUnit() {
        return convertUnit;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public CurrencyBalance(String currencyId, Double currentBalance, Double currentPrice, String convertUnit, String currencyName) {
        this.currencyId = currencyId;
        this.currentBalance = currentBalance;
        this.convertUnit = convertUnit;
        this.currencyName = currencyName;
        this.currentPrice = currentPrice;
        currentBalanceConverted = currentPrice * currentBalance;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setCurrentBalanceConverted(Double currentBalanceConverted) {
        this.currentBalanceConverted = currentBalanceConverted;
    }

}
