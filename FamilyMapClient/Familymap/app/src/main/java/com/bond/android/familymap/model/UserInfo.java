package com.bond.android.familymap.model;

/**
 * Created by bondd on 11/14/2017.
 */

public class UserInfo
{
    private String authToken;
    private String userName;
    private String personID;
    private String firstName;
    private String lastName;

    private static UserInfo instance = null;

    //SINGLETON OBJECT
    private UserInfo()
    {

    }

    public static UserInfo getInstance()
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
        //since this is the last piece of data stashed, init the side lists
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        familyInfo.initFatherSideEvents();
        familyInfo.initMotherSideEvents();
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void logOut()
    {
        authToken = null;
        userName = null;
        personID = null;
        firstName = null;
        lastName = null;
    }

}
