package com.GameWFriends;

import android.content.Context;

import com.GameWFriends.ui.AdminTools.Constants;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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

    //made equal to base as to not have to refactor this code.
    private String url = Constants.BASE_URL;
    private RequestQueue requestQueue; // Volley request queue

    // Constructor
    public VolleyAPIService(Context context) {
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    // Interface for GET request callbacks
    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(JSONObject response);
    }


    /**GetRequest function, allows a get request to be sent using a json object.
     *
     * @param finalUrl the full final url that will be getting sent
     * @param listener Listener instance
    */
    public void getRequest(final String finalUrl, final VolleyResponseListener listener) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, finalUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response); //pass string value
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "That didn't work!";
                        if (error.getMessage() != null) {
                            errorMessage += " " + error.getMessage();
                        }
                        listener.onError(errorMessage);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                // if we end up needing headers later: headers.put("Authorization", "Bearer " + YOUR_TOKEN); or something like that.
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    // POST Request
    public void postRequest(final String url, final JSONObject requestBody, final VolleyResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        String errorMessage = error.getMessage();
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMessage += "\n" + new String(error.networkResponse.data);
                        }
                        listener.onError(errorMessage);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Content-Type", "application/json");
                // if we end up needing headers later: headers.put("Authorization", "Bearer " + YOUR_TOKEN); or something like that.
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

//    // DELETE Request
    public void deleteRequest(final String userId, final VolleyResponseListener listener) {
        String urlWithId = url + "/users/" + userId; // Adjust URL to target specific resource for deletion

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, urlWithId, null,
                response -> {
                    try {
                        listener.onResponse(new JSONObject().put("message", "Deleted Successfully."));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> listener.onError("Delete Failed: " + error.toString())
        );

        requestQueue.add(jsonObjectRequest);
    }

//    // PUT Request
    public void putRequest(final JSONObject requestBody, final VolleyResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, requestBody,
                response -> listener.onResponse(response),
                error -> listener.onError(error.getMessage())
        );

        requestQueue.add(jsonObjectRequest);
    }

}
