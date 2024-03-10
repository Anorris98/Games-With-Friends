package com.GameWFriends.ui.LoginandSignup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.GameWFriends.APIServices.ServerInteractionCode.ServerTools;
import com.GameWFriends.APIServices.ServerInteractionCode.VolleyAPIService;
import com.GameWFriends.APIServices.ViewModel.GenericViewModel;
import com.GameWFriends.R;

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
     * The ServerTools instance for all Server api table manipulation
     */
    ServerTools serverTools;


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

        // Initialize the ViewModel variable with this view after on create.
        mViewModel = new ViewModelProvider(this).get(GenericViewModel.class);

        // Initialize the ServerTools instance with the context, apiService, and ViewModel, will allow us to use server tools methods.
        serverTools = new ServerTools(getContext(), apiService, mViewModel);

        // Observe the LiveData for changes and update the TextView accordingly
        // Update the TextView with the response (ide combined these two statements, left for note clarity.)
//        TextView textViewResponse = view.findViewById(R.id.Textview_ResponseFriend);  //text view for string response
//        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), textViewResponse::setText);

        // Setup button click listeners
        setupListeners(view);

    }

    /**
     * Setup the listeners for the buttons in the fragment
     *
     * @param view the view for buttons and listeners to be recognized. View must be passed.
     */
    private void setupListeners(View view) {

    }


}
