//package com.GameWFriends.BackupsandExtraThings;
//
//import android.content.Context;
//
//import androidx.annotation.Nullable;
//
//import com.GameWFriends.ui.AdminTools.Constants;
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
////Example of how to use this class in other activites:
//// VolleyAPIService apiService = new VolleyAPIService(getContext()); // Use 'this' if in an Activity
////
////apiService.deleteRequest(new VolleyAPIService.VolleyResponseListener() {
////    @Override
////    public void onError(String message) {
////        // Handle error
////    }
////
////    @Override
////    public void onResponse(String response) {
////        // Handle response
////    }
////});
//
//public class VolleyAPIServiceBeforeFlexableGet {
//
//    //made equal to base as to not have to refactor this code.
//    private String url = Constants.BASE_URL;
//    private RequestQueue requestQueue; // Volley request queue
//
//    // Constructor
//    public VolleyAPIServiceBeforeFlexableGet(Context context) {
//        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
//    }
//
//    // Interface for GET request callbacks
//    public interface VolleyResponseListener {
//        void onError(String message);
//        void onResponse(JSONObject response);
//    }
//
//
//    /**GetRequest function, allows a get request to be sent using a json object.
//     *
//     * @param finalUrl the full final url that will be getting sent
//     * @param listener Listener instance
//    */
//    public void getRequest(final String finalUrl, final VolleyResponseListener listener) {
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, finalUrl, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        listener.onResponse(response); //pass string value
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        String errorMessage = "That didn't work!";
//                        if (error.getMessage() != null) {
//                            errorMessage += " " + error.getMessage();
//                        }
//                        listener.onError(errorMessage);
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
//                // if we end up needing headers later: headers.put("Authorization", "Bearer " + YOUR_TOKEN); or something like that.
//                return headers;
//            }
//        };
//
//        requestQueue.add(jsonObjectRequest);
//    }
//
//    /** Post Request function, allows a post request to be sent to the desired url with a payload.
//     *
//     * @param finalUrl the full final url that will be getting sent
//     * @param listener Listener instance
//     * @param requestBody the already formated request body to be sent.
//     */
//    public void postRequest(final String finalUrl, final JSONObject requestBody, final VolleyResponseListener listener) {
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, finalUrl, requestBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        listener.onResponse(response); // Notify listener about the response
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle error
//                        String errorMessage = "Request error";
//                        if (error.getMessage() != null) {
//                            errorMessage = error.getMessage();
//                        }
//                        if (error.networkResponse != null && error.networkResponse.data != null) {
//                            errorMessage += "\n" + new String(error.networkResponse.data);
//                        }
//                        listener.onError(errorMessage); // Notify listener about the error
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json"); // Ensure the server knows we're sending JSON
//
//                return headers;
//            }
//        };
//
//        requestQueue.add(jsonObjectRequest); // Add the request to the queue
//    }
//
//    /** delete Request function, allows a post request to be sent to the desired url with a payload.
//     *
//     * @param finalUrl the full final url that will be getting sent
//     * @param listener Listener instance
//     * @param requestBody the already formated request body to be sent.
//     */
//    public void deleteRequest(final String finalUrl, @Nullable final JSONObject requestBody, final VolleyResponseListener listener) {
//        // Create a JsonObjectRequest with a null body if requestBody is null
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, finalUrl, requestBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        listener.onResponse(response); // Notify listener about the successful response
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Construct a more detailed error message
//                        String errorMessage = "Request error";
//                        if (error.getMessage() != null) {
//                            errorMessage = error.getMessage();
//                        }
//                        if (error.networkResponse != null && error.networkResponse.data != null) {
//                            errorMessage += "\n" + new String(error.networkResponse.data);
//                        }
//                        listener.onError(errorMessage); // Notify listener about the error
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//        };
//
//        // Add the request to the Volley queue
//        requestQueue.add(jsonObjectRequest);
//    }
//
//
////    public void deleteRequest(final String finalUrl, final JSONObject requestBody, final VolleyResponseListener listener) {
////        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, finalUrl, requestBody,
////                new Response.Listener<JSONObject>() {
////                    @Override
////                    public void onResponse(JSONObject response) {
////                        listener.onResponse(response); // Notify listener about the successful response
////                    }
////                },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        // Construct a more detailed error message
////                        String errorMessage = "Request error";
////                        if (error.getMessage() != null) {
////                            errorMessage = error.getMessage();
////                        }
////                        if (error.networkResponse != null && error.networkResponse.data != null) {
////                            errorMessage += "\n" + new String(error.networkResponse.data);
////                        }
////                        listener.onError(errorMessage); // Notify listener about the error
////                    }
////                }) {
////            @Override
////            public Map<String, String> getHeaders() throws AuthFailureError {
////                Map<String, String> headers = new HashMap<>();
////                headers.put("Content-Type", "application/json");
////
////                return headers;
////            }
////        };
////
////        // Use VolleySingleton to add the request to the queue
////        requestQueue.add(jsonObjectRequest);
////    }
//
//
//    /**  Put Request function, allows a post request to be sent to the desired url with a payload.
//     *
//     * @param finalUrl the full final url that will be getting sent
//     * @param listener Listener instance
//     * @param requestBody the already formated request body to be sent.
//     */
//    public void putRequest(final String finalUrl, final JSONObject requestBody, final VolleyResponseListener listener) {
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, finalUrl, requestBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // Notify listener about the successful response
//                        listener.onResponse(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle and construct a more detailed error message
//                        String errorMessage = "Request error";
//                        if (error.getMessage() != null) {
//                            errorMessage = error.getMessage();
//                        }
//                        if (error.networkResponse != null && error.networkResponse.data != null) {
//                            errorMessage += "\n" + new String(error.networkResponse.data);
//                        }
//                        // Notify listener about the error
//                        listener.onError(errorMessage);
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                // Set content type to JSON to inform the server about the type of the request body
//                headers.put("Content-Type", "application/json");
//
//                return headers;
//            }
//        };
//
//        // Add the request to the Volley request queue
//        requestQueue.add(jsonObjectRequest);
//    }
//
//
//
//
//}
