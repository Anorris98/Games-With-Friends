package com.GameWFriends;

/**
 * This class is used to store user info once they have successfully logged in.
 */
public class UserInfo {

    /**
     * Integer to Store UserID
     */
    private Integer userId;

    /**
     * String to Store UserName
     */
    private String userName;

    /**
     * String to Store UserEmail
     */
    private String userEmail;
    // Add additional fields as necessary

    /**
     * Constructor for UserInfo, stores everything once a user has successfully logged  in.
     * @param userId
     * @param userName
     * @param userEmail
     */
    public UserInfo(Integer userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    /**
     * Gets the current users Id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Gets the current users Name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the current users Email
     */
    public String getUserEmail() {
        return userEmail;
    }

}

