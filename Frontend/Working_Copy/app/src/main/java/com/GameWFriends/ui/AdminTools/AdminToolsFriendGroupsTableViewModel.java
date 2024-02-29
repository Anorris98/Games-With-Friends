package com.GameWFriends.ui.AdminTools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class AdminToolsFriendGroupsTableViewModel extends ViewModel {
    private final MutableLiveData<String> responseLiveData = new MutableLiveData<>();

    public LiveData<String> getResponseLiveData() {
        return responseLiveData;
    }

    public void setResponse(String response) {
        responseLiveData.setValue(response);
    }
}
