package com.example.mywallet.utils;

import androidx.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JsonVolleyRequest extends Request<JsonVolleyRequest.ResponseM>
{
    private Response.Listener<JsonVolleyRequest.ResponseM> mListener;

    public JsonVolleyRequest(int method, String url, Response.Listener<JsonVolleyRequest.ResponseM> responseListener, Response.ErrorListener listener)
    {
        super(method, url, listener);
        this.mListener = responseListener;
    }

    @Override
    protected void deliverResponse(ResponseM response)
    {
        this.mListener.onResponse(response);
    }

    @NonNull
    @Override
    protected Response<ResponseM> parseNetworkResponse(@NonNull NetworkResponse response)
    {
        String parsed;
        try
        {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        }
        catch (UnsupportedEncodingException e)
        {
            parsed = new String(response.data);
        }

        ResponseM responseM = new ResponseM();
        responseM.headers = response.headers;
        responseM.response = parsed;

        return Response.success(responseM, HttpHeaderParser.parseCacheHeaders(response));
    }


    public static class ResponseM
    {
        public Map<String, String> headers;
        public String response;
    }

}
