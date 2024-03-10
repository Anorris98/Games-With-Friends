package com.GameWFriends;


//TODO: need to make sure this is updated, then need to make sure it grabs all the users important information and updates them as well.
public class UserInfo {
    private static UserInfo instance;

    // User information fields
    private String username;
    private String email;
    private int userId;

    // Private constructor with userId as a parameter
    private UserInfo(int userId) {
        this.userId = userId;
    }

    // Static method to get the singleton instance
    public static synchronized UserInfo getInstance(int userId) {
        if (instance == null) {
            instance = new UserInfo(userId);
        }
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
}
