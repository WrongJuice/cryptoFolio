package com.example.mywallet.utils;

import com.example.mywallet.R;
import com.example.mywallet.services.ApplicationService;

/**
 * The enum Currency.
 */
public enum Currency {

    /**
     * Eur currency.
     */
    EUR(ApplicationService.getAppContext().getString(R.string.EUR)),
    /**
     * Usd currency.
     */
    USD(ApplicationService.getAppContext().getString(R.string.USD)),
    /**
     * Jpy currency.
     */
    JPY(ApplicationService.getAppContext().getString(R.string.JPY)),
    /**
     * Gbp currency.
     */
    GBP(ApplicationService.getAppContext().getString(R.string.GBP)),
    /**
     * Chf currency.
     */
    CHF(ApplicationService.getAppContext().getString(R.string.CHF)),
    /**
     * Cad currency.
     */
    CAD(ApplicationService.getAppContext().getString(R.string.CAD)),
    /**
     * Rub currency.
     */
    RUB(ApplicationService.getAppContext().getString(R.string.RUB)),
    /**
     * Btc currency.
     */
    BTC(ApplicationService.getAppContext().getString(R.string.BTC));

    private final String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

}
