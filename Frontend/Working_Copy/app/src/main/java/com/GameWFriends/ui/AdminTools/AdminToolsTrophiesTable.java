package com.GameWFriends.ui.AdminTools;

import android.os.Bundle;
import android.util.Base64;
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

public class AdminToolsTrophiesTable extends Fragment {

    private AdminToolsTrophiesTableViewModel mViewModel;

    private VolleyAPIService apiService;

    public static AdminToolsTrophiesTable newInstance() {
        return new AdminToolsTrophiesTable();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        apiService = new VolleyAPIService(getContext());
        return inflater.inflate(R.layout.fragment_admin_tools_trophies_table, container, false);
    }

    private void setupListeners(View view) {
//      Edit text declarations delete when done with this part
        EditText id = view.findViewById(R.id.editTextNumberID);        //ID
        EditText name = view.findViewById(R.id.editName); //
        EditText trophyid = view.findViewById(R.id.editTextNumberTrophyID);         //
        EditText Description = view.findViewById(R.id.editTextDescription);

        Button listUserTrophies = view.findViewById(R.id.listUserTrophies);             // button: Register
        Button addNewTrophy = view.findViewById(R.id.addNewTrophy);                   // button: login
        Button updateLockedTrophy = view.findViewById(R.id.updateLockedTrophy);     // button: View current user info
        Button deleteTrophy = view.findViewById(R.id.deleteTrophy);

        listUserTrophies.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
                listAllTrophies(finalId);


            }
        });

        addNewTrophy.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
                String na = name.getText().toString();
                String de = Description.getText().toString();
                addNewTrophy(finalId,na,de);


            }
        });
        updateLockedTrophy.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
                updateLockedTrophy(finalId);


            }
        });
        deleteTrophy.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(trophyid);
                deleteTrophy(finalId);

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
    public void deleteTrophy(int trophyID){
        String finalUrl = Constants.BASE_URL + "/trophies/"+trophyID;
        JSONObject postData = new JSONObject();

        try {
            postData.put("trophyId", trophyID);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    public void addNewTrophy(int id, String name, String description) {
        String finalUrl = Constants.BASE_URL + "/trophies";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", id);
            postData.put("name", name);
            postData.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }


    }
    public void listAllTrophies(int userID){
        String finalUrl = Constants.BASE_URL + "/trophies";
        JSONObject postData = new JSONObject();

        try {
            postData.put("userId", userID);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }


    }
    public void updateLockedTrophy(int userID){
        String finalUrl = Constants.BASE_URL + "/trophies/"+userID;
        JSONObject postData = new JSONObject();

        try {
            postData.put("userId", userID);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }


    }

}