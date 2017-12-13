package com.bond.android.familymap.model;

import android.util.Log;

import com.bond.android.familymap.R;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_MAGENTA;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ROSE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;

/**
 * Created by bondd on 12/9/2017.
 */

public class Settings {

    private boolean showLifeStoryLines;
    private int lifeStoryLinesColor;
    private boolean showFamilyTreeLines;
    private int familyTreeLinesColor;
    private boolean showSpouseLines;
    private int spouseLinesColor;
    private String mapType;

    private boolean mainLoadMapFragOnCreate = false;

    private static Map<String, Float> eventColors;
    private Set<Polyline> lifelines;
    private Set<Polyline> familyTreeLines;
    private Set<Polyline> spouseLines;

    private Event lastEventSelected; //updated when map fragment paused
    private boolean mapFragInMain;
    Set<Marker> markers;

    private static Settings instance = null;


    //SINGLETON
    private Settings()
    {
        showLifeStoryLines = false;
        lifeStoryLinesColor = R.color.GREEN;
        showFamilyTreeLines = false;
        familyTreeLinesColor = R.color.BLUE;
        showSpouseLines = false;
        spouseLinesColor = R.color.RED;
        mapType = "Normal";

        eventColors = new HashMap<>();
        lifelines = new HashSet<>();
        familyTreeLines = new HashSet<>();
        spouseLines = new HashSet<>();

        markers = new HashSet<>();
    }

    public static Settings getInstance()
    {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    public void logOut()
    {
        lifelines.clear();
        familyTreeLines.clear();
        spouseLines.clear();
        lastEventSelected = null;
    }

    public Set<Polyline> getSpouseLines() {
        return spouseLines;
    }

    public void setSpouseLines(Set<Polyline> spouseLines) {
        this.spouseLines = spouseLines;
    }

    public boolean isMapFragInMain() {
        return mapFragInMain;
    }

    public void setMapFragInMain(boolean mapFragInMain) {
        this.mapFragInMain = mapFragInMain;
    }

    public Event getLastEventSelected() {
        return lastEventSelected;
    }

    public void setLastEventSelected(Event lastEventSelected) {
        this.lastEventSelected = lastEventSelected;
    }

    public Set<Polyline> getLifelines() {
        return lifelines;
    }

    public void setLifelines(Set<Polyline> lifelines) {
        this.lifelines = lifelines;
    }

    public Set<Polyline> getFamilyTreeLines() {
        return familyTreeLines;
    }

    public void setFamilyTreeLines(Set<Polyline> familyTreeLines) {
        this.familyTreeLines = familyTreeLines;
    }

    public boolean isShowLifeStoryLines() {
        return showLifeStoryLines;
    }

    public void setShowLifeStoryLines(boolean showLifeStoryLines) {
        this.showLifeStoryLines = showLifeStoryLines;
    }

    public int getLifeStoryLinesColor() {
        return lifeStoryLinesColor;
    }

    public void setLifeStoryLinesColor(int lifeStoryLinesColor) {
        this.lifeStoryLinesColor = lifeStoryLinesColor;
    }

    public boolean isShowFamilyTreeLines() {
        return showFamilyTreeLines;
    }

    public void setShowFamilyTreeLines(boolean showFamilyTreeLines) {
        this.showFamilyTreeLines = showFamilyTreeLines;
    }

    public int getFamilyTreeLinesColor() {
        return familyTreeLinesColor;
    }

    public void setFamilyTreeLinesColor(int familyTreeLinesColor) {
        this.familyTreeLinesColor = familyTreeLinesColor;
    }

    public boolean isShowSpouseLines() {
        return showSpouseLines;
    }

    public void setShowSpouseLines(boolean showSpouseLines) {
        this.showSpouseLines = showSpouseLines;
    }

    public int getSpouseLinesColor() {
        return spouseLinesColor;
    }

    public void setSpouseLinesColor(int spouseLinesColor) {
        this.spouseLinesColor = spouseLinesColor;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public boolean isMainLoadMapFragOnCreate() {
        return mainLoadMapFragOnCreate;
    }

    public void setMainLoadMapFragOnCreate(boolean mainLoadMapFragOnCreate) {
        this.mainLoadMapFragOnCreate = mainLoadMapFragOnCreate;
    }

    public static Map<String, Float> getEventColors() {
        return eventColors;
    }

    public int getLineColorFromPosition(int position)
    {
        switch (position)
        {
            case 0:
                return R.color.GREEN;
            case 1:
                return R.color.BLUE;
            case 2:
                return R.color.RED;
            case 3:
                return R.color.YELLOW;
            default:
                Log.e("line color", "invalid");
                return R.color.BLACK;
        }
    }

    public int getSpinnerPositionFromColor(int color)
    {
        switch (color)
        {
            case R.color.GREEN:
                return 0;
            case R.color.BLUE:
                return 1;
            case R.color.RED:
                return 2;
            case R.color.YELLOW:
                return 3;
            default:
                Log.e("spinner color", "invalid");
                return -1;
        }
    }

    public String getMapTypeFromPosition(int position)
    {
        switch(position)
        {
            case 0:
                return "Normal";
            case 1:
                return "Hybrid";
            case 2:
                return "Satellite";
            case 3:
                return "Terrain";
            default:
                Log.e("map type", "invalid");
                return "Normal";
        }
    }

    public int getSpinnerPositionFromMapType(String mapType)
    {
        switch (mapType)
        {
            case "Normal":
                return 0;
            case "Hybrid":
                return 1;
            case "Satellite":
                return 2;
            case "Terrain":
                return 3;
            default:
                Log.e("map type", "invalid");
                return -1;

        }
    }

    public Set<Marker> getMarkers() {
        return markers;
    }

    public void setMarkers(Set<Marker> markers) {
        this.markers = markers;
    }

    public void addMarker(Marker m)
    {
        markers.add(m);
    }


}
