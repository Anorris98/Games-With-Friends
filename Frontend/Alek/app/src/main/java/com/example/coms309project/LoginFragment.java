//package com.example.coms309project;
//
//import android.os.Bundle;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import androidx.navigation.fragment.NavHostFragment;
//import com.example.coms309project.R;
//
//public class LoginFragment extends Fragment {
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_login, container, false);
//
//        Button loginButton = view.findViewById(R.id.login_button);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Implement your login logic here
//                // On successful login, navigate to the main screen
//                NavHostFragment.findNavController(LoginFragment.this)
//                        .navigate(R.id.action_navigation_login_to_navigation_home);
//            }
//        });
//
//        return view;
//    }
//}
