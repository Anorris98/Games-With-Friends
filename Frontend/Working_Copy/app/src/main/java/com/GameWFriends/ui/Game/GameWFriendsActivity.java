package com.GameWFriends.ui.Game;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.GameWFriends.R;
import com.GameWFriends.UsefulFragmentandActivityTools;
import com.GameWFriends.databinding.ActivityGameWfriendsBinding;

import java.util.List;

public class GameWFriendsActivity extends AppCompatActivity {

    /**
     * The binding for the MainActivity
     */
    private ActivityGameWfriendsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnCreateInit();

    }


    private void OnCreateInit(){
        binding = ActivityGameWfriendsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_game_wfriends);

    }

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