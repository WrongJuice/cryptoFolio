package com.example.mywallet.utils.Customs;

import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class CustomGetPricesListener implements Response.Listener<JSONArray> {

    private Map<String, Double> prices;

    //response

    @Override
    public void onResponse(JSONArray response) {
        get(response);
    }

    public Map<String, Double> get(JSONArray response) {

        prices = new HashMap<>();

        try {

            for (int i = 0 ; i != response.length() ; i++) {
                prices.put(response.getJSONObject(i).getString("id"),
                        Double.valueOf(response.getJSONObject(i).getString("price")));
            }

            System.out.println("prices=" + prices);

        } catch (Exception e) {
            Log.e("getPrices", "Error while retrieving measures : " + e);
        }

        return prices;

    }

}
