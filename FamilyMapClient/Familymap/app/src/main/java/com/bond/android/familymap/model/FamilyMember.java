package com.bond.android.familymap.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bondd on 12/11/2017.
 */

public class FamilyMember {
    private String fullName;
    private String relation;
    private String gender;
    private String personID;

    public FamilyMember(String fullName, String relation, String gender) {
        this.fullName = fullName;
        this.relation = relation;
        this.gender = gender;
    }

    public FamilyMember(Person p, String relation)
    {
        this.fullName = p.getFirstName() + " " + p.getLastName();
        this.relation = relation;
        this.gender = p.getGender();
        this.personID = p.getPersonID();
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

}
