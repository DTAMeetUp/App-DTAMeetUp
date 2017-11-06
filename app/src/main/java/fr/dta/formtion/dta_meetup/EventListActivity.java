package fr.dta.formtion.dta_meetup;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private String currentFragment;

    private static String LIST_FRAGMENT = "firstFragment";
    private static String DETAILS_FRAGMENT = "detailsFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        // Set Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Toolbar
        myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);


        // Set Fragment
        EventListFragment listFragment = new EventListFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, listFragment).commit();
        this.currentFragment = this.LIST_FRAGMENT;


        // Add Button
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
        this.currentFragment = this.DETAILS_FRAGMENT;


        // If current fragment is details add back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Menu
        Menu menu = myToolbar.getMenu();
        menu.findItem(R.id.action_disconnect).setVisible(false);

        // Set menu item modify/delete visible if eventAuthorId == auth
        String firebaseUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(firebaseUserID.equals( myEvent.getAuthorId() )) {
            menu.findItem(R.id.action_modify).setVisible(true);
            menu.findItem(R.id.action_delete).setVisible(true);
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        Log.d("mylog", "Toolbar Back Button");
        onBackPressed();

        return true;
    }

    @Override
    public void onBackPressed() {

        if( this.currentFragment == this.LIST_FRAGMENT) {
            super.onBackPressed();
            return;
        }

        EventListFragment listFragment = new EventListFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, listFragment).commit();
        this.currentFragment = this.LIST_FRAGMENT;

        // If current fragment is details add back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        // TODO : Change menu
        Menu menu = myToolbar.getMenu();
        menu.findItem(R.id.action_disconnect).setVisible(true);
        menu.findItem(R.id.action_modify).setVisible(false);
        menu.findItem(R.id.action_delete).setVisible(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if( this.currentFragment == this.LIST_FRAGMENT) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            menu.findItem(R.id.action_disconnect).setVisible(true);
            menu.findItem(R.id.action_modify).setVisible(false);
            menu.findItem(R.id.action_delete).setVisible(false);
        }

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

            case R.id.action_modify:
                // TODO
                return true;

            case R.id.action_delete:
                // TODO
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
