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
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdminToolsTrophiesTableViewModel.class);


        // Setup button click listeners
        setupListeners(view);

        // Observe the LiveData for changes and update the TextView accordingly
        // Update the TextView with the response (ide combined these two statements, left for note clarity.)
        TextView textViewResponse = view.findViewById(R.id.trophyResponse);  //text view for string response
        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), textViewResponse::setText);


    }


    private void setupListeners(View view) {
//      Edit text declarations delete when done with this part
        EditText id = view.findViewById(R.id.editTextRequirements);        //ID
        EditText name = view.findViewById(R.id.editName); //
        EditText requirements = view.findViewById(R.id.editTextNumberID);         //
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
                int TrophyUpdate = getUseriD(requirements);
                updateLockedTrophy(finalId,TrophyUpdate);


            }
        });
        deleteTrophy.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
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
        apiService.deleteRequest(finalUrl, trophyID, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Error message context for user deletion
                Toast.makeText(getContext(), "Delete Trophy Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Success message context for user deletion
                Toast.makeText(getContext(), "Delete Trophy Success", Toast.LENGTH_LONG).show();
                try {
                    // Optionally display the response for demo purposes
                    String formattedResponse = response.toString(4); // 4 spaces for indentation
                    mViewModel.setResponse("Delete Trophy response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error parsing user Delete Trophy response", Toast.LENGTH_LONG).show();
                }
            }
        });
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
        apiService.getRequest(finalUrl,  userID, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Display error message
                Toast.makeText(getContext(), "List all trophies Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    //this is currently being used to see the responses in a text for demo 2
                    String formattedResponse = response.toString(4); // Indent with 4 spaces
                    mViewModel.setResponse("List All trophies Response is:\n" + formattedResponse);
                } catch (JSONException e) {
                    // Handle JSON parsing error
                    Toast.makeText(getContext(), "Error List all trophies handling JSON", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
            }
        });


    }
    public void updateLockedTrophy(int userID,int progress){
        String finalUrl = Constants.BASE_URL + "/trophies/"+userID;
        JSONObject postData = new JSONObject();

        try {
            postData.put("userID", userID);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating JSON object for profile update", Toast.LENGTH_SHORT).show();
            return;
        }
        apiService.putRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // Error message context for password change
                Toast.makeText(getContext(), "Update Locked Trophy Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                // Success message context for password change
                Toast.makeText(getContext(), "Update Locked Trophy Success", Toast.LENGTH_LONG).show();
                try {
                    //we only display for demo 2 purposes.
                    String formattedResponse = response.toString(4); // 4 spaces for indentation
                    mViewModel.setResponse("Update Locked Trophy response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error parsing Update locked Trophy response", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}