package com.example.app;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RestClient {

    private String falseUrl = null;
    private Context context;

    private static RequestQueue requestQueue;

    public RestClient(Context context){

        this.context = context;

    }

    private static RestClient singleton = null;

    public static RestClient getInstance(Context context){

        if (singleton==null){

            singleton = new RestClient(context);
            requestQueue = Volley.newRequestQueue(context);

        }
        return singleton;
    }



}
