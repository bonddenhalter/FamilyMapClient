package com.bond.android.familymap.model;

import android.util.Log;

import com.bond.android.familymap.results.EventsResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bondd on 11/17/2017.
 */

public class FamilyInfo
{
    private Event[] events;
    private Person[] people;

    private boolean eventsLoadSuccessful = false;
    private boolean peopleLoadSuccessful = false;

    private boolean baptismFilter = true;
    private boolean birthFilter = true;
    private boolean censusFilter = true;
    private boolean christeningFilter = true;
    private boolean deathFilter = true;
    private boolean marriageFilter = true;
    private boolean fatherFilter = true;
    private boolean motherFilter = true;
    private boolean maleFilter = true;
    private boolean femaleFilter = true;

    private Set<String> fatherSideEvents;
    private Set<String> motherSideEvents;

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

    public Event[] getFilteredEvents()
    {
        List<Event> filtered = new ArrayList<>();
        for (Event e : events)
        {
            if (passesAllFilters(e))
                filtered.add(e);
        }
        return filtered.toArray(new Event[0]);
    }

    public boolean passesAllFilters(Event e)
    {
        //first check event filters
        switch (e.getEventType().toLowerCase())
        {
            case "baptism":
                if (!baptismFilter)
                    return false;
                break;
            case "birth":
                if (!birthFilter)
                    return false;
                break;
            case "census":
                if (!censusFilter)
                    return false;
                break;
            case "christening":
                if (!christeningFilter)
                    return false;
                break;
            case "death":
                if (!deathFilter)
                    return false;
                break;
            case "marriage":
                if (!marriageFilter)
                    return false;
                break;
            default:
                break;
        }
        if (isFatherSideEvent(e) && !fatherFilter)
            return false;
        if (isMotherSideEvent(e) && !motherFilter)
            return false;
        if (isMaleEvent(e) && !maleFilter)
            return false;
        if (isFemaleEvent(e) && !femaleFilter)
            return false;

        return true;
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
        for (Event e : getFilteredEvents())
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



    public boolean isBaptismEvent(Event e)
    {
        return (e.getEventType().toLowerCase().equals("baptism"));
    }

    public boolean isBirthEvent(Event e)
    {
        return (e.getEventType().toLowerCase().equals("birth"));
    }

    public boolean isCensusEvent(Event e)
    {
        return (e.getEventType().toLowerCase().equals("census"));
    }

    public boolean isChristeningEvent(Event e)
    {
        return (e.getEventType().toLowerCase().equals("christening"));
    }

    public boolean isDeathEvent(Event e)
    {
        return (e.getEventType().toLowerCase().equals("death"));
    }

    public boolean isMarriageEvent(Event e)
    {
        return (e.getEventType().toLowerCase().equals("marriage"));
    }

    public boolean isFatherSideEvent(Event e)
    {
        return fatherSideEvents.contains(e.getEventID());
    }

    public boolean isMotherSideEvent(Event e)
    {
        return motherSideEvents.contains(e.getEventID());
    }

    public boolean isMaleEvent(Event e)
    {
        if (e != null) {
            Person p = getPersonFromID(e.getPerson());
            if (p != null)
            {
            String gender = p.getGender().toLowerCase();
            return (gender.equals("male") || gender.equals("m"));
            }
        }
        return false;
    }

    public boolean isFemaleEvent(Event e)
    {
        if (e != null) {
            Person p = getPersonFromID(e.getPerson());
            if (p != null) {
                String gender = p.getGender().toLowerCase();
                return (gender.equals("female") || gender.equals("f"));
            }
        }
        return false;
    }



    public boolean isBaptismFilter() {
        return baptismFilter;
    }

    public void setBaptismFilter(boolean baptismFilter) {
        this.baptismFilter = baptismFilter;
    }

    public boolean isBirthFilter() {
        return birthFilter;
    }

    public void setBirthFilter(boolean birthFilter) {
        this.birthFilter = birthFilter;
    }

    public boolean isCensusFilter() {
        return censusFilter;
    }

    public void setCensusFilter(boolean censusFilter) {
        this.censusFilter = censusFilter;
    }

    public boolean isChristeningFilter() {
        return christeningFilter;
    }

    public void setChristeningFilter(boolean christeningFilter) {
        this.christeningFilter = christeningFilter;
    }

    public boolean isDeathFilter() {
        return deathFilter;
    }

    public void setDeathFilter(boolean deathFilter) {
        this.deathFilter = deathFilter;
    }

    public boolean isMarriageFilter() {
        return marriageFilter;
    }

    public void setMarriageFilter(boolean marriageFilter) {
        this.marriageFilter = marriageFilter;
    }

    public boolean isFatherFilter() {
        return fatherFilter;
    }

    public void setFatherFilter(boolean fatherFilter) {
        this.fatherFilter = fatherFilter;
    }

    public boolean isMotherFilter() {
        return motherFilter;
    }

    public void setMotherFilter(boolean motherFilter) {
        this.motherFilter = motherFilter;
    }

    public boolean isMaleFilter() {
        return maleFilter;
    }

    public void setMaleFilter(boolean maleFilter) {
        this.maleFilter = maleFilter;
    }

    public boolean isFemaleFilter() {
        return femaleFilter;
    }

    public void setFemaleFilter(boolean femaleFilter) {
        this.femaleFilter = femaleFilter;
    }

    public Set<String> getFatherSideEvents() {
        return fatherSideEvents;
    }

    public void setFatherSideEvents(Set<String> fatherSideEvents) {
        this.fatherSideEvents = fatherSideEvents;
    }

    public Set<String> getMotherSideEvents() {
        return motherSideEvents;
    }

    public void setMotherSideEvents(Set<String> motherSideEvents) {
        this.motherSideEvents = motherSideEvents;
    }

    public void initFatherSideEvents()
    {
        fatherSideEvents = new HashSet<>();
        UserInfo userInfo = UserInfo.getInstance();
        String userID = userInfo.getPersonID();
        Person user = getPersonFromID(userID);
        addAllEventsOfPerson(user, fatherSideEvents);
        String fatherID = user.getFather();
        if (fatherID != null && !fatherID.equals(""))
        {
            Person father = getPersonFromID(fatherID);
            if (father != null) {
                addAllEventsOfPerson(father, fatherSideEvents);
                addAllAncestors(father, fatherSideEvents);
            }
        }
    }


    public void initMotherSideEvents()
    {
        motherSideEvents = new HashSet<>();
        UserInfo userInfo = UserInfo.getInstance();
        String userID = userInfo.getPersonID();
        Person user = getPersonFromID(userID);
        addAllEventsOfPerson(user, motherSideEvents);
        String motherID = user.getMother();
        if (motherID != null && !motherID.equals(""))
        {
            Person mother = getPersonFromID(motherID);
            if (mother != null)
            {
                addAllEventsOfPerson(mother, motherSideEvents);
                addAllAncestors(mother, motherSideEvents);
            }
        }
    }


    public void addAllAncestors(Person p, Set set)
    {
        Person father = null;
        Person mother = null;
        if (p.getFather() != null && !p.getFather().equals(""))
            father = getPersonFromID(p.getFather());
        if (father != null) {
            addAllEventsOfPerson(father, set);
            addAllAncestors(father, set);
        }

        if (p.getMother() != null && !p.getMother().equals(""))
            mother = getPersonFromID(p.getMother());
        if (mother != null) {
            addAllEventsOfPerson(mother, set);
            addAllAncestors(mother, set);
        }
    }


    public void addAllEventsOfPerson(Person p, Set set)
    {
        Event[] personEvents = getUnfilteredEventsOfPerson(p.getPersonID());
        for (Event e : personEvents)
        {
            set.add(e.getEventID());
        }
    }

    public Event[] getUnfilteredEventsOfPerson(String personID)
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
}
