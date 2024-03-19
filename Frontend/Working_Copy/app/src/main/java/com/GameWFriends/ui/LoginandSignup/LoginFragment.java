package com.GameWFriends.ui.LoginandSignup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.GameWFriends.APIServices.ServerInteractionCode.CustomResponseHandler;
import com.GameWFriends.APIServices.ServerInteractionCode.ServerTools;
import com.GameWFriends.APIServices.ServerInteractionCode.VolleyAPIService;
import com.GameWFriends.APIServices.ViewModel.GenericViewModel;
import com.GameWFriends.MainActivity;
import com.GameWFriends.R;
import com.GameWFriends.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Alek
* a fragment to handle the login in of a registered user.
 */
public class LoginFragment extends Fragment {

    /**
     * The handler for the checking process
     */
    private Runnable checkRunnable;

    /**
     * The handler for the checking process
     */
    private Handler checkHandler;
    /**
     * The singleton UserInfo instance for the user that is logged in.
     */
    UserInfo userinfo;
    /**
     * The ViewModel for the LoginFragment fragment
     */
    GenericViewModel mViewModel;
    /**
     * The VolleyAPIService for the LoginFragment fragment
     */
    private VolleyAPIService apiService;

    /**
     * The ServerToolsDemoTwo instance for all Server api table manipulation
     */
    private ServerTools servertools;

    /**
     * The JSONObject for the profile information
     */
    private JSONObject ProfileInformation;
    /**
     * The EditText for the email.
     */
    private EditText editTextEmail, editTextPassword;

    /**
     * The button for logging in.
     */
    private Button buttonLogin, buttonSignup;
    /**
     * The user id for the user that is logged in.
     */
    private Integer userid;


    /**
     * constructor for the LoginFragment
     * @return a new instance of the LoginFragment
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        /*Initialize the VolleyAPIService with the fragment's context, context is required for being able to use device resources find view by ID etc.*/
        apiService = new VolleyAPIService(requireContext());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginInit(view);
        setupListeners(view);

        checkHandler= new Handler(Looper.getMainLooper());

        checkRunnable = new Runnable() {
            @Override
            public void run() {
                // Check the value or state of something here
                if (userinfo != null && userinfo.isUserLoggedIn()) {

                    stopChecking();
                    //if user is logged in and initalized we want to transfer control back to the main activity.
                    //transfer back to main activity with the instance of userinfo.
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    //transfer control back to main and clear everything we've done on the stack.
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);

                } else {
                    // Otherwise, re-post this Runnable to check again after some delay
                    checkHandler.postDelayed(this, 1000);
                }
            }
        };



        //start checking the handler for user being logged in.
        startChecking();


    }
    /**
     * Initialize the LoginFragment fragment
     *
     * @param view the view for the fragment to be initialized. View must be passed.
     */
    private void LoginInit(View view) {

        // Initialize the ViewModel variable with this view after on create.
        mViewModel = new ViewModelProvider(this).get(GenericViewModel.class);

        // Initialize the ServerToolsDemoTwo instance with the context, apiService, and ViewModel, will allow us to use server tools methods.
        servertools = new ServerTools(getContext(), apiService, mViewModel);

        editTextEmail = view.findViewById(R.id.editextloginEmail);
        editTextPassword = view.findViewById(R.id.edittextloginPassword);
        buttonLogin = view.findViewById(R.id.buttonloginLogin);
        buttonSignup = view.findViewById(R.id.buttonloginSignUp);

    }

    /**
     * Setup the listeners for the buttons in the fragment
     *
     * @param view the view for buttons and listeners to be recognized. View must be passed.
     */
    private void setupListeners(View view) {


        // Set the listener for the login button
        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            servertools.loginUser(email, password, new CustomResponseHandler() {
                @Override
                public void onSuccess(JSONObject response) {
                    Toast.makeText(getContext(), "Login Success: ", Toast.LENGTH_LONG).show();
                    userid = response.optInt("response", -1);

                    if (userid != -1) {
                        // Initalize user info.
                        userinfo = UserInfo.getInstance(userid, password,email, servertools, getContext());


                    } else {
                        // Handle case where userId is not found or invalid
                        Toast.makeText(getContext(), "Something went wrong on our end.", Toast.LENGTH_LONG).show();
                        Log.e("LoginSuccess", "UserID not found in response");
                    }
                }
                @Override
                public void onError(String message) {
                    Toast.makeText(getContext(), "No matching username and password", Toast.LENGTH_LONG).show();
                }
            });



        });

        // Set the listener for the signup button.
        buttonSignup.setOnClickListener(v -> {
            try{
                ((LoginActivity)getActivity()).changeFragment(SignupFragment.newInstance());
            }
            catch (Exception e){
                Log.e("LoginFragment", "Error changing fragment to SignupFragment");
            }

        });
    }

    private void getprofileInformation(int userid){

        servertools.fetchUserProfile(userid, new CustomResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                ProfileInformation = response;
                try {
                    // Extract individual properties
                    String displayName = response.getString("displayname");
                    String description = response.getString("description");
                    String profilePicture = response.getString("profile-picture");

                    userinfo.setUsername(displayName);
                    userinfo.setBio(description);
                    userinfo.setProfilePhoto(profilePicture);


                } catch (JSONException e) {
                    e.printStackTrace();
                    //todo: Handle parsing error
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "No matching username and password", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startChecking() {
        checkRunnable.run(); // Start the checking process
    }

    private void stopChecking() {
        checkHandler.removeCallbacks(checkRunnable); // Stop the checking process
    }



}
