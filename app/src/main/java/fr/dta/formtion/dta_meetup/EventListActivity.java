package fr.dta.formtion.dta_meetup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.dta.formtion.dta_meetup.eventlistfragments.EventListFragment;

public class EventListActivity extends AppCompatActivity implements EventListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        EventListFragment firstFragment = new EventListFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
    }

    @Override
    public void onListFragmentInteraction(int id) {

    }
}
