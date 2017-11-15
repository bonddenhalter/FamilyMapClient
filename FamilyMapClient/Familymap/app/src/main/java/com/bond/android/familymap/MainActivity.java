package com.bond.android.familymap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_frag_container);

        if (fragment == null)
        {
            fragment = new LoginFragment(); //for now I'm just going to start with the login fragment
            fm.beginTransaction()
                    .add(R.id.main_frag_container, fragment)
                    .commit();
        }

    }
}
