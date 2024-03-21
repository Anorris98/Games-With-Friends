package com.GameWFriends.ui.Game.UI.Chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<List<String>> messages = new MutableLiveData<>(new ArrayList<>());

    /**
     * Get the messages LiveData object
     *
     * @return the messages LiveData object
     */
    public LiveData<List<String>> getMessages() {
        return messages;
    }

}

