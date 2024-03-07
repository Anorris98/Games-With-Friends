package com.GameWFriends.APIServices;

import android.content.Context;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import com.GameWFriends.ui.AdminTools.Constants;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserTools {

    private VolleyAPIService apiService;
    public Context context;
    private ToolsInterface mViewModel;

    public UserTools(Context context, VolleyAPIService apiService, ToolsInterface viewModel) {
        this.context = context;
        this.apiService = apiService;
        this.mViewModel = mViewModel;
    }

    /**
     * Function for Deleting a user
     * @param userIdtoDelete the users ID
     * @param UserIdRequestingDelete the users current password
     */
    public void deleteUser(int userIdtoDelete, int UserIdRequestingDelete) {
        String finalUrl = Constants.BASE_URL + "/users/" + userIdtoDelete;

        apiService.deleteRequest(finalUrl, UserIdRequestingDelete, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Error message context for user deletion
                Toast.makeText(context, "User Deletion Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Success message context for user deletion
                Toast.makeText(context, "User Deletion Success", Toast.LENGTH_LONG).show();
                try {
                    // Optionally display the response for demo purposes
                    String formattedResponse = response.toString(4); // 4 spaces for indentation
                    mViewModel.setResponse("User deletion response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(context, "Error parsing user deletion response", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * Function for changing a users password.
     * @param userId User ID
     * @param oldpwd users old pwd
     * @param newpwd users new password
     */
    public void changeUserPassword(int userId, String oldpwd, String newpwd) {
        String finalUrl = Constants.BASE_URL + "/users/" + userId + "/password";

        JSONObject postData = new JSONObject();
        try {
            postData.put("oldPassword", oldpwd);
            postData.put("newPassword", newpwd);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error creating JSON object for password change", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.putRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Error message context for password change
                Toast.makeText(context, "Password Change Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Success message context for password change
                Toast.makeText(context, "Password Change Success", Toast.LENGTH_LONG).show();
                try {
                    //we only display for demo 2 purposes.
                    String formattedResponse = response.toString(4); // 4 spaces for indentation
                    mViewModel.setResponse("Password change response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(context, "Error parsing password change response", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /** Update user profile information, unsure how this works on the backend, but leave empty if no changes are desired.?
     * @param userId the users ID
     * @param displayName the new user profile name
     * @param description the new user description
     * @param profilePictureData the new profile photo information.
     *
     */
    public void updateUserProfile(int userId, String displayName, String description, byte[] profilePictureData) {
        String finalUrl = Constants.BASE_URL + "/users/" + userId; // URL to the user update endpoint

        // Convert the binary data of the profile picture to a Base64 encoded string
        String profilePictureBase64 = Base64.encodeToString(profilePictureData, Base64.DEFAULT);

        JSONObject postData = new JSONObject();
        try {
            postData.put("displayName", displayName);
            postData.put("description", description);
            postData.put("profilePicture", profilePictureBase64); // Include the Base64 encoded picture data
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.putRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Error message context for profile update
                Toast.makeText(context, "Profile Update Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Success message context for profile update
                Toast.makeText(context, "Profile Update Success", Toast.LENGTH_LONG).show();
                try {
                    // Optionally format the response for readability
                    String formattedResponse = response.toString(4); // 4 spaces for indentation
                    mViewModel.setResponse("Profile update response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(context, "Error parsing profile update response", Toast.LENGTH_LONG).show();
                }
            }
        });
    }




    /**Function converts edit text to an int.
     *
     * @param Id
     * @return the int value for the edittext/the user id
     */
    public int getUseriD(EditText Id){
        int userIDGroupID;

        try {
            userIDGroupID = Integer.parseInt(Id.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Error: please Enter a number ", Toast.LENGTH_LONG).show();


            //just incase set it to error 402 for now.
            userIDGroupID = 402;
        }
        return userIDGroupID;
    }

    /**
     * login to a user profile.
     * Not many notes, however, this is a slightly modified version of the register user,
     * any specifics can be found in that method.
     *
     * @param email the email of the new user..
     * @param password the password the person wants to register
     */
    public void loginUser(String email, String password) {
        String finalUrl = Constants.BASE_URL + "/login";

        JSONObject postData = new JSONObject();
        try {
            postData.put("email", email);
            postData.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error creating JSON object for login", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.postRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {

                Toast.makeText(context, "Login Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, "Login Success", Toast.LENGTH_LONG).show();
                try {
                    //this is currently being used to see the responses in a text for demo 2
                    String formattedResponse = response.toString(4); // Indent with 4 spaces for readability
                    mViewModel.setResponse("Login response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(context, "Error parsing login response", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    /**
     * Retrieves the profile information for a specified user ID.
     *
     * @param userId the ID of the user whose profile is to be fetched.
     */
    public void fetchUserProfile(int userId) {
        String finalUrl = Constants.BASE_URL + "/users/" + userId;

        apiService.getRequest(finalUrl,  userId, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Display error message
                Toast.makeText(context, "Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    //this is currently being used to see the responses in a text for demo 2
                    String formattedResponse = response.toString(4); // Indent with 4 spaces
                    mViewModel.setResponse("Response is:\n" + formattedResponse);
                } catch (JSONException e) {
                    // Handle JSON parsing error
                    Toast.makeText(context, "Error handling JSON", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * Registers a user.
     *
     * @param email the email of the new user..
     * @param password the password the person wants to register
     */

    public void registerUser(String email, String password) {

    }


    /**
     * Retrieves all user data for a specific user
     */
    public void getAllUserData() {
        String finalUrl = Constants.BASE_URL + "/users";

        apiService.getRequest(finalUrl, 0, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Display error message
                Toast.makeText(context, "Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    //this is currently being used to see the responses in a text for demo 2
                    String formattedResponse = response.toString(4); // Indent with 4 spaces
                    mViewModel.setResponse("Response is:\n" + formattedResponse);
                } catch (JSONException e) {
                    // Handle JSON parsing error
                    Toast.makeText(context, "Error handling JSON", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Specific Get request for get users (for demo 2 only currently.)
     * @param finalUrl the final url
     * @param userOrGroupId the user or group id
     * @param listener the listener
     */
    public void getRequest(final String finalUrl,  final int userOrGroupId, final VolleyAPIService.VolleyResponseListener listener) {


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
                return headers;
            }
        };
    }

    }
