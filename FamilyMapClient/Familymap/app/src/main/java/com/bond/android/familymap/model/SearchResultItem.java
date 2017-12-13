package com.bond.android.familymap.model;

import android.graphics.drawable.Drawable;

/**
 * Created by bondd on 12/13/2017.
 */

public class SearchResultItem {

    private Drawable icon;
    private String info;
    private Person person;
    private Event event;

    public SearchResultItem(Drawable icon, String info, Person person) {
        this.icon = icon;
        this.info = info;
        this.person = person;
        event = null;
    }

    public SearchResultItem(Drawable icon, String info, Event event) {
        this.icon = icon;
        this.info = info;
        this.event = event;
        person = null;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
