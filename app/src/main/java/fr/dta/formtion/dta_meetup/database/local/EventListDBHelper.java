package fr.dta.formtion.dta_meetup.database.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arnaud Ringenbach on 28/10/2017.
 */

public class EventListDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "UserList.db";
    public static final int DB_VERSION = 1;

    public EventListDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    public static String getQueryCreate() { return "CREATE TABLE Events ("
            + "id Integer PRIMARY KEY AUTOINCREMENT, "
            + "name Text NOT NULL, "
            + "location Text NOT NULL, "
            + "type Text NOT NULL, "
            + "description Text NOT NULL,"
            + "imageurl Text NOT NULL, "
            + "authorid Integer NOT NULL "
            + ");"; }

    public static String getQueryDrop() {
        return "DROP TABLE IF EXISTS Events;";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getQueryCreate());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(getQueryDrop());
        db.execSQL(getQueryCreate());
    }
}