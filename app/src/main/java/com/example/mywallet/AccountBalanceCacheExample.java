package com.example.mywallet;

import android.content.Context;
import android.util.Log;
/*
import com.binance.android.opensdk.api.BinanceAPIFactory;
import com.binance.android.opensdk.api.BinanceListener;
import com.binance.android.opensdk.api.OAuthParams;
import com.binance.android.opensdk.api.BinanceAPI;
*/

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;

import java.util.TreeMap;


/**
 * Illustrates how to use the user data event stream to create a local cache for the balance of an account.
 */
public class AccountBalanceCacheExample {

    //private BinanceAPI api;
    private static Context appContext;


    public AccountBalanceCacheExample() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("0HJvpJwNLRN41SSQJIaCYpsZ4UUryR3h7XfolarHu4vCFykTPKsoNLY2IC6F0Q1e", "DqvVFLcFh4ltUd4MN7WNQdoMTrEjzovQVjeqS0MyVGb4vasOQP7IMdlH2KP80RK5");
        BinanceApiRestClient client = factory.newRestClient();
        System.out.println("ServerTime="+client.getServerTime());
    }
}
