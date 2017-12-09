package com.bond.android.familymap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_frag_container);

        if (fragment == null)
        {
            fragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.main_frag_container, fragment)
                    .commit();
        }

        Iconify.with(new FontAwesomeModule());

    }
    private GoogleMap map;

    protected void switchToMapFragment()
    {
        Fragment mapFrag = new MapFragment();
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_frag_container, mapFrag)
                .commit();
    }


}
