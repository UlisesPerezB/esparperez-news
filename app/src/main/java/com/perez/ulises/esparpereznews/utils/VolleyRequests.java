package com.perez.ulises.esparpereznews.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.perez.ulises.esparpereznews.trending.RequestSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class VolleyRequests {

    public static void jsonRequest(Context context, final int method, final String url, final String header, final String token, final IRequest.VolleyResponseHandler vrs) {
        StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    String responseUTF8 =
                            new String(response.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8
                    );
                    try {
//                        JSONObject jsonObject = new JSONObject(responseUTF8);
                        JSONObject jsonObject = new JSONObject(response);
                        vrs.onResponse(jsonObject);
                    } catch (JSONException e) {
                        try {
                            JSONArray jsonArray = new JSONArray(responseUTF8);
                            vrs.onResponse(jsonArray);
                        }  catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley", "no furula " + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(header, token);
                return params;
            }
        };
        RequestSingleton.getInstance(context).addToRequestQue(stringRequest);
    }
}
