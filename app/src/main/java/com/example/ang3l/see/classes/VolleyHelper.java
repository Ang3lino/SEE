package com.example.ang3l.see.classes;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyHelper { // clase de ayuda para volley tipo singleton

    private static VolleyHelper instance;
    private RequestQueue requestQueue;
    private static Context context;

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
