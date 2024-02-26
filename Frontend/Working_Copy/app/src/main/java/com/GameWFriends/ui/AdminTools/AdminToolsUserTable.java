package com.GameWFriends.ui.AdminTools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.GameWFriends.R;
import com.GameWFriends.VolleyAPIService;

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
        //text box declarations
//        TextView textViewResponse = view.findViewById(R.id.Textview_Response);  //text view for string response

        //button declarations
        Button buttonViewUserInfo = view.findViewById(R.id.buttonViewUserInfo); // View user Info button
        Button loginButton = view.findViewById(R.id.buttonLogin);               // login button
        Button buttonRegister = view.findViewById(R.id.buttonRegister);         // Register Button

        //listeners start here.
        buttonViewUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call getRequest from VolleyAPIService
               fetchUserProfile(69);

            }
        });

        // Setup similar click listeners for the rest.
    }


    public void fetchUserProfile(int userId) {
        apiService.getRequest(String.valueOf(userId), new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                mViewModel.setResponse("Response is:" + response);
            }
        });
    }
}
