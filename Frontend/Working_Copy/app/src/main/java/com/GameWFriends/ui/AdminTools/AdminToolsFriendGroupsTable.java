package com.GameWFriends.ui.AdminTools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.GameWFriends.R;
import com.GameWFriends.VolleyAPIService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminToolsFriendGroupsTable extends Fragment {

    private AdminToolsFriendGroupsTableViewModel mViewModel;
    private VolleyAPIService apiService;

    public static AdminToolsFriendGroupsTable newInstance() {return new AdminToolsFriendGroupsTable(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Initalize the VolleyAPI Service with the fragments context.
        apiService = new VolleyAPIService(requireContext());

        return inflater.inflate(R.layout.fragment_admin_tools_friend_groups_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdminToolsFriendGroupsTableViewModel.class);

        setupListeners(view);

        // Observe the LiveData for changes and update the TextView accordingly
        // Update the TextView with the response (ide combined these two statements, left for note clarity.)
        TextView textViewResponse = view.findViewById(R.id.Textview_Response);  //text view for string response
        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), textViewResponse::setText);
    }

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
        //Todo: Test
        buttonCreateFriendGroup.setOnClickListener(new View.OnClickListener() { // button: Create Friend Group needs user id to be added to a json object in a list.
            @Override
            public void onClick(View v) {

                String userIds = numberUserId.getText().toString();
                createFriendGroup(userIds);

            }
        });

        //TODO: Get all Friend Groups a user is in.
        buttonGetFriendGroupsUserIsIn.setOnClickListener(new View.OnClickListener() {   // button: List Groups User is in.
            @Override
            public void onClick(View v) {

                Integer userId = Integer.parseInt(numberUserId.getText().toString());
                getFriendGroupsUserIsIn(userId);

            }
        });
        //TODO: Update Friend Group
        buttonUpdateFriendGroup.setOnClickListener(new View.OnClickListener() { // button: Update Friend Group
            @Override
            public void onClick(View v) {

                String userIds = numberUserId.getText().toString();
                Integer groupId = Integer.parseInt(numberGroupId.getText().toString());
                updateFriendGroup(userIds, groupId);
            }
        });

        //TODO: Get Users In Group
        buttonGetUsersInGroup.setOnClickListener(new View.OnClickListener() {   // button: Get Users In Group
            @Override
            public void onClick(View v) {

                Integer groupId = Integer.parseInt(numberGroupId.getText().toString());
                getUsersInGroup(groupId);

            }
        });
        //TODO: Delete a Friend Group
        buttonDeleteFriendGroup.setOnClickListener(new View.OnClickListener() { // button: Delete Friend Groups
            @Override
            public void onClick(View v) {

                    Integer groupId = Integer.parseInt(numberGroupId.getText().toString());
                    deleteFriendGroup(groupId);

            }
        });
    }

    public void createFriendGroup(String stringUserIds) {
        // Split the input string into an array of ID strings
        String[] idStrings = stringUserIds.split(",");

        // Create a JSON array to hold the user IDs
        JSONArray userIdsJsonArray = new JSONArray();

        // Convert each ID from the string array to an integer and add it to the JSON array
        for (String idStr : idStrings) {
            try {
                int id = Integer.parseInt(idStr.trim()); // Trim to remove any leading or trailing spaces
                userIdsJsonArray.put(id);
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid integer
                Toast.makeText(getContext(), "Invalid input: " + idStr, Toast.LENGTH_SHORT).show();
                return; // Return early if any input is invalid
            }
        }

        // Construct the final URL using the groupId
        String finalUrl = Constants.BASE_URL + "/friend_groups";

        // Create the JSON object for the request body
        JSONObject postData = new JSONObject();
        try {
            postData.put("userList", userIdsJsonArray); // Add the user ID array to the JSON object under the key "userList"
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Error creating JSON for update", Toast.LENGTH_SHORT).show();
            return; // Return early if there is an error creating the JSON object
        }

        // Make the PUT request
        apiService.postRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Display error message
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Handle successful response
                Toast.makeText(getContext(), "Friend group updated successfully", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void getFriendGroupsUserIsIn(Integer userId) {
        String finalUrl = Constants.BASE_URL + "/friend-groups";

        // Create the JSON body with userId
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("userId", userId);
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Error creating JSON object", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.postRequest(finalUrl, requestBody, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Display error message
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Handle successful response
                Toast.makeText(getContext(), "Friend groups retrieved successfully", Toast.LENGTH_LONG).show();
                // Optionally parse and use the response data
            }
        });
    }


    public void updateFriendGroup(String stringUserIds, Integer groupId) {
        // Split the input string into an array of ID strings
        String[] idStrings = stringUserIds.split(",");

        // Create a JSON array to hold the user IDs
        JSONArray userIdsJsonArray = new JSONArray();

        // Convert each ID from the string array to an integer and add it to the JSON array
        for (String idStr : idStrings) {
            try {
                int id = Integer.parseInt(idStr.trim()); // Trim to remove any leading or trailing spaces
                userIdsJsonArray.put(id);
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid integer
                Toast.makeText(getContext(), "Invalid input: " + idStr, Toast.LENGTH_SHORT).show();
                return; // Return early if any input is invalid
            }
        }

        // Construct the final URL using the groupId
        String finalUrl = Constants.BASE_URL + "/friend-groups/" + groupId;

        // Create the JSON object for the request body
        JSONObject postData = new JSONObject();
        try {
            postData.put("userList", userIdsJsonArray); // Add the user ID array to the JSON object under the key "userList"
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Error creating JSON for update", Toast.LENGTH_SHORT).show();
            return; // Return early if there is an error creating the JSON object
        }

        // Make the PUT request
        apiService.putRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Display error message
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Handle successful response
                Toast.makeText(getContext(), "Friend group updated successfully", Toast.LENGTH_LONG).show();

            }
        });
    }


    public void getUsersInGroup(Integer groupId) {
        String finalUrl = Constants.BASE_URL + "/friend_groups/" + groupId;

        // Make the GET request
        apiService.getRequest(finalUrl, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Display error message
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Handle successful response
                Toast.makeText(getContext(), "Users retrieved successfully", Toast.LENGTH_LONG).show();
                // Optionally parse and use the response data
            }
        });
    }

    public void deleteFriendGroup(Integer groupId) {
        String finalUrl = Constants.BASE_URL + "/friend_groups/" + groupId; // Assuming this is your endpoint for deleting friend groups

        // Make the DELETE request
        apiService.deleteRequest(finalUrl, null, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Display error message
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Handle successful response
                Toast.makeText(getContext(), "Friend group deleted successfully", Toast.LENGTH_LONG).show();
                // Optionally parse and use the response data
            }
        });
    }





}