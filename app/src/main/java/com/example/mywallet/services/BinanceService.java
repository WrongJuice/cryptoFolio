package com.example.mywallet.services;

import com.android.volley.Response;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.AssetBalance;
import com.example.mywallet.models.CurrencyBalance;
import com.example.mywallet.utils.UpdateUi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Binance service.
 */
public class BinanceService {

    private final UpdateUi updateUi;
    private static BinanceApiRestClient client;

    /**
     * Instantiates a new Binance service.
     *
     * @param updateUi the update ui
     */
    public BinanceService (UpdateUi updateUi) {
        this.updateUi = updateUi;
    }

    /**
     * Sets keys.
     *
     * @param publicKey  the public key
     * @param privateKey the private key
     */
    public static void setKeys (String publicKey, String privateKey) {
        client = BinanceApiClientFactory.newInstance(publicKey, privateKey).newRestClient();
    }

    private class GetBalance implements Runnable {

        private final String endAsset;

        /**
         * Instantiates a new Get balance.
         *
         * @param endAsset the end asset
         */
        public GetBalance (String endAsset) {
            this.endAsset = endAsset;
        }

        @Override
        public void run() {
            Map<String, Double> balances = new HashMap<>();
            List<AssetBalance> assetBalances = new ArrayList<>();
            try {
                assetBalances = client.getAccount().getBalances();
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (AssetBalance balance : assetBalances) {
                if (Double.parseDouble(balance.getFree()) + Double.parseDouble(balance.getLocked()) > 0)
                    balances.put(balance.getAsset(), Double.parseDouble(balance.getFree())
                            + Double.parseDouble(balance.getLocked()));
            }
            // updateUi.updateOriginalBalances(balances);

            Response.Listener<Map<String, Double>> responseListener = response -> {
                double totalWallet = 0;
                for (Map.Entry<String, Double> balance : response.entrySet())
                    totalWallet += balance.getValue();
                updateUi.updateTotalWallet(totalWallet);
            };

            // NomicsService.getPrices(balances, endAsset, responseListener);

            Response.Listener<List<CurrencyBalance>> responseListenerCurrencyBalance = response -> {
                updateUi.setBalancesList(response);
                double totalWallet = 0;
                for (CurrencyBalance balance : response)
                    totalWallet += balance.getCurrentBalanceConverted();
                updateUi.updateTotalWallet(totalWallet);
            };

            NomicsService.getCryptoInfos(balances, endAsset, responseListenerCurrencyBalance);
        }
    }

    /**
     * Gets balance.
     *
     * @param endAsset the end asset
     */
    public void getBalance (String endAsset) {
        GetBalance getBalance = new GetBalance(endAsset);
        Thread getBalanceThread = new Thread(getBalance);
        getBalanceThread.start();
    }

}
