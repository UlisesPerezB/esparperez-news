package com.perez.ulises.esparpereznews.trending;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public final class RequestSingleton {

    private static Context mContext;
    private static RequestSingleton singletonInstance;

    private RequestQueue requestQueue;

    private RequestSingleton(Context context) {
        RequestSingleton.mContext = context;
        requestQueue = getRequestQueue();
    }

    /*
     *  Asigna memoria a la única instancia del singleton y llama al constructor privado
     *  se le asigna la propiedad synchronized ya que será accedida desde varios hilos y con esto
     *  se evitan bloqueos de acceso.
     */
    public static synchronized RequestSingleton getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new RequestSingleton(context);
        }
        return singletonInstance;
    }

    // Obtiene la instancia de la cola de peticiones que se usa en toda la aplicación
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }

    //  Agrega una petición a la cola de peticiones
    public void addToRequestQue(Request request) {
        getRequestQueue().add(request);
    }
}
