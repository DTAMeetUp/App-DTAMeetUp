package fr.dta.formtion.dta_meetup.database.local;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Arnaud Ringenbach on 28/10/2017.
 */

public class EventListDAO {
    private EventListDataSource eventListDataSource;
    private final String TABLE_NAME = "Events";
    private final String COL_ID = "id";
    private final String COL_NAME = "name";
    private final String COL_LOCATION = "location";
    private final String COL_TYPE = "type";
    private final String COL_DESCRIPTION = "description";
    private final String COL_IMAGEURL = "imageurl";
    private final String COL_DAY = "day";
    private final String COL_TIME = "time";
    private final String COL_NBINTERESTED = "nbinterested";
    private final String COL_AUTHORID = "authorid";
    private static EventListDAO eventListDAO;


    private EventListDAO(EventListDataSource eventListDataSource) {
        this.eventListDataSource = eventListDataSource;
    }

    synchronized static EventListDAO getEventListDAO(EventListDataSource eventListDataSource) {
        if (eventListDAO == null)
            eventListDAO = new EventListDAO(eventListDataSource);
        return eventListDAO;
    }

    public synchronized Event create(Event newEvent) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, newEvent.getEventName());
        values.put(COL_LOCATION, newEvent.getEventLocation());
        values.put(COL_TYPE, newEvent.getEventType());
        values.put(COL_DESCRIPTION, newEvent.getEventDescription());
        values.put(COL_IMAGEURL, newEvent.getEventImageUrl());
        values.put(COL_DAY, newEvent.getEventDay());
        values.put(COL_TIME, newEvent.getEventTime());
        values.put(COL_NBINTERESTED, newEvent.getEventNbInterested());
        values.put(COL_AUTHORID, newEvent.getEventAuthorId());

        int id = (int) eventListDataSource.getDB().insert(TABLE_NAME, null, values);
        newEvent.setEventId(id);
        return newEvent;
    }

    public synchronized Event update(Event updatedEvent) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, updatedEvent.getEventName());
        values.put(COL_LOCATION, updatedEvent.getEventLocation());
        values.put(COL_TYPE, updatedEvent.getEventType());
        values.put(COL_DESCRIPTION, updatedEvent.getEventDescription());
        values.put(COL_IMAGEURL, updatedEvent.getEventImageUrl());
        values.put(COL_DAY, updatedEvent.getEventDay());
        values.put(COL_TIME, updatedEvent.getEventTime());
        values.put(COL_NBINTERESTED, updatedEvent.getEventNbInterested());
        values.put(COL_AUTHORID, updatedEvent.getEventAuthorId());
        String clause = COL_ID + " = ?";
        String[] clauseArgs = new String[]{String.valueOf(updatedEvent.getEventId())};
        eventListDataSource.getDB().update(TABLE_NAME, values, clause, clauseArgs);
        return updatedEvent;
    }

    public synchronized void delete(int id) {
        String clause = COL_ID + " = ?";
        String[] clauseArgs = new String[]{String.valueOf(id)};
        eventListDataSource.getDB().delete(TABLE_NAME, clause, clauseArgs);
    }

    public Event read(int id) {
        Event readEvent = new Event();
        String[] allColumns = new String[]{COL_ID, COL_NAME, COL_LOCATION, COL_TYPE, COL_DESCRIPTION, COL_IMAGEURL,
                COL_DAY, COL_TIME, COL_NBINTERESTED, COL_AUTHORID};
        String clause = COL_ID + " = ?";
        String[] clauseArgs = new String[]{String.valueOf(id)};
        Cursor cursor = eventListDataSource.getDB().query(TABLE_NAME, allColumns, clause, clauseArgs, null, null, null);
        cursor.moveToFirst();
        readEvent.setEventId(id);
        readEvent.setEventName(cursor.getString(1));
        readEvent.setEventLocation(cursor.getString(2));
        readEvent.setEventType(cursor.getString(3));
        readEvent.setEventDescription(cursor.getString(4));
        readEvent.setEventImageUrl(cursor.getString(5));
        readEvent.setEventDay(cursor.getInt(6));
        readEvent.setEventTime(cursor.getInt(7));
        readEvent.setEventNbInterested(cursor.getInt(8));
        readEvent.setEventAuthorId(cursor.getInt(9));
        cursor.close();
        return readEvent;
    }

    public ArrayList<Event> readAll() {
        String[] allColumns = new String[]{COL_ID, COL_NAME, COL_LOCATION, COL_TYPE, COL_DESCRIPTION, COL_IMAGEURL,
                COL_DAY, COL_TIME, COL_NBINTERESTED, COL_AUTHORID};
        Cursor cursor = eventListDataSource.getDB().query(TABLE_NAME, allColumns, null, null, null, null, null);
        ArrayList<Event> events = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            events.add(new Event(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getInt(7),
                    cursor.getInt(8), cursor.getInt(9)));
            cursor.moveToNext();
        }
        cursor.close();
        return events;
    }
}
