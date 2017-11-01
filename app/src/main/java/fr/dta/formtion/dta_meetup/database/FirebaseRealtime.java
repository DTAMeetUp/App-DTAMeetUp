package fr.dta.formtion.dta_meetup.database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arnaud on 01/11/2017.
 */

public class FirebaseRealtime {


    public static String saveEvent(Event event) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String eid = database.child("events").push().getKey();
        database.child("events").child(eid).setValue(event.asMap());
        return eid;
    }

    public static void setEventListener() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Event myEvent = dataSnapshot.getValue(Event.class);



                Log.d("READ FIREBASE", "Data changed");
                Log.d("READ FIREBASE", myEvent.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("READ FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

}
