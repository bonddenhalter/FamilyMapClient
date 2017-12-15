package com.bond.android.familymap.model;

import com.bond.android.familymap.R;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by bondd on 12/14/2017.
 */
public class SettingsTest {

    Settings settings;

    @Before
    public void setUp() throws Exception {
        settings = Settings.getInstance();
    }

    @Test
    public void logOut() throws Exception {
        settings.logOut();
        assertTrue(settings.getLifelines().isEmpty());
        assertTrue(settings.getFamilyTreeLines().isEmpty());
        assertTrue (settings.getSpouseLines().isEmpty());
        assertNull(settings.getLastEventSelected());
    }

    @Test
    public void getLineColorFromPosition() throws Exception {
        assertTrue(settings.getLineColorFromPosition(0) == R.color.GREEN );
        assertTrue(settings.getLineColorFromPosition(1) == R.color.BLUE);
        assertTrue(settings.getLineColorFromPosition(2) == R.color.RED);
        assertTrue(settings.getLineColorFromPosition(3) == R.color.YELLOW);
    }

    @Test
    public void getSpinnerPositionFromColor() throws Exception {
        assertTrue(settings.getSpinnerPositionFromColor(R.color.GREEN) == 0);
        assertTrue(settings.getSpinnerPositionFromColor(R.color.BLUE) == 1);
        assertTrue(settings.getSpinnerPositionFromColor(R.color.RED) == 2);
        assertTrue(settings.getSpinnerPositionFromColor(R.color.YELLOW) == 3);
    }

    @Test
    public void getMapTypeFromPosition() throws Exception {
        assertTrue(settings.getMapTypeFromPosition(0).equals("Normal"));
        assertTrue(settings.getMapTypeFromPosition(1).equals("Hybrid"));
        assertTrue(settings.getMapTypeFromPosition(2).equals("Satellite"));
        assertTrue(settings.getMapTypeFromPosition(3).equals("Terrain"));
    }

    @Test
    public void getSpinnerPositionFromMapType() throws Exception {
        assertTrue(settings.getSpinnerPositionFromMapType("Normal") == 0);
        assertTrue(settings.getSpinnerPositionFromMapType("Hybrid") == 1);
        assertTrue(settings.getSpinnerPositionFromMapType("Satellite") == 2);
        assertTrue(settings.getSpinnerPositionFromMapType("Terrain") == 3);
    }


}