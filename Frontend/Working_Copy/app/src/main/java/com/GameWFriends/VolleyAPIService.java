package com.GameWFriends;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


//Example of how to use this class in other activites:
// VolleyAPIService apiService = new VolleyAPIService(getContext()); // Use 'this' if in an Activity
//
//apiService.deleteRequest(new VolleyAPIService.VolleyResponseListener() {
//    @Override
//    public void onError(String message) {
//        // Handle error
//    }
//
//    @Override
//    public void onResponse(String response) {
//        // Handle response
//    }
//});

public class VolleyAPIService {

    private String url = "https://59a7d876-383e-468e-8b60-f3636af76d88.mock.pstmn.io/test";
    private RequestQueue requestQueue; // Volley request queue

    // Constructor
    public VolleyAPIService(Context context) {
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    // Interface for GET request callbacks
    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(String response);
    }

    // GET Request
    public void getRequest(final String userId, final VolleyResponseListener listener) {
        String urlWithId = url + "/users/" + userId; // Assuming 'url' is the base URL ending before '/users/'

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlWithId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError("That didn't work!" + error.toString());
                    }
                });

        requestQueue.add(stringRequest);
    }

    // POST Request
    public void postRequest(final String requestBody, final VolleyResponseListener listener) throws JSONException {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                requestBody.isEmpty() ? null : new JSONObject(requestBody),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
    // DELETE Request
    public void deleteRequest(final VolleyResponseListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                response -> listener.onResponse("Deleted Successfully."),
                error -> listener.onError("Delete Failed: " + error.toString()));

        requestQueue.add(stringRequest);
    }

    // PUT Request
    public void putRequest(final String requestBody, final VolleyResponseListener listener) throws JSONException {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url,
                requestBody.isEmpty() ? null : new JSONObject(requestBody),
                response -> listener.onResponse(response.toString()),
                error -> listener.onError(error.getMessage()));

        requestQueue.add(jsonObjectRequest);
    }
}
