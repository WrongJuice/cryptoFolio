package com.example.mywallet.services;

import com.android.volley.Response;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.AssetBalance;
import com.example.mywallet.utils.UpdateUi;

import java.util.HashMap;
import java.util.Map;

public class BinanceService {

    private final UpdateUi updateUi;
    private static BinanceApiRestClient client;

    public BinanceService (UpdateUi updateUi) {
        this.updateUi = updateUi;
    }

    public static void setKeys (String publicKey, String privateKey) {
        client = BinanceApiClientFactory.newInstance(publicKey, privateKey).newRestClient();
    }

    private class GetBalance implements Runnable {

        private final String endAsset;

        public GetBalance (String endAsset) {
            this.endAsset = endAsset;
        }

        @Override
        public void run() {
            Map<String, Double> balances = new HashMap<>();
            for (AssetBalance balance : client.getAccount().getBalances()) {
                if (Double.parseDouble(balance.getFree()) + Double.parseDouble(balance.getLocked()) > 0)
                    balances.put(balance.getAsset(), Double.parseDouble(balance.getFree())
                            + Double.parseDouble(balance.getLocked()));
            }
            updateUi.updateOriginalBalances(balances);

            Response.Listener<Map<String, Double>> responseListener = response -> {
                double totalWallet = 0;
                for (Map.Entry<String, Double> balance : response.entrySet())
                    totalWallet += balance.getValue();
                updateUi.updateTotalWallet(totalWallet);
            };

            NomicsService.getPrices(balances, endAsset, responseListener);
        }
    }

    public void getBalance (String endAsset) {
        GetBalance getBalance = new GetBalance(endAsset);
        Thread getBalanceThread = new Thread(getBalance);
        getBalanceThread.start();
    }

}
