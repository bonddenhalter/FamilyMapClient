package com.bond.android.familymap;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bond.android.familymap.model.Event;
import com.bond.android.familymap.model.FamilyInfo;
import com.bond.android.familymap.model.Person;
import com.bond.android.familymap.model.Settings;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.graphics.Color.BLACK;
import static com.bond.android.familymap.R.color.BLUE;
import static com.bond.android.familymap.R.color.GREEN;
import static com.bond.android.familymap.R.color.RED;
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
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;


/**
 * Created by bondd on 12/1/2017.
 */

public class MapFragment extends Fragment {

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private TextView mTextView;
    private boolean mMarkerClicked;
    private String personSelectedID;
    private String eventID;

    private Float[] colorChoices = {HUE_GREEN, HUE_RED, HUE_AZURE, HUE_ORANGE, HUE_YELLOW, HUE_CYAN, HUE_MAGENTA, HUE_VIOLET,
            HUE_ROSE, HUE_BLUE};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null)
            eventID = getArguments().getString("eventID");

        final View v = inflater.inflate(R.layout.fragment_map, container, false);

        Settings settings = Settings.getInstance(); //initialize settings

        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

//        mMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                mGoogleMap = googleMap;
//                initMap();
//            }
//        });

        mTextView = (TextView) v.findViewById(R.id.mapMarkerInfo);

        erasePolyLines(settings.getLifelines());
        if (eventID != null) //if we are focused on one event, show their info on bottom
        {
            FamilyInfo familyInfo = FamilyInfo.getInstance();
            Event e = familyInfo.getEvent(eventID);

            populateEventDisplay(e);

            mMarkerClicked = true;

        }
        else
            mMarkerClicked = false;

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMarkerClicked) { //if the text view is pressed and a marker has been selected, open the person activity for that person
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra("personID", personSelectedID);
                    startActivity(intent);
                }
            }
        });

