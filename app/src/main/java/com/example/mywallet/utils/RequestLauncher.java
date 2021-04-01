package com.example.mywallet.utils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mywallet.services.ApplicationService;

public class RequestLauncher implements Runnable {

    private Request<Object> request;

    public RequestLauncher(Request request) {
        this.request = request;
    }

    @Override
    public void run() {
        RequestQueue requestQueue = Volley.newRequestQueue(ApplicationService.getAppContext());
        requestQueue.add(request);
    }
}
