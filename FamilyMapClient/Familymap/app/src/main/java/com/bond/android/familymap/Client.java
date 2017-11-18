package com.bond.android.familymap;

import android.util.Log;

import com.bond.android.familymap.model.UserInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by bondd on 11/16/2017.
 */

/*From Dr. Barker*/
class Client {

    private String hostName;
    private String hostPort;

    //if auth is true, it will send the auth token
    String get(String url, boolean auth) throws Exception {

        System.out.println("CLIENT: GET: " + url);
        System.out.println();

        HttpURLConnection connection =
                (HttpURLConnection) new URL(url).openConnection();

        connection.setRequestMethod("GET");
        if (auth) {
            UserInfo userInfo = UserInfo.getInstance();
            connection.setRequestProperty("Authorization", userInfo.getAuthToken());
        }
        connection.connect();

        getResponseHeaders(connection);

        String response = getResponseBody(connection);

        return response;

    }

    //if auth is true, it will send the auth token
    //return response body
    String post(String url, String request, boolean auth) throws Exception {

        System.out.println("CLIENT: POST: " + url);
        System.out.println();

        HttpURLConnection connection =
                (HttpURLConnection) new URL(url).openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        if (auth) {
            UserInfo userInfo = UserInfo.getInstance();
            connection.setRequestProperty("Authorization", userInfo.getAuthToken());
        }

        sendRequestBody(connection, request);

        //getResponseHeaders(connection);
        int status = connection.getResponseCode();
        if (status != HTTP_OK && status != HTTP_INTERNAL_ERROR)
        {
            //TODO: respond to request failure
            Log.e("server response msg", connection.getResponseMessage());
            return "POST FAILED!"; //TODO: There's a message built in to the response
        }
        else
        {
            BufferedReader br;
            if (status == HTTP_INTERNAL_ERROR)
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            else
                br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            String respBody = sb.toString();

            Log.i("server response msg", respBody);
            return respBody;
        }


    }

    void sendRequestBody(HttpURLConnection connection, String request)
            throws Exception {

        PrintWriter out = new PrintWriter(connection.getOutputStream());
        out.print(request);
        out.close();

        System.out.println("request body:");
        System.out.println(request);
        System.out.println();

    }

    void getResponseHeaders(HttpURLConnection connection) throws Exception {

        System.out.println("response:");

        int status = connection.getResponseCode();
        System.out.println("status: " + status);

        String message = connection.getResponseMessage();
        System.out.println("message: " + message);

        System.out.println();

        //printHeaders(connection.getHeaderFields());

    }

    String getResponseBody(HttpURLConnection connection) throws Exception {

        String response = "";

        System.out.println("response body:");

        int status = connection.getResponseCode();
        if (status == HTTP_OK) {

            Scanner in = new Scanner(connection.getInputStream());
            while (in.hasNextLine()) {
                String line = in.nextLine();
                response += line + "\n";
                System.out.println(line);
            }
            in.close();

        }

        System.out.println();

        return response;

    }

    void printHeaders(Map<String, List<String>> headers) {

        System.out.println("response headers:");

        for (String name : headers.keySet())
            System.out.println(name + " = " + headers.get(name));

        System.out.println();

    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }
}

