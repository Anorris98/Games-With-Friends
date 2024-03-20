package com.GameWFriends.ui.Play;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GameWFriends.APIServices.ServerInteractionCode.VolleyAPIService;
import com.GameWFriends.R;
import com.GameWFriends.databinding.FragmentPlayBinding;
import com.GameWFriends.ui.Game.GameWFriendsActivity;

public class PlayFragment extends Fragment {

    private Button buttonNewGame;
    private FragmentPlayBinding binding;
    private VolleyAPIService apiService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        /*Initialize the VolleyAPIService with the fragment's context, context is required for being able to use device resources find view by ID etc.*/
        apiService = new VolleyAPIService(requireContext());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setupOnClickListeners();
//TODO: Uncomment these.
    }

    private void setupOnClickListeners() {
        buttonNewGame.setOnClickListener(v -> onNewGameClicked());
    }

    private void onNewGameClicked() {
        Intent intent = new Intent(getActivity(), GameWFriendsActivity.class);
        startActivity(intent);
    }

    private void init(View view) {
        buttonNewGame = view.findViewById(R.id.buttonNewGame);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}