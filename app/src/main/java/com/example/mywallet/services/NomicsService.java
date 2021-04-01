package com.example.mywallet.services;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mywallet.R;
import com.example.mywallet.utils.WaitRequestResult;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NomicsService {

    private static final String NOMIC_URL = "https://api.nomics.com/v1/";
    private static final String NOMIC_API_KEY = ApplicationService.getAppContext().getString(R.string.nomic_api_key);
    private static final Object requestFinished = new Object();

    /**
     * Retrieve and put the consumption data into the database between two dates
     * @param startAssets The assets you need to know the price of
     * @param endAsset The asset into which you want to convert your input assets
     */
    public static Map<String, Double> getPrices(ArrayList<String> startAssets , String endAsset) {
        String formattedStartAssets = startAssets.toString();
        String url = NOMIC_URL + "currencies/ticker?key=" + NOMIC_API_KEY + "&format=json&convert="
                + endAsset + "&ids=" + formattedStartAssets.substring(1, formattedStartAssets.length() - 1)
                + "&interval=none";

        Log.i("getPrices..." , url);

        Map<String, Double> prices = new HashMap<>();

        WaitRequestResult waitRequestResult = new WaitRequestResult(requestFinished);

        RequestQueue requestQueue = Volley.newRequestQueue(ApplicationService.getAppContext());

        Response.Listener<JSONArray> responseMListener = response -> {
            try {
                for (int i = 0 ; i != response.length() ; i++) {
                    prices.put(response.getJSONObject(i).getString("id"),
                            Double.valueOf(response.getJSONObject(i).getString("price")));
                }
                System.out.println("prices="+prices);
                waitRequestResult.setHasHadResponseDelivered(true);
                synchronized (requestFinished) {
                    requestFinished.notifyAll();
                }
            } catch (Exception e) {
                Log.e("getPrices", "Error while retrieving measures : " + e);
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, responseMListener,
                error -> Log.e("getPrices", "Error requesting prices " + error.toString())) {};

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Thread waitResult = new Thread(waitRequestResult);
        waitResult.start();

        requestQueue.add(request);

        return prices;

    }

}
