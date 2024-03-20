package com.GameWFriends.ui.Game.UI.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.GameWFriends.APIServices.ServerInteractionCode.ServerTools;
import com.GameWFriends.APIServices.ServerInteractionCode.VolleyAPIService;
import com.GameWFriends.APIServices.ViewModel.GenericViewModel;
import com.GameWFriends.R;
import com.GameWFriends.databinding.FragmentChatBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    /**
     * This class is the fragment for the Account, Has buttons for the user to interact with to change
     * and edit their personal account.
     */


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
        private FragmentChatBinding binding;

        /**
         * The VolleyAPIService for the AccountFragment fragment
         */
        private VolleyAPIService apiService;

        /**
         * The ServerTools instance for all Server api table manipulation
         */
        private ServerTools servertools;


        /**
         * The buttons for the user to interact with to change and edit their personal account.
         */
        private Button ButtonAccountAdminTools;


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            /*Initialize the VolleyAPIService with the fragment's context, context is required for being able to use device resources find view by ID etc.*/
            apiService = new VolleyAPIService(requireContext());

            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_chat, container, false);

        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Init(view);
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

            // Initialize the ServerTools instance with the context, apiService, and ViewModel, will allow us to use server tools methods.
            servertools = new ServerTools(getContext(), apiService, mViewModel);

            viewObjects = new ArrayList<>();

            //button inits

            //TextView inits

            //add everything to the viewObjects list
            viewObjects.add(ButtonAccountAdminTools);
        }

        /**
         * Setup the listeners for the buttons in the fragment
         * Forwards to other methods to handle the logic once a click has occurred.
         * @param view the view for buttons and listeners to be recognized. View must be passed.
         */
        private void setupListeners(View view){

            ButtonAccountAdminTools.setOnClickListener(v -> onAdminToolsClicked());

        }
        /**
         * The method for when the admin tools button is clicked
         */
        private void onAdminToolsClicked() {
            startActivity(new Intent(getActivity(), com.GameWFriends.ui.AdminTools.AdminToolsActivity.class));
        }



}
