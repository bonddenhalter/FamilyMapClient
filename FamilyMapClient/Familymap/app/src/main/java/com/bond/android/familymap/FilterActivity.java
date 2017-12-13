package com.bond.android.familymap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.bond.android.familymap.model.FamilyInfo;

public class FilterActivity extends AppCompatActivity {

    private Switch baptismSwitch;
    private Switch birthSwitch;
    private Switch censusSwitch;
    private Switch christeningSwitch;
    private Switch deathSwitch;
    private Switch marriageSwitch;
    private Switch fatherSideSwitch;
    private Switch motherSideSwitch;
    private Switch maleSwitch;
    private Switch femaleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        final FamilyInfo familyInfo = FamilyInfo.getInstance();

        baptismSwitch = (Switch) findViewById(R.id.baptism_switch);
        baptismSwitch.setChecked(familyInfo.isBaptismFilter());
        baptismSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                familyInfo.setBaptismFilter(isChecked);
            }
        });

        birthSwitch = (Switch) findViewById(R.id.birth_switch);
        birthSwitch.setChecked(familyInfo.isBirthFilter());
        birthSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                familyInfo.setBirthFilter(isChecked);
            }
        });

        censusSwitch = (Switch) findViewById(R.id.census_switch);
        censusSwitch.setChecked(familyInfo.isCensusFilter());
        censusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                familyInfo.setCensusFilter(isChecked);
            }
        });

        christeningSwitch = (Switch) findViewById(R.id.christening_switch);
        christeningSwitch.setChecked(familyInfo.isChristeningFilter());
        christeningSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                familyInfo.setChristeningFilter(isChecked);
            }
        });

        deathSwitch = (Switch) findViewById(R.id.death_switch);
        deathSwitch.setChecked(familyInfo.isDeathFilter());
        deathSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                familyInfo.setDeathFilter(isChecked);
            }
        });

        marriageSwitch = (Switch) findViewById(R.id.marriage_switch);
        marriageSwitch.setChecked(familyInfo.isMarriageFilter());
        marriageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                familyInfo.setMarriageFilter(isChecked);
            }
        });

        fatherSideSwitch = (Switch) findViewById(R.id.father_side_switch);
        fatherSideSwitch.setChecked(familyInfo.isFatherFilter());
        fatherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                familyInfo.setFatherFilter(isChecked);
            }
        });

        motherSideSwitch = (Switch) findViewById(R.id.mother_side_switch);
        motherSideSwitch.setChecked(familyInfo.isMotherFilter());
        motherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                familyInfo.setMotherFilter(isChecked);
            }
        });

        maleSwitch = (Switch) findViewById(R.id.male_switch);
        maleSwitch.setChecked(familyInfo.isMaleFilter());
        maleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                familyInfo.setMaleFilter(isChecked);
            }
        });

        femaleSwitch = (Switch) findViewById(R.id.female_switch);
        femaleSwitch.setChecked(familyInfo.isFemaleFilter());
        femaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                familyInfo.setFemaleFilter(isChecked);
            }
        });
    }
}
