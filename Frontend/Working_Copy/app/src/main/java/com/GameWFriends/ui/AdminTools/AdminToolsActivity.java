package com.GameWFriends.ui.AdminTools;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.GameWFriends.R;
import com.GameWFriends.databinding.ActivityAdminToolsBinding;

/**
 * This class is the activity for the Admin Tools, Has buttons for the user
 * To interact with  and be able to view the tools and manipulation tools.
 */
public class AdminToolsActivity extends AppCompatActivity {

    private ActivityAdminToolsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminToolsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup the listener for back stack changes
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener(){
            @Override
            public void onBackStackChanged() {
                // If there are no entries in the back stack (user navigated back), show the buttons again
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    ShowButtons();
                }
            }
        });


        Button ButtonUserTable          = findViewById(R.id.Button_UserTable);
        Button ButtonFriendGroupsTable  = findViewById(R.id.Button_friendGroupsTable);
        Button ButtonTrophiesTable      = findViewById(R.id.Button_TrophiesTable);
        Button ButtonAccessRoles        = findViewById(R.id.Button_AccessRolesTable);


        //TODO: condense this code, the 4 calls can all be put into a method and we can shrink this whole thing and make it more readable.
        binding.ButtonAccessRolesTable.setOnClickListener(view -> {
            HideButtons();
            // Create a new instance of the fragment
            AdminToolsAccessRolesTable userTableFragment = AdminToolsAccessRolesTable.newInstance();
            // Begin a fragment transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace the container with the new fragment
            transaction.replace(com.GameWFriends.R.id.fragment_container, userTableFragment);
            // Add the transaction to the back stack, so the user can navigate back
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        });


        binding.ButtonTrophiesTable.setOnClickListener(view -> {
            HideButtons();
            // Create a new instance of the fragment
            AdminToolsTrophiesTable userTableFragment = AdminToolsTrophiesTable.newInstance();
            // Begin a fragment transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace the container with the new fragment
            transaction.replace(com.GameWFriends.R.id.fragment_container, userTableFragment);
            // Add the transaction to the back stack, so the user can navigate back
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        });

        binding.ButtonFriendGroupsTable.setOnClickListener(view -> {
            HideButtons();
            // Create a new instance of the fragment
            AdminToolsFriendGroupsTable userTableFragment = AdminToolsFriendGroupsTable.newInstance();
            // Begin a fragment transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace the container with the new fragment
            transaction.replace(com.GameWFriends.R.id.fragment_container, userTableFragment);
            // Add the transaction to the back stack, so the user can navigate back
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        });

        //change to fragment. Hide the current stuff.
        binding.ButtonUserTable.setOnClickListener(view -> {
            HideButtons();
            // Create a new instance of the fragment
            AdminToolsUserTable userTableFragment = AdminToolsUserTable.newInstance();
            // Begin a fragment transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace the container with the new fragment
            transaction.replace(com.GameWFriends.R.id.fragment_container, userTableFragment);
            // Add the transaction to the back stack, so the user can navigate back
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        });
    }


    /**
     * Hides the buttons from the User views
     */
    private void HideButtons(){
        binding.ButtonUserTable.setVisibility(View.GONE);
        binding.ButtonFriendGroupsTable.setVisibility(View.GONE);
        binding.ButtonTrophiesTable.setVisibility(View.GONE);
        binding.ButtonAccessRolesTable.setVisibility(View.GONE);
        binding.TextviewAdminGreeting.setVisibility(View.GONE);
    }

    /**
     * Shows the buttons from the User views
     */
    private void ShowButtons(){
        binding.ButtonUserTable.setVisibility(View.VISIBLE);
        binding.ButtonFriendGroupsTable.setVisibility(View.VISIBLE);
        binding.ButtonTrophiesTable.setVisibility(View.VISIBLE);
        binding.ButtonAccessRolesTable.setVisibility(View.VISIBLE);
        binding.TextviewAdminGreeting.setVisibility(View.VISIBLE);
    }

}
