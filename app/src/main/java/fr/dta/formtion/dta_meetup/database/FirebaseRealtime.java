package fr.dta.formtion.dta_meetup.database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Arnaud on 01/11/2017.
 */

public class FirebaseRealtime {


    public static String saveEvent(Event event) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String eid = database.child("events").push().getKey();
        event.setId(eid);
        database.child("events").child(eid).setValue(event);
        return eid;
    }

}
