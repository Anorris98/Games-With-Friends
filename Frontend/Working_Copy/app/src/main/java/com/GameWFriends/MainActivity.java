package com.GameWFriends;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.GameWFriends.databinding.ActivityMainBinding;
import com.GameWFriends.ui.LoginandSignup.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * @author Alek Norris
 * MainActivity is the main activity for the GameWFriends app
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
        hasLoggedIn = false;
        HasUserLoggedinSignedUp();
    }

    private void HasUserLoggedinSignedUp(){
        //check if user has logged in
        //TODO: this will be needing changed back to false for login screen to work, for demo 2 this has been nulled to use for testing of string pulling.
        //TODO: need to create and store user information in the userINfo class.
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

}