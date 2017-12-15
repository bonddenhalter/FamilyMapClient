package com.bond.android.familymap.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bondd on 12/14/2017.
 */
public class EventTest {
    Event e1;
    Event e2;
    Event e3;
    Event e4;

    @Before
    public void setUp() throws Exception {
        e1 = new Event(null, null, null, null, null, null, null, null, "2000");
        e2 = new Event(null, null, null, null, null, null, null, null, "2000");
        e3 = new Event(null, null, null, null, null, null, null, null, "1999");
        e4 = new Event(null, null, null, null, null, null, null, null, "2001");

    }

    @Test
    public void compareTo() throws Exception {
        assertTrue(e1.compareTo(e1) == 0);
        assertTrue(e1.compareTo(e2) == 0);
        assertTrue(e1.compareTo(e3) > 0);
        assertTrue(e1.compareTo(e4) < 0);
    }


}