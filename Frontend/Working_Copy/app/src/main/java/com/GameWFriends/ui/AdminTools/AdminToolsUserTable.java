package com.GameWFriends.ui.AdminTools;

import android.os.Bundle;
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
import com.GameWFriends.VolleyAPIService;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminToolsUserTable extends Fragment {

    private AdminToolsUserTableViewModel mViewModel;
    private VolleyAPIService apiService;

    public static AdminToolsUserTable newInstance() {
        return new AdminToolsUserTable();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Initialize the VolleyAPIService with the fragment's context
        apiService = new VolleyAPIService(getContext());
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
        TextView textViewResponse = view.findViewById(R.id.Textview_Response);  //text view for string response
        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), textViewResponse::setText);


    }

    private void setupListeners(View view) {
//      Edit text declarations delete when done with this part
        EditText numberUserIDGroupID = view.findViewById(R.id.editTextNumberID);        // texview: Userid/GroupID
        EditText emailEmailAddress  = view.findViewById(R.id.editTextTextEmailAddress); // texview: EmailAddress
        EditText textUsername       = view.findViewById(R.id.editTextUsername);         // texview: Username
        EditText passwordPassword   = view.findViewById(R.id.editTextPassword);         // texview: Password

        //edit text declarations for later use and getting values.
//        int userGroupID;
//        String emailAddress;
//        String username;
//        String password;


        //convert to strings and usable int.
//        int userIDGroupID = Integer.parseInt(numberUserIDGroupID.getText().toString());
//        String emailAddress = emailEmailAddress.getText().toString();
//        String username = textUsername.getText().toString();
//        String password = passwordPassword.getText().toString();



        //button declarations
        Button buttonRegister = view.findViewById(R.id.buttonRegister);             // button: Register
        Button buttonLogin = view.findViewById(R.id.buttonLogin);                   // button: login
        Button buttonViewUserInfo = view.findViewById(R.id.buttonViewUserInfo);     // button: View current user info
        Button buttonUpdateUser = view.findViewById(R.id.buttonUpdateUser);         // button: Updated User account information, display name, Profile Picture, Bio/description
        Button buttonUpdatePassword = view.findViewById(R.id.buttonUpdatePassword); // button: update Password/change password
        Button buttonDeleteUser = view.findViewById(R.id.buttonDeleteUser);         // button: User Account Delete

        //listeners start here
        //TODO: set up registration button.
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

        //Todo Setup ability to log in/return the user info
        buttonLogin.setOnClickListener(new View.OnClickListener() {             // Login
            @Override
            public void onClick(View v) {
            //take username and password and set it using a post, depending on return, we can act on logging in or logging out.
            }
        });


        buttonViewUserInfo.setOnClickListener(new View.OnClickListener() {      // View User info
            @Override
            public void onClick(View v) {
                //get int value
                int userIDGroupID;

                try {
                    userIDGroupID = Integer.parseInt(numberUserIDGroupID.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Error: please Enter a number ", Toast.LENGTH_LONG).show();

                    //TODO: make sure this is is changed to the correct return code.
                    //just incase set it to error 402
                    userIDGroupID = 402;
                }


                // Call getRequest from VolleyAPIService
               fetchUserProfile(userIDGroupID);
            }
        });

        //todo: ALlow user info to be updated
        buttonUpdateUser.setOnClickListener(new View.OnClickListener() {        // Update user info
            @Override
            public void onClick(View v) {
                //getuser id field and Put the payload and ship it to the front end.

            }
        });

        //Todo: Allow a user/us to change account passwords
        buttonUpdatePassword.setOnClickListener(new View.OnClickListener() {    //Update User Password
            @Override
            public void onClick(View v) {
//                Need userID to send, then must send OldPwd, and new password to server.

            }
        });

        //Todo: Allow us or the user to delete their account from the database.
        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {        //Delete user
            @Override
            public void onClick(View v) {
        //get userID and password, load into payload and send it using the UserID and delete to front end.
            }
        });

    }


    /**
     * Retrieves the profile for a specified user ID.
     *
     * @param userId the ID of the user whose profile is to be fetched.
     */
    public void fetchUserProfile(int userId) {
        // Assuming 'baseUrl' is a String variable holding your API's base URL
        String baseUrl = "https://your.api.url"; // Replace with your actual base URL
        String finalUrl = Constants.BASE_URL + "/users/" + userId; // Construct the full URL

        apiService.getRequest(finalUrl, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Display error message
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Convert the JSONObject to a String with indentation for formatting
                    String formattedResponse = response.toString(4); // Indent with 4 spaces

                    // Display the formatted JSON response
                    mViewModel.setResponse("Response is:\n" + formattedResponse);
                } catch (JSONException e) {
                    // Handle JSON parsing error
                    Toast.makeText(getContext(), "Error handling JSON", Toast.LENGTH_LONG).show();
                }
                // Optionally, you might want to move or remove this success toast to suit your flow
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
    //TODO: Markus refused to add username to registration call, so we will have to update this to take in a username as well, then make a call to set the username.
    public void registerUser(String email, String password) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("email", email);
            postData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for registration", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.postRequest("register_endpoint", postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "Registration Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "Registration Success", Toast.LENGTH_LONG).show();
                mViewModel.setResponse("Registration response: " + response);
            }

        });
    }

}
