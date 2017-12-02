package com.bond.android.familymap;

import android.util.Log;

import com.bond.android.familymap.model.UserInfo;
import com.bond.android.familymap.requests.LoginRequest;

import com.bond.android.familymap.requests.RegisterRequest;
import com.bond.android.familymap.results.EventsResult;
import com.bond.android.familymap.results.LoginResult;
import com.bond.android.familymap.results.PeopleResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;

/**
 * Created by bondd on 11/16/2017.
 */

public class Proxy
{

    private static Proxy instance;
    private Encoder encoder;
    private Client client;

    //SINGLETON
    private Proxy() {
        encoder = new Encoder();
        client = new Client();
    }

    public static Proxy getInstance()
    {
        if (instance == null)
            instance = new Proxy();
        return instance;
    }

    //returns true if successful
    LoginResult login(LoginRequest loginData)
    {
        LoginReqBody reqBody = new LoginReqBody(loginData.getUserName(), loginData.getPassword());
        String reqJson = encoder.encodeLoginReq(reqBody);
        client.setHostName(loginData.getServerHost());
        client.setHostPort(loginData.getServerPort());

        String loginPath = "/user/login";
        try {
            String url = "http://" + client.getHostName() + ":" + client.getHostPort() + loginPath;
           // Log.i("URL is: ", url);
            String responseJson = client.post(url, reqJson, false);
            //Log.i("JSON response", responseJson);
            LoginResult result = encoder.decodeLoginResult(responseJson);
            return result;
        }
        catch (Exception e)
        {
            Log.e("Proxy error on log in:", e.getMessage(), e);
            return null;
        }
    }

    EventsResult events()
    {
        String eventsPath = "/event";
        try {
            String url = "http://" + client.getHostName() + ":" + client.getHostPort() + eventsPath;
           // Log.i("URL is: ", url);
            String responseJson = client.get(url, true);
           // Log.i("JSON response", responseJson);
            EventsResult result = encoder.decodeEventsResult(responseJson);
            return result;
        }
        catch (Exception e)
        {
            Log.e("Proxy error on events:", e.getMessage(), e);
            return null;
        }
    }

    PeopleResult people()
    {
        String peoplePath = "/person";
        try {
            String url = "http://" + client.getHostName() + ":" + client.getHostPort() + peoplePath;
          //  Log.i("URL is: ", url);
            String responseJson = client.get(url, true);
          //  Log.i("JSON response", responseJson);
            PeopleResult result = encoder.decodePeopleResult(responseJson);
            return result;
        }
        catch (Exception e)
        {
            Log.e("Proxy error on people:", e.getMessage(), e);
            return null;
        }
    }

    LoginResult register(RegisterRequest registerData)
    {
        RegReqBody regReqBody = new RegReqBody(registerData.getUserName(), registerData.getPassword(), registerData.getEmail(),
                registerData.getFirstName(), registerData.getLastName(), registerData.getGender());
        String reqJson = encoder.encodeRegReq(regReqBody);
        client.setHostName(registerData.getServerHost());
        client.setHostPort(registerData.getServerPort());
        UserInfo userInfo = UserInfo.getInstance();
        userInfo.setFirstName(registerData.getFirstName());
        userInfo.setLastName(registerData.getLastName());

        String regPath = "/user/register";
        try {
            String url = "http://" + client.getHostName() + ":" + client.getHostPort() + regPath;
          //  Log.i("URL is: ", url);
            String responseJson = client.post(url, reqJson, false);
          //  Log.i("JSON response", responseJson);
            LoginResult result = encoder.decodeLoginResult(responseJson); //login result is identical to registerResult
            return result;
        }
        catch (Exception e)
        {
            Log.e("Proxy error on reg:", e.getMessage(), e);
            return null;
        }
    }

    class LoginReqBody{
        public String userName;
        public String password;

        public LoginReqBody(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }
    }

    class RegReqBody
    {
        private String userName;
        private String password;
        private String email;
        private String firstName;
        private String lastName;
        private String gender;

        public RegReqBody(String userName, String password, String email, String firstName, String lastName, String gender) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
        }
    }



    class Encoder {

        Gson gson = new Gson();

        String encodeLoginReq(LoginReqBody body)
        {
            return gson.toJson(body);
        }

        LoginResult decodeLoginResult(String json)
        {
            return gson.fromJson(json, LoginResult.class);
        }

        String encodeRegReq(RegReqBody body)
        {
            return gson.toJson(body);
        }

        EventsResult decodeEventsResult(String json)
        {
            return gson.fromJson(json, EventsResult.class);
        }

        PeopleResult decodePeopleResult(String json)
        {
            return gson.fromJson(json, PeopleResult.class);
        }

/*
        String encodePerson(Person person) {
            return gson.toJson(person);
        }

        Person decodePerson(String json) {
            return gson.fromJson(json, Person.class);
        }
*/
    }
}
