package com.example.mywallet.utils;

import com.example.mywallet.R;
import com.example.mywallet.services.ApplicationService;

public enum Currency {

    EUR(ApplicationService.getAppContext().getString(R.string.EUR)),
    USD(ApplicationService.getAppContext().getString(R.string.USD)),
    JPY(ApplicationService.getAppContext().getString(R.string.JPY)),
    GBP(ApplicationService.getAppContext().getString(R.string.GBP)),
    CHF(ApplicationService.getAppContext().getString(R.string.CHF)),
    CAD(ApplicationService.getAppContext().getString(R.string.CAD)),
    RUB(ApplicationService.getAppContext().getString(R.string.RUB)),
    BTC(ApplicationService.getAppContext().getString(R.string.BTC));

    private final String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
