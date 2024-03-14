package com.GameWFriends;


//TODO: need to make sure this is updated, then need to make sure it grabs all the users important information and updates them as well.

import android.content.Context;

import java.util.ArrayList;

/**
 * This class is used to store the user information for the current user.
 * It is a singleton class, so only one instance of it can exist at a time.
 */
public class UserInfo {
    private boolean userLoggedIn = false;
    /**
     * The instance of the UserInfo class.
     */
    private static UserInfo instance;
    private String profilePhoto;
    private String bio;

    /**
     * The username for the user.
     */
    private String username;
    /**
     * The email for the user.
     */
    private String email;
    /**
     * The user id for the user.
     */
    private int userId;

    /**
     * The password for the user, stored for ease of use in the future.
     */
    private String password;

    /**
     * The friend group ids for the user.
     */
    private ArrayList<Integer> friendGroupIds;
    /**
     * The context for the user.
     */
    private Context context;
    /**
     * private constructor for the UserInfo class.
     * @param userId the user id for the user
     * @param email the email for the user
     * @param password the password for the user
     */
    private UserInfo(int userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    // Static method to get the singleton instance
    public static synchronized UserInfo getInstance(int userId, String email, String password) {
        if (instance == null) {
            instance = new UserInfo(userId, email, password);   //check if instance is null, if so create a new instance, otherwise return the instance
        }
        return instance;
    }

    public static UserInfo getinitalizedInstance() {
        return instance;
    }

    // Getter and setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for userId (setter not provided as userId is set in constructor and presumed immutable)
    public int getUserId() {
        return userId;
    }
    public ArrayList<Integer> getFriendGroupIds() {
        return friendGroupIds;
    }
    public void setFriendGroupIds(ArrayList<Integer> friendGroupIds) {
        this.friendGroupIds = friendGroupIds;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public boolean isUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    private void loadFriendGroupData() {
        //get all friend group ids
    }
}
