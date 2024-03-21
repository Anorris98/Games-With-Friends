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

import com.GameWFriends.Constants;
import com.GameWFriends.R;
import com.GameWFriends.APIServices.ServerInteractionCode.VolleyAPIService;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminToolsAccessRolesTable extends Fragment {

    private AdminToolsAccessRolesTableViewModel mViewModel;

    private VolleyAPIService apiService;

    public static AdminToolsAccessRolesTable newInstance() {
        return new AdminToolsAccessRolesTable();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        apiService = new VolleyAPIService(getContext());
        return inflater.inflate(R.layout.fragment_admin_tools_access_roles_table, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdminToolsAccessRolesTableViewModel.class);


        // Setup button click listeners
        setupListeners(view);

        // Observe the LiveData for changes and update the TextView accordingly
        // Update the TextView with the response (ide combined these two statements, left for note clarity.)
        TextView textViewResponse = view.findViewById(R.id.accessRoleResponse);  //text view for string response
       mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), textViewResponse::setText);


    }


    private void setupListeners(View view) {
//      Edit text declarations delete when done with this part
        EditText id = view.findViewById(R.id.editTextNumIdent);        //ID
        EditText userId = view.findViewById(R.id.editTextNumUsID); //
        EditText roleId = view.findViewById(R.id.editTextNumRoleID);         //


        Button promoteUser = view.findViewById(R.id.promoteUse);             // button: Register
        Button demoteUser = view.findViewById(R.id.demoteUse);                   // button: login
        Button listAllRoles = view.findViewById(R.id.listAllRol);     // button: View current user info
        Button ChangeUserRole = view.findViewById(R.id.ChangeUserRo);

        promoteUser.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
                int finalUserId = getUseriD(userId);
                int finalRoleId = getUseriD(roleId);
                promoteUsers(finalId,finalUserId,finalRoleId);
            }
        });

        demoteUser.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
                int finalUserId = getUseriD(userId);
                int finalRoleId = getUseriD(roleId);
                demoteUsers(finalId,finalUserId,finalRoleId);
            }
        });
        listAllRoles.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
                int finalUserId = getUseriD(userId);
                int finalRoleId = getUseriD(roleId);
                listAllRoles(finalId,finalUserId,finalRoleId);
            }
        });
        ChangeUserRole.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
                int finalUserId = getUseriD(userId);
                int finalRoleId = getUseriD(roleId);
                changeUsersRole(finalId,finalUserId,finalRoleId);
            }
        });
    }
    public int getUseriD(EditText Id){
        int userIDGroupID;

        try {
            userIDGroupID = Integer.parseInt(Id.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Error: please Enter a number ", Toast.LENGTH_LONG).show();


            //just incase set it to error 402 for now.
            userIDGroupID = 402;
        }
        return userIDGroupID;
    }
    public void promoteUsers(int id, int Userid, int Roleid){
        String finalUrl = Constants.ELIURL + "/access_roles";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", id);
            postData.put("userId", Userid);
            postData.put("roleId", Roleid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }
        apiService.postRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {

                Toast.makeText(getContext(), "Promote User Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(getContext(), "Promote User Success", Toast.LENGTH_LONG).show();
                try {
                    //this is currently being used to see the responses in a text for demo 2
                    String formattedResponse = response.toString(4); // Indent with 4 spaces for readability
                    mViewModel.setResponse("Promote response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error parsing promote response", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void demoteUsers(int id, int Userid, int Roleid){
        String finalUrl = Constants.ELIURL + "/access_roles";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", id);
            postData.put("userId", Userid);
            postData.put("roleId", Roleid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }
        apiService.deleteRequest(finalUrl, id, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Error message context for user deletion
                Toast.makeText(getContext(), "demotes user Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Success message context for user deletion
                Toast.makeText(getContext(), "demote user Success", Toast.LENGTH_LONG).show();
                try {
                    // Optionally display the response for demo purposes
                    String formattedResponse = response.toString(4); // 4 spaces for indentation
                    mViewModel.setResponse("demote user response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error parsing user demote user response", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void listAllRoles(int id, int Userid, int Roleid){
        String finalUrl = Constants.ELIURL + "/access_roles";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", id);
            postData.put("userId", Userid);
            postData.put("roleId", Roleid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }
        apiService.getRequest(finalUrl,  Userid, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Display error message
                Toast.makeText(getContext(), "List all roles Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    //this is currently being used to see the responses in a text for demo 2
                    String formattedResponse = response.toString(4); // Indent with 4 spaces
                    mViewModel.setResponse("List All roles Response is:\n" + formattedResponse);
                } catch (JSONException e) {
                    // Handle JSON parsing error
                    Toast.makeText(getContext(), "Error List all roles handling JSON", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void changeUsersRole(int id, int Userid, int Roleid){
        String finalUrl = Constants.ELIURL + "/access_roles";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", id);
            postData.put("userId", Userid);
            postData.put("roleId", Roleid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }
        apiService.putRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Error message context for password change
                Toast.makeText(getContext(), "Change Role Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Success message context for password change
                Toast.makeText(getContext(), " Change Role Success", Toast.LENGTH_LONG).show();
                try {
                    //we only display for demo 2 purposes.
                    String formattedResponse = response.toString(4); // 4 spaces for indentation
                    mViewModel.setResponse("Change Role response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error parsing  change role response", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}