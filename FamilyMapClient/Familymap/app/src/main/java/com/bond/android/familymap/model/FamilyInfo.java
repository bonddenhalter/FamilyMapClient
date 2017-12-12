package com.bond.android.familymap.model;

import android.util.Log;

import com.bond.android.familymap.results.EventsResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public Person getPersonFromEvent(Event e)
    {
        return getPersonFromID(e.getPerson());
    }

    public Person getPersonFromID(String personID)
    {
        for (Person p : people)
        {
            if (p.getPersonID().equals(personID))
            {
                return p;
            }
        }
        return null;
    }

    public Event[] getEventsOfPerson(String personID)
    {
        ArrayList<Event> orderedEvents = new ArrayList<>();
        for (Event e : events)
        {
            if (e.getPerson().equals(personID))
            {
                orderedEvents.add(e);
            }
        }
        Collections.sort(orderedEvents);
        return  orderedEvents.toArray(new Event[0]);
    }

    public Event getEvent(String eventID)
    {
        for (Event e : events)
        {
            if (e.getEventID().equals(eventID))
                return e;
        }
        return null;
    }

    public LatLng getEventLocation(String eventID)
    {
        for (Event e : events)
        {
            if (e.getEventID().equals(eventID))
            {
                double lat = Double.parseDouble(e.getLatitude());
                double lng = Double.parseDouble(e.getLongitude());
                return new LatLng(lat, lng);
            }
        }
        return null;
    }

    public List<Person> findChildren (String parentID)
    {
        List<Person> children = new ArrayList<>();
        for (Person p : people)
        {
            if (p.getFather().equals(parentID) || p.getMother().equals(parentID)) //if this is a child
            {
                children.add(p);
            }
        }
        return children;
    }

    public Person findSpouse(String spouseID)
    {
        for (Person p : people)
        {
            if (p.getSpouse().equals(spouseID))
                return p;
        }
        return null;
    }

    public void wipeAllFamilyData()
    {
        events = null;
        people = null;
        eventsLoadSuccessful = false;
        peopleLoadSuccessful = false;
    }


}
