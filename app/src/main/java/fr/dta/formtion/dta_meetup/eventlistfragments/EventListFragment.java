package fr.dta.formtion.dta_meetup.eventlistfragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import fr.dta.formtion.dta_meetup.R;
import fr.dta.formtion.dta_meetup.database.Event;
import fr.dta.formtion.dta_meetup.database.FirebaseRealtime;

/**
 * Created by Arnaud Ringenbach on 28/10/2017.
 */

public class EventListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Event> events = new ArrayList<>();
    private Context context;
    RecyclerView recyclerView;


    public EventListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        fillEventList();


        // Set the adapter for our recyclerview
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }


        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void fillEventList() {
        /*EventListDataSource eventListDataSource = EventListDataSource.getInstance(getActivity());
        EventListDAO eventListDAO = eventListDataSource.newEventListDAO();
        events = eventListDAO.readAll();
        sortEvents(events);*/
        // TODO fill events ArrayList with Firebase events
        //events = FirebaseRealtime.setEventListener();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Iterator<DataSnapshot> myIterator = dataSnapshot.child("events").getChildren().iterator();
                events.clear();
                while(myIterator.hasNext()) {
                    Event myEvent = dataSnapshot.child("events").child(myIterator.next().getKey()).getValue(Event.class);
                    events.add(myEvent);
                    Log.d("READ FIREBASE", myEvent.toString());
                    Log.d("READ FIREBASE", events.toString());

                }
                sortEvents(events);
                recyclerView.setAdapter(new EventListRecyclerViewAdapter(events, mListener));



                Log.d("READ FIREBASE", "Data changed");
                //Log.d("READ FIREBASE", myEvent.toString());
                //Log.d("READ FIREBASE", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("READ FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

    private void sortEvents(ArrayList<Event> events) {
        if(events.size()<2)
            return;
        boolean change = true;
        while(change == true) {
            change = false;
            for (int i = 0; i < events.size()-1; i++) {
                if(events.get(i).getDateTime() > events.get(i+1).getDateTime()) {
                    Collections.swap(events, i, i + 1);
                    change = true;
                }
            }
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Event myEvent);
    }

}