package com.GameWFriends.ui.AdminTools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.GameWFriends.APIServices.GenericViewModel;
import com.GameWFriends.APIServices.ServerTools;
import com.GameWFriends.APIServices.VolleyAPIService;
import com.GameWFriends.R;

/**
 * @author Alek Norris
 * @updated 2024-03-08, methods were all moved to ServerTools In api, to keep things running smoothly, a stripped version of
 * the original method was left and within them is a call to the server tools method.
 * AdminToolsUserTable is a fragment that allows an admin to perform various actions on user accounts
 * Through Buttons to access specific fragments.
 * Also was used while building the app to test calls and functions for functionality.
 */
public class AdminToolsUserTable extends Fragment {

    /**
     * The ViewModel for the AdminToolsUserTable fragment
     */
    GenericViewModel mViewModel;
    /**
     * The VolleyAPIService for the AdminToolsUserTable fragment
     */
    private VolleyAPIService apiService;

    /**
     * The ServerTools instance for all Server api table manipulation
     */
    ServerTools serverTools;


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
        mViewModel = new ViewModelProvider(this).get(GenericViewModel.class);

        serverTools = new ServerTools(getContext(), apiService, mViewModel);


        // Setup button click listeners
        setupListeners(view);

        // Observe the LiveData for changes and update the TextView accordingly
        // Update the TextView with the response (ide combined these two statements, left for note clarity.)
        TextView textViewResponse = view.findViewById(R.id.Textview_ResponseFriend);  //text view for string response
        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), textViewResponse::setText);


    }

    /**
     * Setup the listeners for the buttons in the fragment
     *
     * @param view the view for buttons and listeners to be recognized. View must be passed.
     */
    private void setupListeners(View view) {
        //Edit text declarations
        EditText numberUserIDGroupID = view.findViewById(R.id.editTextGroupId);        // texview: Userid/GroupID
        EditText emailEmailAddress = view.findViewById(R.id.editTextGroup);           // texview: EmailAddress
        EditText textUsername = view.findViewById(R.id.editTextUserID);          // texview: Username, Havent Used Yet, but will need to eventually.
        EditText passwordPassword = view.findViewById(R.id.editTextRoleId);          // texview: Password


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
                byte[] profilePicture = {1, 2, 3, 4, 5};
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
     *
     * @param userIdtoDelete         the users ID
     * @param UserIdRequestingDelete the users current password
     */
    public void deleteUser(int userIdtoDelete, int UserIdRequestingDelete) {
        serverTools.deleteUser(userIdtoDelete, UserIdRequestingDelete);
    }


    /**
     * Function for changing a users password.
     *
     * @param userId User ID
     * @param oldpwd users old pwd
     * @param newpwd users new password
     */
    public void changeUserPassword(int userId, String oldpwd, String newpwd) {
        serverTools.changeUserPassword(userId, oldpwd, newpwd);
    }


    /**
     * Update user profile information, unsure how this works on the backend, but leave empty if no changes are desired.?
     *
     * @param userId             the users ID
     * @param displayName        the new user profile name
     * @param description        the new user description
     * @param profilePictureData the new profile photo information.
     */
    public void updateUserProfile(int userId, String displayName, String description, byte[] profilePictureData) {
        serverTools.updateUserProfile(userId, displayName, description, profilePictureData);
    }


    /**
     * Function converts edit text to an int.
     *
     * @param Id
     * @return the int value for the edittext/the user id
     */
    public int getUseriD(EditText Id) {
        return serverTools.getUseriD(Id);
    }

    /**
     * login to a user profile.
     * Not many notes, however, this is a slightly modified version of the register user,
     * any specifics can be found in that method.
     *
     * @param email    the email of the new user..
     * @param password the password the person wants to register
     */
    public void loginUser(String email, String password) {
        serverTools.loginUser(email, password);
    }


    /**
     * Retrieves the profile information for a specified user ID.
     *
     * @param userId the ID of the user whose profile is to be fetched.
     */
    public void fetchUserProfile(int userId) {
        serverTools.fetchUserProfile(userId);
    }


    /**
     * Registers a user.
     *
     * @param email    the email of the new user..
     * @param password the password the person wants to register
     */
    public void registerUser(String email, String password) {
        serverTools.registerUser(email, password);
    }


    /**
     * Retrieves all user data for a specific user
     */
    public void getAllUserData() {
        serverTools.getAllUserData();
    }

    /**
     * Specific Get request for get users (for demo 2 only currently.)
     *
     * @param finalUrl      the final url
     * @param userOrGroupId the user or group id
     * @param listener      the listener
     */
    public void getRequest(final String finalUrl, final int userOrGroupId, final VolleyAPIService.VolleyResponseListener listener) {
        serverTools.getRequest(finalUrl, userOrGroupId, listener);
    }

}
