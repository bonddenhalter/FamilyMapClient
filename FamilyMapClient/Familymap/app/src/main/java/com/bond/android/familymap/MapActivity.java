package com.bond.android.familymap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        String eventID = getIntent().getExtras().getString("eventID");
        //Toast.makeText(this, eventID, Toast.LENGTH_SHORT).show();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.map_frag_container);

        if (fragment == null)
        {
            fragment = new MapFragment();

            //send event info
            Bundle bundle = new Bundle();
            bundle.putString("eventID", eventID);
            fragment.setArguments(bundle);
            fm.beginTransaction()
                    .add(R.id.map_frag_container, fragment)
                    .commit();
        }

    }
}
