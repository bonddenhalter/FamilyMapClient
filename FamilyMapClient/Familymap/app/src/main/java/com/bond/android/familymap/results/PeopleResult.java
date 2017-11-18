package com.bond.android.familymap.results;

import com.bond.android.familymap.model.Person;

/**
 * Created by bondd on 11/17/2017.
 */

public class PeopleResult {

    private Person[] data; //an array of Person objects (as in PersonResult)
    private String message;
    public static String invalidAuthTokenMsg = "Error: Invalid auth token.";
    public static String SQLFailureMsg = "Error: SQL failure.";

    public PeopleResult(Person[] data) {
        this.data = data;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
