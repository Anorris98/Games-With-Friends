package com.GameWFriends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.GameWFriends.databinding.ActivityMainBinding;
import com.GameWFriends.ui.LoginandSignup.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/**
 * @author Alek Norris
 * MainActivity is the main activity for the GameWFriendsActivity1 app
 * It is the first activity that is launched when the app is opened
 * It is the activity that contains the navigation bar and the fragments
 * that are displayed when the user selects an option from the navigation bar
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The binding for the MainActivity
     */
    private ActivityMainBinding binding;

    /**
     * The boolean for if the user has logged in
     */
    private boolean hasLoggedIn;

    private UserInfo userinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnCreateInit();
        hasLoggedIn = true;         //change this to be able to skip logging in.
        HasUserLoggedinSignedUp();
    }

    private void HasUserLoggedinSignedUp(){
        //check if user has logged in
        if (!hasLoggedIn) {
            //create a new user instance to begin storing the user information.
//            userinfo = new UserInfo(UserId, Username, Password);

            //setup LoginActivity then handoff to it.
            Intent intent = new Intent(com.GameWFriends.MainActivity.this, LoginActivity.class);
            startActivity(intent);

        }

        //user Has logged in, We can now open the main activity.

    }

    private void OnCreateInit(){

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_messages, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    /**
     * This method is used to change the fragment that is displayed in the main activity
     * @param fragment the fragment that is to be displayed
     * @param viewObjects the list of view objects that are to be hidden
     */
    public void changeFragment(Fragment fragment, List<View> viewObjects) {
        // Hide the UI elements
        UsefulFragmentandActivityTools.hideUiElements(viewObjects);
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