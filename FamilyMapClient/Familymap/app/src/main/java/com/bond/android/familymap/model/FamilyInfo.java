package com.bond.android.familymap.model;

import com.bond.android.familymap.results.EventsResult;

/**
 * Created by bondd on 11/17/2017.
 */

public class FamilyInfo
{
    private Event[] events;
    private Person[] people;

    private boolean eventsLoadSuccessful = false;
    private boolean peopleLoadSuccessful = false;

    private static FamilyInfo instance = null;
    //SINGLETON
    private FamilyInfo()
    {}

    public static FamilyInfo getInstance()
    {
        if (instance == null)
            instance = new FamilyInfo();
        return instance;

    }

    public String getFirstName(String personID)
    {
        for (Person p : people)
        {
            if (p.getPersonID().equals(personID))
                return p.getFirstName();
        }
        return null;
    }

    public String getLastName(String personID)
    {
        for (Person p : people)
        {
            if (p.getPersonID().equals(personID))
                return p.getLastName();
        }
        return null;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    public Person[] getPeople() {
        return people;
    }

    public void setPeople(Person[] people) {
        this.people = people;
    }

    public boolean isEventsLoadSuccessful() {
        return eventsLoadSuccessful;
    }

    public void setEventsLoadSuccessful(boolean eventsLoadSuccessful) {
        this.eventsLoadSuccessful = eventsLoadSuccessful;
    }

    public boolean isPeopleLoadSuccessful() {
        return peopleLoadSuccessful;
    }

    public void setPeopleLoadSuccessful(boolean peopleLoadSuccessful) {
        this.peopleLoadSuccessful = peopleLoadSuccessful;
    }
}
