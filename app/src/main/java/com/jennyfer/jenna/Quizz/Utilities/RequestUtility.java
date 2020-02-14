package com.jennyfer.jenna.Quizz.Utilities;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Clase para gestionar las peticiones HTTP al servidor
 */
public class RequestUtility {

    private RequestQueue requestQueue;
    //private final String TAG = RequestUtility.class.getName();

    /**
     * Constructor de la clase, inicializa la variable de la cola de peticiones
     * @param context
     */
    public RequestUtility(Context context){
        this.requestQueue = Volley.newRequestQueue(context);
    }
    /**
     * Método para hacer llamadas asíncronas POST al servidor, la devolución la cacha 'callback'
     * Gracias Dámaris<3
     * @param callback
     * @param url_peticion
     */
    public synchronized void __callServerPOST(final String url_peticion, final Map<String,String> super_params, final VolleyCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_peticion,
                new Response.Listener<String>() {
                    /**
                     * Manipulador de las respuestas del servidor
                     * @param response
                     */
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                    }
                }
        ){
            /**
             * Es el cuerpo del POST, los parámetros a comunicar al servidor
             * @return Params
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super_params;
            }
        };
        requestQueue.add(stringRequest);
    }

}