package com.bond.android.familymap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.bond.android.familymap.model.Settings;

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
        familyTreeLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setShowFamilyTreeLines(isChecked);
            }
        });

        spouseLinesSwitch = (Switch) findViewById(R.id.spouse_lines_switch);
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
                //TODO
            }
        });

        logoutText = (TextView) findViewById(R.id.logout_button);
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
    }

}
