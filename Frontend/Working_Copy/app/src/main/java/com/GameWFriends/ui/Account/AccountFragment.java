package com.GameWFriends.ui.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.GameWFriends.APIServices.ServerInteractionCode.ServerTools;
import com.GameWFriends.APIServices.ServerInteractionCode.VolleyAPIService;
import com.GameWFriends.APIServices.ViewModel.GenericViewModel;
import com.GameWFriends.R;
import com.GameWFriends.UsefulFragmentandActivityTools;
import com.GameWFriends.databinding.FragmentAccountBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the fragment for the Account, Has buttons for the user to interact with to change
 * and edit their personal account.
 */
public class AccountFragment extends Fragment {

    /**
     * The Handler for the checkHandler
     */
    private List<View> viewObjects;
    /**
     * The UserInfo instance for the AccountFragment fragment
     */
    private GenericViewModel mViewModel;
    /**
     * The ViewModel for the AccountFragment fragment
     */
    private FragmentAccountBinding binding;

    /**
     * The VolleyAPIService for the AccountFragment fragment
     */
    private VolleyAPIService apiService;

    /**
     * The ServerTools instance for all Server api table manipulation
     */
    private ServerTools servertools;

    /**
     * The ImageButton for the AccountFragment fragment
     */
    private ImageButton imageButtonProfilePhoto;

    /**
     * The buttons for the user to interact with to change and edit their personal account.
     */
    private Button ButtonAccountChangeBio,ButtonAccountLogout,
            ButtonAccountChangeEmail, ButtonAccountChangePassword,
            ButtonAccountChangeUsername, ButtonAccountAdminTools;

    /**
     * The TextView for the AccountFragment fragment
     */
    private TextView editTextAccountUsername,editTextAccountBio;

    private String ARG_STRING_KEY;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        /*Initialize the VolleyAPIService with the fragment's context, context is required for being able to use device resources find view by ID etc.*/
        apiService = new VolleyAPIService(requireContext());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init(view);
        setupListeners(view);
//TODO: Uncomment these.
    }

    /**
     * Initialize the LoginFragment fragment
     * Sets up the mViewModel, serverTools, and the view objects.
     * @param view the view for the fragment to be initialized. View must be passed.
     */
    private void Init(View view){
        // Initialize the ViewModel variable with this view after on create.
        mViewModel = new ViewModelProvider(this).get(GenericViewModel.class);

        // Initialize the ServerTools instance with the context, apiService, and ViewModel, will allow us to use server tools methods.
        servertools = new ServerTools(getContext(), apiService, mViewModel);

        viewObjects = new ArrayList<>();

        //button inits
        imageButtonProfilePhoto = view.findViewById(R.id.imageButtonProfilePhoto);
        ButtonAccountChangeBio = view.findViewById(R.id.ButtonAccountChangeBio);
        ButtonAccountChangeUsername = view.findViewById(R.id.ButtonAccountChangeUsername);
        ButtonAccountChangePassword = view.findViewById(R.id.ButtonAccountChangePassword);
        ButtonAccountChangeEmail = view.findViewById(R.id.ButtonAccountChangeEmail);
        ButtonAccountLogout = view.findViewById(R.id.ButtonAccountLogout);
        ButtonAccountAdminTools = view.findViewById(R.id.Button_Account_AdminTools);


        //TextView inits
        editTextAccountUsername = view.findViewById(R.id.textViewUsername);
        editTextAccountBio = view.findViewById(R.id.textViewBio);

        //add everything to the viewObjects list
        viewObjects.add(imageButtonProfilePhoto);
        viewObjects.add(ButtonAccountChangeBio);
        viewObjects.add(ButtonAccountChangeUsername);
        viewObjects.add(ButtonAccountChangePassword);
        viewObjects.add(ButtonAccountChangeEmail);
        viewObjects.add(ButtonAccountLogout);
        viewObjects.add(editTextAccountUsername);
        viewObjects.add(editTextAccountBio);
        viewObjects.add(ButtonAccountAdminTools);
    }

    /**
     * Setup the listeners for the buttons in the fragment
     * Forwards to other methods to handle the logic once a click has occurred.
     * @param view the view for buttons and listeners to be recognized. View must be passed.
     */
    private void setupListeners(View view){
        imageButtonProfilePhoto.setOnClickListener(v -> onProfilePhotoClicked());
        ButtonAccountChangeBio.setOnClickListener(v -> onChangeBioClicked());
        ButtonAccountChangeUsername.setOnClickListener(v -> onChangeUsernameClicked());
        ButtonAccountChangePassword.setOnClickListener(v -> onChangePasswordClicked());
        ButtonAccountChangeEmail.setOnClickListener(v -> onChangeEmailClicked());
        ButtonAccountLogout.setOnClickListener(v -> onLogoutClicked());
        ButtonAccountAdminTools.setOnClickListener(v -> onAdminToolsClicked());

    }
    /**
     * The method for when the admin tools button is clicked
     */
    private void onAdminToolsClicked() {
        startActivity(new Intent(getActivity(), com.GameWFriends.ui.AdminTools.AdminToolsActivity.class));
    }

    /**
     * The method for when the profile photo is clicked
     */
    private void onProfilePhotoClicked() {
        //TODO: Handle profile photo click, maybe just bring up a box for users to use? no need to hide?
    }

    /**
     * The method for when the bio is clicked
     */
    private void onChangeBioClicked() {
        //TODO: Handle change bio click, same as above?
    }

    /**
     * The method for when the username is clicked
     */
    private void onChangeUsernameClicked() {
        ARG_STRING_KEY = "username";
        //hide everything then change fragments.
        UsefulFragmentandActivityTools.hideUiElements(viewObjects);//TODO: Remove this, use change fragment from main activity, pass in a list of view objects.
//        ((MainActivity)getActivity()).changeFragment(AccountChangeInfoFragment.newInstance());
        //TODO: Need to pass a string for the change info fragment

    }

    /**
     * The method for when the password is clicked
     */
    private void onChangePasswordClicked() {
        ARG_STRING_KEY = "password";
        //hide everything then change fragments.
        UsefulFragmentandActivityTools.hideUiElements(viewObjects);//TODO: Remove this, use change fragment from main activity, pass in a list of view objects.
        //TODO: Same as above but will use password information and not username.
    }

    /**
     * The method for when the email is clicked
     */
    private void onChangeEmailClicked() {
        ARG_STRING_KEY = "email";
        //hide everything then change fragments.
        UsefulFragmentandActivityTools.hideUiElements(viewObjects);//TODO: Remove this, use change fragment from main activity, pass in a list of view objects.
        //TODO: same as above but will use email information.
    }

    /**
     * The method for when the logout is clicked
     */
    private void onLogoutClicked() {
        //TODO: Handle logout click
    }

}

