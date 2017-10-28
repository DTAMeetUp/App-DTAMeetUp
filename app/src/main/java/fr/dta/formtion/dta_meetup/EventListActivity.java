package fr.dta.formtion.dta_meetup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.dta.formtion.dta_meetup.R;
import fr.dta.formtion.dta_meetup.database.local.Event;
import fr.dta.formtion.dta_meetup.database.local.EventListDAO;
import fr.dta.formtion.dta_meetup.database.local.EventListDataSource;
import fr.dta.formtion.dta_meetup.eventlistfragments.EventListFragment;

public class EventListActivity extends AppCompatActivity implements EventListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);


        EventListDataSource eventListDataSource = EventListDataSource.getInstance(this);
        EventListDAO eventListDAO = eventListDataSource.newEventListDAO();
        Event newEvent = new Event("Domptage de poneys", "Paradis", "Super Activité", "Blabal", "poney.jpg", 42);
        eventListDAO.create(newEvent);



        EventListFragment firstFragment = new EventListFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
    }

    @Override
    public void onListFragmentInteraction(int id) {

    }
}
