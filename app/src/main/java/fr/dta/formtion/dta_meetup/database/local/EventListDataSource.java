package fr.dta.formtion.dta_meetup.database.local;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Arnaud Ringenbach on 28/10/2017.
 */

public class EventListDataSource {
    private final EventListDBHelper helper;
    private SQLiteDatabase db;
    private static EventListDataSource eventListDataSource;

    private EventListDataSource(Context context) {
        helper = new EventListDBHelper(context);
    }

    SQLiteDatabase getDB() {
        if (db == null) open();
        return db;
    }

    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    // factories
    public EventListDAO newEventListDAO() {
        return EventListDAO.getEventListDAO(this);
    }

    public synchronized static EventListDataSource getInstance(Context context){
        if(eventListDataSource ==null){
            eventListDataSource = new EventListDataSource(context);
            eventListDataSource.open();
        }
        return eventListDataSource;
    }

}

