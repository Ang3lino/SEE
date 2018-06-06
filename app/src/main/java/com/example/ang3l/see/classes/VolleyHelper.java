package com.example.ang3l.see.classes;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyHelper { // clase de ayuda para volley tipo singleton

    private static VolleyHelper instance;
    private RequestQueue requestQueue;
    private static Context context;

//    public static final String IP = "192.168.1.72"; // que corresponda a TU servidor
//    public static final String IP = "10.100.65.61"; // que corresponda a TU servidor
    public static final String IP = "192.168.1.67"; // que corresponda a TU servidor

    // suponemos que el script php esta en el directorio see_php
    public static String getHostUrl(String file) {
        return String.format("http://%s/see_php/%s", IP, file);
    }

    // suponemos que el script php esta en el directorio see_php
    public static String getHostUrlImageDir() {
        return String.format("http://%s/see_php/img/", IP);
    }

    public RequestQueue getRequestQueue() {
       if (requestQueue == null)
           requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }

    private VolleyHelper (Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyHelper getInstance(Context context) {
        if (instance == null)
            instance = new VolleyHelper(context);
        return instance;
    }

    public<T> void addToRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }
}
