package com.GameWFriends.ui.AdminTools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.GameWFriends.APIServices.ViewModel.GenericViewModel;
import com.GameWFriends.APIServices.ServerInteractionCode.ServerTools;
import com.GameWFriends.APIServices.ServerInteractionCode.VolleyAPIService;
import com.GameWFriends.R;

/**
 * @author Alek
 * @updated 3/8/2024 functions moved to ServerTools helper class to clear up clutter and reduce redundancy.
 * This class is the fragment for the Admin Tools Friend Groups Table, Has buttons for the user
 * To interact with  and be able to view the tools and manipulation tools.
 */
public class AdminToolsFriendGroupsTable extends Fragment {

    /**AdminToolsFriendGroupsTableViewModel mViewModel; */
    private GenericViewModel mViewModel;
    /**VolleyAPIService apiService; */
    private VolleyAPIService apiService;

    private ServerTools serverTools;

    /**
     * This method is used to create a new instance of the fragment.
     * @return a new instance of the fragment.
     */
    public static AdminToolsFriendGroupsTable newInstance() {return new AdminToolsFriendGroupsTable(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Initalize the VolleyAPI Service with the fragments context.
        apiService = new VolleyAPIService(requireContext());


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_tools_friend_groups_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GenericViewModel.class);

        serverTools = new ServerTools(getContext(), apiService, mViewModel);

        setupListeners(view);

        // Observe the LiveData for changes and update the TextView accordingly
        // Update the TextView with the response (ide combined these two statements, left for note clarity.)
        TextView textViewResponse = view.findViewById(R.id.Textview_ResponseFriend);  //text view for string response
        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), textViewResponse::setText);


    }

    /**
     * This method is used to setup the listeners for the buttons in the fragment.
     * @param view the view that the fragment is in.
     */
    private void setupListeners(View view) {
        //Edit text declarations
        EditText numberGroupId = view.findViewById(R.id.editTextGroupId);       // texview: GroupId
        EditText numberUserId = view.findViewById(R.id.editTextUserID);         // texview: UserId
        EditText numberRoleId = view.findViewById(R.id.editTextRoleId);         // texview: RoleId



        //button declarations
        Button buttonCreateFriendGroup = view.findViewById(R.id.buttonCreateFriendGroup);             // button: Create Friend Group
        Button buttonGetFriendGroupsUserIsIn = view.findViewById(R.id.buttonGetFriendGroupsUserIsIn); // button: List Groups User is in.
        Button buttonUpdateFriendGroup = view.findViewById(R.id.buttonUpdateFriendGroup);             // button: Update Friend Group
        Button buttonGetUsersInGroup = view.findViewById(R.id.buttonGetUsersInGroup);                 // button: Get Users In Group
        Button buttonDeleteFriendGroup = view.findViewById(R.id.buttonDeleteFriendGroup);             // button: Delete Friend Groups


        //listeners start here
        buttonCreateFriendGroup.setOnClickListener(new View.OnClickListener() { // button: Create Friend Group needs user id to be added to a json object in a list.
            @Override
            public void onClick(View v) {

                String userIds = numberRoleId.getText().toString();
                createFriendGroup(userIds);

            }
        });

        buttonGetFriendGroupsUserIsIn.setOnClickListener(new View.OnClickListener() {   // button: List Groups User is in.
            @Override
            public void onClick(View v) {

                Integer userId = Integer.parseInt(numberUserId.getText().toString());
                getFriendGroupsUserIsIn(userId);

            }
        });
        buttonUpdateFriendGroup.setOnClickListener(new View.OnClickListener() { // button: Update Friend Group
            @Override
            public void onClick(View v) {

                String userIds = numberUserId.getText().toString();
                Integer groupId = Integer.parseInt(numberGroupId.getText().toString());
                updateFriendGroup(userIds, groupId);
            }
        });

        buttonGetUsersInGroup.setOnClickListener(new View.OnClickListener() {   // button: Get Users In Group
            @Override
            public void onClick(View v) {

                Integer groupId = Integer.parseInt(numberGroupId.getText().toString());
                getUsersInGroup(groupId);

            }
        });

        buttonDeleteFriendGroup.setOnClickListener(new View.OnClickListener() { // button: Delete Friend Groups
            @Override
            public void onClick(View v) {
                    int userId = Integer.parseInt(numberUserId.getText().toString());
                    int groupId = Integer.parseInt(numberGroupId.getText().toString());
                    deleteFriendGroup(groupId, userId);

            }
        });
    }

    /**
     * This method is used to create a friend group.
     * @param stringUserIds the string of user ids to be added to the friend group.
     */
    public void createFriendGroup(String stringUserIds) {
        serverTools.createFriendGroup(stringUserIds);
    }

    /**
     * This method is used to get the friend groups that a user is in.
     * @param userId the user id to get the friend groups for.
     */
    public void getFriendGroupsUserIsIn(Integer userId) {
        serverTools.getFriendGroupsUserIsIn(userId);
    }

    /**
     * This method is used to update a friend group.
     * @param stringUserIds the string of user ids to be added to the friend group.
     * @param groupId the id of the group to be updated.
     */
    public void updateFriendGroup(String stringUserIds, Integer groupId) {
        serverTools.updateFriendGroup(stringUserIds, groupId);
    }

    /**
     * This method is used to get the users in a group.
     * @param groupId the id of the group to get the users for.
     */
    public void getUsersInGroup(Integer groupId) {
        serverTools.getUsersInGroup(groupId);
    }

    /**
     * This method is used to delete a friend group.
     * @param groupId the id of the group to be deleted.
     * @param UserRequestingDeleteId the id of the user requesting the delete.
     */
    public void deleteFriendGroup(int groupId, int UserRequestingDeleteId) {
        serverTools.deleteFriendGroup(groupId, UserRequestingDeleteId);
    }
}