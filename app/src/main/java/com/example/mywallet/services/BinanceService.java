package com.example.mywallet.services;

import android.os.AsyncTask;

import com.android.volley.Response;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.AssetBalance;
import com.example.mywallet.utils.UpdateUi;

import java.util.HashMap;
import java.util.Map;

public class BinanceService extends AsyncTask<Object, Object, Object> {

    @Override
    protected Object doInBackground(Object... params) {
        BinanceApiRestClient client =
                BinanceApiClientFactory.newInstance((String) params[0], (String) params[1]).newRestClient();

        Response.Listener<Map<String, Double>> responseListener = new Response.Listener<Map<String, Double>>() {
            @Override
            public void onResponse(Map<String, Double> response) {
                double totalWallet = 0;
                for (Map.Entry<String, Double> balance : response.entrySet())
                    totalWallet += balance.getValue();
                ((UpdateUi) params[3]).updateTotalWallet(totalWallet);
            }
        };

        Map<String, Double> balances = new HashMap<>();
        for (AssetBalance balance : client.getAccount().getBalances()) {
            if (Double.parseDouble(balance.getFree()) + Double.parseDouble(balance.getLocked()) > 0)
                balances.put(balance.getAsset(), Double.parseDouble(balance.getFree())
                        + Double.parseDouble(balance.getLocked()));
        }

        NomicsService.getPrices(balances, "EUR", responseListener);

        return null;

    }

    @Override
    protected void onPostExecute(Object balance) {
    }
}
