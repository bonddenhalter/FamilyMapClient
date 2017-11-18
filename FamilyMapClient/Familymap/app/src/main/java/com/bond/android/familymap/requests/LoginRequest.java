package com.bond.android.familymap.requests;

/**
 * Created by bondd on 11/14/2017.
 */

public class LoginRequest
{
    private String serverHost;
    private String serverPort;
    private String userName;
    private String password;

    public String getServerHost() {
        return serverHost;
    }

    //returns true if all of the data fields are filled in so we can send the request
    public boolean infoComplete()
    {
        return (serverHost != null) && (serverPort != null) && (userName != null) && (password != null);
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
