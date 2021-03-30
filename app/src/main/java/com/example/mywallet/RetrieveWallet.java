package com.example.mywallet;

import android.os.AsyncTask;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.market.TickerStatistics;

public class RetrieveWallet extends AsyncTask<String, String, Double> {

    double spotBalance;

    public RetrieveWallet () {
        spotBalance = 0;
    }

    @Override
    protected Double doInBackground(String... params) {
        double walletValue;
        walletValue = 0;
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(params[0], params[1]);
        BinanceApiRestClient client = factory.newRestClient();
        Account account = client.getAccount();

        TickerStatistics tickerStatistic;

        for (AssetBalance balance : account.getBalances())
            if (balance.getAsset().equals("USDT")) {
                walletValue += Double.parseDouble(balance.getFree());
            } else if (Double.parseDouble(balance.getFree()) + Double.parseDouble(balance.getLocked()) > 0) {
                System.out.println("symbol=" + balance.getAsset() + "|value=" + balance.getFree());
                tickerStatistic = client.get24HrPriceStatistics(balance.getAsset() + "USDT");
                walletValue += (Double.parseDouble(balance.getFree()) + Double.parseDouble(balance.getLocked()))
                        * Double.parseDouble(tickerStatistic.getLastPrice());
            }

        walletValue /= Double.parseDouble(client.get24HrPriceStatistics(params[2] + "USDT").getLastPrice());

        System.out.println("balance ="+walletValue);

        return walletValue;
    }

    @Override
    protected void onPostExecute(Double balance) {
        spotBalance = balance;
    }
}
