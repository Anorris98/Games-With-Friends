package com.GameWFriends.ui.Game;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.GameWFriends.R;
import com.GameWFriends.UsefulFragmentandActivityTools;
import com.GameWFriends.databinding.ActivityGameWfriendsBinding;
import com.GameWFriends.ui.Game.UI.Chat.ChatFragment;
import com.GameWFriends.ui.Game.UI.Chat.ChatViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to handle the GameWFriendsActivity
 * Specifically, it is used to handle the game activity, and the chat lobby.
 */
public class GameWFriendsActivity extends AppCompatActivity {
    private ChatViewModel chatViewModel;
    private View GameView;
    /**
     * The ChatFragment instance for the GameWFriendsActivity
     */
    private Fragment currentChatFragment;

    /**
     * The binding for the MainActivity
     */
    private ActivityGameWfriendsBinding binding;

    /**
     * The button for the game chat
     */
    private Button buttonGameChat;

    /**
     * The list of view objects for the GameWFriendsActivity
     */
    private List<View> viewObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameWfriendsBinding.inflate(getLayoutInflater());
        //this must be a get root method, as it is the root of the view, and we are in the base level for  the activity
        setContentView(binding.getRoot());
        OnCreateInit();
        setupListeners();

        // Listen for changes in the back stack
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            // Check if the back stack is empty
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                // No more fragments in the back stack, so remove the tint
                setActivityInteractivity(true);
                setTint(false);
            }
        });
    }



    /**
     * This method is used to initialize VIEW Objects and Class variables.
     */
    private void OnCreateInit(){

        //initialize the chat fragment instance, and store it.
        currentChatFragment = new ChatFragment();

        //initialize the view objects for an array list.
        viewObjects = new ArrayList<>();

        //initialize buttons
        buttonGameChat = binding.buttonGameChat;

        //initialize orther UI objects

        //add the UI objects to the viewObjects list
        viewObjects.add(buttonGameChat);

    }

    /**
     * This method is used to set the onClickListeners for the buttons in the GameWFriendsActivity
     */
    private void setupListeners() {
        buttonGameChat.setOnClickListener(v -> onbuttonGameChatClick());

    }

    /**
     * This method is used to handle the onClick event for the Game Chat Button.
     */
   private void onbuttonGameChatClick(){
        //show fragment, but since we want this always accessible, lets just show it, without hiding other things.
//        changeFragment(SignupFragment.newInstance(), viewObjects);
         showFragment(ChatFragment.newInstance());

    }

    /**
     * This method is used to set the interactivity of the activity
     * @param functionality the boolean for if the activity is to be interactive
     */
    private void setActivityInteractivity(boolean functionality) {
        binding.buttonGameChat.setEnabled(functionality);

    }


    /**
     * This method is used to set the tint for the GameWFriendsActivity
     * Used When pulling a fragment for chat in.
     * @param applyTint the boolean for if the tint is to be applied
     */
    private void setTint(boolean applyTint) {
        if (applyTint) {
            binding.fragmentContainer.setBackgroundColor(getResources().getColor(R.color.background_color, getTheme())); // Applying tint
//            Blurry.with(this).radius(25).sampling(25).onto((ViewGroup) findViewById(R.id.blurOverlay));
        } else {
            binding.fragmentContainer.setBackgroundColor(getResources().getColor(R.color.background_color, getTheme())); // Removing tint
//            binding.blurOverlay.setVisibility(View.GONE);
            //todo: decide if the blur is actually working or not, then either remove the white background or the blur.
        }
    }


    /**
     * This method is used to show a fragment in the Game Activity,
     * unlike Change Fragment, this will only bring the fragment to the front
     * making it intractable by the user.
     * @param fragment the fragment that is to be shown
     */
    public void showFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Check if the fragment is already added to avoid adding it multiple times
        if (fragment.isAdded()) {
            // Show the ChatFragment if it is already added
            transaction.show(fragment);
        } else {
            // Add the ChatFragment without replacing the existing fragments in the container
            transaction.add(com.GameWFriends.R.id.fragment_container, fragment);

            // Optionally add the transaction to the back stack
            transaction.addToBackStack(null);
        }
        //tint the current screen
        setActivityInteractivity(false);
        setTint(true);
        // Commit the transaction
        transaction.commit();
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

//    @Override
//    public void onWebSocketOpen(ServerHandshake handshakedata) {}
//
//    @Override
//    public void onWebSocketMessage(String message) {}
//
//    @Override
//    public void onWebSocketClose(int code, String reason, boolean remote) {}
//
//    @Override
//    public void onWebSocketError(Exception ex) {}
}