package com.example.mywallet.services;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.example.mywallet.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class NomicsService {

    private static final String NOMICS_URL = "https://api.nomics.com/v1/";
    private static final String NOMICS_API_KEY = ApplicationService.getAppContext().getString(R.string.nomic_api_key);

    private static class CryptoCurrenciesConversionsRequest extends Request<Map<String, Double>> {

        private final Response.Listener<Map<String, Double>> listener;
        private final Map<String, Double> balances;

        public CryptoCurrenciesConversionsRequest(Map<String, Double> balances, String convertCurrencySymbol, Response.Listener<Map<String, Double>> responseListener) {
            super(Request.Method.GET, NOMICS_URL + "currencies/ticker?key=" + NOMICS_API_KEY + "&format=json&convert="
                    + convertCurrencySymbol + "&ids=" + balances.keySet().toString().substring(1, balances.keySet().toString().length() - 1)
                    + "&interval=none", error -> System.out.println("error"));
            this.balances = balances;
            listener = responseListener;
        }

        @Override
        protected Response<Map<String, Double>> parseNetworkResponse(NetworkResponse response) {

            Map<String, Double> prices = new HashMap<>();
            String jsonAsString;

            try {
                jsonAsString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                JSONArray jsonArray = new JSONArray(jsonAsString);
                for (int i = 0 ; i != jsonArray.length() ; i++) {
                    prices.put(jsonArray.getJSONObject(i).getString("id"),
                            Double.parseDouble(jsonArray.getJSONObject(i).getString("price"))
                            * balances.get(jsonArray.getJSONObject(i).getString("id")));
                }
                return Response.success(prices, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException | JSONException e) {
                Log.e("getPrices", "Error while converting currencies : " + e);
                e.printStackTrace();
                return Response.error(new ParseError(e));
            }

        }

        @Override
        protected void deliverResponse(Map<String, Double> response) {
            listener.onResponse(response);
        }

    }

    /**
     * Retrieve and put the consumption data into the database between two dates
     * @param balances The balances you need to know the price of
     * @param endAsset The asset into which you want to convert your input assets
     */
    public static void getPrices(Map<String, Double> balances , String endAsset,
                                                Response.Listener<Map<String, Double>> responseListener) {

        CryptoCurrenciesConversionsRequest cryptoCurrenciesConversionsRequest =
                new CryptoCurrenciesConversionsRequest(balances, endAsset, responseListener);

        RequestQueue requestQueue = Volley.newRequestQueue(ApplicationService.getAppContext());
        requestQueue.add(cryptoCurrenciesConversionsRequest);

    }

}
