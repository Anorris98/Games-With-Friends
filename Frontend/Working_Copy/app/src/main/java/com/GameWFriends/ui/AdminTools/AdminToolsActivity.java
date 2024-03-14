package com.GameWFriends.ui.AdminTools;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.GameWFriends.R;
import com.GameWFriends.databinding.ActivityAdminToolsBinding;

/**
 * @author Alek
 * @updated 03/09/2024 Reduced code clutter, redundant code, as well as methods to make the code more modular and easier to read. while also adding javadocs
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

        setupListeners();
    }

    /**
     * This method is called when the activity is created and initializes the UI elements.
     */
    private void onCreateInit() {

        Button ButtonUserTable          = findViewById(R.id.Button_UserTable);
        Button ButtonFriendGroupsTable  = findViewById(R.id.Button_friendGroupsTable);
        Button ButtonTrophiesTable      = findViewById(R.id.Button_TrophiesTable);
        Button ButtonAccessRoles        = findViewById(R.id.Button_AccessRolesTable);

        setupListeners();
    }

    /**
     * This method is called when the activity is created and from the onCreateInit method. to set up listeners.
     */
    private void setupListeners() {
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

        binding.ButtonAccessRolesTable.setOnClickListener(view -> {
            changeFragment(AdminToolsAccessRolesTable.newInstance());
        });

        binding.ButtonTrophiesTable.setOnClickListener(view -> {
            changeFragment(AdminToolsTrophiesTable.newInstance());
        });

        binding.ButtonFriendGroupsTable.setOnClickListener(view -> {
            changeFragment(AdminToolsFriendGroupsTable.newInstance());
        });

        //change to fragment. Hide the current stuff.
        binding.ButtonUserTable.setOnClickListener(view -> {
            changeFragment(AdminToolsUserTable.newInstance());
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

    /**
     * Changes the fragment to the new fragment
     * @param fragment the new fragment to change to
     */
    public void changeFragment(Fragment fragment){
        // Hide UI elements if needed
        HideButtons();
        // Begin a fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace the container with the new fragment
        transaction.replace(com.GameWFriends.R.id.fragment_container, fragment);
        // Optionally add the transaction to the back stack
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

}
