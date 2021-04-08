package com.example.mywallet.models;

/**
 * The type Currency balance.
 */
public class CurrencyBalance {

    private final String currencyId;
    private final String convertUnit;
    private final String currencyName;
    private Double currentPrice;
    private final Double currentBalance;
    private Double currentBalanceConverted;

    /**
     * Gets currency id.
     *
     * @return the currency id
     */
    public String getCurrencyId() {
        return currencyId;
    }

    /**
     * Gets current price.
     *
     * @return the current price
     */
    public Double getCurrentPrice() {
        return currentPrice;
    }

    /**
     * Gets current balance.
     *
     * @return the current balance
     */
    public Double getCurrentBalance() {
        return currentBalance;
    }

    /**
     * Gets current balance converted.
     *
     * @return the current balance converted
     */
    public Double getCurrentBalanceConverted() {
        return currentBalanceConverted;
    }

    /**
     * Gets convert unit.
     *
     * @return the convert unit
     */
    public String getConvertUnit() {
        return convertUnit;
    }

    /**
     * Gets currency name.
     *
     * @return the currency name
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * Instantiates a new Currency balance.
     *
     * @param currencyId     the currency id
     * @param currentBalance the current balance
     * @param currentPrice   the current price
     * @param convertUnit    the convert unit
     * @param currencyName   the currency name
     */
    public CurrencyBalance(String currencyId, Double currentBalance, Double currentPrice, String convertUnit, String currencyName) {
        this.currencyId = currencyId;
        this.currentBalance = currentBalance;
        this.convertUnit = convertUnit;
        this.currencyName = currencyName;
        this.currentPrice = currentPrice;
        currentBalanceConverted = currentPrice * currentBalance;
    }

    /**
     * Sets current price.
     *
     * @param currentPrice the current price
     */
    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * Sets current balance converted.
     *
     * @param currentBalanceConverted the current balance converted
     */
    public void setCurrentBalanceConverted(Double currentBalanceConverted) {
        this.currentBalanceConverted = currentBalanceConverted;
    }

}
