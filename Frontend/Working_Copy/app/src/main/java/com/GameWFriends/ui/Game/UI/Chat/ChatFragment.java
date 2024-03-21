package com.GameWFriends.ui.Game.UI.Chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.GameWFriends.APIServices.ServerInteractionCode.ServerTools;
import com.GameWFriends.APIServices.ServerInteractionCode.VolleyAPIService;
import com.GameWFriends.APIServices.ViewModel.GenericViewModel;
import com.GameWFriends.Constants;
import com.GameWFriends.R;
import com.GameWFriends.databinding.FragmentChatBinding;

import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment to handle the chat in of a registered user.
 */
public class ChatFragment extends Fragment implements WebSocketListener{

        /**
         * The ChatViewModel instance for the ChatFragment fragment
         */
        private ChatViewModel chatViewModel;


        /**
         * The Handler for the checkHandler
         */
        private List<View> viewObjects;

        /**
         * The UserInfo instance for the AccountFragment fragment
         */
        private GenericViewModel genericViewModel;

        /**
         * The ViewModel for the AccountFragment fragment
         */
        private FragmentChatBinding binding;

        /**
         * The VolleyAPIService for the AccountFragment fragment
         */
        private VolleyAPIService apiService;

        /**
         * The ServerTools instance for all Server api table manipulation
         */
        private ServerTools servertools;

        /**
         * The send Button for the GameChatScreen, allowing a user to send their message.
         */
        private Button buttonGameChatSend;

        /**
         * The EditText for the GameChatScreen, it is where the user enters there message.
         */
        private EditText editTextInputMessage;
        private TextView editTextGameChatMessages;
        private ScrollView scrollView;


        public static ChatFragment newInstance() {
        return new ChatFragment();
    }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            /*Initialize the VolleyAPIService with the fragment's context, context is required for being able to use device resources find view by ID etc.*/
            apiService = new VolleyAPIService(requireContext());

            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_chat, container, false);

        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Init(view);
            setupListeners(view);
        }

        /**
         * Initialize the LoginFragment fragment
         * Sets up the mViewModel, serverTools, and the view objects.
         * @param view the view for the fragment to be initialized. View must be passed.
         */
        private void Init(View view){
            //initialize the ChatServiceManager instance
            WebSocketManager.getInstance().connectWebSocket(Constants.WS_URL);
            WebSocketManager.getInstance().setWebSocketListener(this);


            // Initialize the ViewModel variables with this view after on create.
            //generic view model for server tools, used to do http calls if needed.
            genericViewModel = new ViewModelProvider(this).get(GenericViewModel.class); //todo: Change this to GameViewModel or maybe adapt GenericViewModel?
            chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

            // Initialize the ServerTools instance with the context, apiService, and ViewModel, will allow us to use server tools methods.
            servertools = new ServerTools(getContext(), apiService, genericViewModel);

            viewObjects = new ArrayList<>();

            //button inits
            buttonGameChatSend = view.findViewById(R.id.buttonGameChatSend);
            editTextInputMessage = view.findViewById(R.id.editTextInputMessage);

            //TextView inits
            editTextInputMessage = view.findViewById(R.id.editTextInputMessage);
            
            //scroll view inits
            editTextGameChatMessages = view.findViewById(R.id.ScrollViewGameChatMessages);
            scrollView = view.findViewById(R.id.ScrollViewScroll);


            //add everything to the viewObjects list
            viewObjects.add(buttonGameChatSend);
            viewObjects.add(editTextInputMessage);
            viewObjects.add(editTextInputMessage);
            viewObjects.add(editTextGameChatMessages);
            viewObjects.add(scrollView);
        }

        /**
         * Setup the listeners for the buttons in the fragment
         * Forwards to other methods to handle the logic once a click has occurred.
         * @param view the view for buttons and listeners to be recognized. View must be passed.
         */
        private void setupListeners(View view){

            //button listeners
            buttonGameChatSend.setOnClickListener(v -> onGameChatSendClicked());
        }


        /**
         * The method for when the send button is clicked, it will send the message to the server.
         */
        private void onGameChatSendClicked() {
            String message = editTextInputMessage.getText().toString();

            //if message isnt empty
            if (!message.isEmpty()){
                try {
                    // send message
                    WebSocketManager.getInstance().sendMessage(message);
                    getActivity().runOnUiThread(() -> {
                        editTextInputMessage.clearFocus();
                        editTextInputMessage.setText("");
                    });
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }
            //if message is empty
            else {
                Toast.makeText(getContext(), "Please Enter a message" + message, Toast.LENGTH_LONG).show();
            }
        }
    @Override
    public void onWebSocketMessage(String message) {
        /**
         * In Android, all UI-related operations must be performed on the main UI thread
         * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
         * is used to post a runnable to the UI thread's message queue, allowing UI updates
         * to occur safely from a background or non-UI thread.
         */
        getActivity().runOnUiThread(() -> {
            String s = editTextGameChatMessages.getText().toString();
            editTextGameChatMessages.setText(s + "\n" + message);
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        getActivity().runOnUiThread(() -> {
            String s = editTextGameChatMessages.getText().toString();
            editTextGameChatMessages.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}
}

