package com.GameWFriends.ui.LoginandSignup;

import android.os.Bundle;
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
 * @author Alek
* a fragment to handle the login in of a registered user.
 */
public class LoginFragment extends Fragment {

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
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonSignup;

    private int userid;


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
//                    userid = Integer.parseInt(response.toString());
                    userid = response.optInt("response");
//TODO need to have this update the user info stuff.
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(getContext(), "No matching username and password", Toast.LENGTH_LONG).show();

                }
            });



        });

        // Set the listener for the signup button //TODO: make sure this works.
        buttonSignup.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, SignupFragment.newInstance()).commitNow();
        });
    }


}
