package com.bond.android.familymap.model;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by bondd on 12/14/2017.
 */
public class FamilyInfoTest {

    FamilyInfo familyInfo;
    Event[] testEvents;
    Person[] testPeople;
    @Before
    public void setUp() throws Exception {

        UserInfo userInfo = UserInfo.getInstance();
        userInfo.setPersonID("person0");

        familyInfo = FamilyInfo.getInstance();
        testEvents = new Event[4];
        testEvents[0] = new Event("eventId0", "desc0", "person0", "1.23", "4.56", "country0", "city0", "baptism", "1950");
        testEvents[1] = new Event("eventId1", "desc0", "person1", "lat1", "long1", "country1", "city1", "birth", "1940");
        testEvents[2] = new Event("eventId2", "desc0", "person2", "lat2", "long2", "country2", "city2", "birth", "1969");
        testEvents[3] = new Event("eventId3", "desc0", "person0", "lat3", "long3", "country3", "city3", "death", "2010");
        familyInfo.setEvents(testEvents);

        testPeople = new Person[3];
        testPeople[0] = new Person("person0", "desc0", "fn", "ln", "m", "person1", "person2", ""); //user
        testPeople[1] = new Person("person1", "desc0", "fn1", "ln1", "m", "", "", "person2"); //father
        testPeople[2] = new Person("person2", "desc0", "fn2", "ln2", "f", "", "", "person1"); //mother
        familyInfo.setPeople(testPeople);

        familyInfo.initFatherSideEvents();
        familyInfo.initMotherSideEvents();
    }

    @Test
    public void getFilteredEvents() throws Exception
    {
        familyInfo.setFatherFilter(true);
        familyInfo.setMotherFilter(true);
        familyInfo.setMaleFilter(true);
        familyInfo.setFemaleFilter(true);
        familyInfo.setDeathFilter(true);
        familyInfo.setBaptismFilter(true);

        //only filter out birth events
        familyInfo.setBirthFilter(false);
        Event[] filteredEvents = familyInfo.getFilteredEvents();
        assertNotNull(filteredEvents);
        assertTrue(filteredEvents.length == 2);
        assertTrue(filteredEvents[0] == testEvents[0]);
        assertTrue(filteredEvents[1] == testEvents[3]);

    }

    @Test
    public void passesAllFilters() throws Exception {

        familyInfo.setBirthFilter(true);
        familyInfo.setMaleFilter(true);
        familyInfo.setFemaleFilter(true);

        //family side filter
        familyInfo.setFatherFilter(true);
        familyInfo.setMotherFilter(false);
        assertTrue(familyInfo.passesAllFilters(testEvents[1])); //father event
        assertFalse(familyInfo.passesAllFilters(testEvents[2])); //mother event

        //birth events (event filter)
        familyInfo.setBirthFilter(false);
        familyInfo.setMotherFilter(true);
        assertFalse(familyInfo.passesAllFilters(testEvents[2]));

        //gender filter
        familyInfo.setBirthFilter(true);
        familyInfo.setMaleFilter(false);
        assertFalse(familyInfo.passesAllFilters(testEvents[1]));
        assertTrue(familyInfo.passesAllFilters(testEvents[2]));
    }

    @Test
    public void getFirstName() throws Exception {
        assertEquals(familyInfo.getFirstName("person0"), "fn");
    }

    @Test
    public void getLastName() throws Exception {
        assertEquals(familyInfo.getLastName("person0"), "ln");
    }

    @Test
    public void getPersonFromEvent() throws Exception {
        assertEquals(familyInfo.getPersonFromEvent(testEvents[0]), testPeople[0]);
    }

    @Test
    public void getPersonFromID() throws Exception {
        assertEquals(familyInfo.getPersonFromID("person0"), testPeople[0]);
    }

    @Test
    public void getEventsOfPerson() throws Exception {
        familyInfo.setDeathFilter(true);
        familyInfo.setBirthFilter(true);
        familyInfo.setMaleFilter(true);
        familyInfo.setFatherFilter(true);
        familyInfo.setMaleFilter(true);
        Event[] results = familyInfo.getEventsOfPerson("person0");
        assertNotNull(results);
        Event[] expected = new Event[2];
        expected[0] = testEvents[0];
        expected[1] = testEvents[3];
        assertTrue(results.length == expected.length);
        assertTrue(results[0] == expected[0]);
        assertTrue(results[1] == expected[1]);

        //events should be filtered out
        familyInfo.setMaleFilter(false);
        results = familyInfo.getEventsOfPerson("person0");
        assertTrue(results.length == 0);
    }

