package fr.dta.formtion.dta_meetup;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

import fr.dta.formtion.dta_meetup.database.Event;
import fr.dta.formtion.dta_meetup.eventlistfragments.EventDetailsFragment;
import fr.dta.formtion.dta_meetup.eventlistfragments.EventListFragment;

public class EventListActivity extends AppCompatActivity implements EventListFragment.OnListFragmentInteractionListener, EventDetailsFragment.OnFragmentInteractionListener {

    private FloatingActionButton addEventButton;
    private Toolbar myToolbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        mAuth = FirebaseAuth.getInstance();

        myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);


        EventListFragment firstFragment = new EventListFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();

        this.addEventButton = (FloatingActionButton) findViewById(R.id.addEventFAB);

        this.addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventListActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Event myEvent) {
        EventDetailsFragment detailsFragment = EventDetailsFragment.newInstance(myEvent);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, detailsFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_disconnect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_disconnect:
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                Intent backToLogin = new Intent(EventListActivity.this, LoginActivity.class);
                startActivity(backToLogin);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
