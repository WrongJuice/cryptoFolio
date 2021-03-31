package com.example.mywallet;

import android.os.AsyncTask;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.general.SymbolInfo;
import com.binance.api.client.domain.market.TickerStatistics;

public class RetrieveWallet extends AsyncTask<String, String, Double> {

    double spotBalance;

    public RetrieveWallet () {
        spotBalance = 0;
    }

    @Override
    protected Double doInBackground(String... params) {
        double walletValue = 0;
        BinanceApiRestClient client =
                BinanceApiClientFactory.newInstance(params[0], params[1]).newRestClient();

        TickerStatistics tickerStatistic;
        SymbolInfo symbolInfoUSDT = new SymbolInfo();
        SymbolInfo symbolInfoBTC = new SymbolInfo();

        System.out.println("prices="+client.getAllPrices());
        for (AssetBalance balance : client.getAccount().getBalances())
            if (balance.getAsset().equals("USDT")) {
                walletValue += Double.parseDouble(balance.getFree());
            } else if (Double.parseDouble(balance.getFree()) + Double.parseDouble(balance.getLocked()) > 0) {
                symbolInfoUSDT.setSymbol(balance.getAsset() + "USDT");
                symbolInfoBTC.setSymbol(balance.getAsset() + "BTC");
                System.out.println("symbol=" + balance.getAsset() + "|value=" + (Double.parseDouble(balance.getFree()) + Double.parseDouble(balance.getLocked())));
                if (true) {
                    tickerStatistic = client.get24HrPriceStatistics(symbolInfoUSDT.getSymbol());
                    walletValue += (Double.parseDouble(balance.getFree()) + Double.parseDouble(balance.getLocked()))
                            * Double.parseDouble(tickerStatistic.getLastPrice());
                } else if (symbolInfoBTC.isSpotTradingAllowed()) {
                    tickerStatistic = client.get24HrPriceStatistics(symbolInfoBTC.getSymbol());
                    double assetToBtcValue = (Double.parseDouble(balance.getFree()) + Double.parseDouble(balance.getLocked()))
                            * Double.parseDouble(tickerStatistic.getLastPrice());
                    tickerStatistic = client.get24HrPriceStatistics("BTCUSDT");
                    walletValue += assetToBtcValue * Double.parseDouble(tickerStatistic.getLastPrice());
                }

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
