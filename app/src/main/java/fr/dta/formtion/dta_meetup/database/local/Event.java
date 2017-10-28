package fr.dta.formtion.dta_meetup.database.local;

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
    private int eventAuthorId;

    public Event(int eventId, String eventName, String eventLocation, String eventType, String eventDescription, String eventImageUrl, int eventAuthorId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventImageUrl = eventImageUrl;
        this.eventAuthorId = eventAuthorId;
    }

    public Event(String eventName, String eventLocation, String eventType, String eventDescription, String eventImageUrl, int eventAuthorId) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventImageUrl = eventImageUrl;
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
}
