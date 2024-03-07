package com.GameWFriends.ui.AdminTools;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.GameWFriends.R;
import com.GameWFriends.APIServices.VolleyAPIService;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * AdminToolsUserTable is a fragment that allows an admin to perform various actions on user accounts
 * Through Buttons to access specific fragments.
 * Also was used while building the app to test calls and functions for functionality.
 */
public class AdminToolsUserTable extends Fragment {

    /**
     * The ViewModel for the AdminToolsUserTable fragment
     */
    private AdminToolsUserTableViewModel mViewModel;
    /**
     * The VolleyAPIService for the AdminToolsUserTable fragment
     */
    private VolleyAPIService apiService;



    public static AdminToolsUserTable newInstance() {
        return new AdminToolsUserTable();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Initialize the VolleyAPIService with the fragment's context, context is required for being able to use device resources
        //find view by ID etc.
        apiService = new VolleyAPIService(requireContext());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_tools_user_table, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdminToolsUserTableViewModel.class);


        // Setup button click listeners
        setupListeners(view);

        // Observe the LiveData for changes and update the TextView accordingly
        // Update the TextView with the response (ide combined these two statements, left for note clarity.)
        TextView textViewResponse = view.findViewById(R.id.Textview_ResponseFriend);  //text view for string response
        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), textViewResponse::setText);


    }

    /**
     * Setup the listeners for the buttons in the fragment
     * @param view the view for buttons and listeners to be recognized. View must be passed.
     */
    private void setupListeners(View view) {
        //Edit text declarations
        EditText numberUserIDGroupID = view.findViewById(R.id.editTextGroupId);        // texview: Userid/GroupID
        EditText emailEmailAddress  = view.findViewById(R.id.editTextGroup);           // texview: EmailAddress
        EditText textUsername       = view.findViewById(R.id.editTextUserID);          // texview: Username, Havent Used Yet, but will need to eventually.
        EditText passwordPassword   = view.findViewById(R.id.editTextRoleId);          // texview: Password



        //button declarations
        Button buttonRegister = view.findViewById(R.id.buttonCreateFriendGroup);             // button: Register
        Button buttonLogin = view.findViewById(R.id.buttonGetFriendGroupsUserIsIn);                   // button: login
        Button buttonViewUserInfo = view.findViewById(R.id.buttonViewUserInfo);     // button: View current user info
        Button buttonUpdateUser = view.findViewById(R.id.buttonUpdateFriendGroup);         // button: Updated User account information, display name, Profile Picture, Bio/description
        Button buttonUpdatePassword = view.findViewById(R.id.buttonGetUsersInGroup); // button: update Password/change password
        Button buttonDeleteUser = view.findViewById(R.id.buttonDeleteFriendGroup);         // button: User Account Delete
        Button buttonGetAllUserData = view.findViewById(R.id.buttonGetAllUserInfo);         // button: Get all users

        //listeners start here

        buttonRegister.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                //assign to strings
                String emailAddress = emailEmailAddress.getText().toString();
                String password = passwordPassword.getText().toString();

                //call method to handle this part
                registerUser(emailAddress, password);

            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {             // Login
            @Override
            public void onClick(View v) {
                String emailAddress = emailEmailAddress.getText().toString();
                String password = passwordPassword.getText().toString();

                //call login method
                loginUser(emailAddress, password);
            }
        });


        buttonViewUserInfo.setOnClickListener(new View.OnClickListener() {      // View User info
            @Override
            public void onClick(View v) {
                //get the id entered here.
                int finalId = getUseriD(numberUserIDGroupID);

                // Call getRequest from VolleyAPIService
               fetchUserProfile(finalId);
            }
        });

        buttonUpdateUser.setOnClickListener(new View.OnClickListener() {        // Update user info
            @Override
            public void onClick(View v) {

                int finalId = getUseriD(numberUserIDGroupID);
                //getusername and put it in a string.

                // Call PutRequest from VolleyAPIService
                String displayName = textUsername.getText().toString();
                String description = "I am vibing ^-^";
                byte[] profilePicture = {1,2,3,4,5};
                updateUserProfile(finalId, displayName, description, profilePicture);


            }
        });

        buttonUpdatePassword.setOnClickListener(new View.OnClickListener() {    //Update User Password
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(numberUserIDGroupID);

                String oldpwd = emailEmailAddress.getText().toString();
                String newpwd = passwordPassword.getText().toString();

                changeUserPassword(finalId, oldpwd, newpwd);

//                Need userID to send, then must send OldPwd, and new password to server.

            }
        });


        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {        //Delete user
            @Override
            public void onClick(View v) {
                int IdtoDelete = getUseriD(numberUserIDGroupID);
                int UserIdRequestingDelete = getUseriD(numberUserIDGroupID); //for testing pass the same variable.

                deleteUser(IdtoDelete, UserIdRequestingDelete);

            }
        });
        //get all users button, then call method GetAllUsers
        buttonGetAllUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllUserData();
            }
        });

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
                Toast.makeText(getContext(), "User Deletion Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Success message context for user deletion
                Toast.makeText(getContext(), "User Deletion Success", Toast.LENGTH_LONG).show();
                try {
                    // Optionally display the response for demo purposes
                    String formattedResponse = response.toString(4); // 4 spaces for indentation
                    mViewModel.setResponse("User deletion response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error parsing user deletion response", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getContext(), "Error creating JSON object for password change", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.putRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Error message context for password change
                Toast.makeText(getContext(), "Password Change Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Success message context for password change
                Toast.makeText(getContext(), "Password Change Success", Toast.LENGTH_LONG).show();
                try {
                    //we only display for demo 2 purposes.
                    String formattedResponse = response.toString(4); // 4 spaces for indentation
                    mViewModel.setResponse("Password change response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error parsing password change response", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.putRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Error message context for profile update
                Toast.makeText(getContext(), "Profile Update Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Success message context for profile update
                Toast.makeText(getContext(), "Profile Update Success", Toast.LENGTH_LONG).show();
                try {
                    // Optionally format the response for readability
                    String formattedResponse = response.toString(4); // 4 spaces for indentation
                    mViewModel.setResponse("Profile update response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error parsing profile update response", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getContext(), "Error: please Enter a number ", Toast.LENGTH_LONG).show();


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
            Toast.makeText(getContext(), "Error creating JSON object for login", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.postRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {

                Toast.makeText(getContext(), "Login Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(getContext(), "Login Success", Toast.LENGTH_LONG).show();
                try {
                    //this is currently being used to see the responses in a text for demo 2
                    String formattedResponse = response.toString(4); // Indent with 4 spaces for readability
                    mViewModel.setResponse("Login response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error parsing login response", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    //this is currently being used to see the responses in a text for demo 2
                    String formattedResponse = response.toString(4); // Indent with 4 spaces
                    mViewModel.setResponse("Response is:\n" + formattedResponse);
                } catch (JSONException e) {
                    // Handle JSON parsing error
                    Toast.makeText(getContext(), "Error handling JSON", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * Registers a user.
     *
     * @param email the email of the new user..
     * @param password the password the person wants to register
     */

//    public void registerUser(String email, String password) {
//        String finalUrl = Constants.BASE_URL + "/users";
//
//        JSONObject postData = new JSONObject();
//
//        try {
//            postData.put("email", email);
//            postData.put("password", password);
//            //when needing to add a username with the todo later, add it here.
//            // postData.put("username", username);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Toast.makeText(getContext(), "Error creating JSON object for registration", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        apiService.postRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
//            @Override
//            public void onError(String message) {
//                Log.e("RegistrationError", message);
//                Toast.makeText(getContext(), "Registration Error: " + message, Toast.LENGTH_LONG).show();
//                mViewModel.setResponse("Registration response: " + message);
//            }
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Toast.makeText(getContext(), "Registration Success", Toast.LENGTH_LONG).show();
//                try {
//                    //this is currently being used to see the responses in a text for demo 2
//                    String formattedResponse = response.toString(4); // Indent with 4 spaces for readability
//                    mViewModel.setResponse("Registration response: " + formattedResponse);
//                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "Error parsing registration response", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }

    public void registerUser(String email, String password) {
        String finalUrl = Constants.BASE_URL + "/users";

        JSONObject postData = new JSONObject();

        try {
            postData.put("email", email);
            postData.put("password", password);
            //when needing to add a username with the todo later, add it here.
            // postData.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for registration", Toast.LENGTH_SHORT).show();
            return;
        }
        apiService.postRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e("RegistrationError", message);
                Toast.makeText(getContext(), "Registration Error: " + message, Toast.LENGTH_LONG).show();
                mViewModel.setResponse("Registration response: " + message);
            }

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "Registration Success", Toast.LENGTH_LONG).show();
                try {
                    //this is currently being used to see the responses in a text for demo 2
                    String formattedResponse = response.toString(4); // Indent with 4 spaces for readability
                    mViewModel.setResponse("Registration response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error parsing registration response", Toast.LENGTH_LONG).show();
                }
            }
        });
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
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    //this is currently being used to see the responses in a text for demo 2
                    String formattedResponse = response.toString(4); // Indent with 4 spaces
                    mViewModel.setResponse("Response is:\n" + formattedResponse);
                } catch (JSONException e) {
                    // Handle JSON parsing error
                    Toast.makeText(getContext(), "Error handling JSON", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
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
