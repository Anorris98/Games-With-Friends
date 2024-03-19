package com.GameWFriends.ui.LoginandSignup;

import android.os.Bundle;
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
import com.GameWFriends.R;

import org.json.JSONObject;

/**
 * @author Alek Norris
 * @updated 2024-03-08, methods were all moved to ServerToolsDemoTwo In api, to keep things running smoothly, a stripped version of
 * the original method was left and within them is a call to the server tools method.
 * SignupFragment() is a fragment that allows an admin to perform various actions on user accounts
 * Through Buttons to access specific fragments.
 * Also was used while building the app to test calls and functions for functionality.
 */
public class SignupFragment extends Fragment {

    /**
     * The ViewModel for the SignupFragment() fragment
     */
    GenericViewModel mViewModel;
    /**
     * The VolleyAPIService for the SignupFragment() fragment
     */
    private VolleyAPIService apiService;

    /**
     * The ServerToolsDemoTwo instance for all Server api table manipulation
     */
    ServerTools serverTools;

    /**
     * The button for signing up.
     */
    private Button buttonSignup;

    /**
     * The button for logging in.
     */
    private Button buttonLogin;
    /**
     * The EditText for the email.
     */
    private EditText editTextEmail;
    /**
     * The EditText for the confirm email.
     */
    private EditText editTextConfirmEmail;
    /**
     * The EditText for the password.
     */
    private EditText editTextPassword;
    /**
     * The EditText for the confirm password.
     */
    private EditText editTextConfirmPassword;

    private EditText editTextDisplayName;

    private byte[] profilePhoto;

    /**
     * constructor for the SignupFragment()
     * @return a new instance of the SignupFragment()
     */
    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        /*Initialize the VolleyAPIService with the fragment's context, context is required for being able to use device resources find view by ID etc.*/
        apiService = new VolleyAPIService(requireContext());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the ViewModel variable with this view after on create.
        mViewModel = new ViewModelProvider(this).get(GenericViewModel.class);

        // Initialize the ServerToolsDemoTwo instance with the context, apiService, and ViewModel, will allow us to use server tools methods.
        serverTools = new ServerTools(getContext(), apiService, mViewModel);

        profilePhoto = new byte[0];

        //setup text fields and buttons
        getFields();

        // Setup button click listeners
        setupListeners(view);

    }

    /**
     * Setup the listeners for the buttons in the fragment
     *
     * @param view the view for buttons and listeners to be recognized. View must be passed.
     */
    private void setupListeners(View view) {

        //signup button, will sign up the user, then change them to the login fragment.
        buttonSignup.setOnClickListener(v -> {
            getFields();

            //first check that all fields do not equal null.
            if ((editTextDisplayName.getText().toString().equals("")) || editTextEmail.getText().toString().equals("") || editTextConfirmEmail.getText().toString().equals("") || editTextPassword.getText().toString().equals("") || editTextConfirmPassword.getText().toString().equals("")) {
                //toast letting the user know.
                Toast.makeText(getContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
            }
            else {
                    //check if the email and confirm email are the same
                    if (editTextEmail.getText().toString().equals(editTextConfirmEmail.getText().toString())) {
                        //check if the password and confirm password are the same
                        if (editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {
                            //call the server tools method to sign up the user
                            serverTools.registerUser(editTextEmail.getText().toString(), editTextPassword.getText().toString(), new CustomResponseHandler() {
                                /*
                                    on success, let the user know and change the fragment to the login
                                        fragment, while that is happening we will quickly add a
                                        user to the database and initalize the base user info for the
                                        account on the backend.
                                 */
                                @Override
                                public void onSuccess(JSONObject response) {
                                    Toast.makeText(getContext(), "Signup Success, Please Log in.", Toast.LENGTH_LONG).show();
                                    //get returned User ID from json Object.
                                    int userid = response.optInt("response", -1);
                                    // Initalize user info.
                                    serverTools.updateUserProfile(userid, editTextDisplayName.getText().toString(), "Empty", profilePhoto);

                                    try {
                                        ((LoginActivity) getActivity()).changeFragment(LoginFragment.newInstance());
                                    } catch (Exception e) {
                                        Log.e("LoginFragment", "Error changing fragment to loginFragment");
                                    }
                                }

                                @Override
                                public void onError(String message) {
                                    Toast.makeText(getContext(), "Signup Failed... ", Toast.LENGTH_LONG).show();
                                    //todo: Handle what type of error came back, if user already exists, if email is invalid, etc.
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                            Log.e("SignupFragment", "Passwords do not match");
                        }
                    } else {
                        Toast.makeText(getContext(), "Emails do not match", Toast.LENGTH_SHORT).show();
                        Log.e("SignupFragment", "Emails do not match");
                    }
            }

        });


        // Login Button, will change to fragment to the login fragment.
        buttonLogin.setOnClickListener(v -> {
            getFields();
            try{
                ((LoginActivity)getActivity()).changeFragment(LoginFragment.newInstance());
            }
            catch (Exception e){
                Log.e("LoginFragment", "Error changing fragment to SignupFragment");
            }
        });
    }


    private void getFields(){
        try {
            //email
            editTextEmail = getView().findViewById(R.id.editextsignupEmail);
            //confirm email
            editTextConfirmEmail = getView().findViewById(R.id.edittextsignupConfirmEmail);
            //password
            editTextPassword = getView().findViewById(R.id.edittextsignupPassword);
            //confirm password
            editTextConfirmPassword = getView().findViewById(R.id.etConfirmPassword);
            //signup button
            buttonLogin = getView().findViewById(R.id.btnSignupLogin);  //login button
            //login button
            buttonSignup = getView().findViewById(R.id.btnSignUpSignup);  //signup button
            //displayName
            editTextDisplayName = getView().findViewById(R.id.editextsignupDisplayName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
