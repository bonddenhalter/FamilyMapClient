package com.bond.android.familymap;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bond.android.familymap.model.FamilyInfo;
import com.bond.android.familymap.model.Settings;
import com.bond.android.familymap.model.UserInfo;
import com.bond.android.familymap.results.EventsResult;
import com.bond.android.familymap.results.PeopleResult;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    private Switch lifeStoryLinesSwitch;
    private Switch familyTreeLinesSwitch;
    private Switch spouseLinesSwitch;
    private Spinner lifeStoryLinesSpinner;
    private Spinner familyTreeLinesSpinner;
    private Spinner spouseLinesSpinner;
    private Spinner mapTypeSpinner;
    private TextView resyncDataText;
    private TextView logoutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Settings settings = Settings.getInstance();

        lifeStoryLinesSwitch = (Switch) findViewById(R.id.life_lines_switch);
        lifeStoryLinesSwitch.setChecked(settings.isShowLifeStoryLines()); //preserve previous state
        lifeStoryLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setShowLifeStoryLines(isChecked);
            }
        });

        familyTreeLinesSwitch = (Switch) findViewById(R.id.family_tree_lines_switch);
        familyTreeLinesSwitch.setChecked(settings.isShowFamilyTreeLines());
        familyTreeLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setShowFamilyTreeLines(isChecked);
            }
        });

        spouseLinesSwitch = (Switch) findViewById(R.id.spouse_lines_switch);
        spouseLinesSwitch.setChecked(settings.isShowSpouseLines());
        spouseLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setShowSpouseLines(isChecked);
            }
        });

        lifeStoryLinesSpinner = (Spinner) findViewById(R.id.life_lines_color_spinner);
        lifeStoryLinesSpinner.setSelection(settings.getSpinnerPositionFromColor(settings.getLifeStoryLinesColor()));
        lifeStoryLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settings.setLifeStoryLinesColor(settings.getLineColorFromPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        familyTreeLinesSpinner = (Spinner) findViewById(R.id.family_tree_lines_color_spinner);
        familyTreeLinesSpinner.setSelection(settings.getSpinnerPositionFromColor(settings.getFamilyTreeLinesColor()));
        familyTreeLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settings.setFamilyTreeLinesColor(settings.getLineColorFromPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spouseLinesSpinner = (Spinner) findViewById(R.id.spouse_lines_color_spinner);
        spouseLinesSpinner.setSelection(settings.getSpinnerPositionFromColor(settings.getSpouseLinesColor()));
        spouseLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settings.setSpouseLinesColor(settings.getLineColorFromPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mapTypeSpinner = (Spinner) findViewById(R.id.map_type_spinner);
        mapTypeSpinner.setSelection(settings.getSpinnerPositionFromMapType(settings.getMapType()));
        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settings.setMapType(settings.getMapTypeFromPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        resyncDataText = (TextView) findViewById(R.id.resync_data_button);
        resyncDataText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResyncRequestTask resyncTask = new ResyncRequestTask();
                resyncTask.execute();

                //start async task
                //in async task:

                //wipe family data, reset loadSuccessful flags
                //request family data
                //check flags for success
            }
        });

        logoutText = (TextView) findViewById(R.id.logout_button);
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = UserInfo.getInstance();
                userInfo.logOut(); //delete user data
                settings.setMainLoadMapFragOnCreate(false); //go to login screen
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }
        });
    }

    private class ResyncRequestTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            FamilyInfo familyInfo = FamilyInfo.getInstance();
            familyInfo.wipeAllFamilyData();
            getFamilyInfo();

            if (familyInfo.isEventsLoadSuccessful() && familyInfo.isPeopleLoadSuccessful())
            {
                return true;
            }
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (!success)
                Toast.makeText(SettingsActivity.this, "ReSync failed", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(SettingsActivity.this, "ReSync successful", Toast.LENGTH_SHORT).show();
                Settings settings = Settings.getInstance();
                settings.setMainLoadMapFragOnCreate(true);
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }
        }
    }

    void getFamilyInfo()
    {
        Proxy proxy = Proxy.getInstance();
        EventsResult eventsResult = proxy.events();
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        if (eventsResult == null)
        {
            Log.e("events resp", "null data and msg");
            familyInfo.setEventsLoadSuccessful(false);
        }
        else if (eventsResult.getData() != null)
        {
            familyInfo.setEvents(eventsResult.getData());
            Log.i("Events request", "successful");
            familyInfo.setEventsLoadSuccessful(true);
        }
        else if (eventsResult.getMessage() != null) //there was a failure in the server
        {
            Log.e("events error", eventsResult.getMessage());
            familyInfo.setEventsLoadSuccessful(false);
        }
        else
        {
            Log.e("events resp", "null data and msg");
            familyInfo.setEventsLoadSuccessful(false);
        }

        PeopleResult peopleResult = proxy.people();
        if (peopleResult == null)
        {
            Log.e("people resp", "null data and msg");
            familyInfo.setPeopleLoadSuccessful(false);
        }
        else if (peopleResult.getData() != null)
        {
            familyInfo.setPeople(peopleResult.getData());
//                Toast.makeText(getActivity(), "People succeeded!", //TODO:TEMP!
//                        Toast.LENGTH_SHORT).show();
            Log.i("People request", "successful");
            familyInfo.setPeopleLoadSuccessful(true);
        }
        else if (peopleResult.getMessage() != null) //there was a failure in the server
        {
            Log.e("people error", peopleResult.getMessage());
            familyInfo.setPeopleLoadSuccessful(false);
        }
        else
        {
            Log.e("people resp", "null data and msg");
            familyInfo.setPeopleLoadSuccessful(false);
        }
    }

}
