package com.example.coms309project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false); // Make sure you use the correct layout file name

        // Now use the view to find the EditText and Button
        EditText usernameField = (EditText) view.findViewById(R.id.username);
        EditText passwordField = (EditText) view.findViewById(R.id.password);
        Button loginButton = (Button) view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement your login logic here
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                ImageView CheckBox = view.findViewById(R.id.CheckBox);
                ImageView CheckBoxEmpty = view.findViewById(R.id.CheckBoxEmpty);
                EditText loginResult = view.findViewById(R.id.loginResult);
                String loginMessage = loginResult.getText().toString();

                TODO:
                //replace with api call/server call with check of username and password
                //login successfully
                if(username.equals("username") && password.equals("password")){
                    CheckBoxEmpty.setVisibility(View.INVISIBLE);
                    CheckBox.setVisibility(View.VISIBLE);
                    loginMessage = "Success";
                    loginResult.setText(loginMessage);
                }
                //else login not successful
                else{
                    loginMessage = "Account Not Found";
                    loginResult.setText(loginMessage);
                }
            }
        });

        return view;
    }
}

