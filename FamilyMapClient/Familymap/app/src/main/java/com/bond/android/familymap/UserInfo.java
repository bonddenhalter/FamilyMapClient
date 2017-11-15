package com.bond.android.familymap;

/**
 * Created by bondd on 11/14/2017.
 */

public class UserInfo
{
    private String authToken;
    private String userName;
    private String personID;

    private static UserInfo instance = null;

    //SINGLETON OBJECT
    private UserInfo()
    {

    }

    public UserInfo getInstance()
    {
        if (instance == null)
            instance = new UserInfo();
        return instance;
    }


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
