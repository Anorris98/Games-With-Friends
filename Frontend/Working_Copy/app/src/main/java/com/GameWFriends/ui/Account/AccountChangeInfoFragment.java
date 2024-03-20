package com.GameWFriends.ui.Account;

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
import com.GameWFriends.databinding.FragmentAccountBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the fragment for the ChangeInfoFragment, Has buttons for the user to interact with to change
 * and edit their personal account.
 */
public class AccountChangeInfoFragment extends Fragment {

    /**
     * The Handler for the checkHandler
     */
    private List<View> viewObjects;
    /**
     * The UserInfo instance for the ChangeInfo fragment
     */
    private GenericViewModel mViewModel;
    /**
     * The ViewModel for the ChangeInfo fragment
     */
    private FragmentAccountBinding binding;

    /**
     * The VolleyAPIService for the Change Info fragment
     */
    private VolleyAPIService apiService;

    /**
     * The ServerTools instance for all Server api table manipulation
     */
    private ServerTools servertools;

    /**
     * The type of change info is being loaded.
     */
    private String type;

    public static AccountChangeInfoFragment newInstance(String yourString) {
        AccountChangeInfoFragment fragment = new AccountChangeInfoFragment();
        Bundle args = new Bundle();
        args.putString("type", yourString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            String yourString = getArguments().getString("type");
            type = yourString;

        }

        /*Initialize the VolleyAPIService with the fragment's context, context is required for being able to use device resources find view by ID etc.*/
        apiService = new VolleyAPIService(requireContext());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_change_info, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init(view);//TODO: finish implementing these.
        setupListeners(view);
    }

    /**
     * Initialize the LoginFragment fragment
     * Sets up the mViewModel, serverTools, and the view objects.
     * @param view the view for the fragment to be initialized. View must be passed.
     */
    private void Init(View view){
        // Initialize the ViewModel variable with this view after on create.
        mViewModel = new ViewModelProvider(this).get(GenericViewModel.class);
        // Initialize the ServerToolsDemoTwo instance with the context, apiService, and ViewModel, will allow us to use server tools methods.
        servertools = new ServerTools(getContext(), apiService, mViewModel);
        viewObjects = new ArrayList<>();

        //button inits

    }

    /**
     * Setup the listeners for the buttons in the fragment
     * Forwards to other methods to handle the logic once a click has occurred.
     * @param view the view for buttons and listeners to be recognized. View must be passed.
     */
    private void setupListeners(View view){

    }

}

