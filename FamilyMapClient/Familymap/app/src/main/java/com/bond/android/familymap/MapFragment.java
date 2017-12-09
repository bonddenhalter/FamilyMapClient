package com.bond.android.familymap;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import static android.graphics.Color.BLACK;
import static com.bond.android.familymap.R.color.BLUE;
import static com.bond.android.familymap.R.color.GREEN;
import static com.bond.android.familymap.R.color.RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker;

/**
 * Created by bondd on 12/1/2017.
 */

public class MapFragment extends Fragment {

    GoogleMap mGoogleMap;
    MapView mMapView;
    TextView mTextView;
    boolean mMarkerClicked;
    String personSelectedID;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                initMap();
            }
        });

        mTextView = (TextView) v.findViewById(R.id.mapMarkerInfo);
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


        mMarkerClicked = false;

        return v;


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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

        update = CameraUpdateFactory.zoomTo(0);
        mGoogleMap.moveCamera(update);

        drawAllMarkers();

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Log.e("map", "marker clicked");
                Event e = (Event) marker.getTag();
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
                if (p.getGender().toLowerCase().equals("male"))
                    genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
                        colorRes(R.color.BLUE).sizeDp(40);
                else
                    genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).
                            colorRes(R.color.PINK).sizeDp(40);
                mTextView.setCompoundDrawablesWithIntrinsicBounds(genderIcon, null, null, null);

                mMarkerClicked = true;

                return false;
            }
        });

    }

    void drawAllMarkers()
    {
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        Event[] events = familyInfo.getEvents();
        for (Event e : events)
        {
            LatLng latLng = new LatLng(Double.parseDouble(e.getLatitude()), Double.parseDouble(e.getLongitude()));
            float color = getMarkerColor(e);
            String title = e.getCity() + ", " + e.getCountry();
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(title).icon(defaultMarker(color));

            Marker marker = mGoogleMap.addMarker(markerOptions);
            marker.setTag(e); //associate the event with the marker
        }
    }

    //returns the appropriate color for the event marker
    float getMarkerColor(Event e)
    {
        float markerColor;
        switch(e.getEventType().toLowerCase())
        {
            case "birth":
                markerColor = HUE_GREEN;
                break;
            case "marriage":
                markerColor = HUE_RED;
                break;
            case "death":
                markerColor = HUE_AZURE;
                break;
            default:
                markerColor = BLACK;
                break;
        }
        return markerColor;
    }

    //Life Cycle Stuff

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
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
