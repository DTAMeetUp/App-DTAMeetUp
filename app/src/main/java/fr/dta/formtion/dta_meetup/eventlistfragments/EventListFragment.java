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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.dta.formtion.dta_meetup.EventListActivity;
import fr.dta.formtion.dta_meetup.R;
import fr.dta.formtion.dta_meetup.database.local.Event;
import fr.dta.formtion.dta_meetup.database.local.EventListDAO;
import fr.dta.formtion.dta_meetup.database.local.EventListDataSource;

/**
 * Created by Arnaud Ringenbach on 28/10/2017.
 */

public class EventListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Event> events;
    private Context context;


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


        // Set the adapter for our recyclerview
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            fillEventList();
            recyclerView.setAdapter(new EventListRecyclerViewAdapter(events, mListener));
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
    }

    private void sortEvents(ArrayList<Event> events) {
        if(events.size()<2)
            return;
        boolean change = true;
        while(change == true) {
            change = false;
            for (int i = 0; i < events.size()-1; i++) {
                if(events.get(i).getEventDay() > events.get(i+1).getEventDay()) {
                    Collections.swap(events, i, i + 1);
                    change = true;
                }
            }
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(int id);
    }

}