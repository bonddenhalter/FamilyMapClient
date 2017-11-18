package com.bond.android.familymap.results;

import com.bond.android.familymap.model.Event;

/**
 * Created by bondd on 11/17/2017.
 */

public class EventsResult {


    private Event data[]; //an array of Event objects (as in EventResult)
    private String message;

    public static final String SQLFailureMessage = "A database error occured while retrieving the events.";
    public static final String invalidAuthMessage = "Error: invalid auth token.";

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
