package com.example.mywallet.models;

public class CurrencyBalance {

    private final String currencyId;
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

    public CurrencyBalance(String currencyId, Double currentBalance) {
        this.currencyId = currencyId;
        this.currentBalance = currentBalance;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setCurrentBalanceConverted(Double currentBalanceConverted) {
        this.currentBalanceConverted = currentBalanceConverted;
    }

}
