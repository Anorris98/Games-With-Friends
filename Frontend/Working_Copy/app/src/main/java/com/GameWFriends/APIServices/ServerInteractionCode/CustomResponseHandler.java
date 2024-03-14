package com.GameWFriends.APIServices.ServerInteractionCode;

import org.json.JSONObject;

public interface CustomResponseHandler {
    void onSuccess(JSONObject response);
    void onError(String message);
}
