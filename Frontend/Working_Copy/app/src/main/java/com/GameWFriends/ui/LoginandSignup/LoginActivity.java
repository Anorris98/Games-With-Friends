package com.GameWFriends.ui.LoginandSignup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.GameWFriends.MainActivity;
import com.GameWFriends.R;

public class LoginActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable
    private TextView usernameText;  // define username textview variable
    private Button loginButton;     // define login button variable
    private Button signupButton;    // define signup button variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);             // link to Main activity XML


        /* extract data passed into this activity from another activity */
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            messageText.setText("Login or Signup");
            usernameText.setVisibility(View.INVISIBLE);             // set username text invisible initially
        } else {
            messageText.setText("Welcome");
            usernameText.setText(extras.getString("USERNAME")); // this will come from LoginActivity
            loginButton.setVisibility(View.INVISIBLE);              // set login button invisible
            signupButton.setVisibility(View.INVISIBLE);             // set signup button invisible
            //signed in, so open main activity and adjust
            //TODO: make sure UI updates to reflect the player signed in.
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This code block will be executed after a 5-second delay
                    Intent Loggedin = new Intent(LoginActivity.this, MainActivity.class);
                    Loggedin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(Loggedin);
                    finish();
                }
            }, 2000); // Delay in milliseconds (1000ms = 1s)
        }
    }

    /**
     * OnCreateInit is a method that initializes the UI elements of the LoginActivity
     */
    private void OnCreateInit() {

        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        usernameText = findViewById(R.id.main_username_txt);// link to username textview in the Main activity XML
        loginButton = findViewById(R.id.main_login_btn);    // link to login button in the Main activity XML
        signupButton = findViewById(R.id.main_signup_btn);  // link to signup button in the Main activity XML

        //a call to setup the listeners for the buttons.
        setupListeners();
    }

    /**
     * setupListeners is a method that sets up the listeners for the buttons in the LoginActivity
     */
    private void setupListeners() {
        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideUiElements();

                //TODO: update this now that we are not using the example login stuff.

            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideUiElements();

                /* when signup button is pressed, use intent to switch to Signup Fragment */
                //TODO: update this now that we are not using the example login stuff.

            }
        });

        // Setup the listener for back stack changes
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // If there are no entries in the back stack (user navigated back), show the buttons again
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    showUiElements();
                }
            }
        });

    }

    private void hideUiElements() {
        //hide the login and signup buttons
        loginButton.setVisibility(View.INVISIBLE);
        signupButton.setVisibility(View.INVISIBLE);
        messageText.setVisibility(View.INVISIBLE);
        usernameText.setVisibility(View.INVISIBLE);
    }

    private void showUiElements() {
        //show the login and signup buttons
        loginButton.setVisibility(View.VISIBLE);
        signupButton.setVisibility(View.VISIBLE);
        messageText.setVisibility(View.VISIBLE);
        usernameText.setVisibility(View.VISIBLE);
    }

    public void changeFragment(Class<? extends Fragment> fragmentClass, Bundle args) {
        // Hide UI elements if needed
        hideUiElements();

        // Create a new instance of the fragment using reflection
        Fragment fragment = null;
        try {
            fragment = fragmentClass.newInstance();
            if (args != null) {
                fragment.setArguments(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

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