package fr.dta.formtion.dta_meetup.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arnaud Ringenbach on 28/10/2017.
 */

public class Event {
    private int id;
    private String title;
    private String location;
    private String category;
    private String description;
    private String imageUrl;
    private Date dateTime;
    private Date createdAt;
    private Date modifiedAt;
    private int nbInterested;
    private String authorId;

    public Event() {}

    public Event(int id, String title, String location, String category, String description, String imageUrl,
                 Date dateTime, Date createdAt, Date modifiedAt, int nbInterested, String authorId) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
        this.dateTime = dateTime;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.nbInterested = nbInterested;
        this.authorId = authorId;
    }

    public Event(String title, String location, String category, String description, String imageUrl,
                 Date dateTime, Date createdAt, Date modifiedAt, int nbInterested, String authorId) {
        this.title = title;
        this.location = location;
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
        this.dateTime = dateTime;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.nbInterested = nbInterested;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public int getNbInterested() {
        return nbInterested;
    }

    public void setNbInterested(int nbInterested) {
        this.nbInterested = nbInterested;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public int getYear() {
        return 0;
    } //TODO (or not)

    public int getMonth() {
        return 0;
    } //TODO (or not)

    public int getDayOfMonth() {
        return 0;
    } //TODO (or not)

    public String getMonthFormated() { //TODO
        String months[] = {"JAN", "FEV", "MAR", "AVR", "MAI", "JUN", "JUL", "AOU", "SEP", "OCT", "NOV", "DEC"};
        return months[this.getMonth()-1];
    }

    public String getWeekDay() { //TODO
        String weekDays[] = {"DIM", "LUN", "MAR", "MER", "JEU", "VEN", "SAM",};
        Calendar c = Calendar.getInstance();
        // !! Month is from 1 to 12, but calendar set function takes from 0 to 11
        c.set(this.getYear(), this.getMonth() - 1, this.getDayOfMonth());

        return weekDays[c.get(Calendar.DAY_OF_WEEK)-1];
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", dateTime=" + dateTime +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", eventNbInterested=" + nbInterested +
                ", eventAuthorId='" + authorId + '\'' +
                '}';
    }

    public Map<String, Object> asMap() {
        Map<String, Object> eventAsMap = new HashMap<String, Object>();
        eventAsMap.put("title", this.title);
        eventAsMap.put("location", this.location);
        eventAsMap.put("category", this.category);
        eventAsMap.put("description", this.description);
        eventAsMap.put("imageUrl", this.imageUrl);
        eventAsMap.put("dateTime", this.dateTime);
        eventAsMap.put("createdAt", this.createdAt);
        eventAsMap.put("modifiedAt", this.modifiedAt);
        eventAsMap.put("nbInterested", this.nbInterested);
        eventAsMap.put("authorId", this.authorId);
        return eventAsMap;
    }

    public String save() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String uid = database.child("events").push().getKey();
        database.child("events").child(uid).setValue(this.asMap());


        return uid;
    }
}
