package com.GameWFriends.APIServices.ServerInteractionCode;

import android.content.Context;

import com.GameWFriends.Constants;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to make formated API requests using Volley library.
 */
public class VolleyAPIService {


    /**
     * The base url for the API
     */
    private String url = Constants.BASE_URL;

    /**
     * The request queue
     */
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
    public void getRequest(final String finalUrl, final int userOrGroupId, final VolleyResponseListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalUrl,
                response -> {
                    try {
                        String trimmedResponse = response.trim();
                        JSONObject responseObject = new JSONObject();

                        JSONObject response1 = new JSONObject(response);    //todo, CHECK IF THIS WORKS ON SERVER, IF SO, WE CNA DELETE ALOT OF CODE.

                        // Notify listener about the successful response
                        listener.onResponse(response1);     //WE PASS IT HERE.
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onError("Error processing the response");
                    }
                },
                error -> {
                    String errorMessage = "That didn't work!";
                    if (error.getMessage() != null) {
                        errorMessage += " " + error.getMessage();
                    }
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        errorMessage += "\n" + new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    }
                    listener.onError(errorMessage);
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + userOrGroupId);
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }



    /** Post Request function, allows a post request to be sent to the desired url with a payload.
     *
     * @param finalUrl the full final url that will be getting sent
     * @param listener Listener instance
     * @param requestBody the already formated request body to be sent.
     */
    public void postRequest(final String finalUrl, final JSONObject requestBody, final VolleyResponseListener listener) {
        // Convert the JSON object to a string
        final String jsonString = requestBody.toString();

        // Use StringRequest to handle raw string response
        StringRequest stringRequest = new StringRequest(Request.Method.POST, finalUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Assuming the response is a plain integer in string format, parse it
                            int responseInt = Integer.parseInt(response.trim());
                            // Create a JSONObject to pass back to the listener, simulating the original expected behavior
                            JSONObject responseObject = new JSONObject();
                            responseObject.put("response", responseInt);
                            // Notify listener with a JSONObject containing the integer
                            listener.onResponse(responseObject);
                        } catch (NumberFormatException | JSONException e) {
                            e.printStackTrace();
                            listener.onError("Error parsing response to integer or creating response object");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        String errorMessage = "Request error";
                        if (error.getMessage() != null) {
                            errorMessage = error.getMessage();
                        }
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMessage += "\n" + new String(error.networkResponse.data);
                        }
                        listener.onError(errorMessage); // Notify listener about the error
                    }
                })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                // Override getBody to send the JSON string as the body of the request
                return jsonString.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                // Set the content type to application/json
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json"); // Ensure the server knows we're sending JSON
                return headers;
            }
        };

        // Assuming 'requestQueue' is already initialized
        requestQueue.add(stringRequest); // Add the request to the queue
    }


    /** delete Request function, allows a post request to be sent to the desired url with a payload.
     *
     * @param finalUrl the full final url that will be getting sent
     * @param listener Listener instance
     */
    public void deleteRequest(final String finalUrl, final int UserRequestingDelete, final VolleyResponseListener listener) {
        // Use StringRequest for the DELETE request
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, finalUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Assuming the response might be empty for a DELETE operation,
                            // but we still create a JSONObject to pass a message back to the listener
                            JSONObject responseObject = new JSONObject();
                            // If the server returns a message in the response, you can include it
                            if (!response.trim().isEmpty()) {
                                responseObject.put("message", response);
                            } else {
                                // If there's no message, you can set a default message
                                responseObject.put("message", "Deletion successful");
                            }
                            // Notify listener about the successful response
                            listener.onResponse(responseObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // In case of any JSONException while constructing the response object
                            listener.onError("Error processing the delete response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Construct a more detailed error message
                        String errorMessage = "Request error";
                        if (error.getMessage() != null) {
                            errorMessage += ": " + error.getMessage();
                        }
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMessage += "\n" + new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        }
                        // Notify listener about the error
                        listener.onError(errorMessage);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                // Assuming 'Authorization' header is used for the identity of the user requesting the delete
                headers.put("Authorization", "Bearer " + UserRequestingDelete);
                return headers;
            }
        };

        // Assuming 'requestQueue' is already initialized
        requestQueue.add(stringRequest);
    }



    /**  Put Request function, allows a post request to be sent to the desired url with a payload.
     *
     * @param finalUrl the full final url that will be getting sent
     * @param listener Listener instance
     * @param requestBody the already formated request body to be sent.
     */
    public void putRequest(final String finalUrl, final JSONObject requestBody, final VolleyResponseListener listener) {
        // Convert the JSON object to a string for the request body
        final String requestBodyString = requestBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, finalUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Assuming the response is a string that you want to directly pass to the listener
                        try {
                            // Directly notifying the listener with the string response
                            listener.onResponse(new JSONObject().put("response", response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError("Error creating JSON object from response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle and construct a more detailed error message
                        String errorMessage = "Request error";
                        if (error.getMessage() != null) {
                            errorMessage += ": " + error.getMessage();
                        }
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMessage += "\n" + new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        }
                        // Notify listener about the error
                        listener.onError(errorMessage);
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBodyString.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                // Set the content type to application/json; charset=utf-8
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Assuming 'requestQueue' is already initialized
        requestQueue.add(stringRequest);
    }





}
