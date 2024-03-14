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
import com.GameWFriends.APIServices.ServerInteractionCode.VolleyAPIService;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminToolsTrophiesTable extends Fragment {

    private AdminToolsTrophiesTableViewModel mViewModel;

    private VolleyAPIService apiService;
    /**
     * Create a new instance of AdminToolsTrophiesTable fragment.
     * @return A new instance of AdminToolsTrophiesTable fragment.
     */
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

    /**
     * Setup listeners for buttons in the fragment.
     * @param view The root view of the fragment.
     */
    private void setupListeners(View view) {
//      Edit text declarations delete when done with this part
        EditText id = view.findViewById(R.id.editTextRequirements);        // store the value of id
        EditText name = view.findViewById(R.id.editName); // store the value of name
        EditText requirements = view.findViewById(R.id.editTextNumberID);         // store the value of requiremtn
        EditText Description = view.findViewById(R.id.editTextDescription); // store the name of desciption

        Button listUserTrophies = view.findViewById(R.id.listUserTrophies);             // button: list Viewers
        Button addNewTrophy = view.findViewById(R.id.addNewTrophy);                   // button: Add new trophys
        Button updateLockedTrophy = view.findViewById(R.id.updateLockedTrophy);     // button: update locked trophys
        Button deleteTrophy = view.findViewById(R.id.deleteTrophy);                 // button: delete trophyies

        // listen to the button listen User Table
        listUserTrophies.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
                listAllTrophies(finalId);


            }
        });
        //listen to the button add trophy
        addNewTrophy.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
                String na = name.getText().toString();
                String de = Description.getText().toString();
                addNewTrophy(finalId,na,de);


            }
        });
        //listen to the button update locked trophy
        updateLockedTrophy.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
                int TrophyUpdate = getUseriD(requirements);
                updateLockedTrophy(finalId,TrophyUpdate);


            }
        });
        //listen to the delete trophy button
        deleteTrophy.setOnClickListener(new View.OnClickListener() {          //register New user
            @Override
            public void onClick(View v) {
                int finalId = getUseriD(id);
                deleteTrophy(finalId);

            }
        });


    }
    /**
     * Get user ID from EditText field.
     * @param Id EditText field containing user ID.
     * @return User ID as an integer.
     */
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
    //action preformed once delete trophy is slected
    public void deleteTrophy(int trophyID){
        String finalUrl = Constants.ELIURL + "/trophies/" + trophyID;
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
    //action preformed once add new trophy is slected
    public void addNewTrophy(int id, String name, String description) {
        String finalUrl = Constants.ELIURL + "/trophies";
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
        apiService.postRequest(finalUrl, postData, new VolleyAPIService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "create new trophy Error: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "create new to Success", Toast.LENGTH_LONG).show();
                try {
                    //this is currently being used to see the responses in a text for demo 2
                    String formattedResponse = response.toString(4); // Indent with 4 spaces for readability
                    mViewModel.setResponse("Registration response: " + formattedResponse);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error parsing registration response", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    //action preformed once list all trophy is slected
    public void listAllTrophies(int userID){
        String finalUrl = Constants.ELIURL + "/trophies";
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
    //action preformed once update locked trophy is slected
    public void updateLockedTrophy(int userID,int progress){
        String finalUrl = Constants.ELIURL + "/trophies/"+userID;
        JSONObject postData = new JSONObject();

        try {
            postData.put("userId", userID);
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