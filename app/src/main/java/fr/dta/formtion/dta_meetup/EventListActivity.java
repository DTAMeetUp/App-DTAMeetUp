package fr.dta.formtion.dta_meetup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import fr.dta.formtion.dta_meetup.database.Event;
import fr.dta.formtion.dta_meetup.eventlistfragments.EventListFragment;

public class EventListActivity extends AppCompatActivity implements EventListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        EventListFragment firstFragment = new EventListFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();


/*        //test firebase
        Event newEvent = new Event(42,"Domptage de poneys", "St Herblain", "Activité sportive", "blabla", "poney.jpg",
                new Date(), new Date(), new Date(), 5, "fakeId");
        newEvent.save();*/



    }

    @Override
    public void onListFragmentInteraction(int id) {

    }
}
