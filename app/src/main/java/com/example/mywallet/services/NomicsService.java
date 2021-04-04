package com.example.mywallet.services;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.mywallet.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NomicsService {

    private static final String NOMIC_URL = "https://api.nomics.com/v1/";
    private static final String NOMIC_API_KEY = ApplicationService.getAppContext().getString(R.string.nomic_api_key);
    private static final Object requestFinished = new Object();
    private static boolean hasHadResponseDelivered;
    private static final Object obj = new Object();

    /*public static android.os.AsyncTask<Object, Object, HashMap<String, Double>> convertPrices(HashMap<String, Double> balances, String endAsset) {
        PricesConvertion pricesConvertion = new PricesConvertion();
        return pricesConvertion.execute(balances, endAsset);
    }*/

    private static class CryptoCurrenciesConversionsRequest extends Request<Map<String, Double>> {

        private final Response.Listener<Map<String, Double>> listener;
        private final Map<String, Double> balances;

        public CryptoCurrenciesConversionsRequest(Map<String, Double> balances, String convertCurrencySymbol, Response.Listener<Map<String, Double>> responseListener) {
            super(Request.Method.GET, NOMIC_URL + "currencies/ticker?key=" + NOMIC_API_KEY + "&format=json&convert="
                    + convertCurrencySymbol + "&ids=" + balances.keySet().toString().substring(1, balances.keySet().toString().length() - 1)
                    + "&interval=none", error -> System.out.println("error"));
            this.balances = balances;
            listener = responseListener;
        }

        @Override
        protected Response<Map<String, Double>> parseNetworkResponse(NetworkResponse response) {

            Map<String, Double> prices = new HashMap<>();
            String jsonAsString = "";

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
                Log.e("getPrices", "Error while retrieving measures : " + e);
                e.printStackTrace();
                return Response.error(new ParseError(e));
            }

        }

        @Override
        protected void deliverResponse(Map<String, Double> response) {
            listener.onResponse(response);
        }

    }

    public static String getNomicUrl() {
        return NOMIC_URL;
    }

    public static String getNomicApiKey() {
        return NOMIC_API_KEY;
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

    /**
     * Runs a blocking Volley request
     *
     * @param method        get/put/post etc
     * @param url           endpoint
     * @return the input stream result or exception: NOTE returns null once the onErrorResponse listener has been called
     */
    public JSONArray runJSONArrayRequest(int method, String url) {
        Response.ErrorListener errorListener = error -> Log.e("getPrices", "Error requesting prices " + error.toString());
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(method, url, future, error -> Log.e("getPrices", "Error requesting prices " + error.toString())) {};
        Volley.newRequestQueue(ApplicationService.getAppContext()).add(request);
        try {
            return future.get(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.e("interrupted.", e.toString());
            errorListener.onErrorResponse(new VolleyError(e));
        } catch (ExecutionException e) {
            Log.e("failed.", e.toString());
            errorListener.onErrorResponse(new VolleyError(e));
        } catch (TimeoutException e) {
            Log.e("timed out.", e.toString());
            errorListener.onErrorResponse(new VolleyError(e));
        }
        System.out.println("passed");
        return null;
    }

    public static Object getObj() {
        return obj;
    }

    public static void setHasHadResponseDelivered(boolean hasHadResponseDelivered) {
        NomicsService.hasHadResponseDelivered = hasHadResponseDelivered;
    }

}
