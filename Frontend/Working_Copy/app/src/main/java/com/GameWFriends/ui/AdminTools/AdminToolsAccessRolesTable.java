package com.GameWFriends.ui.AdminTools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.GameWFriends.R;

public class AdminToolsAccessRolesTable extends Fragment {

    private AdminToolsAccessRolesTableViewModel mViewModel;

    public static AdminToolsAccessRolesTable newInstance() {
        return new AdminToolsAccessRolesTable();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_tools_access_roles_table, container, false);
    }

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


    }

    public void demoteUsers(int id, int Userid, int Roleid){
        String finalUrl = Constants.BASE_URL + "/access_roles";

    }

    public void listAllRoles(int id, int Userid, int Roleid){
        String finalUrl = Constants.BASE_URL + "/access_roles";

    }

    public void changeUsersRole(int id, int Userid, int Roleid){
        String finalUrl = Constants.BASE_URL + "/access_roles";
        
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