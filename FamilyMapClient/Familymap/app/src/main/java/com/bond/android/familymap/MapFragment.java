package com.bond.android.familymap;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bond.android.familymap.model.Event;
import com.bond.android.familymap.model.FamilyInfo;
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



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        return v;
    }

    void initMap()
    {
        LatLng centerInit = new LatLng(0, 0);
        CameraUpdate update = CameraUpdateFactory.newLatLng(centerInit);
        mGoogleMap.moveCamera(update);

        update = CameraUpdateFactory.zoomTo(0);
        mGoogleMap.moveCamera(update);

        drawAllMarkers();

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