    @Test
    public void getEvent() throws Exception {
        assertTrue(familyInfo.getEvent("eventId0") == testEvents[0]);

    }

    @Test
    public void getEventLocation() throws Exception {
        LatLng result = familyInfo.getEventLocation("eventId0");
        LatLng expected = new LatLng(Double.parseDouble("1.23"), Double.parseDouble("4.56"));
        assertTrue(result.equals(expected));
    }

    @Test
    public void findChildren() throws Exception {
        List<Person> result = familyInfo.findChildren("person1");
        List<Person> expected = new ArrayList<>();
        expected.add(testPeople[0]);
        assertTrue(result.equals(expected));
    }

    @Test
    public void findSpouse() throws Exception {
        assertTrue(familyInfo.findSpouse("person1") == testPeople[2]);
    }

    @Test
    public void wipeAllFamilyData() throws Exception {
        assertFalse(familyInfo.getEvents() == null);
        assertFalse(familyInfo.getPeople() == null);

        familyInfo.wipeAllFamilyData();
        assertTrue(familyInfo.getEvents() == null);
        assertTrue(familyInfo.getPeople() == null);
        assertFalse(familyInfo.isEventsLoadSuccessful());
        assertFalse(familyInfo.isPeopleLoadSuccessful());

        setUp(); //set things back as they were
    }

    @Test
    public void isBaptismEvent() throws Exception {
        assertTrue(familyInfo.isBaptismEvent(testEvents[0]));
        assertFalse(familyInfo.isBaptismEvent(testEvents[1]));

        //the rest of the events follow this same logic
    }

    @Test
    public void isFatherSideEvent() throws Exception {
        assertTrue(familyInfo.isFatherSideEvent(testEvents[0])); //user event
        assertTrue(familyInfo.isFatherSideEvent(testEvents[1])); //father
        assertFalse(familyInfo.isFatherSideEvent(testEvents[2])); //mother
    }

    @Test
    public void isMotherSideEvent() throws Exception {
        assertTrue(familyInfo.isMotherSideEvent(testEvents[0])); //user event
        assertFalse(familyInfo.isMotherSideEvent(testEvents[1])); //father
        assertTrue(familyInfo.isMotherSideEvent(testEvents[2])); //mother
    }

    @Test
    public void isMaleEvent() throws Exception {
        assertTrue(familyInfo.isMaleEvent(testEvents[1]));
        assertFalse(familyInfo.isMaleEvent(testEvents[2]));
    }

    @Test
    public void isFemaleEvent() throws Exception {
        assertFalse(familyInfo.isFemaleEvent(testEvents[1]));
        assertTrue(familyInfo.isFemaleEvent(testEvents[2]));
    }

    @Test
    public void initFatherSideEvents() throws Exception {
        //this is tested in Setup and other tests
    }

    @Test
    public void initMotherSideEvents() throws Exception {
        //this is tested in Setup and other tests
    }

    @Test
    public void addAllAncestors() throws Exception {
        //this is tested in Setup and other tests (called in initFatherSideEvents())
    }

    @Test
    public void addAllEventsOfPerson() throws Exception {
        //this is tested in Setup and other tests (called in initFatherSideEvents())
    }

    @Test
    public void getUnfilteredEventsOfPerson() throws Exception {
        familyInfo.setBaptismFilter(false);
        familyInfo.setDeathFilter(false);
        familyInfo.setMaleFilter(false);
        familyInfo.setFemaleFilter(false);
        familyInfo.setMotherFilter(false);
        familyInfo.setFatherFilter(false);
        Event[] result = familyInfo.getUnfilteredEventsOfPerson("person0");
        Event[] expected = new Event[2];
        expected[0] = testEvents[0];
        expected[1] = testEvents[3];
        assertTrue(result.length == expected.length);
        assertTrue(result[0].equals(expected[0]));
        assertTrue(result[1].equals(expected[1]));
    }

}