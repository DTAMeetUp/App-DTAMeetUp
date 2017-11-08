package fr.dta.formtion.dta_meetup;

import android.app.Application;
import android.content.Context;

/**
 * Created by nicolasvergoz on 08/11/2017.
 */

public class MeetUp extends Application {
    private static MeetUp instance;

    public MeetUp() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
