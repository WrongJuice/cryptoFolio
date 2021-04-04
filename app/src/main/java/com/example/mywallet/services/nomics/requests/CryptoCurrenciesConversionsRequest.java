package com.example.mywallet.services.nomics.requests;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CryptoCurrenciesConversionsRequest extends Request<Map<String, Double>> {

    private final Response.Listener<Map<String, Double>> listener;

    public CryptoCurrenciesConversionsRequest(int method, String url, Response.Listener<Map<String, Double>> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        listener = responseListener;
    }

    @Override
    protected Response<Map<String, Double>> parseNetworkResponse(NetworkResponse response) {

        Map<String, Double> prices = new HashMap<>();
        String json;

        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0 ; i != jsonArray.length() ; i++) {
                prices.put(jsonArray.getJSONObject(i).getString("id"),
                        Double.valueOf(jsonArray.getJSONObject(i).getString("price")));
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