//        if (mGoogleMap != null) {
//            if (settings.isShowLifeStoryLines())
//                drawLifeStoryLines(eventID, settings.getLifeStoryLinesColor());
//            else
//                erasePolyLines(settings.getLifelines());
//
//            if (settings.isShowFamilyTreeLines())
//                drawFamilyTreeLines(eventID, settings.getFamilyTreeLinesColor());
//            else
//                erasePolyLines(settings.getFamilyTreeLines());
//
//            if (settings.isShowSpouseLines())
//                drawSpouseLines(eventID, settings.getSpouseLinesColor());
//        }

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Settings settings = Settings.getInstance();

        //if (eventID == null) { //if part of the main activity
        if(settings.isMapFragInMain()) {
            inflater.inflate(R.menu.activity_main, menu);

            //set settings icon
            Drawable settingsIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear).colorRes(R.color.WHITE).sizeDp(25);
            MenuItem settingsItem = menu.findItem(R.id.settings_menu_icon);
            settingsItem.setIcon(settingsIcon);

            //set filter icon
            Drawable filterIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_filter).colorRes(R.color.WHITE).sizeDp(25);
            MenuItem filterItem = menu.findItem(R.id.filter_menu_icon);
            filterItem.setIcon(filterIcon);

            //set search icon
            Drawable searchIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_search).colorRes(R.color.WHITE).sizeDp(25);
            MenuItem searchItem = menu.findItem(R.id.search_menu_icon);
            searchItem.setIcon(searchIcon);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.settings_menu_icon:
                //Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;    // return true when handled successfully
            case R.id.filter_menu_icon:
                //Toast.makeText(getActivity(), "Filter", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
                return true;    // return true when handled successfully
            case R.id.search_menu_icon:
              //  Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;    // return true when handled successfully
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    ///HELPER FUNCTIONS
    void initMap()
    {
        LatLng centerInit = new LatLng(0, 0);
        CameraUpdate update = CameraUpdateFactory.newLatLng(centerInit);
        mGoogleMap.moveCamera(update);
        Settings settings = Settings.getInstance();

        mGoogleMap.setMapType(getMapType(settings.getMapType()));

        if (settings.isMapFragInMain()) {
            update = CameraUpdateFactory.zoomTo(0);
            mGoogleMap.moveCamera(update);
        }
        else //zoom in on a specific event
        {
            update = CameraUpdateFactory.zoomTo(6);
            mGoogleMap.moveCamera(update);

            FamilyInfo familyInfo = FamilyInfo.getInstance();
            LatLng eventLocation = familyInfo.getEventLocation(eventID);
            update = CameraUpdateFactory.newLatLng(eventLocation);
            mGoogleMap.moveCamera(update);
        }

        drawAllMarkers();

        //when a marker is clicked
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Log.e("map", "marker clicked");
                Event e = (Event) marker.getTag();
                populateEventDisplay(e);

                mMarkerClicked = true;

                Settings settings = Settings.getInstance();
                settings.setLastEventSelected(e);

                //draw the lines that are enabled
                settings = Settings.getInstance();
                if(settings.isShowLifeStoryLines())
                    drawLifeStoryLines(e.getEventID(), settings.getLifeStoryLinesColor());
                if(settings.isShowFamilyTreeLines())
                    drawFamilyTreeLines(e.getEventID(), settings.getFamilyTreeLinesColor());
                if (settings.isShowSpouseLines())
                    drawSpouseLines(e.getEventID(), settings.getSpouseLinesColor());

                return false;
            }
        });

        if (eventID != null && eventID != "") {
            if (settings.isShowLifeStoryLines())
                drawLifeStoryLines(eventID, settings.getLifeStoryLinesColor());
            else
                erasePolyLines(settings.getLifelines());

            if (settings.isShowFamilyTreeLines())
                drawFamilyTreeLines(eventID, settings.getFamilyTreeLinesColor());
            else
                erasePolyLines(settings.getFamilyTreeLines());

            if (settings.isShowSpouseLines())
                drawSpouseLines(eventID, settings.getSpouseLinesColor());
        }

    }

    int getMapType(String mapType)
    {
        switch(mapType)
        {
            case "Normal":
                return MAP_TYPE_NORMAL;
            case "Hybrid":
                return MAP_TYPE_HYBRID;
            case "Satellite":
                return MAP_TYPE_SATELLITE;
            case "Terrain":
                return MAP_TYPE_TERRAIN;
            default:
                Log.e("map type", "invalid");
                return -1;
        }
    }

    void populateEventDisplay(Event e)
    {
        if (e == null)
            Log.e("Marker event", "event is NULL");
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        Person p = familyInfo.getPersonFromEvent(e);
        if (p == null)
            Log.e("Marker event", "Person is NULL");
        personSelectedID = p.getPersonID();

        String fullName = p.getFirstName() + " " + p.getLastName();
        String eventLabel = e.getEventType() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")";
        mTextView.setText(fullName + "\n" + eventLabel);

        Drawable genderIcon;
        if (p.getGender().toLowerCase().equals("male") || p.getGender().toLowerCase().equals("m"))
            genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
                    colorRes(R.color.BLUE).sizeDp(40);
        else
            genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).
                    colorRes(R.color.PINK).sizeDp(40);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(genderIcon, null, null, null);
    }

    void drawAllMarkers()
    {
        Settings settings = Settings.getInstance();
        FamilyInfo familyInfo = FamilyInfo.getInstance();

        //remove now filtered event markers
        Set<Marker> markers = settings.getMarkers();
        for (Marker m : markers)
        {
            Event event = (Event) m.getTag();
            if (!familyInfo.passesAllFilters(event))
            {
                m.remove();
            }
        }
        markers.clear();
        Event[] events = familyInfo.getFilteredEvents();
        for (Event e : events)
        {
            LatLng latLng = new LatLng(Double.parseDouble(e.getLatitude()), Double.parseDouble(e.getLongitude()));
            float color = getMarkerColor(e);
            String title = e.getCity() + ", " + e.getCountry();
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(title).icon(defaultMarker(color));

            Marker marker = mGoogleMap.addMarker(markerOptions);
            marker.setTag(e); //associate the event with the marker
            settings.addMarker(marker);

            if (e.getEventID().equals(eventID)) {
                marker.showInfoWindow();

            }
        }
        //remove unused markers

//        List<Marker> toRemove = new ArrayList<>();
//        for (Marker m : markers)
//        {
//            Event event = (Event) m.getTag();
//            if (!familyInfo.passesAllFilters(event))
//            {
//                m.remove();
//            }
//        }
//        for (Marker m : toRemove)
//        {
//            markers.remove(m);
//        }
    }

    //returns the appropriate color for the event marker
    float getMarkerColor(Event e)
    {
        Map<String, Float> eventColors = Settings.getEventColors();
        if (!eventColors.containsKey(e.getEventType()))
        {
            int numColorsInMap = eventColors.size();
            eventColors.put(e.getEventType(), colorChoices[numColorsInMap]);
        }

        return eventColors.get(e.getEventType());
    }

    void drawLifeStoryLines(String eventID, int color)
    {
        Settings settings = Settings.getInstance();
        erasePolyLines(settings.getLifelines()); //erase existing life story lines
        //get all of the person's life events
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        Event eventSelected = familyInfo.getEvent(eventID);
        Person person = familyInfo.getPersonFromEvent(eventSelected);
        Event[] events = familyInfo.getEventsOfPerson(person.getPersonID());
        List<LatLng> eventLocations = new ArrayList<>();
        for (Event e : events)
        {
            eventLocations.add(new LatLng(Double.parseDouble(e.getLatitude()), Double.parseDouble(e.getLongitude())));
        }
        for (int i = 0; i < eventLocations.size() - 1; i++)
        {
            drawLine(eventLocations.get(i), eventLocations.get(i+1), 20, color, settings.getLifelines());
        }
    }

    void drawFamilyTreeLines(String eventId, int color)
    {
        Settings settings = Settings.getInstance();
        erasePolyLines(settings.getFamilyTreeLines()); //erase existing family tree lines
        //get all of the person's life events
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        Event eventSelected = familyInfo.getEvent(eventId);
        Person person = familyInfo.getPersonFromEvent(eventSelected);
        familyTreeRecurse(person, eventSelected, 20);
        //selected event is first point
        //find father's earliest event
            //if not null, that's the secondpoint on father side
            //recursively find his parents earliest events
    }

    void drawSpouseLines(String eventId, int color)
    {
        Settings settings = Settings.getInstance();
        erasePolyLines(settings.getSpouseLines()); //erase existing spouse lines
        //get earliest life event for spouse
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        Event eventSelected = familyInfo.getEvent(eventId);
        Person person = familyInfo.getPersonFromEvent(eventSelected);
        if (person.getSpouse() != null && !person.getSpouse().equals(""))
        {
            Person spouse = familyInfo.getPersonFromID(person.getSpouse());
            Event[] spouseEvents = familyInfo.getEventsOfPerson(spouse.getPersonID());
            if (spouseEvents != null && spouseEvents.length > 0 &&spouseEvents[0] != null)
            {
                LatLng spouseLocation = new LatLng(Double.parseDouble(spouseEvents[0].getLatitude()), Double.parseDouble(spouseEvents[0].getLongitude()));
                LatLng personLocation = new LatLng(Double.parseDouble(eventSelected.getLatitude()), Double.parseDouble(eventSelected.getLongitude()));
                drawLine(personLocation, spouseLocation, 20, color, settings.getSpouseLines());
            }
        }
    }


    void familyTreeRecurse(Person curPerson, Event curEvent, int width)
    {
        LatLng curPosition = new LatLng(Double.parseDouble(curEvent.getLatitude()), Double.parseDouble(curEvent.getLongitude()));
        Settings settings = Settings.getInstance();
        int lineColor = settings.getFamilyTreeLinesColor();

        FamilyInfo familyInfo = FamilyInfo.getInstance();

        //find father
        Person father = null;
        if (curPerson.getFather() != null)
            father = familyInfo.getPersonFromID(curPerson.getFather());
        Event[] fatherEvents = null;
        if (father != null)
            fatherEvents = familyInfo.getEventsOfPerson(father.getPersonID());

        if (fatherEvents != null && fatherEvents.length > 0 && fatherEvents[0] != null) {
            LatLng fatherPos = new LatLng(Double.parseDouble(fatherEvents[0].getLatitude()), Double.parseDouble(fatherEvents[0].getLongitude()));
            drawLine(curPosition, fatherPos, width, lineColor, settings.getFamilyTreeLines()); //draw line connecting current person and their father
        }

        //find mother
        Person mother = null;
        if (curPerson.getMother() != null)
            mother = familyInfo.getPersonFromID(curPerson.getMother());
        Event[] motherEvents = null;
        if (mother != null)
            motherEvents = familyInfo.getEventsOfPerson(mother.getPersonID());

        if (motherEvents != null && motherEvents.length > 0 && motherEvents[0] != null)
        {
            LatLng motherPos = new LatLng(Double.parseDouble(motherEvents[0].getLatitude()), Double.parseDouble(motherEvents[0].getLongitude()));
            drawLine(curPosition, motherPos, width, lineColor, settings.getFamilyTreeLines()); //draw line connecting current person and their mother
        }

        //recurse for parents
        int nextWidth = (width / 2 > 0) ? (width / 2) : 1;
        if (father != null && fatherEvents != null && fatherEvents.length > 0 && fatherEvents[0] != null)
            familyTreeRecurse(father, fatherEvents[0], nextWidth);
        if (mother != null && motherEvents != null && motherEvents.length > 0 && motherEvents[0] != null)
            familyTreeRecurse(mother, motherEvents[0], nextWidth);
    }


    void drawLine(LatLng pos1, LatLng pos2, float width, int color, Set<Polyline> existingLines)
    {
        existingLines.add(mGoogleMap.addPolyline(new PolylineOptions()
            .add(pos1, pos2)
            .width(width)
            .color(ContextCompat.getColor(getActivity(), color))));
    }

    void erasePolyLines(Set<Polyline> existingLines)
    {
        for (Polyline line : existingLines)
        {
            line.remove();
        }
        existingLines.clear();
    }


    //Life Cycle Stuff

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                initMap();
            }
        });
        //draw the lines that are enabled
        Settings settings = Settings.getInstance();
        Event e = null;
        if (settings.isMapFragInMain())
            e = settings.getLastEventSelected();
        if (e != null) {
            populateEventDisplay(e);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMarkerClicked) { //if the text view is pressed and a marker has been selected, open the person activity for that person
                        Intent intent = new Intent(getActivity(), PersonActivity.class);
                        intent.putExtra("personID", personSelectedID);
                        startActivity(intent);
                    }
                }
            });
            eventID = e.getEventID();
            if (mGoogleMap != null) {
                if (settings.isShowLifeStoryLines())
                    drawLifeStoryLines(eventID, settings.getLifeStoryLinesColor());
                else
                    erasePolyLines(settings.getLifelines());

                if (settings.isShowFamilyTreeLines())
                    drawFamilyTreeLines(eventID, settings.getFamilyTreeLinesColor());
                else
                    erasePolyLines(settings.getFamilyTreeLines());

                if (settings.isShowSpouseLines())
                    drawSpouseLines(eventID, settings.getSpouseLinesColor());
                else
                    erasePolyLines(settings.getSpouseLines());
            }
        }




    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
