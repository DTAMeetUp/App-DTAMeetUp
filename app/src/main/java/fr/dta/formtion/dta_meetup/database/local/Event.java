package fr.dta.formtion.dta_meetup.database.local;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by Arnaud Ringenbach on 28/10/2017.
 */

public class Event {
    private int eventId;
    private String eventName;
    private String eventLocation;
    private String eventType;
    private String eventDescription;
    private String eventImageUrl;
    private int eventDay;
    private int eventTime;
    private int eventNbInterested;
    private int eventAuthorId;

    public Event(int eventId, String eventName, String eventLocation, String eventType, String eventDescription, String eventImageUrl,
                 int eventDay, int eventTime, int eventNbInterested, int eventAuthorId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventImageUrl = eventImageUrl;
        this.eventDay = eventDay;
        this.eventTime = eventTime;
        this.eventNbInterested = eventNbInterested;
        this.eventAuthorId = eventAuthorId;
    }

    public Event(String eventName, String eventLocation, String eventType, String eventDescription, String eventImageUrl,
                 int eventDay, int eventTime, int eventNbInterested, int eventAuthorId) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventImageUrl = eventImageUrl;
        this.eventDay = eventDay;
        this.eventTime = eventTime;
        this.eventNbInterested = eventNbInterested;
        this.eventAuthorId = eventAuthorId;
    }

    public Event() {}

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public int getEventAuthorId() {
        return eventAuthorId;
    }

    public void setEventAuthorId(int eventAuthorId) {
        this.eventAuthorId = eventAuthorId;
    }

    public int getEventDay() {
        return eventDay;
    }

    public void setEventDay(int eventDay) {
        this.eventDay = eventDay;
    }

    public int getEventTime() {
        return eventTime;
    }

    public void setEventTime(int eventTime) {
        this.eventTime = eventTime;
    }

    public int getEventNbInterested() {
        return eventNbInterested;
    }

    public void setEventNbInterested(int eventNbInterested) {
        this.eventNbInterested = eventNbInterested;
    }

    public int getEventYear() {
        return eventDay/10000;
    }

    public int getEventMonth() {
        return (eventDay%10000)/100;
    }

    public int getEventDayOfMonth() {
        return eventDay%100;
    }

    public String getWeekDay() {
        String weekDays[] = {"Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam",};
        Calendar c = Calendar.getInstance();
        // !! Month is from 1 to 12, but calendar set function takes from 0 to 11
        c.set(this.getEventYear(), this.getEventMonth() - 1, this.getEventDayOfMonth());

        return weekDays[c.get(Calendar.DAY_OF_WEEK)-1];
    }

    public String getFormatedTime() {
        return eventTime/100 + ":" + eventTime%100;
    }

    public String save() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String uid = database.child("events").push().getKey();
        database.child("events").child(uid).setValue(this);

        return uid;
    }
}
