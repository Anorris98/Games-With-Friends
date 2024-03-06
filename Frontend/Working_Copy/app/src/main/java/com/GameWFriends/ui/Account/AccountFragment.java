package com.GameWFriends.ui.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.GameWFriends.StringRequestActivity;
import com.GameWFriends.databinding.FragmentAccountBinding;

/**
 * This class is the fragment for the Account, Has buttons for the user to interact with to change
 * and edit their personal account.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // ViewModel setup
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        // Inflate the layout with View Binding
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set click listeners for buttons here
        binding.ButtonAccountStringReqActivity.setOnClickListener(this);    // String Request Activity
        binding.ButtonAccountAdminTools.setOnClickListener(this);           // Admin tools
        // Example: binding.OtherButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.ButtonAccountStringReqActivity.getId()) {
            startActivity(new Intent(getActivity(), StringRequestActivity.class));

        }
        else if (v.getId() == binding.ButtonAccountAdminTools.getId()){
            startActivity(new Intent(getActivity(), com.GameWFriends.ui.AdminTools.AdminToolsActivity.class));
        }
        //} else if (v.getId() == binding.OtherButton.getId()) {
        // Handle the OtherButton click here
        // Replace OtherButton with the actual ID of another button I have
        else{
            //do nothing
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

