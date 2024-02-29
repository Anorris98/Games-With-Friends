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
/** @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdminToolsTrophiesTableViewModel.class);


        // Setup button click listeners
        setupListeners(view);

        // Observe the LiveData for changes and update the TextView accordingly
        // Update the TextView with the response (ide combined these two statements, left for note clarity.)
        TextView textViewResponse = view.findViewById(R.id.trophyResponse);  //text view for string response
       // mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), textViewResponse::setText);


    }
**/

    private void setupListeners(View view) {
//      Edit text declarations delete when done with this part
        EditText id = view.findViewById(R.id.editTextNumberIdent);        //ID
        EditText userId = view.findViewById(R.id.editName); //
        EditText roleId = view.findViewById(R.id.editTextNumberRoleID);         //


        Button promoteUser = view.findViewById(R.id.promoteUser);             // button: Register
        Button demoteUser = view.findViewById(R.id.demoteUser);                   // button: login
        Button listAllRoles = view.findViewById(R.id.listAllRoles);     // button: View current user info
        Button ChangeUserRole = view.findViewById(R.id.ChangeUserRole);

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
    public void promoteUsers(int id, int Userid, int Roleid){
        String finalUrl = Constants.BASE_URL + "/access_roles";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", id);
            postData.put("userId", Userid);
            postData.put("RoleId", Roleid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void demoteUsers(int id, int Userid, int Roleid){
        String finalUrl = Constants.BASE_URL + "/access_roles";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", id);
            postData.put("userId", Userid);
            postData.put("RoleId", Roleid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void listAllRoles(int id, int Userid, int Roleid){
        String finalUrl = Constants.BASE_URL + "/access_roles";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", id);
            postData.put("userId", Userid);
            postData.put("RoleId", Roleid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void changeUsersRole(int id, int Userid, int Roleid){
        String finalUrl = Constants.BASE_URL + "/access_roles";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", id);
            postData.put("userId", Userid);
            postData.put("RoleId", Roleid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }

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

}